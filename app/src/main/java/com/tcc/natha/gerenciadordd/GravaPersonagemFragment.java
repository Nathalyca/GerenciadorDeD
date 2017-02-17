package com.tcc.natha.gerenciadordd;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.natha.gerenciadordd.models.Personagem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GravaPersonagemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GravaPersonagemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GravaPersonagemFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


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

    private Button GravaButton;

    private Personagem pers;

    private String uID;
    private String persID;

    private FirebaseUser user;


    public GravaPersonagemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GravaPersonagemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GravaPersonagemFragment newInstance(String param1, String param2) {
        GravaPersonagemFragment fragment = new GravaPersonagemFragment();
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

        //Log.d(TAG, "onCreate");

        // Views
        mNomepersField = (EditText) getView().findViewById(R.id.field_nomepers);
        mRacaField = (EditText) getView().findViewById(R.id.field_raca);
        mClasseField = (EditText) getView().findViewById(R.id.field_classe);
        mSubRacaField = (EditText) getView().findViewById(R.id.field_subraca);
        mAntecedenteField = (EditText) getView().findViewById(R.id.field_antecedente);
        mTendenciaField = (EditText) getView().findViewById(R.id.field_tendencia);
        mNivelField = (EditText) getView().findViewById(R.id.field_nivel);
        mPvField = (EditText) getView().findViewById(R.id.field_pv);
        mIniciativaField = (EditText) getView().findViewById(R.id.field_iniciativa);
        mForcaField = (EditText)
                getView().findViewById(R.id.field_forca);
        mDestrezaField = (EditText) getView().findViewById(R.id.field_destreza);
        mConstituicaoField = (EditText) getView().findViewById(R.id.field_constituicao);
        mInteligenciaField = (EditText) getView().findViewById(R.id.field_inteligencia);
        mSabedoriaField = (EditText) getView().findViewById(R.id.field_sabedoria);
        mCarismaField = (EditText) getView().findViewById(R.id.field_carisma);


        // Buttons
        GravaButton = (Button) getView().findViewById(R.id.gravar_button);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grava_personagem, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onClick(View v) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
