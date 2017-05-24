package com.example.matheus.casadocodigocomlibs.application;

import android.app.Application;
import android.content.Context;

import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoModule;
import com.example.matheus.casadocodigocomlibs.module.DaggerCasaDoCodigoComponent;

/**
 * Created by matheus on 30/06/16.
 */
public class CasaDoCodigoApplication extends Application  {

    private CasaDoCodigoComponent component;
    private String TAG = "CasaDoCodigoApp";
    private static CasaDoCodigoApplication instance;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        component = DaggerCasaDoCodigoComponent
                .builder()
                .casaDoCodigoModule(new CasaDoCodigoModule())
                .build();
    }


    public CasaDoCodigoComponent getComponent() {
        return component;
    }
}
