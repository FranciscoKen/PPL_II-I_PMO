package ppl.pmotrainingapps.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Arya Pradipta on 13/03/2018.
 */

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " +
                    remoteMessage.getNotification().getBody());


// build notification
// the addAction re-use the same intent to keep the example short
            Notification n  = new Notification.Builder(this)
                    .setContentTitle("New mail from " + "test@gmail.com")
                    .setContentText("Subject")
                    .setSmallIcon(R.drawable.logoitb)
                    .build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notify_001",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(0, n);
        }

    }
}
