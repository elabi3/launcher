package com.example.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Pair;

// Add total openings
public class AppPack {
	public final static Integer MORNING_PERIOD = Integer.valueOf(0);
	public final static Integer AFTERNOON_PERIOD = Integer.valueOf(1);
	public final static Integer NIGHT_PERIOD = Integer.valueOf(2);
	private Map<Integer, Integer> openingTimes;

	private Drawable icon;
	private String name;
	private String packageName;
	private long firsIntall;
	private long lastUpdate;
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

		this.openingTimes = new HashMap<Integer, Integer>();

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
	 * Opens
	********************************************/
	
	public void setNewOpen() {
		Integer period = getPeriod();
		Integer temp = this.openingTimes.get(period);
		if (temp != null) {
			temp = Integer.valueOf(temp.intValue() + 1);
		} else {
			temp = 1;
		}
		this.openingTimes.put(period, temp);
	}

	public int getTimesOpenAround(Integer period) {
		if (this.openingTimes.get(period) == null) {
			return 0;
		}
		return this.openingTimes.get(period).intValue();
	}
	
	private Integer getPeriod() {
		Pair<Integer, Integer> morning = new Pair<Integer, Integer>(
				Integer.valueOf(6), Integer.valueOf(13));
		Pair<Integer, Integer> afternoon = new Pair<Integer, Integer>(
				Integer.valueOf(13), Integer.valueOf(20));
		Pair<Integer, Integer> night = new Pair<Integer, Integer>(
				Integer.valueOf(20), Integer.valueOf(6));

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH");
		Integer currentTime = new Integer(dateFormatTime.format(calendar
				.getTime()));

		if (currentTime.intValue() >= morning.first.intValue()
				&& currentTime <= morning.second) {
			return MORNING_PERIOD;
		} else if (currentTime.intValue() >= afternoon.first.intValue()
				&& currentTime <= afternoon.second) {
			return AFTERNOON_PERIOD;

		} else if (currentTime.intValue() >= night.first.intValue()
				&& currentTime <= night.second) {
			return NIGHT_PERIOD;
		} else {
			return -1;
		}
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
