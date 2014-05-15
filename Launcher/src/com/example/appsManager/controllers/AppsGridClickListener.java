package com.example.appsManager.controllers;

import java.util.List;

import com.example.appsManager.AppsManager;
import com.example.appsManager.model.AppPack;
import com.example.auxiliar.ActionsIntents;

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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		listApps.get(pos).setNewOpen();

		Intent launchIntent = AppsManager.getInstance(mContext)
				.getPackageManager()
				.getLaunchIntentForPackage(listApps.get(pos).getpackageName());

		ActionsIntents.openApp(this.mContext, launchIntent);

	}

}
