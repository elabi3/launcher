package com.example.controllers.main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.controllers.Settings;
import com.example.launcher.R;

public class MainLeftDrawer implements OnClickListener {
	private DrawerLayout mLayout;
	private Context mContext;
	private Handler mHandler = new Handler();
	private TextView settingsButton;

	public MainLeftDrawer(DrawerLayout layout, Context context) {
		this.mLayout = layout;
		this.mContext = context;
		setupListView();
	}

	private void setupListView() {
		this.settingsButton = (TextView) mLayout.findViewById(R.id.settings);
		this.settingsButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settings: {
			mContext.startActivity(new Intent(mContext, Settings.class));
			break;
		}
		default:
			break;
		}
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mLayout.closeDrawers();
			}
		}, 200);
	}
}
