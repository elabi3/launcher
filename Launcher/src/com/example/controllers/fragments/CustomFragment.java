package com.example.controllers.fragments;

import com.example.launcher.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

public class CustomFragment extends Fragment implements OnLongClickListener,
		android.content.DialogInterface.OnClickListener {
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_fragments_custom,
				container, false);
		mView.setOnLongClickListener(this);
		return mView;
	}

	@Override
	public boolean onLongClick(View v) {
		String[] items = new String[4];
		items[0] = getActivity().getString(R.string.custom_dialog_widget);
		items[1] = getActivity().getString(R.string.custom_dialog_shortcat);
		items[2] = getActivity().getString(R.string.custom_dialog_app);
		items[3] = getActivity().getString(R.string.custom_dialog_remove);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
				AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		builder.setTitle(getActivity().getString(R.string.custom_dialog_title));
		builder.setItems(items, this);

		Dialog alert = builder.create();
		alert.show();
		return true;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
