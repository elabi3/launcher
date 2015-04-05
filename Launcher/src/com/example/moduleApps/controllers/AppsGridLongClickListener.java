package com.example.moduleApps.controllers;

import java.util.List;

import com.example.launcher.R;
import com.example.moduleApps.model.AppPack;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AppsGridLongClickListener implements OnItemLongClickListener,
		OnClickListener {
	private List<AppPack> listApps;
	private Context mContext;
	private AppPack appPack;

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

		final Dialog dialog = new Dialog(mContext);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View newView = inflater.inflate(R.layout.module_apps_dialog_app_detail,
				null, false);
		((ImageView) newView.findViewById(R.id.appIcon)).setImageDrawable(listApps.get(
				pos).getIcon());
		((ImageView) newView.findViewById(R.id.appIcon)).setContentDescription(listApps.get(
				pos).getName());
		
		((TextView) newView.findViewById(R.id.titleApp)).setText(listApps.get(
				pos).getName());

		((TextView) newView.findViewById(R.id.info))
				.setOnClickListener(this);
		((TextView) newView.findViewById(R.id.share))
				.setOnClickListener(this);
		((TextView) newView.findViewById(R.id.unistall))
				.setOnClickListener(this);
		((TextView) newView.findViewById(R.id.detail))
				.setOnClickListener(this);
		
		dialog.setContentView(newView);
		dialog.show();
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.info:
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
		case R.id.share:
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("*/*");
			intent.putExtra(Intent.EXTRA_TEXT, appPack.getName());
			break;
		case R.id.unistall:
			intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:"
					+ appPack.getpackageName()));
			break;
		case R.id.detail:
			intent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + appPack.getpackageName()));
			break;
		default:
			break;
		}
		mContext.startActivity(intent);
	}
}
