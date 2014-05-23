package com.example.moduleApps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import com.example.auxiliar.database.DataBaseInterface;
import com.example.auxiliar.database.Element;
import com.example.moduleApps.auxiliar.SortApps;
import com.example.moduleApps.model.AppPack;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

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
		notifyObservers();
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
	
	// Example implementation 
	public List<AppPack> getAppsMostOpens() {
		List<AppPack> result = Collections.emptyList();
		for (AppPack appPack : listApps) {
			for (Element element : DataBaseInterface.getInstance(mContext).getMostOpenings()) {
				if (appPack.getpackageName().equals(element.getId())) {
					result.add(appPack);
				}
			}
		}
		return result;
	}
	
	public List<AppPack> getAppsTime() {
		// Ask for this apps to database
		return Collections.emptyList();
	}
	
	public List<AppPack> getAppsWeekDay() {
		// Ask for this apps to database
		return Collections.emptyList();
	}
	
	public List<AppPack> getAppsMonthDay() {
		// Ask for this apps to database
		return Collections.emptyList();
	}
	
	public List<AppPack> getAppsByLocation() {
		// Ask for this apps to database
		return Collections.emptyList();
	}
	
	public List<AppPack> getAppsWeekDayTime() {
		// Ask for this apps to database
		return Collections.emptyList();
	}

	
	public List<AppPack> getAppsWeekDayTimeLocation() {
		// Ask for this apps to database
		return Collections.emptyList();
	}
}
