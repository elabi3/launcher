package com.example.auxiliar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prefs {
	public static final String PREFS_TRANSITION = "__TRANSITION__";
	public static final String PREFS_THEME = "__THEME__";

	public static void saveToPrefs(Context context, String key, String value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		final SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public static void saveToPrefs(Context context, String key, int value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		final SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public static String getFromPrefs(Context context, String key,
			String defaultValue) {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		try {
			return sharedPrefs.getString(key, defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static int getFromPrefs(Context context, String key, int defaultValue) {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		try {
			return sharedPrefs.getInt(key, defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}
}
