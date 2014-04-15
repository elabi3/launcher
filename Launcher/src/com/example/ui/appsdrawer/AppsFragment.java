package com.example.ui.appsdrawer;

import java.util.ArrayList;
import java.util.List;

import com.example.data.AppPack;
import com.example.launcher.R;
import com.example.utilities.SortApps;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.GridView;
import android.widget.Spinner;

public class AppsFragment extends Fragment implements OnItemSelectedListener {
	private View mView;
	private Spinner mSpinner;
	private GridView mAppsGrid;
	private AppsGridAdapter mAppsGridAdapter;
	private EditText textSearch;
	private Button buttonClose;

	private List<AppPack> listApps;
	private PackageManager pm;

	private String[] mSpinnerElements = { "Alfabéticamente (AZ)",
			"Alfabéticamente (ZA)", "Actualizaciones Recientes",
			"Instalaciones Recientes",
	/* "Recientes" */};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.apps_fragment, container, false);

		loadGridView();
		loadSpinner();

		buttonClose = (Button) mView.findViewById(R.id.button_close);
		buttonClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textSearch.setText("");
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(textSearch.getWindowToken(), 0);
			}
		});

		textSearch = (EditText) mView.findViewById(R.id.edit_text1);
		textSearch.addTextChangedListener(filterTextWatcher);
		getActivity().getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		return mView;
	}

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

	private void loadGridView() {
		mAppsGrid = (GridView) mView.findViewById(R.id.appsGrid);

		pm = getActivity().getPackageManager();
		set_pacs();
		mAppsGridAdapter = new AppsGridAdapter(getActivity()
				.getApplicationContext(), listApps);
		mAppsGrid.setAdapter(mAppsGridAdapter);

		AppsGridClickListener gridClickListener = new AppsGridClickListener(
				getActivity().getApplicationContext(), pm);
		AppsGridClickListener.listApps = listApps;
		mAppsGrid.setOnItemClickListener(gridClickListener);
	}

	private void set_pacs() {
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> pacList = pm.queryIntentActivities(mainIntent, 0);
		listApps = new ArrayList<AppPack>();

		for (ResolveInfo resolveInfo : pacList) {
			listApps.add(new AppPack(resolveInfo.loadIcon(pm), resolveInfo
					.loadLabel(pm).toString(),
					resolveInfo.activityInfo.packageName, getActivity()));
		}
	}

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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {

		switch (pos) {
		case 0:
			SortApps.sortByName(listApps, false);
			break;
		case 1:
			SortApps.sortByName(listApps, true);
			break;
		case 2:
			SortApps.sortByLastUpdateTime(listApps);
			break;
		case 3:
			SortApps.sortByInstallTime(listApps);
			break;
		case 4: {
			ActivityManager am = (ActivityManager) getActivity()
					.getSystemService(getActivity().ACTIVITY_SERVICE);
			List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am
					.getRunningAppProcesses();
			// SortApps.sortByRecents(pacs, runningAppProcessInfo);
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
