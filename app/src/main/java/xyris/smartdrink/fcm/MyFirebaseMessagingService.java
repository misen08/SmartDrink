package xyris.smartdrink.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import xyris.smartdrink.PantallaInicial;
import xyris.smartdrink.R;
import xyris.smartdrink.entities.FechaHora;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "NOTIFICACIONES";
    public static final String TOPIC = "xyris.smartdrink";

    SharedPreferences sp;
    SharedPreferences.Editor dateNotificationsEditor;

    @Override
        public void onNewToken(String s) {
        super.onNewToken(s);

        Log.e(TAG, "Token: " + s);
        String subscribe = TOPIC + "." + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.i(TAG,"Suscribiendose al TOPIC: " + subscribe);
        FirebaseMessaging.getInstance().subscribeToTopic(subscribe);

        Log.i(TAG,"Suscribiendose al TOPIC: " + TOPIC);
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String bodyNotification = "";
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        String dateMantenimiento = new FechaHora().formatDateMantenimiento(Calendar.getInstance().getTime());

        String from = remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibido de: " + from);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notificación: " + remoteMessage.getNotification().getBody());
            bodyNotification = remoteMessage.getNotification().getBody();
            showNotificationMessage(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data: " + remoteMessage.getData());
        }


        if(bodyNotification.contains("mantenimiento")){
            String fechasMantenimiento = sp.getString("FECHAS_MANTENIMIENTO", "ERROR");
            dateNotificationsEditor = sp.edit();
            if(fechasMantenimiento.equals("ERROR")){
                dateNotificationsEditor.putString("FECHAS_MANTENIMIENTO", dateMantenimiento.toString());
            } else {
                dateNotificationsEditor.putString("FECHAS_MANTENIMIENTO", fechasMantenimiento + "," + dateMantenimiento.toString());
            }
            dateNotificationsEditor.commit();
            Log.d("TAG_MICA", sp.getString("FECHAS_MANTENIMIENTO", "ERROR"));
        }

    }


    private void showNotificationMessage(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("Hearty365")
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("Info");

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }
}
