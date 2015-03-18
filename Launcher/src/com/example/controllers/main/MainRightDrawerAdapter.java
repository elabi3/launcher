package com.example.controllers.main;

import com.example.launcher.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MainRightDrawerAdapter extends BaseAdapter {

	public MainRightDrawerAdapter() {

	}

	@Override
	public int getCount() {
		return MainActivity.spaces.size();
	}

	@Override
	public Object getItem(int position) {
		return MainActivity.spaces.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View returnView = MainActivity.spaces.get(position).getView(parent);
		if (position == MainActivity.selectedSpace) {
			returnView.setBackgroundResource(R.color.White_transparent);
		}
		return returnView;
	}
}