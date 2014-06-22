package com.example.moduleApps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import com.example.auxiliar.database.Interface;
import com.example.auxiliar.database.DatabaseElementOpen;
import com.example.moduleApps.auxiliar.SortApps;
import com.example.moduleApps.model.AppPack;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

public class AppsManager extends Observable {
	private static AppsManager instance = null;
	private Context mContext;
	private PackageManager pm;
	private List<AppPack> listApps;

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

		listApps = new ArrayList<AppPack>();
		for (ResolveInfo resolveInfo : pacList) {
			listApps.add(new AppPack(resolveInfo.loadIcon(pm), resolveInfo
					.loadLabel(pm).toString(),
					resolveInfo.activityInfo.packageName, mContext));
		}
	}

	public PackageManager getPackageManager() {
		return this.pm;
	}

	public void packageChange() {
		createAppList();
		setChanged();
		notifyObservers();
	}

	// Call newOpening from click listener
	public void newOpening(String id) {
		DatabaseElementOpen element = new DatabaseElementOpen(id, "app", "", "");
		Interface.getInstance(mContext).newOpening(element);
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

	// Example implementation - generic method maybe
	public List<AppPack> getAppsMostOpens() {
		List<AppPack> result = new ArrayList<AppPack>();
		
		for (String element : Interface.getInstance(mContext).getMostOpenings()) {
			for (AppPack appPack : listApps) {
				boolean already = false;
				for (AppPack appInserted : result) {
					if (appInserted.getpackageName().equals(element)) {
						already = true;
					}
				}

				if (!already && appPack.getpackageName().equals(element)) {
					result.add(appPack);
				}
			}
		}
		return result;
	}
	
	public List<AppPack> getAppsLessOpens() {
		List<AppPack> result = new ArrayList<AppPack>();
		
		for (String element : Interface.getInstance(mContext).getLessOpenings()) {
			for (AppPack appPack : listApps) {
				boolean already = false;
				for (AppPack appInserted : result) {
					if (appInserted.getpackageName().equals(element)) {
						already = true;
					}
				}

				if (!already && appPack.getpackageName().equals(element)) {
					result.add(appPack);
				}
			}
		}
		return result;
	}

	public List<AppPack> getAppsWeekDayTime() {
		List<AppPack> result = new ArrayList<AppPack>();

		for (String element : Interface.getInstance(mContext)
				.getElementsWeekDayTime()) {
			for (AppPack appPack : listApps) {
				// Check if it is already added
				boolean already = false;
				for (AppPack appInserted : result) {
					if (appInserted.getpackageName().equals(element)) {
						already = true;
					}
				}

				if (!already && appPack.getpackageName().equals(element)) {
					result.add(appPack);
				}
			}
		}
		return result;
	}

	public List<AppPack> getAppsWeekDayTimeLocation() {
		// Ask for this apps to database
		return Collections.emptyList();
	}
}
