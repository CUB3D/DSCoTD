package uk.co.cub3d.dscotd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Callum on 08/03/2017.
 */

public class COTDGrabberThread extends Thread
{
	public String link = "";
	public MainActivity activity;

	public COTDGrabberThread(MainActivity activity)
	{
		this.activity = activity;

		if(Settings.debug)
		{
			link = activity.getResources().getString(R.string.DBG_COTD_PAGE_URL);
		}
		else
		{
			link = activity.getResources().getString(R.string.REAL_COTD_PAGE_URL);
		}
	}

	@Override
	public void run()
	{
		Utils.setStatus(activity, "Downloading page");
		try
		{
			URL url = new URL(link);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			String content = "";
			String line;
			while((line = reader.readLine()) != null)
			{
				content += line;
			}

			Utils.setStatus(activity, "Getting code");
			Pattern pattern = Pattern.compile(">[a-z]{8}<");
			Matcher cotdMatcher = pattern.matcher(content);
			if(cotdMatcher.find())
			{
				String cotd = cotdMatcher.group().substring(1, 9);
				Utils.setCoTD(activity, cotd);
				Utils.setStatus(activity, "Done");
			}
			else
			{
				Utils.setCoTD(activity, "Error: Unable to get cotd");
			}
		}catch(Exception e) {
			e.printStackTrace();
			Utils.setCoTD(activity, "Are you connected to DSCoTD?");
		}
	}
}
