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
		// TODO refresh AppList every Xmn
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

	public List<AppPack> getAppsByName() {
		List<AppPack> result = this.listApps;
		SortApps.sortByName(result, false);
		return result;
	}

	public List<AppPack> getAppsByInstallDate() {
		List<AppPack> result = this.listApps;
		SortApps.sortByInstallTime(result);
		return result;
	}

	public List<AppPack> getAppsByUpdateDate() {
		List<AppPack> result = this.listApps;
		SortApps.sortByLastUpdateTime(result);
		return result;
	}

	// TODO
	public List<AppPack> getAppRecents() {
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(mContext.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am
				.getRunningAppProcesses();
		// SortApps.sortByRecents(pacs, runningAppProcessInfo);
		return listApps;
	}

	// TODO
	public List<AppPack> getAppsInPeriod(Integer period) {
		List<AppPack> result = new ArrayList<AppPack>();
		for (AppPack app : this.listApps) {
			if (app.getTimesOpenAround(period) >= 0) {
				result.add(app);
			}
		}
		// Sort List by times in period
		return result;
	}

	// TODO
	public List<AppPack> getMostOpenApps() {
		List<AppPack> result = this.listApps;
		// Sort List by total times open
		return result;
	}
}
