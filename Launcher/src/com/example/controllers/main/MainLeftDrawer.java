package com.example.controllers.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.auxiliar.ActionsIntents;
import com.example.auxiliar.ContactsManager;
import com.example.auxiliar.ContactsManager.People;
import com.example.controllers.Settings;
import com.example.launcher.R;

public class MainLeftDrawer implements OnClickListener {
	private DrawerLayout mLayout;
	private Context mContext;
	private Handler mHandler = new Handler();

	private List<TextView> textViews;
	private int[] textViewsIds = { R.id.senEmail, R.id.newEvent, R.id.newAlarm,
			R.id.settings };

	private List<LinearLayout> linearLayouts;
	private int[] linearLayoutsIds = { R.id.contact_0, R.id.contact_1,
			R.id.contact_2, R.id.contact_3, R.id.contact_4, R.id.contact_5 };

	private List<People> people;

	public MainLeftDrawer(DrawerLayout layout, Context context) {
		this.mLayout = layout;
		this.mContext = context;
		setupElements();
		setupContacts();
	}

	private void setupElements() {
		textViews = new ArrayList<TextView>();
		for (int i = 0; i < textViewsIds.length; i++) {
			textViews.add((TextView) mLayout.findViewById(textViewsIds[i]));
			textViews.get(i).setOnClickListener(this);
		}
		ImageView image = (ImageView) mLayout.findViewById(R.id.new_phone_call);
		image.setOnClickListener(this);
	}

	private void setupContacts() {
		linearLayouts = new ArrayList<LinearLayout>();
		for (int i = 0; i < linearLayoutsIds.length; i++) {
			linearLayouts.add((LinearLayout) mLayout
					.findViewById(linearLayoutsIds[i]));
			linearLayouts.get(i).setOnClickListener(this);
		}

		// FavoriteContacts
		ContactsManager cM = new ContactsManager(mContext);
		people = cM.favoritesContacts();
		int i = 0;
		for (People p : people) {
			((TextView) linearLayouts.get(i++).getChildAt(1)).setText(p.name);
		}
		cM.recentsContacts();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.senEmail: {
			ActionsIntents.senEmail(mContext);
			break;
		}
		case R.id.newEvent: {
			ActionsIntents.newEvent(mContext);
			break;
		}
		case R.id.newAlarm: {
			ActionsIntents.newAlarm(mContext);
			break;
		}
		case R.id.new_phone_call: {
			ActionsIntents.newPhoneCall(mContext);
			break;
		}
		case R.id.settings: {
			mContext.startActivity(new Intent(mContext, Settings.class));
			break;
		}
		default: {
			int position = -1;
			for (int i = 0; i < linearLayoutsIds.length; i++) {
				if (v.getId() == linearLayoutsIds[i]) {
					position = i;
				}
			}
			openPerson(position);
			break;
		}
		}
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mLayout.closeDrawers();
			}
		}, 200);
	}

	private void openPerson(int position) {
		if (position == -1) {
			return;
		}

		final Dialog dialog = new Dialog(mContext);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(
				R.layout.controllers_main_left_drawer_contact_detail, null,
				false);
		
		((TextView) view.findViewById(R.id.personName))
				.setText(people.get(position).name);

		dialog.setContentView(view);
		dialog.show();
	}

}
