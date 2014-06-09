package com.example.controllers;

import com.example.launcher.R;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity implements
		OnPreferenceClickListener {
	private static final String VERSION_UNAVAILABLE = "N/A";
	public static TransitionEffect currentEffect = TransitionEffect.Standard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		findPreference("spaces_transition").setOnPreferenceClickListener(this);
		findPreference("spaces_transition").setSummary(currentEffect.name());
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

	private void getTransition() {
		final CharSequence[] items = new CharSequence[TransitionEffect.values().length];
		int i = 0;
		for (TransitionEffect transitionEffect : TransitionEffect.values()) {
			items[i++] = transitionEffect.name();
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.preferences_spaces_transition_chooser_title));
		builder.setItems(items, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				currentEffect = TransitionEffect.valueOf((String) items[which]);
				findPreference("spaces_transition").setSummary(
						currentEffect.name());
				MainActivity.getInstance().recreate();
			}
		});

		Dialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.getKey().equals("spaces_transition"))
			getTransition();
		return false;
	}

}
