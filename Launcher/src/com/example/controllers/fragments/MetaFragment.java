package com.example.controllers.fragments;

import com.example.controllers.main.SpaceItem;
import android.support.v4.app.Fragment;

public class MetaFragment extends Fragment {

	public SpaceItem mItem;

	public static Fragment newInstance(SpaceItem item) {
		MetaFragment fragmentFirst = new MetaFragment();
		fragmentFirst.mItem = item;
		return fragmentFirst;
	}

}
