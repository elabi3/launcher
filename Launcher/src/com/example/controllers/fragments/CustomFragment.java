package com.example.controllers.fragments;

import com.example.launcher.R;
import com.example.moduleCustom.CustomAppWidgetsInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

public class CustomFragment extends Fragment implements OnLongClickListener,
		android.content.DialogInterface.OnClickListener {
	private View mView;
	private CustomAppWidgetsInterface appWidgetsInterface;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		appWidgetsInterface = new CustomAppWidgetsInterface(getActivity());

		mView = inflater.inflate(R.layout.controllers_fragments_custom,
				container, false);
		mView.setOnLongClickListener(this);
		return mView;
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
			startActivityForResult(appWidgetsInterface.selectWidget(),
					CustomAppWidgetsInterface.REQUEST_PICK_APPWIDGET);
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// resultCode == -1 == OK
		switch (requestCode) {
		case CustomAppWidgetsInterface.REQUEST_PICK_APPWIDGET:
			Log.v("", "Holita");
			startActivityForResult(appWidgetsInterface.configureWidget(data),
					CustomAppWidgetsInterface.REQUEST_CREATE_APPWIDGET);
			break;
		case CustomAppWidgetsInterface.REQUEST_CREATE_APPWIDGET:
			Log.v("", "Wejeje");
			appWidgetsInterface.createWidget(data);
			break;
		default:
			break;
		}
	}
}
