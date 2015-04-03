package com.example.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import com.example.controllers.main.ViewPagerTransitions;
import com.example.launcher.R;

public class Settings extends PreferenceActivity implements
		OnPreferenceClickListener, OnClickListener {
	private static final String VERSION_UNAVAILABLE = "N/A";
	private AlertDialog.Builder transitionsDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Settings");
		getActionBar().setElevation(0);
		ColorDrawable colorDrawable = new ColorDrawable(Color.DKGRAY);
		getActionBar().setBackgroundDrawable(colorDrawable);
		getWindow().getDecorView().setBackgroundColor(Color.DKGRAY);
		
		addPreferencesFromResource(R.xml.preferences);
		findPreference("spaces_transition").setSummary(ViewPagerTransitions.getSelectedTransition());
		findPreference("spaces_transition").setOnPreferenceClickListener(this);

		findPreference("theme_chooser").setOnPreferenceClickListener(this);
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
		if (preference.getKey().equals("spaces_transition")) {
			showTransitions();
		}
		if (preference.getKey().equals("theme_chooser")) {

		}
		return false;
	}

	private void showTransitions() {
		transitionsDialog = new AlertDialog.Builder(this,
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		transitionsDialog.setTitle("Spaces Transitions");
		transitionsDialog.setItems(ViewPagerTransitions.transitions, this);

		Dialog alert = transitionsDialog.create();
		alert.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		ViewPagerTransitions.setSelectedTransition(which);
		findPreference("spaces_transition").setSummary(ViewPagerTransitions.getSelectedTransition());
	}
}
