package com.example.matheus.casadocodigocomlibs.module;

import android.content.Context;

import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.converter.ItemServiceConverterFactory;
import com.example.matheus.casadocodigocomlibs.converter.LivroServiceConverterFactory;
import com.example.matheus.casadocodigocomlibs.model.Carrinho;
import com.example.matheus.casadocodigocomlibs.model.CartaoDao;
import com.example.matheus.casadocodigocomlibs.model.DaoMaster;
import com.example.matheus.casadocodigocomlibs.model.DaoSession;
import com.example.matheus.casadocodigocomlibs.server.DeviceService;
import com.example.matheus.casadocodigocomlibs.server.ItemService;
import com.example.matheus.casadocodigocomlibs.server.ListaLivrosService;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CasaDoCodigoModule {
    public static final String SERVER = "http://cdcmob.herokuapp.com/";

    @Provides
    @Singleton
    public Carrinho getCarrinho() {
        return new Carrinho();
    }

    @Provides
    @Singleton
    public ListaLivrosService getListaLivrosService() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(new LivroServiceConverterFactory())
                .build();

        ListaLivrosService service = client.create(ListaLivrosService.class);
        return service;
    }

    @Provides
    @Singleton
    public DeviceService getDeviceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.83.120:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeviceService service = retrofit.create(DeviceService.class);

        return service;
    }

    @Provides
    @Singleton
    public ItemService getItemService() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(new ItemServiceConverterFactory())
                .build();

        ItemService service = client.create(ItemService.class);
        return service;
    }


    @Provides
    @Singleton
    public Context getContext() {

        return CasaDoCodigoApplication.getInstance();
    }

    @Provides
    @Singleton
    public DaoSession getDaoSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cdc-bd");
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();


    }


    @Provides
    @Singleton
    public CartaoDao getCartaoDao(DaoSession session) {

        return session.getCartaoDao();

    }
}
