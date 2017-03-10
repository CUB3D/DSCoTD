package uk.co.cub3d.dscotd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * Created by Callum on 08/03/2017.
 */

public class WifiReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
		if(info != null && info.isConnected()) {

			WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			String ssid = wifiManager.getConnectionInfo().getSSID();

			Resources resources = context.getResources();

			//Home wifi ssid for testing
			String debugWIFISSID = resources.getString(R.string.DBG_WIFI_SSID);

			//SSID of school
			String realWIFISSID = resources.getString(R.string.REAL_WIFI_SSID);

			//Who's bright idea was it to return a string SURROUNDED BY QUOTES
			if((ssid.equals(debugWIFISSID) && Utils.DEBUG) || ssid.equals(realWIFISSID))
			{
				//Ignore walled garden check if debug mode is on
				if(Utils.isWalledGardenConnection() || Utils.DEBUG)
				{
					Toast.makeText(context, "Logging in", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(context, MainActivity.class);
					i.putExtra(Utils.OPENED_AUTO_OR_NORMAL, true);
					context.startActivity(i);
				}
			}
		}
	}
}
