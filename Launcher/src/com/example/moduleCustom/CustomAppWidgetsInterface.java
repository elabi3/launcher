package com.example.moduleCustom;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CustomAppWidgetsInterface {
	public static final int REQUEST_CREATE_APPWIDGET = 900;
	public static final int REQUEST_PICK_APPWIDGET = 1;
	public static final int APPWIDGET_HOST_ID = 2;

	private Context mContext;
	private AppWidgetManager mAppWidgetManager;
	private CustomAppWidgetHost mAppWidgetHost;

	public CustomAppWidgetsInterface(Context context) {
		this.mContext = context;
		mAppWidgetManager = AppWidgetManager.getInstance(this.mContext);
		mAppWidgetHost = new CustomAppWidgetHost(this.mContext,
				APPWIDGET_HOST_ID);
	}

	public Intent selectWidget() {
		Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
		pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				mAppWidgetHost.allocateAppWidgetId());
		return pickIntent;
	}

	public Intent configureWidget(Intent data) {
		Bundle extras = data.getExtras();
		int appWidgetId = extras
				.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager
				.getAppWidgetInfo(appWidgetId);
		// if (appWidgetInfo.configure != null) {
		Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
		intent.setComponent(appWidgetInfo.configure);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		return intent;
		// }
	}

	public CustomAppWidgetHostView createWidget(Intent data) {
		Log.v("", "Aqui");
		Bundle extras = data.getExtras();
		int appWidgetId = extras
				.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager
				.getAppWidgetInfo(appWidgetId);

		CustomAppWidgetHostView hostView = (CustomAppWidgetHostView) mAppWidgetHost
				.onCreateView(this.mContext, appWidgetId, appWidgetInfo);
		hostView.setAppWidget(appWidgetId, appWidgetInfo);
		return hostView;

		/*
		 * RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		 * extras.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH),
		 * extras.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT));
		 */

		/*
		 * RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		 * customView.getWidth() / 3, customView.getHeight() / 3); lp.leftMargin
		 * = numWidgets * (customView.getWidth() / 3);
		 */

		/*
		 * hostView.setOnLongClickListener(new OnLongClickListener() {
		 * 
		 * @Override public boolean onLongClick(View v) {
		 * System.out.println("LONG PRESSED WIDGET"); return false; } });
		 * 
		 * GridLayout gridLayout = (GridLayout)
		 * view.findViewById(R.id.cutom_layout); gridLayout.addView(hostView);
		 */
		// customView.addView(hostView, lp);
	}

}
