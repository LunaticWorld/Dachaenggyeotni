package com.MakeItEasy.dachaenggyeotni;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class Junbimul_DBHelper extends SQLiteOpenHelper {
	final static String KEY_ID = "_id";
	final static String KEY_JUNBIMUL_GAWMOK = "junbimul_gawmok";
	final static String KEY_JUNBIMUL_INFO = "junbimul_info";
	final static String TABLE_NAME = "mytable";
	final static String KEY_YEAR = "junbimul_year";
	final static String YEAR = "jyear";
	final static String MONTH = "jmonth";
	final static String DAY = "jday";

	public Junbimul_DBHelper(Context context) {
		super(context, "MyData.db", null, 3);
	}

	public void onCreate(SQLiteDatabase db) {
		// AUTOINCREMENT 속성 사용 시 PRIMARY KEY로 지정한다.
		String query = String.format("CREATE TABLE %s ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s TEXT,"
				+ "%s TEXT," + "%s TEXT," + "'%s' TEXT," + "'%s' TEXT,"
				+ "'%s' TEXT);", TABLE_NAME, KEY_JUNBIMUL_GAWMOK,
				KEY_JUNBIMUL_INFO, KEY_YEAR, YEAR, MONTH, DAY);
		db.execSQL(query);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String query = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
		db.execSQL(query);
		onCreate(db);
	}

}
