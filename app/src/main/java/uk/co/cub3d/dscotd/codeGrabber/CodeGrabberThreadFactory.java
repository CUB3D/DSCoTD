package uk.co.cub3d.dscotd.codeGrabber;

import android.content.Context;

import uk.co.cub3d.dscotd.MainActivity;
import uk.co.cub3d.dscotd.R;
import uk.co.cub3d.dscotd.Settings;
import uk.co.cub3d.dscotd.Utils;

/**
 * Created by Callum on 09/07/2017.
 */

public class CodeGrabberThreadFactory
{
	public static final int THREAD_RESPONCE_NONE = 0;
	public static final int THREAD_RESPONCE_GOOD = 1;
	public static final int THREAD_RESPONCE_ERROR_NOCODE = -1;
	public static final int THREAD_RESPONCE_ERROR_TIMEOUT = -2;
	public static final int THREAD_RESPONCE_ERROR_WRONGNETWORK = -3;

	private static CodeGrabberThreadFactory instance;
	private COTDGrabberThread thread;
	protected String code;
	protected int threadResponse = 0;


	// Check if the thread is done and update ui if it is constantly
	public void spawnPollerThread(MainActivity activity)
	{
		new Thread(new COTDGrabberThreadPollerThread(this.thread, activity)).start();
	}

	//Getters and helpers

	public boolean isCodeGrabberDone()
	{
		return this.threadResponse != THREAD_RESPONCE_NONE;
	}

	public boolean wasCodeGrabbedSuccessfully()
	{
		return this.threadResponse == THREAD_RESPONCE_GOOD;
	}

	public String getCoTD()
	{
		return this.code;
	}

	//Statics

	public static CodeGrabberThreadFactory getInstance(Context context)
	{
		if(instance == null)
		{
			instance = new CodeGrabberThreadFactory();
			instance.thread = new COTDGrabberThread(getCodeURL(context), instance);
			instance.thread.start();
		}

		return instance;
	}

	public static String getCodeURL(Context context)
	{
		if(Settings.debug)
		{
			return context.getResources().getString(R.string.DBG_COTD_PAGE_URL);
		}
		else
		{
			return context.getResources().getString(R.string.REAL_COTD_PAGE_URL);
		}
	}
}
