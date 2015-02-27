package com.example.controllers;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import com.example.launcher.R;

public class Settings extends PreferenceActivity implements
		OnPreferenceClickListener {
	private static final String VERSION_UNAVAILABLE = "N/A";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		findPreference("version").setSummary(getVersionName());
	}

	private String getVersionName() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			return info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			return VERSION_UNAVAILABLE;
		}
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		return false;
	}

}
