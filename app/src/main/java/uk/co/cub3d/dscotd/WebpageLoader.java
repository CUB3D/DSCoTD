package uk.co.cub3d.dscotd;

import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Callum on 09/03/2017.
 */

public class WebpageLoader implements Runnable
{
	public MainActivity activity;

	public WebpageLoader(MainActivity mainActivity)
	{
		this.activity = mainActivity;
	}

	@Override
	public void run()
	{
		long startTime = System.currentTimeMillis();

		//Wait until the CoTD has been grabbed
		while(true)
		{
			//CoTD has been grabbed when this variable has been set
			if(MainActivity.codeOfTheDay.length() == 8)
			{
				break;
			}

			// Close this thread and error out if it has been running for more than 30 seconds
			if(System.currentTimeMillis() - startTime > 30 * 1000)
			{
				Toast.makeText(activity, "Error: Did not grab CoTD in time", Toast.LENGTH_SHORT).show();
				return;
			}
		}

		Intent i = new Intent(activity, LoginPageView.class);
		i.putExtra(Utils.CODE_OF_THE_DAY_BUNDLE, MainActivity.codeOfTheDay);
		activity.startActivityForResult(i, LoginPageView.REQUEST_STANDARD);
	}
}
