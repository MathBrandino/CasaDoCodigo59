package com.example.matheus.casadocodigocomlibs.fcm;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by matheus on 30/11/16.
 */

public class FCMRegister {

    public String registra() {

        FirebaseInstanceId instance = FirebaseInstanceId.getInstance();

        String token = instance.getToken();

        return token;
    }
}
