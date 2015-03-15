package com.example.moduleApps.model;

import com.example.launcher.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AppPack {
	private Drawable icon;
	private String name;
	private String packageName;

	private long firsIntall;
	private long lastUpdate;

	private LayoutInflater mInflater;
	private Context mContext;

	/********************************************
	 * Contructor
	 ********************************************/

	public AppPack(Drawable icon, String name, String packageName,
			Context context) {
		super();
		this.icon = icon;
		this.name = name;
		this.packageName = packageName;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(this.mContext);

		refreshIntallUpdateInfo();
	}

	/********************************************
	 * Getters & Setters
	 ********************************************/

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpackageName() {
		return packageName;
	}

	public void setpackageName(String packageName) {
		this.packageName = packageName;
	}

	public long getFirsIntall() {
		return firsIntall;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	/********************************************
	 * First install & LastUpdate
	 ********************************************/

	public void refreshIntallUpdateInfo() {
		new InstallInformation().execute();
	}

	private class InstallInformation extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			firsIntall = getAppFirstInstallTime();
			lastUpdate = getAppLastUpdateTime();
			return null;
		}
	}

	private long getAppFirstInstallTime() {
		PackageInfo packageInfo;
		try {
			packageInfo = mContext.getPackageManager().getPackageInfo(
					this.packageName, 0);
			return packageInfo.firstInstallTime;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private long getAppLastUpdateTime() {
		try {
			PackageInfo packageInfo = mContext.getPackageManager()
					.getPackageInfo(this.packageName, 0);
			return packageInfo.lastUpdateTime;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/********************************************
	 * View
	 ********************************************/

	public View getView() {
		View returnView = mInflater.inflate(
				R.layout.module_apps_grid_item_drawer, null);

		ImageView icon = (ImageView) returnView.findViewById(R.id.icon_image);
		TextView text = (TextView) returnView.findViewById(R.id.icon_text);

		icon.setImageDrawable(this.icon);
		icon.setContentDescription(this.name);
		text.setText(this.name);

		return returnView;
	}

	public View getViewCompact() {
		View returnView = mInflater.inflate(R.layout.module_apps_grid_item,
				null);

		ImageView icon = (ImageView) returnView.findViewById(R.id.icon_image);
		icon.setImageDrawable(this.icon);
		icon.setContentDescription(this.name);

		return returnView;
	}

}
