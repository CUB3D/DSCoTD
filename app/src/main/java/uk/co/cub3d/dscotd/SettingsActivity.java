package uk.co.cub3d.dscotd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		Switch autoConnect = (Switch) findViewById(R.id.auto_connect_switch);
		autoConnect.setChecked(Settings.shouldAutoConnect);
		autoConnect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b)
			{
				Settings.shouldAutoConnect = b;
			}
		});
	}

	@Override
	public void finish()
	{
		Settings.saveSettings(this);
		super.finish();
	}
}
