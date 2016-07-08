package com.example.matheus.casadocodigocomlibs.application;

import android.app.Application;

import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoModule;
import com.example.matheus.casadocodigocomlibs.module.DaggerCasaDoCodigoComponent;

/**
 * Created by matheus on 30/06/16.
 */
public class CasaDoCodigoApplication extends Application {

    private CasaDoCodigoComponent component;

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
