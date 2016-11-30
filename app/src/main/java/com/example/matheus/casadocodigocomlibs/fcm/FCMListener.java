package com.example.matheus.casadocodigocomlibs.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.activity.CarrinhoActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by matheus on 30/11/16.
 */

public class FCMListener extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        String message = data.get("message");

        PendingIntent pedingIntent =
                PendingIntent.getActivity(getBaseContext(), 123,
                        new Intent(getApplicationContext(), CarrinhoActivity.class),
                        PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(getBaseContext())
                .setSmallIcon(R.drawable.casadocodigo)
                .setContentTitle(message)
                .setColor(Color.WHITE)
                .setAutoCancel(true)
                .setContentIntent(pedingIntent)
                .build();


        Context context = getBaseContext();
        NotificationManager systemService = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        systemService.notify(123, notification);
    }
}
