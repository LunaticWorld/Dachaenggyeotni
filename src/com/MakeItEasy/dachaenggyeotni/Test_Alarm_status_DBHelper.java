package com.MakeItEasy.dachaenggyeotni;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Test_Alarm_status_DBHelper extends SQLiteOpenHelper {
	final static String KEY_PRIMARY = "_pos";
	final static String TABLE_ALARM_DATA = "alarm_data";
	final static String KEY_SET_DATE = "date";
	final static String KEY_ONOFF = "onoff";
	final static String KEY_VIBRATE = "vibrate";
	final static String KEY_PENDING = "unique";
	final static String KEY_CALENDAR_DATA = "calendar_data";
	final static String KEY_GAWJAE_GAWMOK = "gawjae_gawmok";
	final static String KEY_GAWJAE_INFO = "gawjae_info";
	
	public Test_Alarm_status_DBHelper(Context context) {
		super(context, "Alarm_data.db", null, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query = String.format("CREATE TABLE %s ("
				+ "%s INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "%s TEXT, "
				+ "%s TEXT, "
				+ "'%s' TEXT, "
				+ "'%s' TEXT, "
				+ "'%s' TEXT );", TABLE_ALARM_DATA, KEY_PRIMARY, KEY_SET_DATE, KEY_ONOFF, KEY_VIBRATE,
				KEY_PENDING, KEY_CALENDAR_DATA);
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String query = String.format("DROP TABLE IF EXISTS %s", TABLE_ALARM_DATA);
		db.execSQL(query);
		onCreate(db);
	}

}
