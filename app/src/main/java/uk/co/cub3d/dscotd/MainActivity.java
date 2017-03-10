package uk.co.cub3d.dscotd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
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
	public static TextureView touchDetectorView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		status = (TextView) findViewById(R.id.textView);
		cotdView = (TextView) findViewById(R.id.textView2);
		loadingSpinner = (ProgressBar) findViewById(R.id.inProgress);
		touchDetectorView = (TextureView) findViewById(R.id.textureView);

		//Hacky way of detecting whole screen touch, cover it with a translucent view and add a touch listener
		touchDetectorView.setAlpha(0);
		touchDetectorView.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent)
			{
				startConnectingThread();
				return true;
			}
		});

		mainActivity = this;
		Settings.loadSettings(this);

		Utils.setStatus(this, "Connecting");
		loadingSpinner.setIndeterminate(true);

		//Check if automatically opened
		if(getIntent().getBooleanExtra(Utils.OPENED_AUTO, false))
		{
			if(!Settings.shouldAutoConnect)
			{
				//quit
				this.finishAndRemoveTask();
			}
			//Only start connecting instantly if the app was opened automatically
			startConnectingThread();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_activity_options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.menu1:
				Intent settings = new Intent(this, SettingsActivity.class);
				startActivity(settings);
				return true;
		}

		return false;
	}

	//Should stop memory leaks from the static copy of this activity that is needed to kill the app completely
	public void safeExit()
	{
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
