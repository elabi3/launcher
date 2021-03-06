package com.spaces.moduleApps;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.spaces.auxiliar.database.DataBaseInterface;
import com.spaces.auxiliar.database.DatabaseElementOpen;
import com.spaces.moduleApps.auxiliar.SortApps;
import com.spaces.moduleApps.model.AppPack;

public class AppsManager extends Observable {
	private static AppsManager instance = null;
	private Context mContext;
	private PackageManager pm;
	public static List<AppPack> listApps;
	public static List<AppPack> nextApps;

	public static AppsManager getInstance(Context context) {
		if (instance == null) {
			instance = new AppsManager(context);
		}
		instance.mContext = context;
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

	private List<AppPack> checkIfAppExist(List<String> elements) {
		List<AppPack> result = new ArrayList<AppPack>();

		for (String element : elements) {
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
		DataBaseInterface.getInstance(mContext).newOpening(
				new DatabaseElementOpen(id, "app", "", ""));

		// Next apps
		nextApps = checkIfAppExist(DataBaseInterface.getInstance(mContext)
				.getNextElements(id));
		setChanged();
		notifyObservers();
	}

	/********************************************
	 * Get List of apps
	 ********************************************/

	public List<AppPack> getAppsByName() {
		List<AppPack> result = listApps;
		SortApps.sortByName(result, false);
		return result;
	}

	public List<AppPack> getAppsMostOpens() {
		return checkIfAppExist(DataBaseInterface.getInstance(mContext)
				.getMostOpenings());
	}

	public List<AppPack> getAppsRecomended(int maximun) {
		return checkIfAppExist(DataBaseInterface.getInstance(mContext)
				.getRecomended(maximun));
	}

	public List<AppPack> getNextApps() {
		return nextApps;
	}
}
