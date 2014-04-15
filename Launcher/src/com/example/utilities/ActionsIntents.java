package com.example.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.ContactsContract;

public class ActionsIntents {

	public static void senEmail(Context context) {
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
	    context.startActivity(intent);
	}

	public static void newContact(Context context) {
		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
		context.startActivity(intent);
	}

	public static void newEvent(Context context) {
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");
		context.startActivity(intent);
	}

	public static void newAlarm(Context context) {
		Intent alarmClockIntent = new Intent();
		alarmClockIntent.setAction(AlarmClock.ACTION_SET_ALARM);
		alarmClockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(alarmClockIntent);
	}

	public static void openApp(Context context, Intent launchIntent) {
		context.startActivity(launchIntent);
	}

}
