package com.MakeItEasy.dachaenggyeotni;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Test_Dialog extends Activity implements OnClickListener {
	Test_Alarm_status_DBHelper mDBHelper;
	SQLiteDatabase db;
	Gawjae_DBHelper gawjae_dbHelper;
	SQLiteDatabase db_gawjae;
	Variable variable;
	AudioManager aManager;
	static Test_Gawjae_item main;
	RingtoneManager ringtonemanager;
	Uri uri;
	Button OK_btn, REVIBRATE_btn;
	AlarmManager alarm;
	Ringtone r;
	Vibrator vi;
	Intent intent;
	PendingIntent pender;
	int Year, Month, Day, Hour, Minute, Second, state;
	long pattern[] = { 1000, 250 };
	long endpattern[] = { 1, 0 };
	int pos = 0;
	Calendar c = Calendar.getInstance();
	
	TextView test_gawjae_name, test_gawjae_info;
	String test_gawjae_name_string, test_gawjae_info_string;
	String calendar_data;
	String now_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_dialoglayout);
		Intent get = getIntent();
		gawjae_dbHelper = new Gawjae_DBHelper(getApplicationContext());
		mDBHelper = new Test_Alarm_status_DBHelper(getApplicationContext());
		variable = (Variable) getApplicationContext();
		state = variable.state;
		if (state == 1) {// 메인 엑티비티에 있는 mySwitch가 체크 되면 수행
			aManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);// 소리 상태로 변경
			vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);// 진동 발생
			vi.vibrate(pattern, 0);
		}
		test_gawjae_name = (TextView)findViewById(R.id.test_gawjae_name);
		test_gawjae_info = (TextView)findViewById(R.id.test_gawjae_info);
		Time now = new Time();// 현재 시간
		now.setToNow();
		Year = now.year;
		Month = now.month;
		Day = now.monthDay;
		Hour = now.hour;
		Minute = now.minute;
		ArrayList<mData> mDataArr = Test_Gawjae_Activity.mDataArr;
		Test_Gawjae_Adapter adapter = new Test_Gawjae_Adapter(getApplicationContext(), mDataArr);
		
		Date date = new Date(Year,Month,Day,Hour,Minute);
		now_time = String.valueOf(date);
		
		db = mDBHelper.getReadableDatabase();
		db_gawjae = gawjae_dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM alarm_data;", null);
		Cursor getcursor = db_gawjae.rawQuery("SELECT * FROM gawjae;", null);
		c.moveToFirst();
		int pos = 0;
		while(!c.isAfterLast()){
			
			calendar_data = c.getString(c.getColumnIndex("date"));
			getcursor.moveToFirst();
			while(!getcursor.isAfterLast()){
			if(calendar_data.equals(now_time)){
				
				
				/*getcursor.moveToPosition(pos);*/
				try{
				test_gawjae_info_string = getcursor.getString(getcursor.getColumnIndex("gawjae_info"));
				test_gawjae_name_string = getcursor.getString(getcursor.getColumnIndex("gawjae_gawmok"));
				test_gawjae_info.setText(""+test_gawjae_info_string);
				test_gawjae_name.setText(""+test_gawjae_name_string);
				
				}catch(Exception e){
					
				}
				
				}
			getcursor.moveToNext();
			}
			
			Log.i("값 : ", pos +","+ test_gawjae_name_string);
			/*c.moveToNext();*/
			c.moveToNext();
		}
		getcursor.close();
		c.close();
		mDBHelper.close();

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
		// | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD//잠금 화면 위에 뜨게하기.
		// | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON// 켜진 화면 유지 화면안꺼지게하기
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);// 화면 깨우기.

		OK_btn = (Button) findViewById(R.id.ok_btn1);

		REVIBRATE_btn = (Button) findViewById(R.id.revibrate_btn2);

		OK_btn.setOnClickListener(this);
		REVIBRATE_btn.setOnClickListener(this);

		r = RingtoneManager.getRingtone(this, RingtoneManager
				.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM));
		r.play();// 알람음 실행
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ok_btn1:
			try {
				vi.vibrate(endpattern, -1);// 진동 끊기
			} catch (Exception e) {
			}
			r.stop();
			try {
				db = mDBHelper.getWritableDatabase();
				db.execSQL("UPDATE alarm_data SET onoff = 'off' WHERE _pos = "+pos+ ";");
				
				db.close();
				mDBHelper.close();
				// 값 변경 될까?
			} catch (Exception e) {
				Test_Gawjae_item.setAlarm.setChecked(false);
			}
			if (state == 1) {
				aManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				if (variable.isSilent == true) {// 초기 상태가 무음 이였으면
					aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// 다시
																			// 무음상태로
																			// 변환
																			// (밑에도
																			// 같은
																			// 방식)
				} else if (variable.isSound == true) {
					aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				} else if (variable.isVibrate == true) {
					aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				}
			}
			finish();
			break;
		case R.id.revibrate_btn2:
			Toast.makeText(getApplicationContext(), "5분후 다시알립니다.",
					Toast.LENGTH_SHORT).show();
			try {
				vi.vibrate(endpattern, -1);
			} catch (Exception e) {
			}
			alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

			intent = new Intent(Test_Dialog.this, Test_AlarmRecever.class);
			pender = PendingIntent.getBroadcast(Test_Dialog.this, 1, intent, 0);// 알람
																			// 리시버
																			// 다시
																			// 실행

			Time now = new Time();
			now.setToNow();
			Year = now.year;
			Month = now.month;
			Day = now.monthDay;
			Hour = now.hour;
			Minute = now.minute;
			Second = now.second;

			c.set(Year, Month, Day, Hour, Minute + 5, Second);// 클릭된 시간부터 5분 추가

			alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pender);

			r.stop();
			if (state == 1) {
				aManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				if (variable.isSilent == true) {
					aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				} else if (variable.isSound == true) {
					aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				} else if (variable.isVibrate == true) {
					aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				}
			}
			finish();
			break;
		}
	}

}
