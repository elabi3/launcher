package com.example.utilities;

import java.util.ArrayList;
import java.util.List;

import com.example.data.AppPack;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;

public class SortApps {

	public static void sortByName(List<AppPack> listApps, boolean inverse) {
		int i, j;
		AppPack temp;

		for (i = 0; i < listApps.size() - 1; i++) {
			for (j = i + 1; j < listApps.size(); j++) {
				if (!inverse) {
					if (listApps.get(i).getName().compareToIgnoreCase(
							listApps.get(j).getName()) > 0) {
						temp = listApps.get(i);
						listApps.set(i, listApps.get(j));
						listApps.set(j, temp);
					}
				} else {
					if (listApps.get(i).getName().compareToIgnoreCase(
							listApps.get(j).getName()) < 0) {
						temp = listApps.get(i);
						listApps.set(i, listApps.get(j));
						listApps.set(j, temp);
					}
				}

			}
		}
	}

	// Recientes
	public static void sortByInstallTime(List<AppPack> listApps) {
		int i, j;
		AppPack temp;

		for (i = 0; i < listApps.size() - 1; i++) {
			for (j = i + 1; j < listApps.size(); j++) {
				if (listApps.get(i).getFirsIntall() < listApps.get(j).getFirsIntall()) {
					temp = listApps.get(i);
					listApps.set(i, listApps.get(j));
					listApps.set(j, temp);
				}
			}
		}
	}

	// Recientes
	public static void sortByLastUpdateTime(List<AppPack> listApps) {
		int i, j;
		AppPack temp;

		for (i = 0; i < listApps.size() - 1; i++) {
			for (j = i + 1; j < listApps.size(); j++) {
				if (listApps.get(i).getLastUpdate() < listApps.get(j).getLastUpdate()) {
					temp = listApps.get(i);
					listApps.set(i, listApps.get(j));
					listApps.set(j, temp);
				}
			}
		}
	}

	// Optimizar este codigo para que en vez de crear otro array lo reordene bien
	/*public static void sortByRecents(AppPack array[],
			List<ActivityManager.RunningAppProcessInfo> running) {
		List<AppPack> returnList = new ArrayList<AppPack>();
		for (RunningAppProcessInfo runningAppProcessInfo : running) {
			for (int i = 0; i < array.length; i++) {
				if (array[i].getpackageName().equals(
						runningAppProcessInfo.processName)) {
					returnList.add(array[i]);
				}
			}
		}
		
		returnList.toArray(array);
	}*/

}
