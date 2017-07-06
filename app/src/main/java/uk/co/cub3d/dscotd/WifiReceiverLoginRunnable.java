package uk.co.cub3d.dscotd;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Callum on 14/03/2017.
 */

public class WifiReceiverLoginRunnable implements Runnable
{
	public Context context;

	public WifiReceiverLoginRunnable(Context context)
	{
		this.context = context;
	}

	@Override
	public void run()
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
