package fr.univartois.ili.fsnet.mobile.services.client.graphicsElements;

import fr.univartois.ili.fsnet.mobile.services.client.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore.Audio;


/**
 * 
 * @author alexandre thery
 *
 */
public class SimpleMessagesNotification{


	public SimpleMessagesNotification(NotificationManager notificationManager,Intent intent,Activity activity,String note,String title,String message)
	{
		Notification notification = new Notification(R.drawable.icon_fsnet, 
				note, System.currentTimeMillis());
		
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "1");
		notification.vibrate = new long[] {200};
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;	
		Intent intentNotification = intent;
		PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intentNotification, 0);
		notification.setLatestEventInfo(activity, title, message, pendingIntent);
		notificationManager.notify(0, notification);

	}


}
