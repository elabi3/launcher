package com.spaces.auxiliar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactsManager {
	private Context mContext;

	public ContactsManager(Context context) {
		this.mContext = context;
	}

	public class People {
		public String name;
		public String intentUriString;
		public String photo;

		public People(String name, String intentUriString, String photo) {
			this.name = name;
			this.intentUriString = intentUriString;
			this.photo = photo;
		}
	}

	public List<People> favoritesContacts() {
		Uri queryUri = ContactsContract.Contacts.CONTENT_URI;

		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.STARRED,
				ContactsContract.Contacts.PHOTO_THUMBNAIL_URI };

		String selection = ContactsContract.Contacts.STARRED + "='1'";

		Cursor cursor = mContext.getContentResolver().query(queryUri,
				projection, selection, null, null);

		List<People> people = new ArrayList<People>();
		while (cursor.moveToNext()) {
			// title - name
			String title = (cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

			// intentURI
			String contactID = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));

			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.withAppendedPath(
					ContactsContract.Contacts.CONTENT_URI,
					String.valueOf(contactID));
			intent.setData(uri);
			String intentUriString = intent.toUri(0);

			// photo
			String photo = (cursor
					.getString(cursor
							.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)));

			people.add(new People(title, intentUriString, photo));
		}
		cursor.close();

		return people;
	}

	public List<String> recentsContacts() {
		Uri queryUri = android.provider.CallLog.Calls.CONTENT_URI;

		String[] projection = new String[] { ContactsContract.Contacts._ID,
				CallLog.Calls._ID, CallLog.Calls.NUMBER,
				CallLog.Calls.CACHED_NAME, CallLog.Calls.DATE };

		String sortOrder = String.format("%s limit 500 ", CallLog.Calls.DATE
				+ " DESC");

		Cursor cursor = mContext.getContentResolver().query(queryUri,
				projection, null, null, sortOrder);

		while (cursor.moveToNext()) {
			String phoneNumber = cursor.getString(cursor
					.getColumnIndex(CallLog.Calls.NUMBER));

			String title = (cursor.getString(cursor
					.getColumnIndex(CallLog.Calls.CACHED_NAME)));

			if (phoneNumber == null || title == null)
				continue;

			String uri = "tel:" + phoneNumber;
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse(uri));
			String intentUriString = intent.toUri(0);

			Log.v("", title);
		}
		cursor.close();
		return null;
	}

}
