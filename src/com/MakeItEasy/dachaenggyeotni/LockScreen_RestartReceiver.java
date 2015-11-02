package com.MakeItEasy.dachaenggyeotni;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LockScreen_RestartReceiver extends BroadcastReceiver {

	static public final String ACTION_RESTART_SERVICE = "RestartReceiver.restart"; // °ªÀº
																					// ¸¾´ë·Î

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(ACTION_RESTART_SERVICE)) {

			Intent i = new Intent(context, LockScreen_Service.class);

			context.startService(i);

		}

	}

}
