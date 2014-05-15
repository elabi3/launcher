package com.example.appsManager.controllers;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.example.appsManager.model.AppPack;
import com.example.launcher.R;

public class AppsGrid {
	private Context mContext;
	private View mView;
	private GridView mAppsGrid;
	private AppsGridAdapter mAppsGridAdapter;
	private List<AppPack> listApps;
	private int maximun;

	public AppsGrid(Context mContext, List<AppPack> listApps, int maximun) {
		this.mContext = mContext;
		this.listApps = listApps;
		this.maximun = maximun;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.apps_grid, null);

		loadGridView();
	}

	public GridView getGridView() {
		return mAppsGrid;
	}

	/********************************************
	 * Grid
	 ********************************************/

	private void loadGridView() {
		Log.v("Prueba", "123");
		mAppsGrid = (GridView) mView.findViewById(R.id.appsGrid);
		mAppsGridAdapter = new AppsGridAdapter(
				mContext.getApplicationContext(), listApps);
		mAppsGrid.setAdapter(mAppsGridAdapter);
		AppsGridClickListener gridClickListener = new AppsGridClickListener(
				mContext.getApplicationContext(), listApps);
		mAppsGrid.setOnItemClickListener(gridClickListener);
	}

}