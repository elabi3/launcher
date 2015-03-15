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

public class AppsGrid implements Observer {
	public static final int APPS_GRID_ALL = 0;
	public static final int APPS_GRID_RECENTS = 1;
	public static final int APPS_GRID_MOST_OPENS = 2;
	public static final int APPS_GRID_RECOMENDED = 7;
	public static final int APPS_GRID_NEXT = 8;

	public static final int NO_MAXIMUN_LIMIT = -1;

	public static final int APPS_GRID_DEFAULT_ORDER = -1;
	public static final int APPS_GRID_AZ_ORDER = 0;
	public static final int APPS_GRID_UPDATE_ORDER = 2;
	public static final int APPS_GRID_INSTALL_ORDER = 3;
	public static final int APPS_GRID_USED_ORDER = 4;
	public static final int APPS_GRID_MOST_OPENS_ORDER = 5;


	public static final int GRID_DRAWER = R.layout.module_apps_grid_drawer;
	public static final int GRID = R.layout.module_apps_grid;

	private int selectedOrder;

	private Context mContext;
	private View mView;
	private GridView mAppsGrid;
	private AppsGridAdapter mAppsGridAdapter;
	private List<AppPack> listApps;
	private int gridType;
	private int maximun;
	private int refreshRate;

	// Pasar tasa de refresco - cada X mn
	public AppsGrid(Context mContext, int type, int gridType, int maximun,
			int refreshRate) {
		this.mContext = mContext;
		this.gridType = gridType;
		this.maximun = maximun;
		this.refreshRate = refreshRate;

		// Auto add like oberver of Apps Manager
		AppsManager.getInstance(mContext).addObserver(this);

		// Layout and Grid
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(type, null);
		mAppsGrid = (GridView) mView.findViewById(R.id.appsGrid);

		selectedOrder = APPS_GRID_DEFAULT_ORDER;
		refreshListApps();
		loadGridView(type);

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

	public void sortAppsBy(int order) {
		switch (order) {
		case APPS_GRID_AZ_ORDER:
			SortApps.sortByName(listApps, false);
			selectedOrder = APPS_GRID_AZ_ORDER;
			break;
		case APPS_GRID_UPDATE_ORDER:
			SortApps.sortByLastUpdateTime(listApps);
			selectedOrder = APPS_GRID_UPDATE_ORDER;
			break;
		case APPS_GRID_INSTALL_ORDER:
			SortApps.sortByInstallTime(listApps);
			selectedOrder = APPS_GRID_INSTALL_ORDER;
			break;
		default:
			break;
		}
		if (mAppsGridAdapter != null) {
			mAppsGridAdapter.updateList(listApps);
		}
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
		case APPS_GRID_RECOMENDED: 
			temp = AppsManager.getInstance(mContext).getAppsRecomended(maximun);
			break;
		default:
			break;
		}

		listApps = new ArrayList<AppPack>();
		if (maximun == NO_MAXIMUN_LIMIT) {
			listApps = temp;
		} else {
			if (temp.size() > 0) {
				int until = maximun > temp.size() ? temp.size() : maximun;
				for (int i = 0; i < until; i++) {
					listApps.add(temp.get(i));
				}
			}
		}

		if (mAppsGridAdapter != null) {
			mAppsGridAdapter.updateList(listApps);
		}

		if (selectedOrder >= 0) {
			sortAppsBy(selectedOrder);
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

	private void loadGridView(int type) {
		AppsGridClickListener gridClickListener = new AppsGridClickListener(
				mContext, listApps);
		AppsGridLongClickListener gridLongClickListener = new AppsGridLongClickListener(
				listApps);
		mAppsGridAdapter = new AppsGridAdapter(type, listApps,
				gridClickListener, gridLongClickListener);

		mAppsGrid.setAdapter(mAppsGridAdapter);
		mAppsGrid.setOnItemClickListener(gridClickListener);
		mAppsGrid.setOnItemLongClickListener(gridLongClickListener);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		refreshListApps();
	}

}