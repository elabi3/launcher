package com.example.ui.appsdrawer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.launcher.R;
import com.example.ui.CustomFragment;
import com.example.utilities.AppsManager;

public class AppsFragment extends CustomFragment implements
		OnItemSelectedListener {
	private View mView;
	private Spinner mSpinner;
	private GridView mAppsGrid;
	private AppsGridAdapter mAppsGridAdapter;
	private EditText textSearch;
	private Button buttonClose;

	private String[] mSpinnerElements = { "Alfabéticamente (AZ)",
			"Alfabéticamente (ZA)", "Actualizaciones Recientes",
			"Instalaciones Recientes",
	/* "Recientes" */};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.apps_fragment, container, false);

		 //loadSpinner();
		// loadTextSearch();
		loadGridView();

		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		return mView;
	}

	@Override
	public String getTitle() {
		return "Apps";
	}

	@Override
	public void setActionBar() {
		//getActivity().getActionBar().setCustomView(mSpinner);
	}

	/********************************************
	 * Spinner
	 ********************************************/

	/*
	 * private void loadSpinner() { mSpinner = (Spinner)
	 * mView.findViewById(R.id.spinnerApps); ArrayAdapter<String> adapter = new
	 * ArrayAdapter<String>(getActivity() .getApplicationContext(),
	 * android.R.layout.simple_spinner_item);
	 * 
	 * for (String element : mSpinnerElements) { adapter.add(element); }
	 * 
	 * adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
	 * ); mSpinner.setAdapter(adapter);
	 * mSpinner.setOnItemSelectedListener(this); }
	 */

	/********************************************
	 * Search
	 ********************************************/

	/*
	 * private void loadTextSearch() { buttonClose = (Button)
	 * mView.findViewById(R.id.button_close); buttonClose.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { textSearch.setText("");
	 * InputMethodManager imm = (InputMethodManager)
	 * getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
	 * imm.hideSoftInputFromWindow(textSearch.getWindowToken(), 0); } });
	 * 
	 * textSearch = (EditText) mView.findViewById(R.id.edit_text1);
	 * textSearch.addTextChangedListener(filterTextWatcher); }
	 */

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {

		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			mAppsGridAdapter.getFilter().filter(s);
		}
	};

	/********************************************
	 * Grid
	 ********************************************/

	private void loadGridView() {
		mAppsGrid = (GridView) mView.findViewById(R.id.appsGrid);
		mAppsGridAdapter = new AppsGridAdapter(getActivity()
				.getApplicationContext(), AppsManager.getInstance(getActivity()).getAppsByName());
		mAppsGrid.setAdapter(mAppsGridAdapter);

		AppsGridClickListener gridClickListener = new AppsGridClickListener(
				getActivity().getApplicationContext(), AppsManager.getInstance(getActivity()).getPackageManager());
		AppsGridClickListener.listApps = AppsManager.getInstance(getActivity()).getAppsByName();
		mAppsGrid.setOnItemClickListener(gridClickListener);
	}
	
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {

		switch (pos) {
		case 0:
			//SortApps.sortByName(listApps, false);
			break;
		case 1:
			//SortApps.sortByName(listApps, true);
			break;
		case 2:
			//SortApps.sortByLastUpdateTime(listApps);
			break;
		case 3:
			//SortApps.sortByInstallTime(listApps);
			break;
		case 4: {

		}
		default:
			break;
		}
		mAppsGridAdapter.notifyDataSetChanged();
	}
	

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
