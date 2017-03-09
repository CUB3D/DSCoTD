package uk.co.cub3d.dscotd;

import android.content.Intent;

/**
 * Created by Callum on 09/03/2017.
 */

public class WebpageLoader implements Runnable
{
	public MainActivity activity;
	public String codeOfTheDay;

	public WebpageLoader(MainActivity mainActivity, String codeOfTheDay)
	{
		this.activity = mainActivity;
		this.codeOfTheDay = codeOfTheDay;
	}

	@Override
	public void run()
	{
		Intent i = new Intent(activity, LoginPageView.class);
		i.putExtra(Utils.CODE_OF_THE_DAY_BUNDLE, codeOfTheDay);
		activity.startActivity(i);
	}
}
