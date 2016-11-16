package com.example.matheus.casadocodigocomlibs.application;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.matheus.casadocodigocomlibs.event.SignInEvent;
import com.example.matheus.casadocodigocomlibs.event.SignOutEvent;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoModule;
import com.example.matheus.casadocodigocomlibs.module.DaggerCasaDoCodigoComponent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by matheus on 30/06/16.
 */
public class CasaDoCodigoApplication extends Application  {

    private CasaDoCodigoComponent component;
    private String TAG = "CasaDoCodigoApp";

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerCasaDoCodigoComponent
                .builder()
                .casaDoCodigoModule(new CasaDoCodigoModule())
                .build();
    }


    public CasaDoCodigoComponent getComponent() {
        return component;
    }
}
