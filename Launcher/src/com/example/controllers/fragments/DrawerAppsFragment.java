package com.example.controllers.fragments;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.launcher.R;
import com.example.moduleApps.controllers.AppsGrid;

public class DrawerAppsFragment extends Fragment implements OnClickListener {
	private View mView;
	private EditText textSearch;
	private Button buttonClose;
	private AppsGrid appsGrid;

	private Button button;
	private List<Button> buttons;
	private Boolean open = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		appsGrid = new AppsGrid(getActivity(), AppsGrid.GRID_DRAWER,
				AppsGrid.APPS_GRID_ALL, AppsGrid.NO_MAXIMUN_LIMIT, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_fragments_drawer_apps,
				container, false);

		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.content);
		layout.addView(appsGrid.getGridView());

		loadTextSearch();
		loadFilter();
		return mView;
	}

	/********************************************
	 * Button Sort
	 ********************************************/

	private void loadFilter() {
		int[] buttonsIds = { R.id.button_1, R.id.button_2, R.id.button_4,
				R.id.button_5 };
		button = (Button) mView.findViewById(R.id.button);
		button.setOnClickListener(this);

		buttons = new ArrayList<Button>();
		for (int id : buttonsIds) {
			Button b = (Button) mView.findViewById(id);
			b.setOnClickListener(this);
			buttons.add(b);
		}

		buttons.get(0).setBackgroundResource(R.drawable.background_button_selected);
	}

	private void openMenu() {
		Point point = new Point();
		((WindowManager) getActivity().getSystemService(
				getActivity().WINDOW_SERVICE)).getDefaultDisplay().getSize(
				point);
		float percentage = 0.10f;
		long duration = 200;

		for (Button b : buttons) {
			ObjectAnimator alpha, mover;

			if (!open) {
				alpha = ObjectAnimator.ofFloat(b, "alpha", 0, 1);
				mover = ObjectAnimator.ofFloat(b, "translationY", 0, point.y
						* percentage);
			} else {
				alpha = ObjectAnimator.ofFloat(b, "alpha", 1, 0);
				mover = ObjectAnimator.ofFloat(b, "translationY", point.y
						* percentage, 0);
			}
			alpha.setDuration(duration).start();
			mover.setDuration(duration).start();
			percentage = percentage + 0.10f;
		}
		open = !open;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_1:
			appsGrid.sortAppsBy(AppsGrid.APPS_GRID_AZ_ORDER);
			break;
		case R.id.button_2:
			break;
		case R.id.button_4:
			appsGrid.sortAppsBy(AppsGrid.APPS_GRID_INSTALL_ORDER);
			break;
		case R.id.button_5:
			appsGrid.sortAppsBy(AppsGrid.APPS_GRID_UPDATE_ORDER);
			break;
		}
		if (v.getId() != R.id.button) {
			for (Button b : buttons) {
				b.setBackgroundResource(R.drawable.background_button);
			}
			v.setBackgroundResource(R.drawable.background_button_selected);
		}
		openMenu();
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
				buttonClose.setVisibility(View.GONE);
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
			buttonClose.setVisibility(s.length() == 0 ? View.GONE
					: View.VISIBLE);
			appsGrid.filterList(s);
		}
	};
}