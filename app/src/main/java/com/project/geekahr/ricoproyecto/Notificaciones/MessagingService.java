package com.project.geekahr.ricoproyecto.Notificaciones;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.geekahr.ricoproyecto.Fragmentos.HomeFragment;
import com.project.geekahr.ricoproyecto.R;

/**
 * Created by geekahr on 24/10/16.
 */

public class MessagingService extends FirebaseMessagingService{

    public static final String TAG = "NOTICIAS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        String from = remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibido de: " + from);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notificación: " + remoteMessage.getNotification().getBody());

            mostrarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data: " + remoteMessage.getData());
        }
    }

    private void mostrarNotificacion(String title, String body) {

        Intent intent = new Intent(this, HomeFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_icon_notify)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
