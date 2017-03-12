package uk.co.cub3d.dscotd;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Callum on 10/03/2017.
 */

public class Settings
{
	public static final String SHARED_PREFS_ID = "uk.co.cub3d.shared_pref_Settings";
	public static boolean shouldAutoConnect = true;
	public static boolean debug = false;

	public static void loadSettings(Context context)
	{
		SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFS_ID, Context.MODE_PRIVATE);
		shouldAutoConnect = sharedPrefs.getBoolean("shouldAutoCorrect", shouldAutoConnect);
		debug = sharedPrefs.getBoolean("debug", debug);
	}

	public static void saveSettings(Context context)
	{
		SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFS_ID, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putBoolean("shouldAutoCorrect", shouldAutoConnect);
		editor.putBoolean("debug", debug);
	}
}
