package com.MakeItEasy.dachaenggyeotni;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class LockScreen_View extends Activity {
	private SeekBar mUnlockBar;
	private BackPressCloseHandler backPressCloseHandler;
	ListView list;
	Junbimul_DBHelper mHelper;
	SQLiteDatabase db;
	Cursor cursor;
	TextView empty1, txt;
	
	Junbimul_DBHelper del_mHelper;
	SQLiteDatabase del_db;
	
	int Year, Month, Day, get_Year, get_Month, get_Day;

	TextView lock_day, lock_hour;
	Junbimul_CursorAdapter myAdapter;
	final static String KEY_ID = "_id";
	final static String KEY_JUNBIMUL_GAWMOK = "junbimul_gawmok";
	final static String KEY_JUNBIMUL_INFO = "junbimul_info";
	// final static String KEY_GAWJAE_GAWMOK = "gawjae_gawmok";
	// final static String KEY_GAWJAE_INFO = "gawjae_info";
	final static String TABLE_NAME = "mytable";
	final static String YEAR = "jyear";
	final static String MONTH = "jmonth";
	final static String DAY = "jday";

	final static String querySelectAll = String.format("SELECT * FROM '%s' Order by junbimul_year asc",
			TABLE_NAME);

	String ampm, str = null;

	static int deviceWidth, deviceHeight;

	protected void GetDeviceScale() {

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		deviceWidth = displayMetrics.widthPixels;
		deviceHeight = displayMetrics.heightPixels;
	}

	protected void SetViewScale() {

		lock_day.setTextSize(TypedValue.COMPLEX_UNIT_PX, deviceHeight * 5 / 100);
		lock_hour.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				deviceHeight * 3 / 100);
		empty1.setTextSize(TypedValue.COMPLEX_UNIT_PX, deviceHeight * 6 / 100);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lockscreen);
		
		list = (ListView) findViewById(R.id.junbimul_list1);
		
		Time now = new Time();// ���� �ð�
		now.setToNow();
		Year = now.year;
		Month = now.month;
		Month = Month + 1;
		Day = now.monthDay;
		
		del_mHelper = new Junbimul_DBHelper(getApplicationContext());
		del_db = del_mHelper.getWritableDatabase();
		Cursor c = del_db.rawQuery(querySelectAll, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			get_Year = c.getInt(c.getColumnIndex("jyear"));
			get_Month = c.getInt(c.getColumnIndex("jmonth"));
			get_Day = c.getInt(c.getColumnIndex("jday"));
			if (get_Year <= Year) {
				Log.i("1��Ȯ��", ""+get_Year);
				if (get_Month <= Month) {
					Log.i("2��Ȯ��", ""+get_Month);
					if (get_Day < Day) {
						Log.i("3��Ȯ��", ""+get_Day);
						
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

		

		mHelper = new Junbimul_DBHelper(this);
		db = mHelper.getWritableDatabase();

		cursor = db.rawQuery(querySelectAll, null);
		myAdapter = new Junbimul_CursorAdapter(this, cursor);
		lock_day = (TextView) findViewById(R.id.lock_day);
		lock_hour = (TextView) findViewById(R.id.lock_hour);
		empty1 = (TextView) findViewById(R.id.empty1);

		list.setAdapter(myAdapter);
		empty1.setText("�غ� ����� �����ϴ�.");
		list.setEmptyView(empty1);

		Calendar cal = Calendar.getInstance(Locale.KOREA); // �־��� �ð��뿡 �°� ����
															// �ð����� �ʱ�ȭ��
		// GregorianCalender ��ü��
		// ��ȯ.// �Ǵ� Calendar now =
		// Calendar.getInstance(Locale.KOREA);
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
		if (day_of_week == 1)
			str = "�Ͽ���";
		else if (day_of_week == 2)
			str = "������";
		else if (day_of_week == 3)
			str = "ȭ����";
		else if (day_of_week == 4)
			str = "������";
		else if (day_of_week == 5)
			str = "�����";
		else if (day_of_week == 6)
			str = "�ݿ���";
		else if (day_of_week == 7)
			str = "�����";

		if (cal.get(Calendar.AM_PM) == Calendar.AM)
			ampm = "����";
		else
			ampm = "����";

		lock_day.setText(cal.get(Calendar.YEAR) + "�� "
				+ (cal.get(Calendar.MONTH) + 1) + "�� " + cal.get(Calendar.DATE)
				+ "�� " + str);
		lock_hour.setText(ampm + " " + cal.get(Calendar.HOUR) + "�� "
				+ cal.get(Calendar.MINUTE) + "�� ");

		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		mUnlockBar = (SeekBar) findViewById(R.id.seekBar2);
		// ���������� ���߱�����
		mUnlockBar.setThumbOffset(10);
		mUnlockBar.setMax(100);
		mUnlockBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (seekBar.getProgress() >= 80) {
					finish();
					seekBar.setProgress(100);
					seekBar.setEnabled(false);
					seekBar.setFocusable(false);

				} else {
					seekBar.setProgress(0);
				}
			}
		});
		backPressCloseHandler = new BackPressCloseHandler(this);
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		backPressCloseHandler.onBackPressed();
	}

}
