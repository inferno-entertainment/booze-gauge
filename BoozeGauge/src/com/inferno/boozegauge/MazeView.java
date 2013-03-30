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
	private class Line {
		public final float x1;
		public final float y1;
		public final float x2;
		public final float y2;
		
		public final boolean isVert;

		public Line(float x1, float y1, float x2, float y2, boolean isVert) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.isVert = isVert;
		}
	}

	private MazeGenerator mGen;
	private Maze maze;
	private Vector<Line> lines;

	private int mazeSizeX = 7, mazeSizeY = 7;
	private int height, width, lineWidth;
	private float cellHeight, cellWidth, totalCellHeight, totalCellWidth;

	private int mazeFinishX = mazeSizeX - 1, mazeFinishY = mazeSizeY - 1;
	private Paint line, red, background;

	private float xPos, yPos;
	private long start;

	// Constructor
	public MazeView(Context context) {
		super(context);
		init();
	}

	public MazeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

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

	private void initLines() {
		lines = new Vector<Line>();

		boolean[][] vLines = maze.getVLines();
		boolean[][] hLines = maze.getHLines();

		//iterate over the boolean arrays to draw walls
		for(int i = 0; i < vLines.length; i++)
			for(int j = 0; j < vLines[i].length; j++)
				if(vLines[i][j]) {
					//Line l = new Line(j*cellHeight, i*cellWidth, j*cellHeight, (i+1)*cellWidth);
					lines.add(new Line(j*cellHeight, i*cellWidth, j*cellHeight, (i+1)*cellWidth, true));
				}
		for(int i = 0; i < hLines.length; i++)
			for(int j = 0; j < hLines[i].length; j++)
				if(hLines[i][j])
					lines.add(new Line(j*cellWidth, i*cellHeight, (j+1)*cellWidth, i*cellHeight, false));
	}

	private boolean collision(float tX, float tY) {
		float radius = cellWidth / 2 - 5;

		for(Line l : lines) {
			if(Math.abs(tX - l.x1) > radius || Math.abs(tX - l.x2) > radius || Math.abs(tY - l.y1) > radius || Math.abs(tY - l.y2) > radius)
				return true;
		}

		return false;
	}

	// Called back to draw the view. Also called by invalidate().
	@Override
	protected void onDraw(Canvas canvas) {
		if(lines == null) {
			xPos = (cellWidth/2);
			yPos = (cellHeight/2);
			initLines();
		}

		canvas.drawRect(0, 0, width, height, background);

		for(Line l : lines) {
			canvas.drawLine(l.x1, l.y1, l.x2, l.y2, line);
		}

		canvas.drawCircle(xPos, yPos, cellWidth/2 - 5, red);

		canvas.drawText("F",
				(mazeFinishX * totalCellWidth)+(cellWidth*0.25f),
				(mazeFinishY * totalCellHeight)+(cellHeight*0.75f),
				red);

		invalidate();  // Force a re-draw
		
		if(xPos + (cellWidth/2) > mazeFinishX * totalCellWidth && yPos + (cellWidth/2) > mazeFinishY * totalCellHeight) {
			((LogTest)this.getContext()).calculateScore(System.currentTimeMillis() - start);
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
		float radius = cellWidth / 2 - 5;

		if(currentX >= xPos - radius && currentX <= xPos + radius)
			if(currentY >= yPos - radius && currentY <= yPos + radius) {
				//if(!collision(currentX, currentY)) {
					// Save current x, y
					xPos = currentX;
					yPos = currentY;
				//}
			}

		return true;  // Event handled
	}

}