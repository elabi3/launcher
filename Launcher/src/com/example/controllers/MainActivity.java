package com.example.controllers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.launcher.R;

public class MainActivity extends FragmentActivity implements
		ViewPager.OnPageChangeListener {
	private static MainActivity instance;
	private Class<?>[] mFragments = new Class<?>[] { DrawerFragment.class,
			MinimalistFragment.class };
	private ViewPager mViewPager;
	private DrawerLayout mDrawerLayout;

	public static MainActivity getInstance() {
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;

		// Important!!! Navigation Bar transparent
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

		// Init elements 
		setContentView(R.layout.controllers_main_activity);
		setupViewPager();

		// setup drawer
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_element);
		// mDrawerLayout.setScrimColor(Color.TRANSPARENT);
	}

	private void setupViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.pager);

		/*
		 * mViewPager.getViewTreeObserver().addOnPreDrawListener( new
		 * ViewTreeObserver.OnPreDrawListener() {
		 * 
		 * @Override public boolean onPreDraw() { Bitmap bmp = ((BitmapDrawable)
		 * getWallpaper()) .getBitmap();
		 * 
		 * blur(Bitmap.createScaledBitmap(bmp, mViewPager.getWidth(),
		 * mViewPager.getHeight(), true), mViewPager); System.gc(); return true;
		 * } });
		 */

		mViewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),
				mFragments));
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(1);
	}

	private void blur(Bitmap bkg, View view) {
		float radius = 25;

		Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
				(int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);

		canvas.translate(-view.getLeft(), -view.getTop());
		canvas.drawBitmap(bkg, 0, 0, null);

		RenderScript rs = RenderScript.create(this);
		Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
		ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs,
				overlayAlloc.getElement());

		blur.setInput(overlayAlloc);
		blur.setRadius(radius);
		blur.forEach(overlayAlloc);
		overlayAlloc.copyTo(overlay);

		view.setBackground(new BitmapDrawable(getResources(), overlay));
		overlayAlloc.destroy();
		rs.destroy();
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

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {

	}
}
