package com.example.ui;

import com.example.launcher.R;
import com.example.ui.appsdrawer.AppsFragment;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity /*implements
		ViewPager.OnPageChangeListener*/ {

	//private ViewPager mViewPager;
	private static JazzyViewPager mJazzy;

	private Class<?>[] mFragments = new Class<?>[] {
			TodayFragment.class,
			MainFragment.class,
			AppsFragment.class
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),
				mFragments));
		mViewPager.setOnPageChangeListener(this);*/
		setupJazziness(TransitionEffect.Tablet);
	}

	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),
				mFragments));
		mJazzy.setPageMargin(30);
		mJazzy.setCurrentItem(1);
	}
	
	/*@Override
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

	}*/

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
		    if(object != null){
		        return ((Fragment)object).getView() == view;
		    }else{
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
