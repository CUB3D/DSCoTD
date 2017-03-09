package uk.co.cub3d.dscotd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
	//TODO: this should not be static
	public static Activity mainActivity;
	public static ProgressBar loadingSpinner;
	public static TextView status;
	public static TextView cotdView;
	public static String codeOfTheDay = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		status = (TextView) findViewById(R.id.textView);
		cotdView = (TextView) findViewById(R.id.textView2);
		loadingSpinner = (ProgressBar) findViewById(R.id.inProgress);

		mainActivity = this;

		Utils.setStatus("Connecting");
		loadingSpinner.setIndeterminate(true);
		startConnectingThread();
	}

	public void showWebpage()
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				Intent i = new Intent(mainActivity, LoginPageView.class);
				i.putExtra(Utils.CODE_OF_THE_DAY_BUNDLE, codeOfTheDay);
				startActivity(i);
			}
		});
	}

	public void startConnectingThread()
	{
		new COTDGrabberThread(this).start();
	}
}
