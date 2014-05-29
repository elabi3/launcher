package com.example.moduleApps.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.example.launcher.R;
import com.example.moduleApps.AppsManager;
import com.example.moduleApps.auxiliar.SortApps;
import com.example.moduleApps.model.AppPack;

public class AppsGrid  implements Observer {
	public static final int APPS_GRID_ALL = 0;
	public static final int APPS_GRID_RECENTS = 1;
	public static final int APPS_GRID_MOST_OPENS = 2;
	public static final int APPS_GRID_NEAR_NOW = 3;
	public static final int APPS_GRID_WEEK_DAY = 4;
	public static final int APPS_GRID_MONTH_DAY = 5;
	public static final int APPS_GRID_LOCATION = 6;
	public static final int APPS_GRID_WEEK_DAY_TIME = 7;
	public static final int APPS_GRID_WEEK_DAY_TIME_LOCATION = 8;
	public static final int NO_MAXIMUN_LIMIT = -1;

	private Context mContext;
	private View mView;
	private GridView mAppsGrid;
	private AppsGridAdapter mAppsGridAdapter;
	private List<AppPack> listApps;
	private int gridType;
	private int maximun;
	private int refreshRate;

	// Pasar tasa de refresco - cada X mn
	public AppsGrid(Context mContext, int gridType, int maximun, int refreshRate) {
		this.mContext = mContext;
		this.gridType = gridType;
		this.maximun = maximun;
		this.refreshRate = refreshRate;

		// Layout and Grid
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.module_apps_grid, null);
		mAppsGrid = (GridView) mView.findViewById(R.id.appsGrid);

		refreshListApps();
		loadGridView();
		
		if (refreshRate > 0) {
			refresh();
		}
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
	 * Refresh 
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
			temp = AppsManager.getInstance(mContext).getAppsMostOpens();
			break;
		case APPS_GRID_NEAR_NOW:
			temp = AppsManager.getInstance(mContext).getAppsTime();
			break;
		case APPS_GRID_WEEK_DAY:
			temp = AppsManager.getInstance(mContext).getAppsWeekDay();
			break;
		case APPS_GRID_MONTH_DAY:
			temp = AppsManager.getInstance(mContext).getAppsMonthDay();
			break;
		case APPS_GRID_LOCATION:
			temp = AppsManager.getInstance(mContext).getAppsByLocation();
			break;
		case APPS_GRID_WEEK_DAY_TIME:
			temp = AppsManager.getInstance(mContext).getAppsWeekDayTime();
			break;
		case APPS_GRID_WEEK_DAY_TIME_LOCATION:
			temp = AppsManager.getInstance(mContext).getAppsWeekDayTimeLocation();
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
	
	private void refresh() {
		final Handler handler = new Handler();
		Timer timer = new Timer();
		TimerTask doAsynchronousTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						try {
							refreshListApps();
						} catch (Exception e) {

						}
					}
				});
			}
		};
		timer.schedule(doAsynchronousTask, 0, refreshRate); 
	}

	/********************************************
	 * Grid
	 ********************************************/

	private void loadGridView() {		
		AppsGridClickListener gridClickListener = new AppsGridClickListener(
				mContext.getApplicationContext(), listApps);
		mAppsGridAdapter = new AppsGridAdapter(
				mContext.getApplicationContext(), listApps, gridClickListener);

		
		mAppsGrid.setAdapter(mAppsGridAdapter);
		mAppsGrid.setOnItemClickListener(gridClickListener);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Log.v("3","Update");
		refreshListApps();
		mAppsGridAdapter.notifyDataSetChanged();
	}

}