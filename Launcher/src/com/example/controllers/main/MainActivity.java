package com.example.controllers.main;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.example.controllers.fragments.CustomFragment;
import com.example.controllers.fragments.DrawerAppsFragment;
import com.example.controllers.fragments.SmartFragment;
import com.example.launcher.R;
import com.example.moduleApps.AppsManager;

public class MainActivity extends FragmentActivity implements
		ViewPager.OnPageChangeListener {
	private static MainActivity instance;
	public static List<SpaceItem> spaces;
	public static int mainSpace = 1;
	public static int selectedSpace = 1;

	private ViewPager mViewPager;
	private static HomePagerAdapter mHomePagerAdapter;
	private MainRightDrawer mainRightDrawer;

	public static MainActivity getInstance() {
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		AppsManager.getInstance(this);

		// Important!!! Navigation Bar transparent
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

		// Setup Spaces
		setupSpaces();

		// Set Content
		setContentView(R.layout.controllers_main_activity);
		mViewPager = (ViewPager) findViewById(R.id.pager);

		// Setup drawers
		mainRightDrawer = new MainRightDrawer(
				(DrawerLayout) findViewById(R.id.drawer_layout), mViewPager);

		// SetupViewPager
		setupViewPager();
	}

	@Override
	public void onBackPressed() {
		mViewPager.setCurrentItem(mainSpace);
	}

	private void setupSpaces() {
		spaces = new ArrayList<SpaceItem>();
		spaces.add(new SpaceItem(this, R.string.drawer_apps_title,
				R.drawable.ic_action_content_clear, DrawerAppsFragment.class));
		spaces.add(new SpaceItem(this, R.string.smart_title,
				R.drawable.ic_launcher, SmartFragment.class));
		spaces.add(new SpaceItem(this, R.string.custom_title,
				R.drawable.ic_launcher, CustomFragment.class));
	}

	private void setupViewPager() {
		mHomePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(),
				spaces);
		mViewPager.setAdapter(mHomePagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(mainSpace);
		mViewPager.setOffscreenPageLimit(spaces.size());
		mViewPager.setPageTransformer(false, new ViewPagerTransitions(
				ViewPagerTransitions.TRANSITION_TABLET));
	}

	public static void removeSpace(Fragment fragment) {
		for (SpaceItem item : spaces) {
			if (item.getFragment().equals(fragment)) {
				spaces.remove(item);
				mHomePagerAdapter.setFragments(spaces);
				mHomePagerAdapter.notifyDataSetChanged();
				break;
			}
		}
	}

	private static class HomePagerAdapter extends FragmentPagerAdapter {
		private List<SpaceItem> mFragments;

		public HomePagerAdapter(FragmentManager fragmentManager,
				List<SpaceItem> fragments) {
			super(fragmentManager);
			this.mFragments = fragments;
		}

		public void setFragments(List<SpaceItem> mFragments) {
			this.mFragments = mFragments;
		}

		/*
		 * @Override public void destroyItem(View collection, int position,
		 * Object o) { View view = (View) o; ((ViewPager)
		 * collection).removeView(view); view = null; }
		 */

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public Fragment getItem(int index) {
			try {
				return mFragments.get(index).getFragment();
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		selectedSpace = arg0;
		mainRightDrawer.setSelectedView(selectedSpace);
	}
}
