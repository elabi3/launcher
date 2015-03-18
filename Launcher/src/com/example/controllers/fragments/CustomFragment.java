package com.example.controllers.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.controllers.main.MainActivity;
import com.example.launcher.R;
import com.example.moduleCustom.ShortcutClickListener;

public class CustomFragment extends Fragment implements OnLongClickListener,
		android.content.DialogInterface.OnClickListener {
	private View mView;
	private RelativeLayout mLayout;

	private static final int REQUEST_PICK_APPWIDGET = 1;
	private static final int REQUEST_PICK_SHORTCUT = 2;
	private AppWidgetManager mAppWidgetManager;
	private AppWidgetHost mAppWidgetHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAppWidgetManager = AppWidgetManager.getInstance(getActivity());
		mAppWidgetHost = new AppWidgetHost(getActivity(), 1000);

		mView = inflater.inflate(R.layout.controllers_fragments_custom,
				container, false);
		mView.setOnLongClickListener(this);
		mLayout = (RelativeLayout) mView.findViewById(R.id.cutom_layout);
		return mView;
	}

	@Override
	public void onStart() {
		super.onStart();
		mAppWidgetHost.startListening();
	}

	@Override
	public void onStop() {
		super.onStop();
		mAppWidgetHost.stopListening();
	}

	@Override
	public boolean onLongClick(View v) {
		String[] items = {
				getActivity().getString(R.string.custom_dialog_widget),
				getActivity().getString(R.string.custom_dialog_shortcat),
				getActivity().getString(R.string.custom_dialog_app),
				getActivity().getString(R.string.custom_dialog_remove) };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		builder.setTitle(getActivity().getString(R.string.custom_dialog_title));
		builder.setItems(items, this);

		Dialog alert = builder.create();
		alert.show();
		return true;
	}

	@Override
	public void onClick(DialogInterface dialog, int item) {
		switch (item) {
		case 0:
			startActivityForResult(selectWidget(), REQUEST_PICK_APPWIDGET);
			break;
		case 1:
			startActivityForResult(selectWidget(), REQUEST_PICK_SHORTCUT);
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

	public Intent selectWidget() {
		Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
		pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				mAppWidgetHost.allocateAppWidgetId());
		return pickIntent;
	}

	public Intent selectShortcut() {
		Intent intent = new Intent(Intent.ACTION_PICK_ACTIVITY);
		intent.putExtra(Intent.EXTRA_INTENT, new Intent(
				Intent.ACTION_CREATE_SHORTCUT));
		return intent;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// resultCode == -1 == OK
		switch (requestCode) {
		case REQUEST_PICK_APPWIDGET: {
			int appWidgetId = data.getExtras().getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
			if (appWidgetId > 0) {
				createWidget(appWidgetId);
			}
			break;
		}
		case REQUEST_PICK_SHORTCUT: {
			createShortcut(data);
			break;
		}
		default:
			break;
		}
	}

	public void createWidget(int appWidgetId) {
		AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager
				.getAppWidgetInfo(appWidgetId);
		AppWidgetHostView hostView = mAppWidgetHost.createView(getActivity(),
				appWidgetId, appWidgetInfo);
		hostView.setAppWidget(appWidgetId, appWidgetInfo);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				appWidgetInfo.minWidth, appWidgetInfo.minHeight);
		this.mLayout.addView(hostView, lp);

		Log.i("", "The widget size is: " + appWidgetInfo.minHeight + "*"
				+ appWidgetInfo.minHeight);
	}

	public void createShortcut(Intent intent) {
		Intent.ShortcutIconResource iconResource = intent
				.getParcelableExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE);
		Bitmap icon = intent.getParcelableExtra(Intent.EXTRA_SHORTCUT_ICON);
		String shortcutLabel = intent
				.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);
		Intent shortIntent = intent
				.getParcelableExtra(Intent.EXTRA_SHORTCUT_INTENT);

		if (icon == null && iconResource != null) {
			Resources resources = null;
			try {
				resources = getActivity().getPackageManager()
						.getResourcesForApplication(iconResource.packageName);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			if (resources != null) {
				int id = resources.getIdentifier(iconResource.resourceName,
						null, null);
				if (resources.getDrawable(id) instanceof StateListDrawable) {
					Drawable d = ((StateListDrawable) resources.getDrawable(id))
							.getCurrent();
					icon = ((BitmapDrawable) d).getBitmap();
				} else
					icon = ((BitmapDrawable) resources.getDrawable(id))
							.getBitmap();
			}
		}

		if (shortcutLabel != null && shortIntent != null && icon != null) {
			LayoutParams lp = new LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			LinearLayout ll = (LinearLayout) LayoutInflater.from(getActivity())
					.inflate(R.layout.module_apps_grid_item_drawer, null);

			((ImageView) ll.findViewById(R.id.icon_image)).setImageBitmap(icon);
			((TextView) ll.findViewById(R.id.icon_text)).setText(shortcutLabel);

			ll.setOnClickListener(new ShortcutClickListener(getActivity()));
			ll.setTag(shortIntent);
			this.mLayout.addView(ll, lp);
		}

	}
}
