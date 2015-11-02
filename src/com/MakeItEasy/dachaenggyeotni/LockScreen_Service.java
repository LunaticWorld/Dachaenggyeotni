package com.MakeItEasy.dachaenggyeotni;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;

public class LockScreen_Service extends Service {

	private LockScreen_Receiver mReceiver = null;
	private LockScreen_UpdateReceiver pReceiver;

	public void registerRestartAlarm(boolean isOn) {

		Intent intent = new Intent(LockScreen_Service.this,
				LockScreen_RestartReceiver.class);

		intent.setAction(LockScreen_RestartReceiver.ACTION_RESTART_SERVICE);

		PendingIntent sender = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intent, 0);

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

		if (isOn) {

			am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
					SystemClock.elapsedRealtime() + 1000, 10000, sender);

		} else {

			am.cancel(sender);

		}

	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;

	}

	@Override
	public void onCreate() {

		super.onCreate();

		pReceiver = new LockScreen_UpdateReceiver();

		IntentFilter pFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);

		pFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);

		pFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);

		pFilter.addDataScheme("package");

		registerReceiver(pReceiver, pFilter);

		mReceiver = new LockScreen_Receiver();

		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);

		registerReceiver(mReceiver, filter);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		super.onStartCommand(intent, flags, startId);

		startForeground(1, new Notification());

		Notification notification = new Notification(R.drawable.ic_launcher,
				"다 챙겼니 스크린락 ", System.currentTimeMillis());

		notification.setLatestEventInfo(getApplicationContext(), "다 챙겼니 스크린 락",
				"다 챙겼니 스크린 락이 정상적인 실행 중 입니다.", null);

		startForeground(1, notification);

		if (intent != null) {

			if (intent.getAction() == null) {

				if (mReceiver == null) {

					mReceiver = new LockScreen_Receiver();

					IntentFilter filter = new IntentFilter(
							Intent.ACTION_SCREEN_OFF);

					registerReceiver(mReceiver, filter);

				}

			}

		}

		return START_REDELIVER_INTENT;

	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		if (pReceiver != null)

			unregisterReceiver(pReceiver);

		if (mReceiver != null) {

			unregisterReceiver(mReceiver);

		}

	}

}
