package com.example.launcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity implements
		ViewPager.OnPageChangeListener {

	private ViewPager mViewPager;

	private Class<?>[] mFragments = new Class<?>[] {
			MainFragment.class,
			AppsFragment.class
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),
				mFragments));
		mViewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

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
	}

}
