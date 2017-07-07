package uk.co.cub3d.dscotd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Callum on 08/03/2017.
 */

public class WifiReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
		if(info != null && info.isConnected())
		{

			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			final String ssid = wifiManager.getConnectionInfo().getSSID();

			Resources resources = context.getResources();

			//Home wifi ssid for testing
			final String debugWIFISSID = resources.getString(R.string.DBG_WIFI_SSID);

			//SSID of school
			final String realWIFISSID = resources.getString(R.string.REAL_WIFI_SSID);

			//Who's bright idea was it to return a string SURROUNDED BY QUOTES
			//I bet the same person decided that quotes in value xml's are stripped
			if(ssid.equals(Settings.debug ? debugWIFISSID : realWIFISSID))
			{
				//Load the settings to check if auto logging in is enabled
				Settings.loadSettings(context);

				if(Settings.shouldAutoConnect)
				{
					new Thread(new WifiReceiverLoginRunnable(context)).start();
				}
			}
		}
	}
}
