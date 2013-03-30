package com.inferno.boozegauge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;

public class MazeView extends View {
	private MazeGenerator mGen;
	private Maze maze;

	private int mazeSizeX = 7, mazeSizeY = 7;
	private int height, width, lineWidth;
	private float cellHeight, cellWidth, totalCellHeight, totalCellWidth;

	private int mazeFinishX = mazeSizeX - 1, mazeFinishY = mazeSizeY - 1;
	private Paint line, red, background;

	private int xPos, yPos;	

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

		line = new Paint();
		line.setColor(getResources().getColor(R.color.line));
		red = new Paint();
		red.setColor(getResources().getColor(R.color.position));
		background = new Paint();
		background.setColor(getResources().getColor(R.color.game_bg));
		setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

	// Called back to draw the view. Also called by invalidate().
	@Override
	protected void onDraw(Canvas canvas) {
		boolean[][] vLines = maze.getVLines();
		boolean[][] hLines = maze.getHLines();

		canvas.drawRect(0, 0, width, height, background);

		//iterate over the boolean arrays to draw walls
		for(int i = 0; i < vLines.length; i++)
			for(int j = 0; j < vLines[i].length; j++)
				if(vLines[i][j])
					canvas.drawLine(j*cellHeight, i*cellWidth, j*cellHeight, (i+1)*cellWidth, line);
		for(int i = 0; i < hLines.length; i++)
			for(int j = 0; j < hLines[i].length; j++)
				if(hLines[i][j])
					canvas.drawLine(j*cellWidth, i*cellHeight, (j+1)*cellWidth, i*cellHeight, line);

		canvas.drawText("F",
				(mazeFinishX * totalCellWidth)+(cellWidth*0.25f),
				(mazeFinishY * totalCellHeight)+(cellHeight*0.75f),
				red);
		
		invalidate();  // Force a re-draw
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
	/*@Override
	public boolean onTouchEvent(MotionEvent event) {
		float currentX = event.getX();
		float currentY = event.getY();

		if(currentX >= xMin + ballRadius && currentX <= xMax - ballRadius)
			if(currentY >= yMin + ballRadius && currentY <= yMax - ballRadius) {
				// Save current x, y
				ballX = currentX;
				ballY = currentY;
			}

		return true;  // Event handled
	}*/
}