package com.inferno.boozegauge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;
   
public class GameView extends View {
   private int xMax;
   private int yMax;
   private float ballRadius = 40; // Ball's radius
   private float ballX = ballRadius;  // Ball's center (x,y)
   private float ballY = ballRadius;
   private RectF ballBounds;      // Needed for Canvas.drawOval
   private Paint paint;           // The paint (e.g. style, color) used for drawing
     
   // Constructor
   public GameView(Context context) {
      super(context);
      ballBounds = new RectF();
      paint = new Paint();
      this.setFocusableInTouchMode(true);
      ballX += this.getLeft();
      ballY += this.getTop();
   }
   
   public GameView(Context context, AttributeSet attrs) {
	  super(context, attrs);
      ballBounds = new RectF();
      paint = new Paint();
      this.setFocusableInTouchMode(true);
      ballX += this.getLeft();
      ballY += this.getTop();
   }
  
   // Called back to draw the view. Also called by invalidate().
   @Override
   protected void onDraw(Canvas canvas) {
      // Draw the ball
      ballBounds.set(ballX-ballRadius, ballY-ballRadius, ballX+ballRadius, ballY+ballRadius);
      paint.setColor(Color.GREEN);
      canvas.drawOval(ballBounds, paint);
        
      // Update the position of the ball, including collision detection and reaction.
      //update();
  
      // Delay
      try {  
         Thread.sleep(30);  
      } catch (InterruptedException e) { }
      
      invalidate();  // Force a re-draw
   }
   
   // Detect collision and update the position of the ball.
   /*private void update() {
      // Get new (x,y) position
      ballX += ballSpeedX;
      ballY += ballSpeedY;
      // Detect collision and react
      if (ballX + ballRadius > xMax) {
         ballSpeedX = -ballSpeedX;
         ballX = xMax-ballRadius;
      } else if (ballX - ballRadius < xMin) {
         ballSpeedX = -ballSpeedX;
         ballX = xMin+ballRadius;
      }
      if (ballY + ballRadius > yMax) {
         ballSpeedY = -ballSpeedY;
         ballY = yMax - ballRadius;
      } else if (ballY - ballRadius < yMin) {
         ballSpeedY = -ballSpeedY;
         ballY = yMin + ballRadius;
      }
   }*/
   
   // Called back when the view is first created or its size changes.
   @Override
   public void onSizeChanged(int w, int h, int oldW, int oldH) {
      // Set the movement bounds for the ball
      xMax = w-1;
      yMax = h-1;
   }
   
   // Touch-input handler
   @Override
   public boolean onTouchEvent(MotionEvent event) {
      float currentX = event.getX();
      float currentY = event.getY();
      
      if(currentX >= ballX - ballRadius && currentX <= ballX + ballRadius)
    	  if(currentY >= ballY - ballRadius && currentY <= ballY + ballRadius) {
    		// Save current x, y
    	      ballX = currentX;
    	      ballY = currentY;
    	  }
      
      return true;  // Event handled
   }
}