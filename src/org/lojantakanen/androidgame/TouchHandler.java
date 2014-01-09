package org.lojantakanen.androidgame;
import android.view.View.OnTouchListener;
import java.util.List;
import org.lojantakanen.gamelib.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener
{
	public boolean isTouchDown(int pointer);
	public int getTouchX(int pointer);
	public int getTouchY(int pointer);
	public List<TouchEvent> getTouchEvents();
}
