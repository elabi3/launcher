package com.example.utilities;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.example.data.AppPack;

public class AppsManager {
	private static AppsManager instance = null;
	private List<AppPack> listApps = new ArrayList<AppPack>();
	private PackageManager pm;
	private Context mContext;

	private AppsManager(Context context) {
		mContext = context;
		createAppList();
	}

	public static AppsManager getInstance(Context context) {
		if (instance == null) {
			instance = new AppsManager(context);
		}
		return instance;
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
	
	public List<AppPack> getAppsByName() {
		SortApps.sortByName(listApps, false);
		return listApps;
	}

	public List<AppPack> getAppsByInstallDate() {
		SortApps.sortByInstallTime(listApps);
		return listApps;
	}
	
	public List<AppPack> getAppsByUpdateDate() {
		SortApps.sortByLastUpdateTime(listApps);
		return listApps;
	}
	
	public List<AppPack> getAppRecents() {
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(mContext.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am
				.getRunningAppProcesses();
		// SortApps.sortByRecents(pacs, runningAppProcessInfo);
		return listApps;
	}
	
	
	// getAppsInPeriod
	// getMostOpenApps
}
