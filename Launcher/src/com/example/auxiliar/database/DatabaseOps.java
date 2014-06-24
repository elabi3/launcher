package com.example.auxiliar.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseOps {
	private static DatabaseOps instance = null;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase db;

	public static DatabaseOps getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseOps(context);
		}
		return instance;
	}

	private DatabaseOps(Context context) {
		mDbHelper = new DatabaseHelper(context);
		db = mDbHelper.getWritableDatabase();
	}

	public long insertNewOpen(DatabaseElementOpen element) {
		ContentValues values = new ContentValues();
		values.put(DatabaseTableOpens.COLUMN_NAME, element.getId());
		values.put(DatabaseTableOpens.COLUMN_TYPE, element.getType());
		values.put(DatabaseTableOpens.COLUMN_CATEGORY, element.getCategory());
		values.put(DatabaseTableOpens.COLUMN_TIME, element.getTime());
		values.put(DatabaseTableOpens.COLUMN_WEEK_DAY, element.getWeek_day());
		values.put(DatabaseTableOpens.COLUMN_MONTH_DAY, element.getMonth_day());
		values.put(DatabaseTableOpens.COLUMN_LATITUDE, element.getLatitude());
		values.put(DatabaseTableOpens.COLUMN_LONGITUDE, element.getLongitude());
		return db.insert(DatabaseTableOpens.TABLE_NAME, null, values);
	}

	public List<String> getAll() {
		String[] columns = { DatabaseTableOpens.COLUMN_NAME };
		Cursor cursor = query(columns, null, null);
		List<String> elements = new ArrayList<String>(cursor.getCount());

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			elements.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		return Collections.unmodifiableList(elements);
	}

	// select name from opens where week_day = 1 and time > 1629 and time <
	// 1637;
	public List<String> getElementsWeekDayTime(int weekday, int interval[]) {
		String[] columns = { DatabaseTableOpens.COLUMN_NAME };
	
		String selection;
		String[] selectionArgs;
		if (weekday == -1) {
			selection = DatabaseTableOpens.COLUMN_TIME + ">? AND "
					+ DatabaseTableOpens.COLUMN_TIME + "<?";
			selectionArgs = new String[2];
			selectionArgs[0] = Integer.toString(interval[1]);
			selectionArgs[1] = Integer.toString(interval[0]);
		} else {
			selection = DatabaseTableOpens.COLUMN_WEEK_DAY + "=? AND "
					+ DatabaseTableOpens.COLUMN_TIME + ">? AND "
					+ DatabaseTableOpens.COLUMN_TIME + "<?";
			selectionArgs = new String[3];
			selectionArgs[0] = Integer.toString(weekday);
			selectionArgs[1] = Integer.toString(interval[1]);
			selectionArgs[2] = Integer.toString(interval[0]);
		}

		Cursor cursor = query(columns, selection, selectionArgs);
		List<String> elements = new ArrayList<String>(cursor.getCount());

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			elements.add(cursor.getString(0));
			cursor.moveToNext();
		}
		cursor.close();
		return Collections.unmodifiableList(elements);
	}
	
	public int getOpeningTimes(String name) {
		String[] columns = { DatabaseTableOpens.COLUMN_NAME };
		String selection = DatabaseTableOpens.COLUMN_NAME + "=?";
		String[] selectionArgs = {name}; 
		
		Cursor cursor = query(columns, selection, selectionArgs);
		return cursor.getCount();
	}

	private Cursor query(String[] columns, String selection,
			String[] selectionArgs) {
		SQLiteDatabase database = mDbHelper.getReadableDatabase();
		return database.query(DatabaseTableOpens.TABLE_NAME, columns,
				selection, selectionArgs, null, null, null);
	}

}
