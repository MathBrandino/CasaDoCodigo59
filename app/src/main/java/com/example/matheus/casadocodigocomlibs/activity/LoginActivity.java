package com.example.matheus.casadocodigocomlibs.activity;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.event.SignInEvent;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.example.matheus.casadocodigocomlibs.server.WebClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Component;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "CasaDoCodigoApp";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private boolean flagLogado = false;

    @BindView(R.id.login_email)
    EditText email;
    @BindView(R.id.login_senha)
    EditText senha;

    private WebClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();


        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null && !flagLogado) {
                    flagLogado = true;
                    EventBus.getDefault().post(new SignInEvent());
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
            }
        };

        firebaseAuth.addAuthStateListener(listener);
    }


    @Subscribe
    public void vaiParaMain(SignInEvent event) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ciclo", "OnStop Login");
        EventBus.getDefault().unregister(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAuth.removeAuthStateListener(listener);


    }

    @OnClick(R.id.login_logar)
    public void logar() {


        String email = this.email.getText().toString().trim();
        String senha = this.senha.getText().toString().trim();

        if (!email.isEmpty() || !senha.isEmpty()) {
            final ProgressDialog progressDialog = ProgressDialog.show(this, "Aguarde", "Fazendo login", false, false);
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            progressDialog.dismiss();

                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail", task.getException());
                                Snackbar.make(LoginActivity.this.email, "Acesso não autorizado, verifique suas informações",
                                        Snackbar.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            Snackbar.make(this.senha, "Por favor complete todos os campos", Snackbar.LENGTH_SHORT)
                    .show();
        }


    }


    @OnClick(R.id.login_novo)
    public void cadastrar() {
        startActivity(new Intent(this, NovoUserActivity.class));
    }

}