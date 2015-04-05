package com.example.controllers.main;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.launcher.R;

public class MainRightDrawer implements OnItemClickListener, DrawerListener {

	private DrawerLayout mLayout;
	private ViewPager mViewPager;
	private ListView list;
	private MainRightDrawerAdapter adapter;

	public MainRightDrawer(DrawerLayout layout, ViewPager viewPager) {
		mLayout = layout;
		mViewPager = viewPager;
		setupListView();

		mLayout.setDrawerListener(this);
	}

	private void setupListView() {
		list = (ListView) mLayout.findViewById(R.id.drawerRightList);
		adapter = new MainRightDrawerAdapter();
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setOnItemClickListener(this);
		list.setDividerHeight(0);
	}

	public void setSelectedView(int selected) {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		view.setSelected(true);
		mLayout.closeDrawers();

		MainActivity.selectedSpace = position;
		mViewPager.setCurrentItem(MainActivity.selectedSpace);
	}

	@Override
	public void onDrawerClosed(View view) {

	}

	@Override
	public void onDrawerOpened(View view) {

	}

	@Override
	public void onDrawerSlide(View view, float position) {
		//view.setAlpha(position);
	}

	@Override
	public void onDrawerStateChanged(int arg0) {

	}
}
