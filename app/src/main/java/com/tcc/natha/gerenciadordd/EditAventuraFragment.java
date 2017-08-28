package com.tcc.natha.gerenciadordd;

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
import com.tcc.natha.gerenciadordd.models.AventuraMestreItem;
import com.tcc.natha.gerenciadordd.models.Personagem;
import com.tcc.natha.gerenciadordd.models.PersonagemItem;
import com.tcc.natha.gerenciadordd.models.SequencialAventura;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditAventuraFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditAventuraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAventuraFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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

    private int sequencialDefault = 1;

    private String sequencialAux;

    private View view;

    private FirebaseUser user;

    public EditAventuraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPersonagemFragment.
     */

    // TODO: Rename and change types and number of parameters
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
/*
        Bundle b = getActivity().getIntent().getExtras();
        if(b!= null){
            persoID = b.getString("persoID", null);
        }
        Log.d(TAG, "persoID:" + persoID);
        pers = new Personagem();*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_aventura, container, false);

        Log.d(TAG, "onCreate");

        context = getActivity().getApplicationContext();

        // Views
        mNomeAventField = (EditText) view.findViewById(R.id.field_nomeavent);

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

                Log.d(TAG, "getCurrentUser");

                user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d(TAG, "onAuthStateChanged:email:" + user.getEmail());

                    mDatabase.child("SequencialAventura").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            seqAvent = dataSnapshot.getValue(SequencialAventura.class);
                            if(seqAvent != null){
                                sequencialAux = String.valueOf(seqAvent.getSeqCodAventura()+1);
                                mNumAventTextField.setText(sequencialAux);
                                Log.d(TAG, "mNumAventTextField" + mNumAventTextField);

                            }else {
                                seqAvent.setSeqCodAventura(sequencialDefault);
                                Log.d(TAG, "seqAvent.getSeqCodAventura()" + seqAvent.getSeqCodAventura());
                                mNumAventTextField.setText(String.valueOf(seqAvent.getSeqCodAventura()));
                                Log.d(TAG, "mNumAventTextField" + mNumAventTextField);
                            }
 /*                   mDatabase.child("Personagens").child(persoID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            pers = dataSnapshot.getValue(Personagem.class);
                            if(pers != null){
                                mNomepersField.setText(pers.getNomePerso());
                                mRacaField.setText(pers.getRaca());
                                mSubRacaField.setText(pers.getSubRaca());
                                mClasseField.setText(pers.getClasse());
                                mNivelField.setText(pers.getNivel());
                                mAntecedenteField.setText(pers.getAntecedente());
                                mTendenciaField.setText(pers.getTendencia());
                                mClasseArmadField.setText(pers.getClasseArmad());
                                mIniciativaField.setText(pers.getIniciativa());
                                mDeslocField.setText(pers.getDesloc());
                                mJogadorField.setText(pers.getJogador());
                                mXpField.setText(pers.getXp());
                                mPvTotalField.setText(pers.getPvTotal());
                                mPvAtualField.setText(pers.getPvAtual());
                                mPvTempField.setText(pers.getPvTemp());
                                mForcaField.setText(pers.getForca());
                                mDestrezaField.setText(pers.getDestreza());
                                mConstituicaoField.setText(pers.getConstituicao());
                                mInteligenciaField.setText(pers.getInteligencia());
                                mSabedoriaField.setText(pers.getSabedoria());
                                mCarismaField.setText(pers.getCarisma());
                                mForca2Field.setText(pers.getForca2());
                                mDestreza2Field.setText(pers.getDestreza2());
                                mConstituicao2Field.setText(pers.getConstituicao2());
                                mInteligencia2Field.setText(pers.getInteligencia2());
                                mSabedoria2Field.setText(pers.getSabedoria2());
                                mCarisma2Field.setText(pers.getCarisma2());
                            }else{
                                pers = new Personagem(user.getUid());
                            }*/
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                } else {

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Log.d(TAG, "onButtonPressed");
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
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
        Log.d(TAG, "onDetach");
        super.onDetach();
        mListener = null;
    }


    public void gravaAventura(){

        Log.d(TAG, "User" + user);

        Log.d(TAG, "grava aventura");

        avent.setNomeAventura(mNomeAventField.getText().toString());

        avent.setSeqAventura(seqAvent.getSeqCodAventura());

        String key = mDatabase.child("Aventura").push().getKey();

        mDatabase.child("Aventura").child(key).setValue(avent);

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
        Log.d(TAG, "gravado");

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        Log.d(TAG, "onClick");
        if (i == R.id.gravar_button) {
            Log.d(TAG, "chama grava aventura");
            gravaAventura();
        }
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

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of loginFragment");
        gravaAventura();
        super.onPause();
    }
}
