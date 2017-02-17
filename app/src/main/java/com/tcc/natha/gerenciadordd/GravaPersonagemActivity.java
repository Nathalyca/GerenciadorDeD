package com.tcc.natha.gerenciadordd;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.natha.gerenciadordd.models.Personagem;
import com.google.firebase.database.DatabaseReference;


public class GravaPersonagemActivity extends AppCompatActivity implements
        View.OnClickListener{

    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "GravaPersonagemActivity";
    private DatabaseReference mPersReference;
    private DatabaseReference mDatabase;

    private EditText mNomepersField;
    private EditText mClasseField;
    private EditText mRacaField;
    private EditText mSubRacaField;
    private EditText mAntecedenteField;
    private EditText mTendenciaField;
    private EditText mNivelField;
    private EditText mPvField;
    private EditText mIniciativaField;
    private EditText mForcaField;
    private EditText mDestrezaField;
    private EditText mConstituicaoField;
    private EditText mInteligenciaField;
    private EditText mSabedoriaField;
    private EditText mCarismaField;

    private Personagem pers;

    private String uID;
    private String persID;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_grava_personagem);

        Log.d(TAG, "onCreate");

        // Views
        mNomepersField = (EditText) findViewById(R.id.field_nomepers);
        mRacaField = (EditText) findViewById(R.id.field_raca);
        mClasseField = (EditText) findViewById(R.id.field_classe);
        mSubRacaField = (EditText) findViewById(R.id.field_subraca);
        mAntecedenteField = (EditText) findViewById(R.id.field_antecedente);
        mTendenciaField = (EditText) findViewById(R.id.field_tendencia);
        mNivelField = (EditText) findViewById(R.id.field_nivel);
        mPvField = (EditText) findViewById(R.id.field_pv);
        mIniciativaField = (EditText) findViewById(R.id.field_iniciativa);
        mForcaField = (EditText)
                findViewById(R.id.field_forca);
        mDestrezaField = (EditText) findViewById(R.id.field_destreza);
        mConstituicaoField = (EditText) findViewById(R.id.field_constituicao);
        mInteligenciaField = (EditText) findViewById(R.id.field_inteligencia);
        mSabedoriaField = (EditText) findViewById(R.id.field_sabedoria);
        mCarismaField = (EditText) findViewById(R.id.field_carisma);


        // Buttons
        findViewById(R.id.gravar_button).setOnClickListener(this);

        //BD
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPersReference = FirebaseDatabase.getInstance().getReference()
                .child("personagens");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                Log.d(TAG, "getCurrentUser");

                user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d(TAG, "onAuthStateChanged:email:" + user.getEmail());
                } else {

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        int i = v.getId();
        if (i == R.id.gravar_button) {
            Log.d(TAG, "chama grava personagem");
            gravaPersonagem();
        }
    }

    public void gravaPersonagem(){

        Log.d(TAG, "user" + user);

        pers = new Personagem(user.getUid());

        Log.d(TAG, "grava personagem");

        pers.setPersonagem(mNomepersField.getText().toString());

        pers.setClasse(mClasseField.getText().toString());

        pers.setRaca(mRacaField.getText().toString());

        pers.setSubRaca (mSubRacaField.getText().toString());

        pers.setAntecedente (mAntecedenteField.getText().toString());

        pers.setTendencia (mTendenciaField.getText().toString());

  //      pers.setNivel (Integer.parseInt(mNivelField.getText().toString()));

        pers.setNivel (mNivelField.getText().toString());

        pers.setPv (mPvField.getText().toString());

        pers.setIniciativa (mIniciativaField.getText().toString());

        pers.setForca (mForcaField.getText().toString());

        pers.setDestreza (mDestrezaField.getText().toString());

        pers.setConstituicao (mConstituicaoField.getText().toString());

        pers.setInteligencia (mInteligenciaField.getText().toString());

        pers.setSabedoria (mSabedoriaField.getText().toString());

        pers.setCarisma (mCarismaField.getText().toString());
        String key = mDatabase.child("personagens").push().getKey();
        mDatabase.child("Personagens").child(key).setValue(pers);
        mDatabase.child("Users").child(user.getUid()).child("personagens").child(key).setValue(true);

        Log.d(TAG, "persid: "+ mDatabase.child("Users").child(user.getUid()).child("personagens").child(key).toString());
        Log.d(TAG, "persid: "+ key);

        mDatabase.child("Personagens").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Personagem pers1 = dataSnapshot.getValue(Personagem.class);
                System.out.println(pers1.getPersonagem());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });

        Log.d(TAG, "gravado");

    }

    //mEmailField.getText().toString(), mPasswordField.getText().toString()

}
