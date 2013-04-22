package com.inferno.boozegauge;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * This View gives a visual representation of a players
 * performance during the Balance test. The square moves
 * faster when the player is doing worse.
 */
public class BalanceWidget extends View {
	private RectF rect;
	private float targetx = 0f;
	private float targety = 0f;
	private float currentx = 0f;
	private float currenty = 0f;
	private float centerx = 0f;
	private float centery = 0f;
	private float bound = 0f;
	private final static float width = 20f;
	private final static float height = 20f;
	private Paint paint = new Paint();
	private Random rand = new Random();

	public BalanceWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setARGB(255, 0, 50, 200);
		paint.setStyle(Paint.Style.FILL);
	}
	
	public BalanceWidget(Context context, AttributeSet attrs, int defstyle) {
		super(context, attrs, defstyle);
		paint.setARGB(255, 0, 50, 200);
		paint.setStyle(Paint.Style.FILL);
	}

	protected void onDraw(Canvas canvas) {
		canvas.drawRect(rect, paint);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		targetx = currentx = centerx = w/2;
		targety = currenty = centery = h/2;
		bound = Math.min(w, h) / 2;
		rect = new RectF(currentx - width, currenty - height, currentx + width, currenty + height);
	}
	
	/**
	 * Called to update the position of the square. Should be called at
	 * regular intervals.
	 *
	 * @param instantaneous_score used to calculate how fast to move square
	 */
	public void step(float instantaneous_score) {
		float diffx = targetx - currentx;
		float diffy = targety - currenty;
		float diffmag = (float)Math.sqrt(diffx*diffx + diffy*diffy);
		float velocity = instantaneous_score * instantaneous_score * 2.0f;
		if (diffmag != 0) {
			currentx += (diffx/diffmag) * velocity;
			currenty += (diffy/diffmag) * velocity;
		}
		rect.offsetTo(currentx, currenty);
		if (diffmag < velocity) {
			targetx = centerx + (rand.nextFloat()-.5f) * bound;
			targety = centery + (rand.nextFloat()-.5f) * bound;
		}
		invalidate();
	}
}