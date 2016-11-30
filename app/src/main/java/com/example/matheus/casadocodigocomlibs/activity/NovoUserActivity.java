package com.example.matheus.casadocodigocomlibs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheus.casadocodigocomlibs.R;
import com.example.matheus.casadocodigocomlibs.application.CasaDoCodigoApplication;
import com.example.matheus.casadocodigocomlibs.fcm.FCMRegister;
import com.example.matheus.casadocodigocomlibs.module.CasaDoCodigoComponent;
import com.example.matheus.casadocodigocomlibs.server.WebClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NovoUserActivity extends AppCompatActivity {

    private static final String TAG = "CasaDoCodigoApp";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.novo_user_email)
    EditText email;

    @BindView(R.id.novo_user_senha)
    EditText senha;

    private WebClient client;

    @Inject
    public void setWebClient(WebClient client) {
        this.client = client;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_user);
        ButterKnife.bind(this);

        CasaDoCodigoApplication application = (CasaDoCodigoApplication) getApplication();
        CasaDoCodigoComponent component = application.getComponent();
        component.inject(this);


        instanciaFirebaseAuth();
        criaListenerParaFirebaseAuth();

    }


    private void instanciaFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void criaListenerParaFirebaseAuth() {
        mAuth.signOut();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String id = new FCMRegister().registra();
                    client.enviaDadosFirebaseParaServidor(user.getEmail(),id);

                    vaiParaMain();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void vaiParaMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.novo_user_cadastrar)
    public void cadastrar() {
        String email = this.email.getText().toString().trim();
        String senha = this.senha.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(NovoUserActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}