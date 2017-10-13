package com.tcc.natha.gerenciadordd.fragments.personagem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.tcc.natha.gerenciadordd.models.Personagem;
import com.tcc.natha.gerenciadordd.models.PersonagemItem;

import java.util.List;


public class EditPersonagemFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EditPersonagemFragment";
    private Context context;
    private DatabaseReference mPersReference;
    private DatabaseReference mDatabase;
    private EditText mNomepersField;
    private EditText mRacaField;
    private EditText mSubRacaField;
    private EditText mClasseField;
    private EditText mNivelField;
    private EditText mAntecedenteField;
    private EditText mTendenciaField;
    private EditText mClasseArmadField;
    private EditText mIniciativaField;
    private EditText mDeslocField;
    private EditText mJogadorField;
    private EditText mXpField;
    private EditText mPvTotalField;
    private EditText mPvAtualField;
    private EditText mPvTempField;
    private EditText mForcaField;
    private EditText mDestrezaField;
    private EditText mConstituicaoField;
    private EditText mInteligenciaField;
    private EditText mSabedoriaField;
    private EditText mCarismaField;
    private EditText mForca2Field;
    private EditText mDestreza2Field;
    private EditText mConstituicao2Field;
    private EditText mInteligencia2Field;
    private EditText mSabedoria2Field;
    private EditText mCarisma2Field;
    private Button gravaButton;
    private Personagem pers;
    private View view;
    private String uID;
    private FirebaseUser user;
    private String persoID;

    public EditPersonagemFragment() {

    }

    public static EditPersonagemFragment newInstance(String param1, String param2) {
        EditPersonagemFragment fragment = new EditPersonagemFragment();
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
        if(b!= null){
            persoID = b.getString("persoID", null);
        }
        pers = new Personagem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_personagem, container, false);
        context = getActivity().getApplicationContext();

        // Views
        mNomepersField = (EditText) view.findViewById(R.id.field_nomepers);
        mRacaField = (EditText) view.findViewById(R.id.field_raca);
        mSubRacaField = (EditText) view.findViewById(R.id.field_subraca);
        mClasseField = (EditText) view.findViewById(R.id.field_classe);
        mNivelField = (EditText) view.findViewById(R.id.field_nivel);
        mAntecedenteField = (EditText) view.findViewById(R.id.field_antecedente);
        mTendenciaField = (EditText) view.findViewById(R.id.field_tendencia);
        mClasseArmadField = (EditText) view.findViewById(R.id.field_classeArmad);
        mIniciativaField = (EditText) view.findViewById(R.id.field_iniciativa);
        mDeslocField = (EditText) view.findViewById(R.id.field_desloc);
        mJogadorField = (EditText) view.findViewById(R.id.field_jogador);
        mXpField = (EditText) view.findViewById(R.id.field_xp);
        mPvTotalField = (EditText) view.findViewById(R.id.field_pv_total);
        mPvTempField = (EditText) view.findViewById(R.id.field_pv_temp);
        mPvAtualField = (EditText) view.findViewById(R.id.field_pv_atual);
        mForcaField = (EditText) view.findViewById(R.id.field_forca);
        mDestrezaField = (EditText) view.findViewById(R.id.field_destreza);
        mConstituicaoField = (EditText) view.findViewById(R.id.field_constituicao);
        mInteligenciaField = (EditText) view.findViewById(R.id.field_inteligencia);
        mSabedoriaField = (EditText) view.findViewById(R.id.field_sabedoria);
        mCarismaField = (EditText) view.findViewById(R.id.field_carisma);
        mForca2Field = (EditText) view.findViewById(R.id.field_forca2);
        mDestreza2Field = (EditText) view.findViewById(R.id.field_destreza2);
        mConstituicao2Field = (EditText) view.findViewById(R.id.field_constituicao2);
        mInteligencia2Field = (EditText) view.findViewById(R.id.field_inteligencia2);
        mSabedoria2Field = (EditText) view.findViewById(R.id.field_sabedoria2);
        mCarisma2Field = (EditText) view.findViewById(R.id.field_carisma2);

        // Buttons
        gravaButton = (Button) view.findViewById(R.id.gravar_button);
        gravaButton.setOnClickListener(this);

        //BD
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPersReference = FirebaseDatabase.getInstance().getReference()
                .child("personagens");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    mDatabase.child("Personagens").child(persoID).addValueEventListener(new ValueEventListener() {
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

    public void gravaPersonagem(){
        if(pers!= null && mNomepersField.getText().length() > 0){
            pers.setNomePerso(mNomepersField.getText().toString());
            pers.setRaca(mRacaField.getText().toString());
            pers.setSubRaca (mSubRacaField.getText().toString());
            pers.setClasse(mClasseField.getText().toString());
            pers.setNivel (mNivelField.getText().toString());
            pers.setAntecedente (mAntecedenteField.getText().toString());
            pers.setTendencia (mTendenciaField.getText().toString());
            pers.setClasseArmad(mClasseArmadField.getText().toString());
            pers.setIniciativa (mIniciativaField.getText().toString());
            pers.setDesloc(mDeslocField.getText().toString());
            pers.setJogador(mJogadorField.getText().toString());
            pers.setXp(mXpField.getText().toString());
            pers.setPvTotal (mPvTotalField.getText().toString());
            pers.setPvAtual (mPvAtualField.getText().toString());
            pers.setPvTemp (mPvTempField.getText().toString());
            pers.setForca (mForcaField.getText().toString());
            pers.setDestreza (mDestrezaField.getText().toString());
            pers.setConstituicao (mConstituicaoField.getText().toString());
            pers.setInteligencia (mInteligenciaField.getText().toString());
            pers.setSabedoria (mSabedoriaField.getText().toString());
            pers.setCarisma (mCarismaField.getText().toString());
            pers.setForca2 (mForca2Field.getText().toString());
            pers.setDestreza2 (mDestreza2Field.getText().toString());
            pers.setConstituicao2 (mConstituicao2Field.getText().toString());
            pers.setInteligencia2 (mInteligencia2Field.getText().toString());
            pers.setSabedoria2 (mSabedoria2Field.getText().toString());
            pers.setCarisma2 (mCarisma2Field.getText().toString());
            mDatabase.child("Personagens").child(persoID).setValue(pers);
            PersonagemItem personagemItem = new PersonagemItem(persoID,pers.getNomePerso(), pers.getClasse(), pers.getNivel());
            mDatabase.child("Users").child(user.getUid()).child("Personagens").child(persoID).setValue(personagemItem);
            Toast.makeText(context, R.string.gravadoSucesso , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "pers null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.gravar_button) {
            gravaPersonagem();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume(){
        super.onResume();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        for (Fragment fragment: fragmentList ) {
            if(fragment != null){
                if(fragment.getId() != this.getId()){
                    fragment.onPause();
                }
            }
        }
        transaction.commit();
    }

    @Override
    public void onPause(){
        gravaPersonagem();
        super.onPause();
    }

    public interface OnPageSelectedListener {
        void onPageSelected();
    }
}
