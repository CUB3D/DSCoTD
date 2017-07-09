package uk.co.cub3d.dscotd.codeGrabber;

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
	public CodeGrabberThreadFactory parent;

	public COTDGrabberThread(String urlToGrab, CodeGrabberThreadFactory parent)
	{
		System.err.println("Start COTDGRABTHRED");
		this.link = urlToGrab;
		this.parent = parent;
	}

	@Override
	public void run()
	{
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

			Pattern pattern = Pattern.compile(">[a-z]{8}<");
			Matcher cotdMatcher = pattern.matcher(content);
			if(cotdMatcher.find())
			{
				parent.code = cotdMatcher.group().substring(1, 9);
				parent.threadResponse = CodeGrabberThreadFactory.THREAD_RESPONCE_GOOD;
			}
			else
			{
				parent.threadResponse = CodeGrabberThreadFactory.THREAD_RESPONCE_ERROR_NOCODE;
			}
		}catch(Exception e) {
			e.printStackTrace();
			parent.threadResponse = CodeGrabberThreadFactory.THREAD_RESPONCE_ERROR_WRONGNETWORK;
		}
	}
}
