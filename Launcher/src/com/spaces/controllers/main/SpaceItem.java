package com.spaces.controllers.main;

import com.example.launcher.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SpaceItem {

	private LayoutInflater mInflater;

	private int name;
	private int image;
	private Class className;
	private Fragment fragment;

	public SpaceItem(Context context, int name, int image, Class className) {
		this.mInflater = LayoutInflater.from(context);
		this.image = image;
		this.name = name;
		this.className = className;
		try {
			this.fragment = (Fragment) this.className.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public int getImage() {
		return image;
	}

	public int getName() {
		return name;
	}

	public Class getClassName() {
		return className;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public View getView(ViewGroup parent) {
		View returnView = mInflater
				.inflate(R.layout.controllers_main_right_drawer_list_item,
						parent, false);

		TextView nameView = (TextView) returnView.findViewById(R.id.title_list);
		nameView.setText(name);

		ImageView imageView = (ImageView) returnView
				.findViewById(R.id.image_list);
		imageView.setContentDescription("hola");
		imageView.setImageResource(image);

		return returnView;
	}
}
