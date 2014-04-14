package com.example.launcher;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class AppsGridClickListener implements OnItemClickListener {
	public static List<AppPack> listApps;
	private Context mContext;
	private PackageManager pmForClickListener;

	public AppsGridClickListener(Context mContext, PackageManager pm) {
		super();
		this.mContext = mContext;
		this.pmForClickListener = pm;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		Intent launchIntent = this.pmForClickListener
				.getLaunchIntentForPackage(listApps.get(pos).getpackageName());
		ActionsIntents.openApp(this.mContext, launchIntent);
	}

}
