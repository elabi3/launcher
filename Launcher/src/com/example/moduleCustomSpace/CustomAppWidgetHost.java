package com.example.moduleCustomSpace;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;

public class CustomAppWidgetHost extends AppWidgetHost {

	public CustomAppWidgetHost(Context context, int hostId) {
		super(context, hostId);
	}

	@Override
	public AppWidgetHostView onCreateView(Context context, int appWidgetId,
			AppWidgetProviderInfo appWidget) {
		return new CustomAppWidgetHostView(context);
	}

	@Override
	public void stopListening() {
		super.stopListening();
		clearViews();
	}
}