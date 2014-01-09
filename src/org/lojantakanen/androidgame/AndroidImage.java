package org.lojantakanen.androidgame;

import org.lojantakanen.gamelib.Image;
import org.lojantakanen.gamelib.Graphics.ImageFormat;
import android.graphics.Bitmap;

public class AndroidImage implements Image
{
	Bitmap bitmap;
	ImageFormat imageFormat;

	public AndroidImage(Bitmap bitmap, ImageFormat imageFormat) 
	{
		this.bitmap = bitmap;
		this.imageFormat = imageFormat;
	}
	
	@Override
	public int getHeight()
	{
		return bitmap.getHeight();
	}

	@Override
	public ImageFormat getFormat()
	{
		return this.imageFormat;
	}

	@Override
	public int getWidth()
	{
		return bitmap.getWidth();
	}

	@Override
	public void dispose()
	{
		bitmap.recycle();
	}	
}
