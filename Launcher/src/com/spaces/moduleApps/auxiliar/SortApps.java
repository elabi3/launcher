package com.spaces.moduleApps.auxiliar;

import java.util.List;

import com.spaces.moduleApps.model.AppPack;

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

	// Opens
	public static void sortByOpens(List<AppPack> listApps) {
		int i, j;
		AppPack temp;

		for (i = 0; i < listApps.size() - 1; i++) {
			for (j = i + 1; j < listApps.size(); j++) {
				if (listApps.get(i).getOpens() < listApps.get(j)
						.getOpens()) {
					temp = listApps.get(i);
					listApps.set(i, listApps.get(j));
					listApps.set(j, temp);
				}
			}
		}
	}

}
