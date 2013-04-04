package com.inferno.boozegauge;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;

public class MazeView extends View {
	/*
	 * Android does not have it's own line object
	 * This is a simple representation of a line 
	 * segment
	 */
	private class Line {
		public final float x1;
		public final float y1;
		public final float x2;
		public final float y2;

		public Line(float x1, float y1, float x2, float y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}

	private MazeGenerator mGen;  //intermediate maze generator
	private Maze maze;  //usable representation of the maze
	private Vector<Line> lines;  //contains all the lines in the maze with all their calculated, concrete locations

	private final int mazeSizeX = 7, mazeSizeY = 7;  //cell dimension of the maze
	private int height, width, lineWidth;  //height and width of the view, width of a line
	private float cellHeight, cellWidth, totalCellHeight, totalCellWidth;  //size of a cell in the maze, size of all the cells

	private int mazeFinishX = mazeSizeX - 1, mazeFinishY = mazeSizeY - 1;  //sets the maze's exit to the lower right corner
	private Paint line, red, background;  //colors for the various objects in the view

	float radius;  //radius of the player's ball
	private float xPos, yPos;  //location of the ball's center
	private long start;  //start time for the maze

	// Constructor
	public MazeView(Context context) {
		super(context);
		init();
	}

	public MazeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	/*
	 * initializes all of the fields that must be allocated/created
	 */
	private void init() {
		mGen = new MazeGenerator(mazeSizeX, mazeSizeY);
		maze = mGen.display();

		lines = null;

		line = new Paint();
		line.setColor(getResources().getColor(R.color.line));
		red = new Paint();
		red.setColor(getResources().getColor(R.color.position));
		background = new Paint();
		background.setColor(getResources().getColor(R.color.game_bg));
		setFocusable(true);
		this.setFocusableInTouchMode(true);

		start = System.currentTimeMillis();
	}
	
	/*
	 * uses the Maze object to calculate the physical coordinates of each line
	 */
	private void initLines() {
		lines = new Vector<Line>();

		boolean[][] vLines = maze.getVLines();
		boolean[][] hLines = maze.getHLines();

		//iterate over the boolean arrays to draw walls
		for(int i = 0; i < vLines.length; i++)
			for(int j = 0; j < vLines[i].length; j++)
				if(vLines[i][j]) {
					//Line l = new Line(j*cellHeight, i*cellWidth, j*cellHeight, (i+1)*cellWidth);
					lines.add(new Line(j*cellHeight, i*cellWidth, j*cellHeight, (i+1)*cellWidth));
				}
		for(int i = 0; i < hLines.length; i++)
			for(int j = 0; j < hLines[i].length; j++)
				if(hLines[i][j])
					lines.add(new Line(j*cellWidth, i*cellHeight, (j+1)*cellWidth, i*cellHeight));
	}
	
	/*
	 * makes sure the ball's next position (tX, tY) does not collide with a line
	 * 
	 * explanation of the algorithm can be found at http://math.stackexchange.com/a/2844
	 */
	private boolean collision(float tX, float tY) {
		for(Line l : lines) {
			//d is the minimum distance of the ball from the line containing l
			//if d is greater than the radius, there can be no collision
			//if d is less than the radius, the ball may intersect with the line segment l
			double d = Math.abs((l.x1 - l.x2) * (tX - l.x1)+ (l.y2 - l.y1) * (tY - l.y1)) / Math.sqrt(Math.pow(l.x1 - l.x2, 2) + Math.pow(l.y1 - l.y2, 2));
			if(d <= radius)
			{
				double c1 = (l.x1 * l.x2) - (l.x1 * tX) + (l.x2 * tX) - Math.pow(l.x2, 2) + (l.y1 * l.y2) - (l.y1 * tY) + (l.y2 * tY) - Math.pow(l.y2, 2);
				double c2 = (l.x1 * l.x2) + (l.x1 * tX) - (l.x2 * tX) - Math.pow(l.x1, 2) + (l.y1 * l.y2) + (l.y1 * tY) - (l.y1 * tY) - Math.pow(l.y2, 2);
				if(c1 >= 0 && c2 >= 0)
					return true;

				double dPrime = Math.min(Math.sqrt(Math.pow(l.x1 - tX, 2) + Math.pow(l.y1 - tY, 2)), Math.sqrt(Math.pow(l.x2 - tX, 2) + Math.pow(l.y2 - tY, 2)));
				if(dPrime <= radius)
					return true;
			}
		}

		return false;
	}

	// Called back to draw the view. Also called by invalidate().
	@Override
	protected void onDraw(Canvas canvas) {
		//the lines vector cannot be initialized until the size of the canvas has been determined
		if(lines == null) {
			xPos = (cellWidth/2);
			yPos = (cellHeight/2);
			radius = cellWidth / 3;
			initLines();
		}

		canvas.drawRect(0, 0, width, height, background);

		for(Line l : lines) {
			canvas.drawLine(l.x1, l.y1, l.x2, l.y2, line);
		}

		canvas.drawCircle(xPos, yPos, radius, red);

		canvas.drawText("F",
				(mazeFinishX * totalCellWidth)+(cellWidth*0.25f),
				(mazeFinishY * totalCellHeight)+(cellHeight*0.75f),
				red);

		invalidate();  // Force a re-draw

		if(xPos + (cellWidth/2) > mazeFinishX * totalCellWidth && yPos + (cellWidth/2) > mazeFinishY * totalCellHeight) {
			((LogTest)this.getContext()).collectScore(System.currentTimeMillis() - start);
		}
	}


	// Called back when the view is first created or its size changes.
	@Override
	public void onSizeChanged(int w, int h, int oldW, int oldH) {
		width = (w < h)?w:h;
		height = width;         //for now square mazes
		lineWidth = 1;          //for now 1 pixel wide walls
		cellWidth = (width - ((float)mazeSizeX*lineWidth)) / mazeSizeX;
		totalCellWidth = cellWidth+lineWidth;
		cellHeight = (height - ((float)mazeSizeY*lineWidth)) / mazeSizeY;
		totalCellHeight = cellHeight+lineWidth;
		red.setTextSize(cellHeight*0.75f);
		super.onSizeChanged(w, h, oldW, oldH);
	}

	// Touch-input handler
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float currentX = event.getX();
		float currentY = event.getY();

		if(currentX >= xPos - radius && currentX <= xPos + radius)
			if(currentY >= yPos - radius && currentY <= yPos + radius) {
				if(!collision(currentX, currentY)) {
					// Save current x, y
					xPos = currentX;
					yPos = currentY;
				}
			}

		return true;  // Event handled
	}

}