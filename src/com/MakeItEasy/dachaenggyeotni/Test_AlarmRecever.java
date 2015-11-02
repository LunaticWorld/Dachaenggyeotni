package com.MakeItEasy.dachaenggyeotni;

import java.util.Date;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class Test_AlarmRecever extends BroadcastReceiver {
	long startTime = SystemClock.elapsedRealtime();
	long cycleTime = 5 * 1000;

	Notification notify;
	int unique = 0;
	Date date_format;
	@Override
	public void onReceive(Context context, Intent intent) {
		/*Intent intent2 = new Intent(context, Test_PushClick.class);
		PendingIntent pender = PendingIntent
				.getActivity(context, unique, intent2, 0);

		Toast.makeText(context, "알람이 왔어요.", Toast.LENGTH_LONG).show();

		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notify = new Notification(R.drawable.picture, "text",
				System.currentTimeMillis());
		notify.setLatestEventInfo(context, "이부분이", "푸쉬알림 위치에 띄워주는 부분", pender);
		notify.flags |= Notification.FLAG_INSISTENT;
		notify.number++;
		notify.flags |= Notification.FLAG_SHOW_LIGHTS;
		notify.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationmanager.notify(1, notify);*/
		
////////////////////////////////////////////////////////////////////////////////////////////위에는 푸쉬 알림 아래는 화면에 띄워주는 투명 엑티비티
		unique = intent.getIntExtra("get_time", 0);
		date_format = (Date) intent.getSerializableExtra("date_format");
		Log.i("현재 시간은 ->>", ""+date_format);
		Intent intent1 = new Intent(context, Test_Dialog.class);
		PendingIntent pender1 = PendingIntent.getActivity(context, unique, intent1, 0);
		intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent1.putExtra("get_pos", unique);
		context.startActivity(intent1);
	}

}