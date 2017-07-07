package uk.co.cub3d.dscotd;

import android.app.Activity;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	//TODO: this should not exist (needed for exiting)
	public static MainActivity mainActivity;
	public ProgressBar loadingSpinner;
	public static TextView status;
	public static TextView cotdView;
	public static String codeOfTheDay = "";
	public TextureView touchDetectorView;

	public long timeOfLastTouchEvent = 0;

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
				if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					spawnWebpageLoaderThread();
				}
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
			//Only start connecting instantly if the app was opened automatically
			Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show();
			spawnWebpageLoaderThread();
		}
		spwanCOTDGrabberThread();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == LoginPageView.REQUEST_STANDARD)
		{
			if(resultCode == Activity.RESULT_OK)
			{
				this.finish();
			}
		}
	}

	public void spawnWebpageLoaderThread()
	{
		// If unintialized or if the screen hasn't been touched for 300 ms
		if(timeOfLastTouchEvent == 0 || (System.currentTimeMillis() - timeOfLastTouchEvent) >= 300)
		{
			timeOfLastTouchEvent = System.currentTimeMillis();
			runOnUiThread(new WebpageLoader(this));
		}
	}

	public void spwanCOTDGrabberThread()
	{
		new COTDGrabberThread(this).start();
	}
}
