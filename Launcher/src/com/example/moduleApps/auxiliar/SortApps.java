package com.example.moduleApps.auxiliar;

import java.util.ArrayList;
import java.util.List;

import com.example.moduleApps.model.AppPack;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;

public class SortApps {

	public static void sortByName(List<AppPack> listApps, boolean inverse) {
		int i, j;
		AppPack temp;

		for (i = 0; i < listApps.size() - 1; i++) {
			for (j = i + 1; j < listApps.size(); j++) {
				if (!inverse) {
					if (listApps.get(i).getName()
							.compareToIgnoreCase(listApps.get(j).getName()) > 0) {
						temp = listApps.get(i);
						listApps.set(i, listApps.get(j));
						listApps.set(j, temp);
					}
				} else {
					if (listApps.get(i).getName()
							.compareToIgnoreCase(listApps.get(j).getName()) < 0) {
						temp = listApps.get(i);
						listApps.set(i, listApps.get(j));
						listApps.set(j, temp);
					}
				}

			}
		}
	}

	// Fecha de instalacion
	public static void sortByInstallTime(List<AppPack> listApps) {
		int i, j;
		AppPack temp;

		for (i = 0; i < listApps.size() - 1; i++) {
			for (j = i + 1; j < listApps.size(); j++) {
				if (listApps.get(i).getFirsIntall() < listApps.get(j)
						.getFirsIntall()) {
					temp = listApps.get(i);
					listApps.set(i, listApps.get(j));
					listApps.set(j, temp);
				}
			}
		}
	}

	// Fecha ultima actualizacion
	public static void sortByLastUpdateTime(List<AppPack> listApps) {
		int i, j;
		AppPack temp;

		for (i = 0; i < listApps.size() - 1; i++) {
			for (j = i + 1; j < listApps.size(); j++) {
				if (listApps.get(i).getLastUpdate() < listApps.get(j)
						.getLastUpdate()) {
					temp = listApps.get(i);
					listApps.set(i, listApps.get(j));
					listApps.set(j, temp);
				}
			}
		}
	}

	// Sacamos los procesos que se parezcan
	public static List<AppPack> sortByRecents(List<AppPack> listApps,
			List<ActivityManager.RunningAppProcessInfo> running) {
		List<AppPack> returnList = new ArrayList<AppPack>();

		for (RunningAppProcessInfo runningAppProcessInfo : running) {
			for (int i = 0; i < listApps.size(); i++) {
				if (listApps.get(i).getpackageName()
						.equals(runningAppProcessInfo.processName)) {
					returnList.add(listApps.get(i));
				}
			}
		}
		return returnList;
	}

}
