package com.example.auxiliar.settingsManagers;

public class ThemeManager {
	private static ThemeManager instance;

	public static ThemeManager getInstance() {
		if (instance == null) {
			instance = new ThemeManager();
		}
		return instance;
	}

	public void getSelectedTheme() {

	}

	public void setSelectedTheme() {

	}
}
