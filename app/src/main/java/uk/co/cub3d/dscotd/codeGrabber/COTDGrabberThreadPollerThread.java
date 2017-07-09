package uk.co.cub3d.dscotd.codeGrabber;

import uk.co.cub3d.dscotd.MainActivity;
import uk.co.cub3d.dscotd.Utils;

/**
 * Created by Callum on 09/07/2017.
 */

public class COTDGrabberThreadPollerThread implements Runnable
{
	// The COTD grabber thread
	public Thread thread;
	public MainActivity activity;

	public COTDGrabberThreadPollerThread(Thread thread, MainActivity activity)
	{
		this.thread = thread;
		this.activity = activity;
	}

	@Override
	public void run()
	{
		try
		{
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		CodeGrabberThreadFactory factory = CodeGrabberThreadFactory.getInstance(null);

		if(factory.isCodeGrabberDone())
		{
			if(factory.wasCodeGrabbedSuccessfully())
			{
				Utils.setCoTD(activity, factory.code);
				Utils.setStatus(activity, "Done");
			} else if(factory.threadResponse == CodeGrabberThreadFactory.THREAD_RESPONCE_ERROR_NOCODE)
			{
				Utils.setCoTD(activity, "Error: Unable to get CoTD");
			} else if(factory.threadResponse == CodeGrabberThreadFactory.THREAD_RESPONCE_ERROR_WRONGNETWORK)
			{
				Utils.setCoTD(activity, "Are you connected to DSCoTD?");
			}
		}
		else
		{
			Utils.setStatus(activity, "Getting code");
		}
	}
}
