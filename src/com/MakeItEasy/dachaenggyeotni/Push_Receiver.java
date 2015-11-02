package com.MakeItEasy.dachaenggyeotni;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

public class Push_Receiver extends BroadcastReceiver {
	long startTime = SystemClock.elapsedRealtime();
	long cycleTime = 5 * 1000;

	Notification notify;

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent intent2 = new Intent(context, Push_PushClick.class);
		PendingIntent pender = PendingIntent
				.getActivity(context, 0, intent2, 0);

		Toast.makeText(context, "알람이 왔어요.", Toast.LENGTH_LONG).show();

		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notify = new Notification(R.drawable.picture, "다 챙겼니 - 과제 알림",
				System.currentTimeMillis());
		notify.setLatestEventInfo(context, "과제 알람", "과학 : 역사 박물관 다녀와 보고서 작성하기 ", pender);
		notify.flags |= Notification.FLAG_INSISTENT;
		notify.number++;
		notify.flags |= Notification.FLAG_SHOW_LIGHTS;
		notify.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationmanager.notify(1, notify);

		
	}

	private void finish() {
		// TODO Auto-generated method stub
	
			finish();
		
	}
	

}
