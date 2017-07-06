package com.tcc.natha.gerenciadordd;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener{

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUsersReference;

    private EditText mEmailField;
    private EditText mPasswordField;

    private Context context;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_login);

            context = this.getApplicationContext();

            // Views
            mEmailField = (EditText) findViewById(R.id.field_email);
            mEmailField.setText("nathalyca03@gmail.com");
            mPasswordField = (EditText) findViewById(R.id.field_password);
            mPasswordField.setText("@abcd1234");

            // Buttons
            findViewById(R.id.email_sign_in_button).setOnClickListener(this);
            findViewById(R.id.email_create_account_button).setOnClickListener(this);
            findViewById(R.id.email_forgot).setOnClickListener(this);

            //BD
            mUsersReference = FirebaseDatabase.getInstance().getReference()
                    .child("users");

            mAuth = FirebaseAuth.getInstance();
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User is signed in
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
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Criado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        // [END create_user_with_email]

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.email_forgot) {
            mAuth.sendPasswordResetEmail(mEmailField.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Um email de verificação foi enviado para o seu email",
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Email enviado.");
                            }
                        }
                    });
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "chama GravaPersonagemFragment");

                            Intent intent = new Intent(context, MenuActivity.class);
                            startActivity(intent);

                        }

                    }
                });
        // [END sign_in_with_email]
    }

}
