package com.example.matheus.casadocodigocomlibs.event;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by matheus on 22/11/16.
 */
public class FCMEvent {
    public final RemoteMessage remoteMessage;

    public FCMEvent(RemoteMessage remoteMessage) {
        this.remoteMessage = remoteMessage;
    }
}
