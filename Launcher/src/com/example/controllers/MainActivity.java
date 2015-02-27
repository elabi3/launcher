package com.example.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.auxiliar.ActionsIntents;
import com.example.launcher.R;

public class MainActivity extends FragmentActivity implements
		ViewPager.OnPageChangeListener, OnClickListener {
	private static MainActivity instance;

	private ViewPager mViewPager;
	private Class<?>[] mFragments = new Class<?>[] { AppsDrawerFragment.class,
			MinimalistFragment.class };
	private DrawerLayout mDrawerLayout;

	// Acciones
	private TextView sendEmail;
	private TextView newContact;
	private TextView newEvent;
	private TextView newAlarm;
	private TextView torch;

	public static MainActivity getInstance() {
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;

		setContentView(R.layout.controllers_main_activity);
		setupViewPager();
		// setup drawer
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_element);
		mDrawerLayout.setScrimColor(Color.TRANSPARENT);

		// Load Actions
		loadActions();
	}

	private void setupViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager
				.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),
						mFragments));
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(1);
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

	// Acciones
	public void loadActions() {
		sendEmail = (TextView) findViewById(R.id.sendEmail);
		newContact = (TextView) findViewById(R.id.newContact);
		newEvent = (TextView) findViewById(R.id.newEvent);
		newAlarm = (TextView) findViewById(R.id.newAlarm);
		torch = (TextView) findViewById(R.id.torch);

		sendEmail.setOnClickListener(this);
		newContact.setOnClickListener(this);
		newEvent.setOnClickListener(this);
		newAlarm.setOnClickListener(this);
		torch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v instanceof TextView) {
			if (v.equals(sendEmail)) {
				ActionsIntents.senEmail(this);
			} else if (v.equals(newContact)) {
				ActionsIntents.newContact(this);
			} else if (v.equals(newEvent)) {
				ActionsIntents.newEvent(this);
			} else if (v.equals(newAlarm)) {
				ActionsIntents.newAlarm(this);
			} else {
				Intent intent = new Intent(this, Settings.class);
				startActivity(intent);
				// ActionsIntents.turnTorch();
			}
		}
	}

}
