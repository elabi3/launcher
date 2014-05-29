package com.example.moduleApps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PackageChangeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context ctx, Intent intent) {
		Log.v("1","Se–al");
		AppsManager.getInstance(ctx).packageChange();
	}
}
