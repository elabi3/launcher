package com.example.launcher;

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

public class AppsGridAdapter extends BaseAdapter implements Filterable {
	private Context mContext;
	private List<AppPack> listApps;
	private List<AppPack> originalListApps;
	
	public AppsGridAdapter(Context c, List<AppPack> listApps) {
		this.mContext = c;
		this.listApps = listApps;
		this.originalListApps = listApps;
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

	@Override
	public Filter getFilter() {
		Filter listfilter = new MyFilter();
		return listfilter;
	}

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
				AppsGridClickListener.listApps = listApps;
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}

	}
}
