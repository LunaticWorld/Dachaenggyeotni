package com.MakeItEasy.dachaenggyeotni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class LockScreen_UpdateReceiver extends BroadcastReceiver {

	Variable state;
	
	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		state = (Variable)context.getApplicationContext();
		
		SharedPreferences pref = context.getSharedPreferences("screenlock_state", context.MODE_PRIVATE);
		state.screenlock_state = pref.getBoolean("screen_state", true);
		
		if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {

			// 앱이 설치되었을 때

		} else if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {

			// 앱이 삭제되었을 때

		} else if (action.equals(Intent.ACTION_PACKAGE_REPLACED)) {

			// 앱이 업데이트 되었을 때
			if(state.screenlock_state){
			Intent i = new Intent(context, LockScreen_Service.class);

			context.startService(i);
			}
		}

	}

}
