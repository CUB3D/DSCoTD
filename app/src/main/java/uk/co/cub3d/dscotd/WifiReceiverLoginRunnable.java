package uk.co.cub3d.dscotd;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Callum on 14/03/2017.
 */

public class WifiReceiverLoginRunnable implements Runnable
{
	public Context context;
	public String ssid;

	public WifiReceiverLoginRunnable(Context context, String ssid)
	{
		this.context = context;
		this.ssid = ssid;
	}

	@Override
	public void run()
	{
		//Who's bright idea was it to return a string SURROUNDED BY QUOTES
		//I bet the same person decided that quotes in value xml's are stripped
		if(ssid.equals(ssid))
		{
			//Ignore walled garden check if debug mode is on
			if(Utils.isWalledGardenConnection())
			{
				Intent i = new Intent(context, MainActivity.class);
				i.putExtra(Utils.OPENED_AUTO, true);
				context.startActivity(i);
			}
		}
	}
}
