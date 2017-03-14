package uk.co.cub3d.dscotd;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Callum on 06/03/2017.
 */

public class Utils
{
	public static final String CODE_OF_THE_DAY_BUNDLE = "codeOfTheDay";
	public static final String OPENED_AUTO = "openedAutoOrNormal";


	private static final String WALLED_GARDEN_URL = "http://clients3.google.com/generate_204";
	private static final int WALLED_GARDEN_SOCKET_TIMEOUT_MS = 10000;

	public static void setStatus(Activity act, final String newStatus)
	{
		//Make sure to only edit components on the ui thread
		act.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				MainActivity.status.setText("Status: " + newStatus);
			}
		});
	}

	public static void setCoTD(Activity act, final String newCoTD)
	{
		MainActivity.codeOfTheDay = newCoTD;

		//Make sure to only edit components on the ui thread
		act.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				MainActivity.cotdView.setText("CoTD: " + newCoTD);
			}
		});
	}

	public static String readFileFully(Context context, int resource)
	{
		InputStream login = context.getApplicationContext().getResources().openRawResource(resource);
		BufferedReader reader = new BufferedReader(new InputStreamReader(login));

		String content = "";
		try
		{
			String line;
			while((line = reader.readLine()) != null)
			{
				content += line;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return content;
	}

	//I did not write this, it was originally part of AOSP, I got it from here: https://stackoverflow.com/a/14030276
	//Don't sue me plz
	public static boolean isWalledGardenConnection()
	{
		//If the check is off then assume always in a walled garden
		if(!Settings.walledGardenCheck)
		{
			return true;
		}
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(WALLED_GARDEN_URL); // "http://clients3.google.com/generate_204"
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setInstanceFollowRedirects(false);
			urlConnection.setConnectTimeout(WALLED_GARDEN_SOCKET_TIMEOUT_MS);
			urlConnection.setReadTimeout(WALLED_GARDEN_SOCKET_TIMEOUT_MS);
			urlConnection.setUseCaches(false);
			urlConnection.getInputStream();
			// We got a valid response, but not from the real google
			return urlConnection.getResponseCode() != 204;
		} catch (IOException e) {
				System.out.println("Walled garden check - probably not a portal: exception " + e);
			return false;
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}
}
