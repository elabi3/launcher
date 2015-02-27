package com.example.moduleApps.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.launcher.R;
import com.example.moduleApps.model.AppPack;

public class AppsGridAdapter extends BaseAdapter implements Filterable {
	private Context mContext;
	private List<AppPack> listApps;
	private List<AppPack> originalListApps;
	private AppsGridClickListener gridClickListener;
	private AppsGridLongClickListener gridLongClickListener;
	private int type;

	public AppsGridAdapter(Context c, int type, List<AppPack> listApps,
			AppsGridClickListener gridClickListener,
			AppsGridLongClickListener gridLongClickListener) {
		this.mContext = c;
		this.type = type;
		this.listApps = listApps;
		this.originalListApps = listApps;
		this.gridClickListener = gridClickListener;
		this.gridLongClickListener = gridLongClickListener;
	}

	public void updateList(List<AppPack> listApps) {
		this.listApps = listApps;
		this.originalListApps = listApps;
		gridClickListener.setListApps(originalListApps);
		gridLongClickListener.setListApps(originalListApps);
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
			if (this.type == AppsGrid.GRID) {
				convertView = li.inflate(R.layout.module_apps_grid_item, null);

				viewHolder = new ViewHolder();
				viewHolder.icon = (ImageView) convertView
						.findViewById(R.id.icon_image);
				viewHolder.text = (TextView) convertView
						.findViewById(R.id.icon_text);

				convertView.setTag(viewHolder);
			} else {
				convertView = li.inflate(
						R.layout.module_apps_grid_item_compact, null);

				viewHolder = new ViewHolder();
				viewHolder.icon = (ImageView) convertView
						.findViewById(R.id.icon_image);

				convertView.setTag(viewHolder);
			}
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.icon.setImageDrawable(listApps.get(pos).getIcon());
		viewHolder.icon.setContentDescription(listApps.get(pos).getName());

		if (this.type == AppsGrid.GRID) {
			viewHolder.text.setText(listApps.get(pos).getName());
		}
		return convertView;
	}

	public Filter getFilter() {
		Filter listfilter = new MyFilter();
		return listfilter;
	}

	/********************************************
	 * Filter Class
	 ********************************************/

	private class MyFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults result = new FilterResults();

			if (constraint.length() == 0) {
				result.values = originalListApps;
				result.count = originalListApps.size();
				return result;
			}

			List<AppPack> filteredApps = new ArrayList<AppPack>();
			String filterString = constraint.toString().toLowerCase();

			for (AppPack originalApp : originalListApps) {
				if (originalApp.getName().toLowerCase().contains(filterString)) {
					filteredApps.add(originalApp);
				}
			}

			result.values = filteredApps;
			result.count = filteredApps.size();

			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			if (results.count > 0) {
				listApps = (List<AppPack>) results.values;
				gridClickListener.setListApps(listApps);
				gridLongClickListener.setListApps(listApps);
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}

	}
}
