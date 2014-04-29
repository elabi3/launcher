package com.example.ui;

import com.example.launcher.R;
import com.example.ui.appsdrawer.AppsFragment;
import com.example.ui.main.MainFragment;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements
		com.jfeinstein.jazzyviewpager.JazzyViewPager.OnPageChangeListener {

	private static JazzyViewPager mJazzy;
	private Class<?>[] mFragments = new Class<?>[] { /* TodayFragment.class, */
	MainFragment.class, AppsFragment.class };

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("");
		getActionBar().setIcon(android.R.color.transparent);
		setupJazziness(TransitionEffect.Tablet);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_element);
		mDrawerLayout.setScrimColor(Color.TRANSPARENT);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		/*
		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		*/
	}

	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),
				mFragments));
		mJazzy.setPageMargin(30);
		mJazzy.setCurrentItem(0);
		mJazzy.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		CustomFragment selectedFragment = null;
		try {
			selectedFragment = (CustomFragment) mFragments[arg0].newInstance();
		} catch (Exception e) {

		}
		if (selectedFragment != null) {
			setTitle(selectedFragment.getTitle());
			selectedFragment.setActionBar();
			// TODO: Poner una animacion de cambio de alpha y que se seleccione
			// desde el fragment cambio == 0
			if (arg0 == 1) {
				View view = this.getWindow().getDecorView();
				view.setBackgroundColor(Color.parseColor("#80000000"));
			} else {
				View view = this.getWindow().getDecorView();
				view.setBackgroundColor(Color.TRANSPARENT);
			}
		}
	}

	private static class HomePagerAdapter extends FragmentPagerAdapter {
		private final Class<?>[] mFragments;

		public HomePagerAdapter(FragmentManager fragmentManager,
				Class<?>[] fragments) {
			super(fragmentManager);
			this.mFragments = fragments;
		}

		@Override
		public int getCount() {
			return mFragments.length;
		}

		@Override
		public Fragment getItem(int index) {
			try {
				return (Fragment) mFragments[index].newInstance();
			} catch (Exception e) {
				return null;
			}
		}

		// Metodos necesarios para JazzyViewPager
		@Override
		public boolean isViewFromObject(View view, Object object) {
			if (object != null) {
				return ((Fragment) object).getView() == view;
			} else {
				return false;
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			Object obj = super.instantiateItem(container, position);
			mJazzy.setObjectForPosition(obj, position);
			return obj;
		}
	}

}
