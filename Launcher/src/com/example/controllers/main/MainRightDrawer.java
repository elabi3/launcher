package com.example.controllers.main;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.launcher.R;

public class MainRightDrawer implements OnItemClickListener {

	private DrawerLayout mLayout;
	private ViewPager mViewPager;
	private ListView list;
	private MainRightDrawerAdapter adapter;

	public MainRightDrawer(DrawerLayout layout, ViewPager viewPager) {
		mLayout = layout;
		mViewPager = viewPager;
		setupListView();
	}

	private void setupListView() {
		list = (ListView) mLayout.findViewById(R.id.drawerRightList);
		adapter = new MainRightDrawerAdapter();
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setOnItemClickListener(this);
	}

	public void setSelectedView(int selected) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		view.setSelected(true);
		mLayout.closeDrawers();

		MainActivity.selectedSpace = position;
		mViewPager.setCurrentItem(MainActivity.selectedSpace);
	}
}
