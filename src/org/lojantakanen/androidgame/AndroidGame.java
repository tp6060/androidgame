package org.lojantakanen.androidgame;

import android.app.Activity;
import org.lojantakanen.gamelib.*;
import android.os.*;
import android.view.*;
import android.content.res.*;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.app.*;
import android.os.PowerManager.WakeLock;
import org.lojantakanen.androidgame.AndroidFastRendererView;

public abstract class AndroidGame extends Activity implements Game
{
	AndroidFastRendererView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
		int frameBufferWidth = isPortrait ? 800 : 1280;
		int frameBufferHeight = isPortrait ? 1200 : 800;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
		float scaleX = (float)frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = (float)frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();
		renderView = new AndroidFastRendererView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getInitScreen();
		setContentView(renderView);
	
		PowerManager powerManager = (PowerManager)getSystemService(Service.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(powerManager.FULL_WAKE_LOCK, "GGGGame");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();
		if (isFinishing()) {
			screen.dispose();
		}
	}
	@Override
	public Input getInput() {
		return input;	
	}
	@Override
	public FileIO getFileIO() {
		return fileIO;
	}
	@Override
	public Graphics getGraphics() {
		return graphics;
	}
	@Override
	public Audio getAudio() {
		return audio;
	}
	@Override
	public void setScreen(Screen screen) {
		if (screen == null) {
			throw new IllegalArgumentException("Screen must not be null");
		}
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	@Override
	public Screen getCurrentScreen()
	{
		// TODO: Implement this method
		return screen;
	}	
}
