package com.example.ui.appsdrawer;

import java.util.List;

import com.example.data.AppPack;
import com.example.utilities.ActionsIntents;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
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
		listApps.get(pos).setNewOpen();
		Log.v("OPENTIMES", listApps.get(pos).getName() + listApps.get(pos).getTimesOpenAround(AppPack.AFTERNOON_PERIOD));
		Intent launchIntent = this.pmForClickListener
				.getLaunchIntentForPackage(listApps.get(pos).getpackageName());
		ActionsIntents.openApp(this.mContext, launchIntent);
	}

}
