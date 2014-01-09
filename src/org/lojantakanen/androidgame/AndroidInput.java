package org.lojantakanen.androidgame;
import org.lojantakanen.gamelib.Input;
import android.view.View;
import android.content.Context;
import junit.runner.*;
import java.util.List;
import android.os.Build.VERSION;

public class AndroidInput implements Input
{
	TouchHandler touchHandler;
	public AndroidInput(Context context,View view,
	float scaleX, float scaleY) 
	{
		if (Integer.parseInt(VERSION.SDK)<5) {
			touchHandler = new SingleTouchHandler(view, scaleX,scaleY);
		} else {
			touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
		}
	}
	@Override
	public int getTouchY(int pointer)
	{
		return touchHandler.getTouchY(pointer);
	}

	@Override
	public int getTouchX(int pointer)
	{
		return touchHandler.getTouchX(pointer);
	}

	@Override
	public boolean isTouchDown(int pointer)
	{
		return touchHandler.isTouchDown(pointer);
	}
	public List<TouchEvent> getTouchEvents()
	{
		return touchHandler.getTouchEvents();
	}
}
