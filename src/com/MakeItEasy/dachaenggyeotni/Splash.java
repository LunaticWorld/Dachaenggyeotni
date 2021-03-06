package com.MakeItEasy.dachaenggyeotni;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				finish(); // 액티비티 종료
			}
		};

		handler.sendEmptyMessageDelayed(0, 2000); // ms, 3초후 종료시킴
	}

}
