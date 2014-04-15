package com.example.ui;

import com.example.launcher.R;
import com.example.ui.appsdrawer.AppsFragment;
import com.example.ui.appsdrawer.AppsGridAdapter;
import com.example.ui.appsdrawer.AppsGridClickListener;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class TodayFragment extends Fragment {
	private View mView;
	/*private GridView mRecomendedAppsGrid;
	private AppsGridAdapter mRecomendedAppsGridAdapter;
	private PackageManager pm;*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.today_fragment, container, false);
		//loadGridView();
		return mView;
	}
	
	// Hay algo que est‡ mal hecho
	private void loadGridView() {
		/*mRecomendedAppsGrid = (GridView) mView.findViewById(R.id.appsGridRecomended);
		pm = getActivity().getPackageManager();

		mRecomendedAppsGridAdapter = new AppsGridAdapter(getActivity()
				.getApplicationContext(), AppsFragment.listApps);
		mRecomendedAppsGrid.setAdapter(mRecomendedAppsGridAdapter);

		AppsGridClickListener gridClickListener = new AppsGridClickListener(
				getActivity().getApplicationContext(), pm);
		AppsGridClickListener.listApps = AppsFragment.listApps;
		mRecomendedAppsGrid.setOnItemClickListener(gridClickListener);*/
	}
	
}
