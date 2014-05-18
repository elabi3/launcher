package com.example.moduleApps.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.example.launcher.R;
import com.example.moduleApps.AppsManager;
import com.example.moduleApps.auxiliar.SortApps;
import com.example.moduleApps.model.AppPack;

public class AppsGrid {
	public static final int APPS_GRID_ALL = 0;
	public static final int APPS_GRID_RECENTS = 1;
	public static final int APPS_GRID_MOST_OPENS = 2;
	public static final int APPS_GRID_LOCATION = 3;
	public static final int APPS_GRID_NEAR_NOW = 4;
	public static final int NO_MAXIMUN_LIMIT = -1;

	private Context mContext;
	private View mView;
	private GridView mAppsGrid;
	private AppsGridAdapter mAppsGridAdapter;
	private List<AppPack> listApps;
	private int gridType;
	private int maximun;

	public AppsGrid(Context mContext, int gridType, int maximun) {
		this.mContext = mContext;
		this.gridType = gridType;
		this.maximun = maximun;

		refreshListApps();

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.module_apps_grid, null);

		loadGridView();
	}
	
	/********************************************
	 * Getters
	 ********************************************/

	public GridView getGridView() {
		return mAppsGrid;
	}

	public AppsGridAdapter getGridAdapter() {
		return mAppsGridAdapter;
	}

	public void filterList(CharSequence s) {
		mAppsGridAdapter.getFilter().filter(s);
	}

	/********************************************
	 * Sort List Apps
	 ********************************************/

	public void sortAppsByName(boolean inverse) {
		SortApps.sortByName(listApps, inverse);
		mAppsGridAdapter.notifyDataSetChanged();
	}

	public void sortAppsByLastUpdate() {
		SortApps.sortByLastUpdateTime(listApps);
		mAppsGridAdapter.notifyDataSetChanged();
	}

	public void sortAppsByInstallTime() {
		SortApps.sortByInstallTime(listApps);
		mAppsGridAdapter.notifyDataSetChanged();
	}

	/********************************************
	 * Fill List Apps
	 ********************************************/

	private void refreshListApps() {
		List<AppPack> temp = new ArrayList<AppPack>();
		switch (gridType) {
		case APPS_GRID_ALL:
			temp = AppsManager.getInstance(mContext).getAppsByName();
			break;
		case APPS_GRID_RECENTS:
			temp = AppsManager.getInstance(mContext).getAppsRecents();
			break;
		case APPS_GRID_MOST_OPENS:
			temp = AppsManager.getInstance(mContext).getAppsMostOpen();
			break;
		case APPS_GRID_LOCATION:
			temp = AppsManager.getInstance(mContext).getAppsByLocation();
			break;
		case APPS_GRID_NEAR_NOW:
			temp = AppsManager.getInstance(mContext).getAppsNearNow();
			break;
		default:
			break;
		}

		listApps = new ArrayList<AppPack>();
		if (maximun == NO_MAXIMUN_LIMIT) {
			listApps = temp;
		} else {
			for (int i = 0; i < maximun; i++) {
				listApps.add(temp.get(i));
			}
		}
	}

	/********************************************
	 * Grid
	 ********************************************/

	private void loadGridView() {
		mAppsGrid = (GridView) mView.findViewById(R.id.appsGrid);
		
		AppsGridClickListener gridClickListener = new AppsGridClickListener(
				mContext.getApplicationContext(), listApps);
		mAppsGridAdapter = new AppsGridAdapter(
				mContext.getApplicationContext(), listApps, gridClickListener);

		
		mAppsGrid.setAdapter(mAppsGridAdapter);
		mAppsGrid.setOnItemClickListener(gridClickListener);
	}
}