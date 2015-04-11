package com.spaces.moduleApps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PackageChangeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context ctx, Intent intent) {
		AppsManager.getInstance(ctx).packageChange();
	}
}
