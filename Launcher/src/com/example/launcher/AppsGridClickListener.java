package com.example.launcher;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class AppsGridClickListener implements OnItemClickListener {

	private Context mContext;
	private List<AppPack> listApps;
	private PackageManager pmForClickListener;
	
	public AppsGridClickListener(Context mContext,
			List<AppPack> listApps, PackageManager pm) {
		super();
		this.mContext = mContext;
		this.listApps = listApps;
		this.pmForClickListener = pm;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		Intent launchIntent = this.pmForClickListener.getLaunchIntentForPackage(listApps.get(pos).getpackageName());
		ActionsIntents.openApp(this.mContext, launchIntent);
	}

}
