package org.lojantakanen.androidgame;
import org.lojantakanen.androidgame.TouchHandler;
import org.lojantakanen.gamelib.Pool;
import java.util.*;
import android.view.*;
import org.lojantakanen.gamelib.Input.TouchEvent;
import org.lojantakanen.gamelib.Pool.PoolObjectFactory;

public class SingleTouchHandler implements TouchHandler
{
	boolean isTouched;
	int touchX;
	int touchY;
	Pool<TouchEvent> touchEventsPool;
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;
	
	public SingleTouchHandler(View view, float scaleX, float scaleY)
	{
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>()
		{
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		touchEventsPool = new Pool<TouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	@Override
	public int getTouchY(int pointer)
	{
		synchronized(this) {
			return touchY;
		}
	}

	@Override
	public int getTouchX(int pointer)
	{
		synchronized(this){
			return touchX;
		}
	}

	@Override
	public List getTouchEvents()
	{
		synchronized(this) {
			int len = touchEvents.size();
			for (int i = 0; i< len; i++) {
				touchEventsPool.free(touchEvents.get(i));
			}
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}

	@Override
	public boolean isTouchDown(int pointer)
	{
		synchronized(this) {
			if (pointer == 0) {
				return isTouched;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		synchronized(this) {
			TouchEvent touchEvent = touchEventsPool.newObject();
			switch (event.getAction()) {
				case (MotionEvent.ACTION_DOWN):
					touchEvent.type = TouchEvent.TOUCH_DOWN;
					isTouched = true;
					break;
				case (MotionEvent.ACTION_CANCEL):
				case (MotionEvent.ACTION_UP):
					touchEvent.type = TouchEvent.TOUCH_UP;
					isTouched = true;
					break;
				case (MotionEvent.ACTION_MOVE):
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					isTouched = true;
					break;
			}
			touchEvent.x = touchX = (int)(event.getX()*scaleX);
			touchEvent.y = touchY = (int)(event.getY()*scaleY);
			touchEventsBuffer.add(touchEvent);
			return true;
		}
	}
	
}
