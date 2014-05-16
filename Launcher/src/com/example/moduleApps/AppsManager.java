package com.example.moduleApps;

import java.util.ArrayList;
import java.util.List;

import com.example.moduleApps.auxiliar.SortApps;
import com.example.moduleApps.model.AppPack;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class AppsManager {
	private static AppsManager instance = null;
	private Context mContext;
	private PackageManager pm;
	private List<AppPack> listApps = new ArrayList<AppPack>();

	public static AppsManager getInstance(Context context) {
		if (instance == null) {
			instance = new AppsManager(context);
		}
		return instance;
	}

	private AppsManager(Context context) {
		mContext = context;
		pm = mContext.getPackageManager();
		createAppList();
	}

	private void createAppList() {
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> pacList = pm.queryIntentActivities(mainIntent, 0);

		for (ResolveInfo resolveInfo : pacList) {
			listApps.add(new AppPack(resolveInfo.loadIcon(pm), resolveInfo
					.loadLabel(pm).toString(),
					resolveInfo.activityInfo.packageName, mContext));
		}
	}

	public PackageManager getPackageManager() {
		return this.pm;
	}

	/********************************************
	 * Get List of apps
	 ********************************************/
	
	public List<AppPack> getAppsByName() {
		List<AppPack> result = this.listApps;
		SortApps.sortByName(result, false);
		return result;
	}

	public List<AppPack> getAppsRecents() {
		List<AppPack> result = this.listApps;
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(mContext.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am
				.getRunningAppProcesses();
		return SortApps.sortByRecents(result, runningAppProcessInfo);
	}

	public List<AppPack> getAppsMostOpen() {
		// Ask for this apps to database
		List<AppPack> result = this.listApps;
		SortApps.sortByMostOpen(result);
		return result;
	}
	
	public List<AppPack> getAppsByLocation() {
		// Ask for this apps to database
		List<AppPack> result = this.listApps;
		return result;
	}
	
	public List<AppPack> getAppsNearNow() {
		// Ask for this apps to database
		List<AppPack> result = new ArrayList<AppPack>();
		return result;
	}
	
	public List<AppPack> getAppsByLocationAndNearNow() {
		// Ask for this apps to database
		List<AppPack> result = new ArrayList<AppPack>();
		return result;
	}
}
