package org.lojantakanen.androidgame;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Canvas;

public class AndroidFastRendererView extends SurfaceView implements Runnable
{
	AndroidGame game;
	Bitmap frameBuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;
	
	public AndroidFastRendererView(AndroidGame game, Bitmap framebuffer) {
		super(game);
		this.game = game;
		this.frameBuffer = framebuffer;
		this.holder = getHolder();
	}
	public void resume()
	{
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	@Override
	public void run()
	{
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while (running){
			if (!holder.getSurface().isValid()) {
				continue;
			}
			float deltaTime = (System.nanoTime() - startTime)/10000000.0f;
			startTime = System.nanoTime();
			if (deltaTime > 3.15) {
				deltaTime = (float)3.15;
			}
			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().paint(deltaTime);
			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(frameBuffer,null,dstRect,null);
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	public void pause()
	{
		running = false;
		while (true) {
			try {
				renderThread.join();
				break;
			}
			catch (InterruptedException e) {
				
			}
		}
	}	
}
