package com.MakeItEasy.dachaenggyeotni;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Test_Gawjae_item extends Activity implements OnClickListener {
	final static String KEY_PRIMARY = "_pos";
	final static String TABLE_ALARM_DATA = "alarm_data";
	final static String KEY_SET_DATE = "date";
	final static String KEY_ONOFF = "onoff";
	final static String KEY_VIBRATE = "vibrate";
	final static String KEY_PENDING = "unique";

	Test_Alarm_status_DBHelper mDBHelper;
	SQLiteDatabase db;

	Button datePickerButton, timePickerButton;
	static ToggleButton setAlarm;
	Uri uri;
	int myYear = 0, myMonth = 0, myDay = 0, myHour = 0, myMinute = 0,
			mySecond = 0;
	TextView txtLabel;
	AlarmManager alarm;
	int Year, Month, Day, Hour, Minute;

	PendingIntent pender;
	Intent intent;
	static Calendar c = Calendar.getInstance();
	TextView text1, text2;

	Variable variable;
	ToggleButton mySwitch;
	AudioManager audioManager;
	int unique = 0;
	int pos = 0;
	static int pos_move = 0;
	int id = 0;
	String alarm_date;
	String set_check = "off", set_vibrate = "off";
	String date;
	String calendar_data;
	Date date_format;
	String date_date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gawjae_item);
		/*SharedPreferences pref = getSharedPreferences("alarm_set_status",
				Context.MODE_PRIVATE);*/

		Intent intent2 = getIntent();
		unique = intent2.getIntExtra("now_time", 0);
		pos = intent2.getIntExtra("position", 0);
		
		Log.i("현재 시간은 ->", "" + pos);
		setAlarm = (ToggleButton) this.findViewById(R.id.button1);
		setAlarm.setOnClickListener(this);// 알랑 on off 버튼
		datePickerButton = (Button) findViewById(R.id.datepickerbutton);
		timePickerButton = (Button) findViewById(R.id.timepickerbutton);
		datePickerButton.setOnClickListener(this);
		timePickerButton.setOnClickListener(this);
		txtLabel = (TextView) findViewById(R.id.textView1);
		
		
		
		mySwitch = (ToggleButton) findViewById(R.id.toggleButton2);// 무음,진동
		// 상태에서도
		// 소리나게 설정

		mDBHelper = new Test_Alarm_status_DBHelper(getApplicationContext());

		try {
			db = mDBHelper.getReadableDatabase();

			Cursor cursor = db.rawQuery("SELECT * FROM alarm_data;", null);
			cursor.moveToPosition(pos);

			date = cursor.getString(cursor.getColumnIndex(KEY_SET_DATE));
			
			if (cursor.getString(cursor.getColumnIndex("onoff")).equals("on")) {
				setAlarm.setChecked(true);
				txtLabel.setText("" + date);
			}
			if (cursor.getString(cursor.getColumnIndex("vibrate")).equals("on")) {
				mySwitch.setChecked(true);
			}
			if(cursor.getInt(cursor.getColumnIndex("unique")) >=0){
				
				/*calendar_data = cursor.getString(cursor.getColumnIndex("calendar_data"));
				DateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss", Locale.KOREA);
				unique = Integer.valueOf(cursor.getString(cursor.getColumnIndex("unique")));
				pender = this.getDistinctPendingIntent(intent, unique);
				date_format = df.parse(calendar_data);
				myYear = date_format.getYear();
				myMonth = date_format.getMonth();
				myDay = date_format.getDay();
				myHour = date_format.getHours();
				myMinute = date_format.getMinutes();
				mySecond = 0;
				Log.i("현재 날짜 get : ", ""+date_format);
				Calendar ca = Calendar.getInstance();
				ca.set(myYear, myMonth, myDay, myHour, myMinute, myDay);
				alarm.set(AlarmManager.RTC_WAKEUP, ca.getTimeInMillis(), pender);*/
				calendar_data = cursor.getString(cursor.getColumnIndex("date"));
				DateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss", Locale.KOREA);
				unique = Integer.valueOf(cursor.getString(cursor.getColumnIndex("unique")));
				date_format = df.parse(calendar_data);
				myYear = date_format.getYear();
				myMonth = date_format.getMonth();
				myDay = date_format.getDay();
				myHour = date_format.getHours();
				myMinute = date_format.getMinutes();
				mySecond = 0;
				
				unique = Integer.valueOf(cursor.getString(cursor.getColumnIndex("pender")));
				pender = this.getDistinctPendingIntent(intent, unique);

				alarm.cancel(pender);
				pender.cancel();

				
				setAlarm.setChecked(true);
			}else{
				setAlarm.setChecked(false);
			}
			cursor.close();

		} catch (Exception e) {
			/*Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();*/
		}

		
		

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		variable = (Variable) getApplicationContext();// 공유 변수 초기화(필수)

		
		
		/*setAlarm.setChecked(pref.getBoolean("set_alarm_button", false));
		mySwitch.setChecked(pref.getBoolean("set_myswitch", false));*/
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					variable.state = 1;
					switch (audioManager.getRingerMode()) {
					case AudioManager.RINGER_MODE_VIBRATE:// 현재 상태가 진동상태일 경우
						variable.isVibrate = true;
						break;
					case AudioManager.RINGER_MODE_NORMAL:// 현재 상태가 소리 상태일 경우
						variable.isSound = true;
						break;
					case AudioManager.RINGER_MODE_SILENT:// 현재 상태가 무음 상태일 경우
						variable.isSilent = true;
						break;
					}
				} else {
					variable.state = 0;
				}
			}
		});
	}

	public void setAlarm() {

		try {
			if (myYear < 1900) {
				Toast.makeText(getApplicationContext(), "먼저 값을 설정해주세요!!",
						Toast.LENGTH_SHORT).show();
				setAlarm.setChecked(false);
				return;
			}
			Time now = new Time();// 현재 시간
			now.setToNow();
			Year = now.year;
			Month = now.month;
			Day = now.monthDay;
			Hour = now.hour;
			Minute = now.minute;

			alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
			if (myMinute <= Minute)
				if (myHour <= Hour)
					if (myMinute <= Minute)
						if (myDay <= Day)
							if (myMonth <= Month)
								if (myYear <= Year) {
									Toast.makeText(getApplicationContext(),
											"현재시간이나,이전시간에 설정하셨습니다.",// 설정한 시간이
																	// 이전 시간일 경우
																	// 차단
											Toast.LENGTH_SHORT).show();
									setAlarm.setChecked(false);
									return;
								}
			intent = new Intent(Test_Gawjae_item.this, Test_AlarmRecever.class);
			intent.putExtra("date_format", date_format);

			pender = this.getDistinctPendingIntent(intent, unique);

			/*
			 * pender = PendingIntent .getBroadcast(Test_Gawjae_item.this, 0,
			 * intent, 0);// AlarmRecever 클래스로 넘어감
			 */
			c.set(myYear, myMonth, myDay, myHour, myMinute, mySecond);// 사용자가
																		// 설정한
																		// 시간을
																		// 여기에
																		// set

			alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pender);// 알람
																			// set

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "값을 전부 입력해주세요.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void cancleAlarm() {
		try {
			alarm.cancel(pender);// 알람 cancel
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "먼저 값을 설정해주세요!!",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button1:
			if (setAlarm.isChecked()) {// on 일경우 off 일경우
				setAlarm();
			} else {
				cancleAlarm();
			}
			break;
		case R.id.datepickerbutton:// 날짜 지정
			myYear = c.get(Calendar.YEAR);
			myMonth = c.get(Calendar.MONTH);
			myDay = c.get(Calendar.DAY_OF_MONTH);

			Dialog dlgDate = new DatePickerDialog(this, myDateSetListener,
					myYear, myMonth, myDay);
			dlgDate.show();

			break;
		case R.id.timepickerbutton:// 시간 지정
			myHour = c.get(Calendar.HOUR_OF_DAY);
			myMinute = c.get(Calendar.MINUTE);

			Dialog dlgTime = new TimePickerDialog(this, myTimeSetListener,
					myHour, myMinute, false);
			dlgTime.show();

			break;

		default:
			break;
		}
	}

	void setLabel() {// 엑티비티 맨 위에 보이는 텍스트임 ㅇㅋ?
		txtLabel.setText(""
				+DateFormat.getInstance().format(c.getTime()));// 시간을
																		// 보여줌

	}

	DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			c.set(year, monthOfYear, dayOfMonth);
			c.set(Calendar.SECOND, 0);

			myYear = c.get(Calendar.YEAR);
			myMonth = c.get(Calendar.MONTH);
			myDay = c.get(Calendar.DAY_OF_MONTH);

		}
	};

	TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			c.set(Calendar.HOUR_OF_DAY, hourOfDay);
			c.set(Calendar.MINUTE, minute);
			c.set(Calendar.SECOND, 0);

			myHour = c.get(Calendar.HOUR_OF_DAY);
			myMinute = c.get(Calendar.MINUTE);
			setLabel();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:

			Intent i = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);// 알람음
																			// 선택
			startActivityForResult(i, 0);

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			try {
				uri = data
						.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);// 선택한
																						// 알람
																						// 값을
																						// 저장

				RingtoneManager.setActualDefaultRingtoneUri(this,
						RingtoneManager.TYPE_ALARM, uri);// 그 알람값을 시스템에 저장
			} catch (Exception e) {
			}
			break;
		default:
			break;
		}
	}

	protected PendingIntent getDistinctPendingIntent(Intent intent,
			int requestId) {
		PendingIntent pi = PendingIntent.getBroadcast(this, // context
				requestId, // request id
				intent, // intent to be delivered
				0);

		// pending intent flags
		// PendingIntent.FLAG_ONE_SHOT);
		return pi;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		/*SharedPreferences pref = getSharedPreferences("alarm_set_status",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = pref.edit();*/
		
		
		if (setAlarm.isChecked()) {
			/*edit.putBoolean("setAlarm_button", true);*/
			set_check = "on";
		} else {
			/*edit.putBoolean("setAlarm_button", false);*/
			set_check = "off";
		}
		if (mySwitch.isChecked()) {
			/*edit.putBoolean("set_myswitch", true);*/
			set_vibrate = "on";
		} else {
			/*edit.putBoolean("set_myswitch", false);*/
			set_vibrate = "off";
		}

		if (txtLabel.getText().toString().equals("")) {

		} else {
			try {
				Date date = new Date(myYear,myMonth,myDay,myHour,myMinute);
				alarm_date = String.valueOf(date);
			} catch (Exception e) {
				alarm_date = date;
			}
			calendar_data = String.valueOf(""+c.getTime());
			Log.i("캘린더 시간 : ", calendar_data);

			db = mDBHelper.getWritableDatabase();
			db.execSQL("DELETE FROM alarm_data WHERE _pos = " + pos + ";");


			mDBHelper.close();
			
			mDBHelper = new Test_Alarm_status_DBHelper(getApplicationContext());
			db = mDBHelper.getWritableDatabase();
			
			String query = String.format(
					"INSERT INTO %s VALUES ( '%s', '%s', '%s', '%s', '%s', '%s' );",
					TABLE_ALARM_DATA, pos, alarm_date, set_check, set_vibrate, unique, calendar_data);
			
			db.execSQL(query);
			
		}
		
		/*edit.commit();*/
		super.onStop();
	}
}
