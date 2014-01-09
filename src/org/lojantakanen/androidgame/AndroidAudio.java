package org.lojantakanen.androidgame;

import org.lojantakanen.gamelib.Audio;
import org.lojantakanen.gamelib.*;
import android.content.res.*;
import android.media.*;
import android.app.*;
import java.io.*;

public class AndroidAudio implements Audio
{
	AssetManager assets;
	SoundPool soundPool;
	
	public AndroidAudio(Activity activity)
	{
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	@Override
	public Sound createSound(String file)
	{
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(file);
			int soundId = soundPool.load(assetDescriptor,0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e){
			throw new RuntimeException("Could not load sound '"+file+"'");			
		}
	}

	@Override
	public Music createMusic(String file)
	{
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(file);
			return new AndroidMusic(assetDescriptor);			
		} catch (IOException e){
			throw new RuntimeException("Could not load music '"+file+"'");
		}
	}
	
}
