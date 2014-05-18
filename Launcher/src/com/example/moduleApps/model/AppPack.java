package com.example.moduleApps.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;

public class AppPack {
	private Drawable icon;
	private String name;
	private String packageName;

	private long firsIntall;
	private long lastUpdate;

	private List<AppOpeningInfo> openingInfo;

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
		this.openingInfo = new ArrayList<AppOpeningInfo>();

		launchAsyncTask();
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

	private void launchAsyncTask() {
		final Handler handler = new Handler();
		Timer timer = new Timer();
		TimerTask doAsynchronousTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						try {
							new InstallInformation().execute();
						} catch (Exception e) {

						}
					}
				});
			}
		};
		timer.schedule(doAsynchronousTask, 0, 600000); // execute in every ten
														// minutes*
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

}
