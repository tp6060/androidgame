package org.lojantakanen.androidgame;

import org.lojantakanen.gamelib.Sound;
import android.media.*;

public class AndroidSound implements Sound
{
	int soundId;
	SoundPool SoundPool;
	
	public AndroidSound(SoundPool soundPool, int soundId)
	{
		this.SoundPool = soundPool;
		this.soundId = soundId;
	}
	
	@Override
	public void play(float volume)
	{
		
	}

	@Override
	public void dispose()
	{
		
	}
}
