package com.spaces.moduleApps.controllers;

import java.util.List;

import com.spaces.moduleApps.AppsManager;
import com.spaces.moduleApps.model.AppPack;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class AppsGridClickListener implements OnItemClickListener {
	private List<AppPack> listApps;
	private Context mContext;

	public AppsGridClickListener(Context mContext, List<AppPack> listApps) {
		super();
		this.mContext = mContext;
		this.listApps = listApps;
	}

	public void setListApps(List<AppPack> listApps) {
		this.listApps = listApps;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		Intent launchIntent = AppsManager.getInstance(mContext)
				.getPackageManager()
				.getLaunchIntentForPackage(listApps.get(pos).getpackageName());
		mContext.startActivity(launchIntent);
		AppsManager.getInstance(mContext).newOpening(
				listApps.get(pos).getpackageName());
	}

}
