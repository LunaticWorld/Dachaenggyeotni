package com.MakeItEasy.dachaenggyeotni;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Junbimul_moklok extends Activity implements OnClickListener {
	private Button startServiceButton;
	private Button stopServiceButton;

	ImageView img1;
	TextView txt1, txt2, empty;

	ListView list;

	Variable state;

	int Year, Month, Day, get_Year, get_Month, get_Day;

	Junbimul_DBHelper mHelper, del_mHelper;
	SQLiteDatabase db, del_db;
	Cursor cursor;
	Junbimul_CursorAdapter myAdapter;
	final static String KEY_ID = "_id";
	final static String KEY_JUNBIMUL_GAWMOK = "junbimul_gawmok";
	final static String KEY_JUNBIMUL_INFO = "junbimul_info";
	// final static String KEY_GAWJAE_GAWMOK = "gawjae_gawmok";
	// final static String KEY_GAWJAE_INFO = "gawjae_info";
	final static String TABLE_NAME = "mytable";
	final static String KEY_YEAR = "junbimul_year";
	final static String YEAR = "jyear";
	final static String MONTH = "jmonth";
	final static String DAY = "jday";

	final static String querySelectAll = String.format(
			"SELECT * FROM '%s' Order by junbimul_year asc", TABLE_NAME);

	static int deviceWidth, deviceHeight;

	protected void GetDeviceScale() {

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		deviceWidth = displayMetrics.widthPixels;
		deviceHeight = displayMetrics.heightPixels;
	}

	protected void SetViewScale() {

		txt1.setTextSize(TypedValue.COMPLEX_UNIT_PX, deviceWidth * 5 / 100);
		empty.setTextSize(TypedValue.COMPLEX_UNIT_PX, deviceWidth * 9 / 100);
		txt2.setTextSize(TypedValue.COMPLEX_UNIT_PX, deviceWidth * 7 / 100);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.junbimul_moklok);
		GetDeviceScale();

		Time now = new Time();// 현재 시간
		now.setToNow();
		Year = now.year;
		Month = now.month;
		Month = Month + 1;
		Day = now.monthDay;
		Log.i("현재시간 check ", ""+Year);
		Log.i("현재시간 check ", ""+Month);
		Log.i("현재시간 check ", ""+Day);

		del_mHelper = new Junbimul_DBHelper(getApplicationContext());
		del_db = del_mHelper.getWritableDatabase();
		Cursor c = del_db.rawQuery(querySelectAll, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			get_Year = c.getInt(c.getColumnIndex("jyear"));
			get_Month = c.getInt(c.getColumnIndex("jmonth"));
			get_Day = c.getInt(c.getColumnIndex("jday"));
			if (get_Year <= Year) {
				Log.i("1차확인", ""+get_Year);
				if (get_Month <= Month) {
					Log.i("2차확인", ""+get_Month);
					if (get_Day < Day) {
						Log.i("3차확인", ""+get_Day);
						
						del_db.execSQL("DELETE FROM mytable WHERE jyear = '"
								+ get_Year + "' AND jmonth = '" + get_Month
								+ "' AND jday = '" + get_Day + "'");
					}
				}
			}
			c.moveToNext();
		}
		c.close();
		del_mHelper.close();

		state = (Variable) getApplicationContext();

		img1 = (ImageView) findViewById(R.id.junbimul_list_Image);
		img1.setScaleType(ImageView.ScaleType.FIT_CENTER);
		startServiceButton = (Button) findViewById(R.id.startService);
		stopServiceButton = (Button) findViewById(R.id.stopService);
		GetDeviceScale();

		startServiceButton.setOnClickListener(this);
		stopServiceButton.setOnClickListener(this);

		txt1 = (TextView) findViewById(R.id.textView2);

		txt2 = (TextView) findViewById(R.id.junmoklok1);
		list = (ListView) findViewById(R.id.junbimul_list2);

		empty = (TextView) findViewById(R.id.empty);

		mHelper = new Junbimul_DBHelper(this);
		db = mHelper.getWritableDatabase();

		cursor = db.rawQuery(querySelectAll, null);
		myAdapter = new Junbimul_CursorAdapter(this, cursor);

		list.setAdapter(myAdapter);

		empty.setText("준비물 목록이 없습니다.");
		list.setEmptyView(empty);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int Pos, long ID) {
				DB_DELETE(ID);
				DB_LOAD();

				return false;
			}
		});

		SetViewScale();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.startService:
			state.screenlock_state = true;
			startService(new Intent(this, LockScreen_Service.class));
			Toast.makeText(getApplicationContext(), "스크린락이 적용되었습니다.",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.stopService:
			state.screenlock_state = false;
			stopService(new Intent(this, LockScreen_Service.class));
			Toast.makeText(getApplicationContext(), "스크린락이 해제되었습니다.",
					Toast.LENGTH_SHORT).show();
			break;

		}
	}

	void DB_DELETE(long ID) {
		// TODO Auto-generated method stub
		String[] id = { String.valueOf(ID) };

		db.delete("mytable", "_id=?", id);

	}

	void DB_LOAD() {
		// 불러옴
		db = mHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(querySelectAll, null);
		startManagingCursor(cursor);
		// 갱신
		myAdapter.changeCursor(cursor);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		SharedPreferences pref = getSharedPreferences("screenlock_state",
				MODE_PRIVATE);
		SharedPreferences.Editor edit = pref.edit();
		edit.putBoolean("screen_state", state.screenlock_state);
		edit.commit();

		super.onStop();
	}

}
