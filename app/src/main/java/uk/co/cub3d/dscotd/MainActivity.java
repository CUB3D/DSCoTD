package uk.co.cub3d.dscotd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
	//TODO: this should not exist (needed for exiting)
	public static MainActivity mainActivity;
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

		Utils.setStatus(this, "Connecting");
		loadingSpinner.setIndeterminate(true);
		startConnectingThread();
	}

	//Should stop memory leaks from the static copy of this activity that is needed to kill the app completely
	public void safeExit()
	{
		MainActivity.mainActivity = null;
		this.finishAndRemoveTask();
	}

	public void showWebpage()
	{
		runOnUiThread(new WebpageLoader(this, codeOfTheDay));
	}

	public void startConnectingThread()
	{
		new COTDGrabberThread(this).start();
	}
}
