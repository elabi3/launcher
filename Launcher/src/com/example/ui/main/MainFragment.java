package com.example.ui.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.launcher.R;
import com.example.utilities.ActionsIntents;

public class MainFragment extends Fragment implements OnClickListener {
	private View mView;
	private Handler handler = new Handler();
	private TextView timeHour;
	private TextView timeMin;

	private TextView sendEmail;
	private TextView newContact;
	private TextView newEvent;
	private TextView newAlarm;
	private TextView torch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.main_fragment, container, false);
		loadClock();
		loadActions();

		return mView;
	}
	
	public void loadClock() {
		timeHour = (TextView) mView.findViewById(R.id.timeHour);
		timeMin = (TextView) mView.findViewById(R.id.timeMin);
		
		handler = new Handler();
		handler.postDelayed(runnable, 1000);
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			/* do what you need to do */
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH");
			SimpleDateFormat dateFormatMin = new SimpleDateFormat("mm");
			SimpleDateFormat dateFormatSec = new SimpleDateFormat("ss");

			// Revisar para poner segœn el localized
			SimpleDateFormat dateFormatDate = new SimpleDateFormat(
					"dd:MMMM:yyyy");

			final String strDateHour = dateFormatHour
					.format(calendar.getTime());
			final String strDateMin = dateFormatMin.format(calendar.getTime());
			final String strDateSec = dateFormatSec.format(calendar.getTime());
			final String strDate = dateFormatDate.format(calendar.getTime());

			timeHour.setText(strDateHour);
			timeMin.setText(strDateMin);

			/* and here comes the "trick" */
			handler.postDelayed(this, 1000);
		}
	};

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
				ActionsIntents.turnTorch();
			}
		}
	}

}