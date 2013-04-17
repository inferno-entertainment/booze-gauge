package com.inferno.boozegauge;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;

public class MazeView extends View {
	private MazeGenerator mGen;  //maze generator
	private Vector<RectF> lines;  //contains all the lines in the maze with all their calculated, concrete locations

	private final int mazeSizeX = 7, mazeSizeY = 7;  //cell dimension of the maze
	private int height, width, lineWidth;  //height and width of the view, width of a line
	private float cellHeight, cellWidth, totalCellHeight, totalCellWidth;  //size of a cell in the maze, size of all the cells

	private int mazeFinishX = mazeSizeX - 1, mazeFinishY = mazeSizeY - 1;  //sets the maze's exit to the lower right corner
	private Paint line, red, background;  //colors for the various objects in the view

	float radius;  //radius of the player's ball
	private float xPos, yPos;  //location of the ball's center

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

		lines = null;

		line = new Paint();
		line.setColor(getResources().getColor(R.color.line));
		red = new Paint();
		red.setColor(getResources().getColor(R.color.position));
		background = new Paint();
		background.setColor(getResources().getColor(R.color.game_bg));
		setFocusable(true);
		this.setFocusableInTouchMode(true);
	}
	
	/*
	 * uses the Maze object to calculate the physical coordinates of each line
	 */
	private void initLines() {
		lines = new Vector<RectF>();

		boolean[][] vLines = mGen.getVLines();
		boolean[][] hLines = mGen.getHLines();

		//iterate over the boolean arrays to draw walls
		for(int i = 0; i < vLines.length; i++)
			for(int j = 0; j < vLines[i].length; j++)
				if(vLines[i][j]) {
					lines.add(new RectF(j*cellHeight, i*cellWidth, j*cellHeight + 1, (i+1)*cellWidth));
				}
		for(int i = 0; i < hLines.length; i++)
			for(int j = 0; j < hLines[i].length; j++)
				if(hLines[i][j])
					lines.add(new RectF(j*cellWidth, i*cellHeight, (j+1)*cellWidth, i*cellHeight + 1));
	}
	
	/*
	 * makes sure the ball's next position (tX, tY) does not collide with a line
	 */
	private boolean collision(float tX, float tY) {
		RectF temp = new RectF(tX-radius, tY-radius, tX+radius, tY+radius);
		for(RectF l : lines) {
			if(temp.intersect(l))
				return true;
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

		for(RectF l : lines) {
			canvas.drawRect(l, line);
		}

		canvas.drawRect(xPos - radius, yPos - radius, xPos + radius, yPos + radius, red);

		canvas.drawText("F",
				(mazeFinishX * totalCellWidth)+(cellWidth*0.25f),
				(mazeFinishY * totalCellHeight)+(cellHeight*0.75f),
				red);

		invalidate();  // Force a re-draw

		if(xPos + (radius) > mazeFinishX * totalCellWidth && yPos + (radius) > mazeFinishY * totalCellHeight) {
			((SpaceTest)this.getContext()).endTest();
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
				// Save current x, y
				if(!collision(currentX, yPos))
					xPos = currentX;
				if(!collision(xPos, currentY))
					yPos = currentY;
			}

		return true;  // Event handled
	}

}