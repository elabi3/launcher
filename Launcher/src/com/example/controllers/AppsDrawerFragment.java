package com.example.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.launcher.R;
import com.example.moduleApps.controllers.AppsGrid;

public class AppsDrawerFragment extends Fragment implements
		OnItemSelectedListener {
	private View mView;
	private Spinner mSpinner;
	private EditText textSearch;
	private Button buttonClose;
	private AppsGrid appsGrid;

	private String[] mSpinnerElements = { "Alfabéticamente (AZ)",
			"Alfabéticamente (ZA)", "Actualizaciones Recientes",
			"Instalaciones Recientes" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.controllers_apps_fragment, container, false);

		loadGridApps();
		loadSpinner();
		loadTextSearch();

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
		appsGrid = new AppsGrid(getActivity(), AppsGrid.APPS_GRID_ALL, AppsGrid.NO_MAXIMUN_LIMIT, 0);
		layout.addView(appsGrid.getGridView());
	}

	/********************************************
	 * Spinner
	 ********************************************/

	private void loadSpinner() {
		mSpinner = (Spinner) mView.findViewById(R.id.spinnerApps);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(), android.R.layout.simple_spinner_item);

		for (String element : mSpinnerElements) {
			adapter.add(element);
		}

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapter);
		mSpinner.setOnItemSelectedListener(this);
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {

		switch (pos) {
		case 0:
			appsGrid.sortAppsByName(false);
			break;
		case 1:
			appsGrid.sortAppsByName(true);
			break;
		case 2:
			appsGrid.sortAppsByLastUpdate();
			break;
		case 3:
			appsGrid.sortAppsByInstallTime();
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

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
