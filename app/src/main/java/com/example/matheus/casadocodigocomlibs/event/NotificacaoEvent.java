package com.example.matheus.casadocodigocomlibs.event;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by matheus on 23/11/16.
 */
public class NotificacaoEvent {
    public final RemoteMessage remoteMessage;

    public NotificacaoEvent(RemoteMessage remoteMessage) {
        this.remoteMessage = remoteMessage;
    }
}
