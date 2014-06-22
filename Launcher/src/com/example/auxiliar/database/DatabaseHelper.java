package com.example.auxiliar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "database.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		DatabaseTableOpens.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		DatabaseTableOpens.onUpgrade(database, oldVersion, newVersion);
	}

}
