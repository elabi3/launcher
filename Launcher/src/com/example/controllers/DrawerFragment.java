package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.launcher.R;
import com.example.moduleApps.controllers.AppsGrid;

public class DrawerFragment extends Fragment implements OnClickListener {
	private View mView;
	private EditText textSearch;
	private Button buttonClose;
	private AppsGrid appsGrid;

	private Button button;
	private List<Button> buttons;
	private Boolean open = false;
	private WindowManager wm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_drawer_fragment,
				container, false);

		wm = (WindowManager) getActivity().getSystemService(
				getActivity().WINDOW_SERVICE);

		loadGridApps();
		loadTextSearch();
		loadFilter();

		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		return mView;
	}

	/********************************************
	 * Grid apps
	 ********************************************/

	private void loadGridApps() {
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.content);

		// create and add grid
		appsGrid = new AppsGrid(getActivity(), AppsGrid.GRID_DRAWER,
				AppsGrid.APPS_GRID_ALL, AppsGrid.NO_MAXIMUN_LIMIT, 0);
		layout.addView(appsGrid.getGridView());

		/*appsGrid.getGridView().getViewTreeObserver()
				.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

					@Override
					public boolean onPreDraw() {
						Bitmap bmp = ((BitmapDrawable) getActivity()
								.getWallpaper()).getBitmap();
						blur(bmp, appsGrid.getGridView());
						return true;
					}
				});*/
	}

	private void blur(Bitmap bkg, View view) {
		float radius = 20;

		Bitmap overlay = Bitmap.createBitmap((int) wm.getDefaultDisplay().getWidth(),
				(int) wm.getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(overlay);

		canvas.translate(-view.getLeft(), -view.getTop());
		canvas.drawBitmap(bkg, 0, 0, null);

		RenderScript rs = RenderScript.create(getActivity());

		Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);

		ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs,
				overlayAlloc.getElement());

		blur.setInput(overlayAlloc);
		blur.setRadius(radius);
		blur.forEach(overlayAlloc);
		overlayAlloc.copyTo(overlay);
		view.setBackground(new BitmapDrawable(getResources(), overlay));
		rs.destroy();
	}

	/********************************************
	 * Button Sort
	 ********************************************/

	private void loadFilter() {
		button = (Button) mView.findViewById(R.id.button);
		button.setOnClickListener(this);

		buttons = new ArrayList<Button>();
		buttons.add((Button) mView.findViewById(R.id.button_2));
		buttons.add((Button) mView.findViewById(R.id.button_3));
		buttons.add((Button) mView.findViewById(R.id.button_4));

		for (Button b : buttons) {
			b.setOnClickListener(this);
		}
	}

	private void openMenu() {
		open = true;
		float width = wm.getDefaultDisplay().getWidth();
		float percentage = 0.20f;
		long duration = 150;

		for (Button b : buttons) {
			ObjectAnimator alpha = ObjectAnimator.ofFloat(b, "alpha", 0, 1);
			alpha.setDuration(duration);
			alpha.start();

			ObjectAnimator moverX = ObjectAnimator.ofFloat(b, "translationX",
					0, width * percentage);

			moverX.setDuration(duration);
			moverX.start();

			/*
			 * ObjectAnimator moverY = ObjectAnimator.ofFloat(b, "translationY",
			 * 0, width * -percentage);
			 * 
			 * moverY.setDuration(duration); moverY.start();
			 */

			percentage = percentage + 0.24f;
		}
	}

	private void closeMenu() {
		open = false;
		float width = wm.getDefaultDisplay().getWidth();
		float percentage = 0.20f;
		long duration = 150;

		for (Button b : buttons) {
			ObjectAnimator alpha = ObjectAnimator.ofFloat(b, "alpha", 1, 0);
			alpha.setDuration(duration);
			alpha.start();

			ObjectAnimator mover = ObjectAnimator.ofFloat(b, "translationX",
					width * percentage, 0);
			mover.setDuration(duration);
			mover.start();

			percentage = percentage + 0.24f;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			if (!open) {
				openMenu();
			} else {
				closeMenu();
			}
			break;
		case R.id.button_2:
			appsGrid.sortAppsBy(AppsGrid.APPS_GRID_INSTALL_ORDER);
			closeMenu();
			break;
		case R.id.button_3:
			closeMenu();
			break;
		case R.id.button_4:
			appsGrid.sortAppsBy(AppsGrid.APPS_GRID_AZ_ORDER);
			closeMenu();
			break;
		}
	}

	/********************************************
	 * Search
	 ********************************************/

	private void loadTextSearch() {
		buttonClose = (Button) mView.findViewById(R.id.button_close);
		buttonClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				textSearch.setText("");
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(getActivity().INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(textSearch.getWindowToken(), 0);
			}
		});

		textSearch = (EditText) mView.findViewById(R.id.edit_text1);
		textSearch.addTextChangedListener(filterTextWatcher);
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {

		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			appsGrid.filterList(s);
		}
	};
}