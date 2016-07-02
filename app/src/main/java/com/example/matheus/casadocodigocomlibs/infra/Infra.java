package com.example.matheus.casadocodigocomlibs.infra;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by matheus on 01/07/16.
 */
public class Infra {

    public static void removeBotaoVoltar(AppCompatActivity activity) {

        activity.invalidateOptionsMenu();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public static void colocaBotaoVoltar(AppCompatActivity activity) {
        activity.invalidateOptionsMenu();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
