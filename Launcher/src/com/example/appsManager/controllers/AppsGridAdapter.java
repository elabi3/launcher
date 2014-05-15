package com.example.appsManager.controllers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appsManager.model.AppPack;
import com.example.launcher.R;

public class AppsGridAdapter extends BaseAdapter {
	private Context mContext;
	private List<AppPack> listApps;

	public AppsGridAdapter(Context c, List<AppPack> listApps) {
		this.mContext = c;
		this.listApps = listApps;
	}

	@Override
	public int getCount() {
		return listApps.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	private static class ViewHolder {
		ImageView icon;
		TextView text;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		LayoutInflater li = (LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = li.inflate(R.layout.app_grid_item, null);

			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.icon_image);
			viewHolder.text = (TextView) convertView
					.findViewById(R.id.icon_text);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.icon.setImageDrawable(listApps.get(pos).getIcon());
		viewHolder.text.setText(listApps.get(pos).getName());
		return convertView;
	}
}
