package com.example.ui;

import com.example.launcher.R;
import com.example.utilities.ActionsIntents;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainFragment extends Fragment implements OnItemSelectedListener,
		OnClickListener {
	private View mView;
	private Spinner mSpinner;

	private TextView sendEmail;
	private TextView newContact;
	private TextView newEvent;
	private TextView newAlarm;
	private TextView torch;

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
		torch = (TextView) mView.findViewById(R.id.torch);

		sendEmail.setOnClickListener(this);
		newContact.setOnClickListener(this);
		newEvent.setOnClickListener(this);
		newAlarm.setOnClickListener(this);
		torch.setOnClickListener(this);
	}

	// Set boolean flag when torch is turned on/off
	private boolean isFlashOn = false;
	private Camera camera = Camera.open();
	final Parameters p = camera.getParameters();

	@Override
	public void onClick(View v) {
		if (v instanceof TextView) {
			if (v.equals(sendEmail)) {
				ActionsIntents.senEmail(getActivity());
			} else if (v.equals(newContact)) {
				ActionsIntents.newContact(getActivity());
			} else if (v.equals(newEvent)) {
				ActionsIntents.newEvent(getActivity());
			} else if (v.equals(newAlarm)) {
				ActionsIntents.newAlarm(getActivity());
			} else {

				if (isFlashOn) {
					// Set the flashmode to off
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					// Pass the parameter ti camera object
					camera.setParameters(p);
					// Set flag to false
					isFlashOn = false;
					// Set the button text to Torcn-ON
				}
				else {
					// Set the flashmode to on
					p.setFlashMode(Parameters.FLASH_MODE_TORCH);
					// Pass the parameter ti camera object
					camera.setParameters(p);
					// Set flag to true
					isFlashOn = true;
					// Set the button text to Torcn-OFF
				}
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