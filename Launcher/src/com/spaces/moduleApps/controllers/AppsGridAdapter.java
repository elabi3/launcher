package com.spaces.moduleApps.controllers;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.spaces.moduleApps.model.AppPack;

public class AppsGridAdapter extends BaseAdapter implements Filterable {
	private List<AppPack> listApps;
	private List<AppPack> originalListApps;
	private AppsGridClickListener gridClickListener;
	private AppsGridLongClickListener gridLongClickListener;
	private int type;

	public AppsGridAdapter(int type, List<AppPack> listApps,
			AppsGridClickListener gridClickListener,
			AppsGridLongClickListener gridLongClickListener) {
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
		this.notifyDataSetChanged();
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

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		if (this.type == AppsGrid.GRID_DRAWER) {
			return listApps.get(pos).getView();
		}

		return listApps.get(pos).getViewCompact();
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
