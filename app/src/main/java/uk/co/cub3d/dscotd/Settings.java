package uk.co.cub3d.dscotd;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Callum on 10/03/2017.
 */

public class Settings
{
	public static final String SHARED_PREFS_ID = "uk.co.cub3d.shared_pref_Settings";
	public static boolean shouldAutoConnect = true;
	public static boolean walledGardenCheck = true;
	public static boolean debug = false;

	public static void loadSettings(Context context)
	{
		SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFS_ID, Context.MODE_PRIVATE);
		shouldAutoConnect = sharedPrefs.getBoolean("shouldAutoConnect", shouldAutoConnect);
		walledGardenCheck = sharedPrefs.getBoolean("shouldCheckForWalledGarden", walledGardenCheck);
		debug = sharedPrefs.getBoolean("debug", debug);
	}

	public static void saveSettings(Context context)
	{
		SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFS_ID, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putBoolean("shouldAutoConnect", shouldAutoConnect);
		editor.putBoolean("shouldCheckForWalledGarden", walledGardenCheck);
		editor.putBoolean("debug", debug);
		if(editor.commit())
		{
			Toast.makeText(context, "Saved settings", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(context, "Failed to save settings", Toast.LENGTH_SHORT).show();
		}
	}
}
