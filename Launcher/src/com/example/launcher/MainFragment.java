package com.example.launcher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v4.app.Fragment;
import java.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class MainFragment extends Fragment implements OnItemSelectedListener,
		OnClickListener {
	private View mView;
	private Spinner mSpinner;

	private TextView sendEmail;
	private TextView newContact;
	private TextView newEvent;
	private TextView newAlarm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.main_fragment, container, false);
		loadSpinner();
		loadActions();
		return mView;
	}

	public void loadSpinner() {
		mSpinner = (Spinner) mView.findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(), android.R.layout.simple_spinner_item);
		adapter.add("Minimalista");
		adapter.add("Inteligente");
		adapter.add("+ A–adir Espacio");
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapter);
		mSpinner.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void loadActions() {
		sendEmail = (TextView) mView.findViewById(R.id.sendEmail);
		newContact = (TextView) mView.findViewById(R.id.newContact);
		newEvent = (TextView) mView.findViewById(R.id.newEvent);
		newAlarm = (TextView) mView.findViewById(R.id.newAlarm);

		sendEmail.setOnClickListener(this);
		newContact.setOnClickListener(this);
		newEvent.setOnClickListener(this);
		newAlarm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v instanceof TextView) {
			if (v.equals(sendEmail)) {
				ActionsIntents.senEmail(getActivity());
			} else if (v.equals(newContact)) {
				ActionsIntents.newContact(getActivity());
			} else if (v.equals(newEvent)) {
				ActionsIntents.newEvent(getActivity());
			} else {
				ActionsIntents.newAlarm(getActivity());
			}
		}
	}

	/*
	 * public void setTime() { TextView timeText = (TextView)
	 * mView.findViewById(R.id.time);
	 * 
	 * String format = "HH:mm"; SimpleDateFormat sdf = new
	 * SimpleDateFormat(format, Locale.getDefault());
	 * 
	 * timeText.setText(sdf.format(new Date())); timeText.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { Intent alarmClockIntent = new
	 * Intent(); alarmClockIntent.setAction(AlarmClock.ACTION_SHOW_ALARMS);
	 * alarmClockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 * getActivity().startActivity(alarmClockIntent); } }); }
	 */

	/*
	 * public void setDate() { TextView dateText = (TextView)
	 * mView.findViewById(R.id.date);
	 * 
	 * DateFormat dateFormat =
	 * android.text.format.DateFormat.getLongDateFormat(getActivity
	 * ().getBaseContext()); dateText.setText(dateFormat.format(new Date())); }
	 */

}