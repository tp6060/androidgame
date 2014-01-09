package org.lojantakanen.androidgame;

import org.lojantakanen.gamelib.Music;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.content.res.*;
import java.io.*;

public class AndroidMusic implements Music,OnCompletionListener,
OnSeekCompleteListener,OnPreparedListener,OnVideoSizeChangedListener
{
	MediaPlayer mediaPlayer;
	boolean isPrepared = false;
	
	public AndroidMusic(AssetFileDescriptor assetDescriptor)
	{
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
			assetDescriptor.getStartOffset(), assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
		}
		catch (Exception e) {
			throw new RuntimeException("Could not load music");
		}
	}
	@Override
	public void dispose()
	{
		if (this.mediaPlayer.isPlaying()) {
			this.mediaPlayer.stop();
		}
		this.mediaPlayer.release();
	}

	@Override
	public void setVolume(float volume)
	{
		mediaPlayer.setVolume(volume,volume);
	}

	@Override
	public void pause()
	{
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	@Override
	public void setLooping(boolean looping)
	{
		mediaPlayer.setLooping(looping);
	}

	@Override
	public boolean isPlaying()
	{
		return mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped()
	{
		return !isPrepared;
	}

	@Override
	public boolean isLooping()
	{
		return mediaPlayer.isLooping();
	}

	@Override
	public void stop()
	{
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		synchronized(this) {
			isPrepared = false;
		}
	}

	@Override
	public void play()
	{
		if (mediaPlayer.isPlaying()) {
			return;
		}
		try {
			synchronized(this) {
				if (!isPrepared) {
					mediaPlayer.prepare();
				}
				mediaPlayer.start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void seekBegin()
	{
		mediaPlayer.seekTo(0);
	}

	@Override
	public void onPrepared(MediaPlayer p1)
	{
		synchronized(this) {
			isPrepared = true;
		}
	}

	@Override
	public void onSeekComplete(MediaPlayer p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer p1, int p2, int p3)
	{
		// TODO: Implement this method
	}

	@Override
	public void onCompletion(MediaPlayer p1)
	{
		synchronized(this) {
			isPrepared = false;
		}
	}
}
