package com.example.moduleApps.controllers;

import java.util.List;

import com.example.launcher.R;
import com.example.moduleApps.model.AppPack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class AppsGridLongClickListener implements OnItemLongClickListener,
		android.content.DialogInterface.OnClickListener {
	private List<AppPack> listApps;
	private Context mContext;
	private AppPack appPack;
	private String[] items = new String[4];

	public AppsGridLongClickListener(Context context, List<AppPack> listApps) {
		super();
		this.mContext = context;
		this.listApps = listApps;
	}

	public void setListApps(List<AppPack> listApps) {
		this.listApps = listApps;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int pos,
			long arg3) {
		appPack = listApps.get(pos);
		items[0] = mContext.getString(R.string.module_app_long_app_info);
		items[1] = mContext.getString(R.string.module_app_long_app_share);
		items[2] = mContext.getString(R.string.module_app_long_app_unistall);
		items[3] = mContext.getString(R.string.module_app_long_app_detail);

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext,
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		builder.setTitle(listApps.get(pos).getName());
		builder.setItems(items, this);

		Dialog alert = builder.create();
		alert.show();
		return true;
	}

	@Override
	public void onClick(DialogInterface dialog, int item) {
		Intent intent = null;
		switch (item) {
		case 0:
			try {
				// Open the specific App Info page:
				intent = new Intent(
						android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.setData(Uri.parse("package:" + appPack.getpackageName()));
			} catch (ActivityNotFoundException e) {
				// Open the generic Apps page:
				intent = new Intent(
						android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
			}
			break;
		case 1:
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("*/*");
			intent.putExtra(Intent.EXTRA_TEXT, appPack.getName());
			break;
		case 2:
			intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:"
					+ appPack.getpackageName()));
			break;
		case 3:
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + appPack.getpackageName()));
			break;
		default:
			break;
		}
		mContext.startActivity(intent);
	}
}
