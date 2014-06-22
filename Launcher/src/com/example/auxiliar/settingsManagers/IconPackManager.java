package com.example.auxiliar.settingsManagers;

public class IconPackManager {
	private static IconPackManager instance;

	public static IconPackManager getInstance() {
		if (instance == null) {
			instance = new IconPackManager();
		}
		return instance;
	}
	
	public void getAviableIconsPacks() {

	}
	
	public void getSelectedIconPack() {
	}

	public void setSelectedIconPack() {

	}
}
