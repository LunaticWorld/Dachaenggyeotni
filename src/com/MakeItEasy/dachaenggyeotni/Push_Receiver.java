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

		Toast.makeText(context, "�˶��� �Ծ��.", Toast.LENGTH_LONG).show();

		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notify = new Notification(R.drawable.picture, "�� ì��� - ���� �˸�",
				System.currentTimeMillis());
		notify.setLatestEventInfo(context, "���� �˶�", "���� : ���� �ڹ��� �ٳ�� ���� �ۼ��ϱ� ", pender);
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
