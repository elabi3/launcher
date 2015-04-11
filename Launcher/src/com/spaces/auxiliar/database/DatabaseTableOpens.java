package com.spaces.auxiliar.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseTableOpens {
	public static final String TABLE_NAME = "opens";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_WEEK_DAY = "week_day";
	public static final String COLUMN_MONTH_DAY = "month_day";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";

	private static final String DATABASE_CREATE = "create table " + TABLE_NAME
			+ " (" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null, " + COLUMN_TYPE
			+ " text not null, " + COLUMN_CATEGORY + ", " + COLUMN_TIME
			+ " integer, " + COLUMN_WEEK_DAY + " integer, " + COLUMN_MONTH_DAY
			+ " integer, " + COLUMN_LATITUDE + ", " + COLUMN_LONGITUDE
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		Log.i("OpenTable", DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		Log.i("OpenTable", "DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}

}
