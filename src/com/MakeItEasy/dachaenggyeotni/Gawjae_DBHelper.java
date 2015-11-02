package com.MakeItEasy.dachaenggyeotni;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class Gawjae_DBHelper extends SQLiteOpenHelper {
	final static String KEY_ID = "_id";
	final static String KEY_JUNBIMUL_GAWMOK = "junbimul_gawmok";
	final static String KEY_JUNBIMUL_INFO = "junbimul_info";
	final static String TABLE_NAME = "mytable";
	final static String TABLE_GAWJAE = "gawjae";
	final static String KEY_GAWJAE_GAWMOK = "gawjae_gawmok";
	final static String KEY_GAWJAE_INFO = "gawjae_info";

	public Gawjae_DBHelper(Context context) {
		super(context, "MyData.db01", null, 2);
	}

	public void onCreate(SQLiteDatabase db01) {
		// AUTOINCREMENT 속성 사용 시 PRIMARY KEY로 지정한다.
		String query = String.format("CREATE TABLE %s ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "%s TEXT,"
				+ "%s INTEGER );", TABLE_GAWJAE, KEY_GAWJAE_GAWMOK,
				KEY_GAWJAE_INFO);
		db01.execSQL(query);
	}

	public void onUpgrade(SQLiteDatabase db01, int oldVersion, int newVersion) {
		String query = String.format("DROP TABLE IF EXISTS %s", TABLE_GAWJAE);
		db01.execSQL(query);
		onCreate(db01);
	}

}