package com.MakeItEasy.dachaenggyeotni;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	Variable isCheck;
	Boolean ischeck;
	Intent information;
	ImageButton information_btn;
	public static final String KEY_MY_PREFERENCE = "information";
	private BackPressCloseHandler backPressCloseHandler;

	Button junbimul_a, junbimul_b, junbimul_c;

	int a = 0; // onStop 2회 실행 방지

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		junbimul_a = (Button) findViewById(R.id.junbimul_a);
		junbimul_b = (Button) findViewById(R.id.junbimul_b);
		junbimul_c = (Button) findViewById(R.id.junbimul_c);

		startActivity(new Intent(this, Splash.class));
		backPressCloseHandler = new BackPressCloseHandler(this);
		Handler handler = new Handler();

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				isCheck = (Variable) getApplicationContext();
				information_btn = (ImageButton) findViewById(R.id.information_btn);
				information_btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						information = new Intent(getApplicationContext(),
								dialog.class);
						startActivity(information);
					}
				});

				SharedPreferences prefs = getSharedPreferences("ischeck",
						MODE_PRIVATE);
				ischeck = prefs
						.getBoolean(KEY_MY_PREFERENCE, isCheck.isChecked);
				isCheck.isChecked = ischeck; // 업데이트시 getboolean이
												// default값(false)으로 바뀜 방지
				// 첫번째 인자는 키, 두번째 인자는 키에 대한 데이터가 존재하지 않을 경우의 디폴트값

				if (ischeck == false) {
					information = new Intent(getApplicationContext(),
							dialog.class);
					startActivity(information);
				}
				a++; // <- 이거 없으면 onStop 두번 실행되서 에러난다.
			}
		}, 2000);

		junbimul_a.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						CalendarView.class);
				startActivity(intent);
			}
		});
		junbimul_b.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						Junbimul_moklok.class);
				startActivity(intent);
			}
		});
		junbimul_c.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						Test_Gawjae_Activity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		backPressCloseHandler.onBackPressed();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (a == 1) {
			SharedPreferences prefs = getSharedPreferences("ischeck",
					MODE_PRIVATE);
			SharedPreferences.Editor edit = prefs.edit();
			edit.putBoolean(KEY_MY_PREFERENCE, isCheck.isChecked);
			edit.commit();

		}
		super.onStop();
	}

}
