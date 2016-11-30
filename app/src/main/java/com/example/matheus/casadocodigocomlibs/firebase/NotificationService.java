package com.example.matheus.casadocodigocomlibs.firebase;

import com.example.matheus.casadocodigocomlibs.event.NotificacaoEvent;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by matheus on 22/11/16.
 */

public class NotificationService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        EventBus.getDefault().post(new NotificacaoEvent(remoteMessage));
    }
}

