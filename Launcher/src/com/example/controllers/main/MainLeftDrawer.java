package com.example.controllers.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.auxiliar.ActionsIntents;
import com.example.auxiliar.ContactsManager;
import com.example.controllers.Settings;
import com.example.launcher.R;

public class MainLeftDrawer implements OnClickListener {
	private DrawerLayout mLayout;
	private Context mContext;
	private Handler mHandler = new Handler();

	private List<TextView> textViews;
	private int[] textViewsIds = { R.id.senEmail, R.id.newEvent, R.id.newAlarm,
			R.id.settings };

	public MainLeftDrawer(DrawerLayout layout, Context context) {
		this.mLayout = layout;
		this.mContext = context;
		setupElements();
	}

	private void setupElements() {
		textViews = new ArrayList<TextView>();
		for (int i = 0; i < textViewsIds.length; i++) {
			textViews.add((TextView) mLayout.findViewById(textViewsIds[i]));
			textViews.get(i).setOnClickListener(this);
		}
		
		// FavoriteContacts
		ContactsManager cM = new ContactsManager(mContext);
		cM.favoritesContacts();
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
		case R.id.settings: {
			mContext.startActivity(new Intent(mContext, Settings.class));
			break;
		}
		default:
			break;
		}
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mLayout.closeDrawers();
			}
		}, 200);
	}
}
