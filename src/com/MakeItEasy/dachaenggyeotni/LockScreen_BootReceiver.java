package com.MakeItEasy.dachaenggyeotni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class LockScreen_BootReceiver extends BroadcastReceiver {

	Variable state;
	
	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			
			state = (Variable)context.getApplicationContext();
			
			SharedPreferences pref = context.getSharedPreferences("screenlock_state", context.MODE_PRIVATE);
			state.screenlock_state = pref.getBoolean("screen_state", true);
			if(state.screenlock_state){
			Intent i = new Intent(context, LockScreen_Service.class);

			context.startService(i);
			}
		}

	}

}
