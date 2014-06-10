package com.example.auxiliar.settingsManagers;

public class WallpaperManager {
	private static WallpaperManager instance;

	public static WallpaperManager getInstance() {
		if (instance == null) {
			instance = new WallpaperManager();
		}
		return instance;
	}
}
