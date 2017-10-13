package com.tcc.natha.gerenciadordd.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.natha.gerenciadordd.R;
import com.tcc.natha.gerenciadordd.models.AventuraMestreItem;
import com.tcc.natha.gerenciadordd.models.SequencialAventura;

public class EditAventuraFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EditAventuraFragment";
    private Context context;
    private DatabaseReference mSeqReference;
    private DatabaseReference mDatabase;
    private EditText mNomeAventField;
    private EditText mNumAventTextField;
    private Button gravaButton;
    private AventuraMestreItem avent;
    private SequencialAventura seqAvent;
    private String aventID;
    private int sequencialDefault = 1;
    private int sequencialAux;
    private View view;
    private FirebaseUser user;

    public EditAventuraFragment() {

    }

    public static EditAventuraFragment newInstance(String param1, String param2) {
        EditAventuraFragment fragment = new EditAventuraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Bundle b = getActivity().getIntent().getExtras();
        seqAvent = new SequencialAventura();
        if(b!= null){
            seqAvent.setSeqCodAventura(b.getInt("seqAventura", 0));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_aventura, container, false);
        context = getActivity().getApplicationContext();

        // Views
        mNomeAventField = (EditText) view.findViewById(R.id.field_nomeavent);
        mNumAventTextField = (EditText) view.findViewById(R.id.text_numaventtext);

        // Buttons
        gravaButton = (Button) view.findViewById(R.id.gravar_button);
        gravaButton.setOnClickListener(this);

        //BD
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSeqReference = FirebaseDatabase.getInstance().getReference()
                .child("SequencialAventura");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    mDatabase.child("Aventura").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            avent = dataSnapshot.getValue(AventuraMestreItem.class);
                            if (avent != null) {


                            }else{
                                avent = new AventuraMestreItem();
                                aventID = mDatabase.child("Aventura").push().getKey();
                                mNumAventTextField.setText(seqAvent.getSeqCodAventura() + "");
 /*                   mDatabase.child("Personagens").child(persoID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            pers = dataSnapshot.getValue(Personagem.class);
                            if(pers != null){
                                mNomepersField.setText(pers.getNomePerso());
                                mCarisma2Field.setText(pers.getCarisma2());
                            }else{
                                pers = new Personagem(user.getUid());
                            }*/
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void gravaAventura(){
        avent.setNomeAventura(mNomeAventField.getText().toString());
        avent.setSeqAventura(seqAvent.getSeqCodAventura());
        mDatabase.child("Aventura").child(aventID).setValue(avent);
        mDatabase.child("SequencialAventura").setValue(seqAvent);
       // Log.d(TAG, "persid: "+ mDatabase.child("Users").child(user.getUid()).child("Personagens").child(key).toString());
        //Log.d(TAG, "persid: "+ key);
/*
        mDatabase.child("Personagens").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Personagem pers1 = dataSnapshot.getValue(Personagem.class);
                mNomepersField.setText(pers1.getNomePerso());
                mRacaField.setText(pers1.getRaca());
                mClasseField.setText(pers1.getClasse());
                mSubRacaField.setText(pers1.getSubRaca());
                mAntecedenteField.setText(pers1.getAntecedente());
                mTendenciaField.setText(pers1.getTendencia());
                mNivelField.setText(pers1.getNivel());
                mPvTotalField.setText(pers1.getPvTotal());
                mIniciativaField.setText(pers1.getIniciativa());
                mForcaField.setText(pers1.getForca());
                mDestrezaField.setText(pers1.getDestreza());
                mConstituicaoField.setText(pers1.getConstituicao());
                mInteligenciaField.setText(pers1.getInteligencia());
                mSabedoriaField.setText(pers1.getSabedoria());
                mCarismaField.setText(pers1.getCarisma());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        }); */

        Toast.makeText(context, R.string.gravadoSucesso , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.gravar_button) {
            gravaAventura();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        gravaAventura();
    }
}
