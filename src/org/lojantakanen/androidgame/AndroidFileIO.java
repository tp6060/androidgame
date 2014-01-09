package org.lojantakanen.androidgame;
import org.lojantakanen.gamelib.FileIO;
import android.content.res.*;
import java.io.*;
import android.content.*;
import android.os.*;
import android.preference.*;

public class AndroidFileIO implements FileIO
{
	Context context;
	AssetManager assets;
	String externalStoragePath;
	
	public AndroidFileIO(Context context)
	{
		this.context = context;
		this.assets = context.getAssets();
		this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
		
	}
	
	@Override
	public OutputStream writeFile(String file) throws IOException
	{
		return new FileOutputStream(externalStoragePath+file);
	}

	@Override
	public InputStream readFile(String file) throws IOException
	{
		return new FileInputStream(externalStoragePath+file);
	}

	@Override
	public InputStream readAsset(String file) throws IOException
	{
		return assets.open(file);
	}

	@Override
	public SharedPreferences getSharedPref()
	{
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

}
