package com.tcc.natha.gerenciadordd.fragments.personagem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.tcc.natha.gerenciadordd.models.personagem.Personagem;
import com.tcc.natha.gerenciadordd.models.personagem.PersonagemInstance;
import com.tcc.natha.gerenciadordd.models.personagem.PersonagemItem;

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
        PersonagemInstance.getInstance();
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

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            PersonagemInstance.getInstance().setPersonagem(dataSnapshot.getValue(Personagem.class));
                            if(PersonagemInstance.getInstance().getPersonagem() != null){
                                loadFields();
                                Toast.makeText(context, "loadFields EditPersonagemFragment", Toast.LENGTH_SHORT).show();
                            }else{
                                PersonagemInstance.getInstance().setPersonagem(new Personagem(user.getUid()));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    mDatabase.child("Personagens").child(persoID).addValueEventListener(valueEventListener);
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
        if(PersonagemInstance.getInstance().getPersonagem()!= null && mNomepersField.getText().length() > 0){
            Personagem personagem = PersonagemInstance.getInstance().getPersonagem();
            personagem.setNomePerso(mNomepersField.getText().toString());
            personagem.setRaca(mRacaField.getText().toString());
            personagem.setSubRaca (mSubRacaField.getText().toString());
            personagem.setClasse(mClasseField.getText().toString());
            personagem.setNivel (mNivelField.getText().toString());
            personagem.setAntecedente (mAntecedenteField.getText().toString());
            personagem.setTendencia (mTendenciaField.getText().toString());
            personagem.setClasseArmad(mClasseArmadField.getText().toString());
            personagem.setIniciativa (mIniciativaField.getText().toString());
            personagem.setDesloc(mDeslocField.getText().toString());
            personagem.setJogador(mJogadorField.getText().toString());
            personagem.setXp(mXpField.getText().toString());
            personagem.setPvTotal (mPvTotalField.getText().toString());
            personagem.setPvAtual (mPvAtualField.getText().toString());
            personagem.setPvTemp (mPvTempField.getText().toString());
            personagem.setForca (mForcaField.getText().toString());
            personagem.setDestreza (mDestrezaField.getText().toString());
            personagem.setConstituicao (mConstituicaoField.getText().toString());
            personagem.setInteligencia (mInteligenciaField.getText().toString());
            personagem.setSabedoria (mSabedoriaField.getText().toString());
            personagem.setCarisma (mCarismaField.getText().toString());
            personagem.setForca2 (mForca2Field.getText().toString());
            personagem.setDestreza2 (mDestreza2Field.getText().toString());
            personagem.setConstituicao2 (mConstituicao2Field.getText().toString());
            personagem.setInteligencia2 (mInteligencia2Field.getText().toString());
            personagem.setSabedoria2 (mSabedoria2Field.getText().toString());
            personagem.setCarisma2 (mCarisma2Field.getText().toString());
            PersonagemInstance.getInstance().setPersonagem(personagem);
            mDatabase.child("Personagens").child(persoID).setValue(PersonagemInstance.getInstance().getPersonagem());
            PersonagemItem personagemItem = new PersonagemItem(persoID,PersonagemInstance.getInstance().getPersonagem().getNomePerso(), PersonagemInstance.getInstance().getPersonagem().getClasse(), PersonagemInstance.getInstance().getPersonagem().getNivel());
            mDatabase.child("Users").child(user.getUid()).child("Personagens").child(persoID).setValue(personagemItem);
        }else{
            Toast.makeText(context, "pers null", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadFields(){
        mNomepersField.setText(PersonagemInstance.getInstance().getPersonagem().getNomePerso());
        mRacaField.setText(PersonagemInstance.getInstance().getPersonagem().getRaca());
        mSubRacaField.setText(PersonagemInstance.getInstance().getPersonagem().getSubRaca());
        mClasseField.setText(PersonagemInstance.getInstance().getPersonagem().getClasse());
        mNivelField.setText(PersonagemInstance.getInstance().getPersonagem().getNivel());
        mAntecedenteField.setText(PersonagemInstance.getInstance().getPersonagem().getAntecedente());
        mTendenciaField.setText(PersonagemInstance.getInstance().getPersonagem().getTendencia());
        mClasseArmadField.setText(PersonagemInstance.getInstance().getPersonagem().getClasseArmad());
        mIniciativaField.setText(PersonagemInstance.getInstance().getPersonagem().getIniciativa());
        mDeslocField.setText(PersonagemInstance.getInstance().getPersonagem().getDesloc());
        mJogadorField.setText(PersonagemInstance.getInstance().getPersonagem().getJogador());
        mXpField.setText(PersonagemInstance.getInstance().getPersonagem().getXp());
        mPvTotalField.setText(PersonagemInstance.getInstance().getPersonagem().getPvTotal());
        mPvAtualField.setText(PersonagemInstance.getInstance().getPersonagem().getPvAtual());
        mPvTempField.setText(PersonagemInstance.getInstance().getPersonagem().getPvTemp());
        mForcaField.setText(PersonagemInstance.getInstance().getPersonagem().getForca());
        mDestrezaField.setText(PersonagemInstance.getInstance().getPersonagem().getDestreza());
        mConstituicaoField.setText(PersonagemInstance.getInstance().getPersonagem().getConstituicao());
        mInteligenciaField.setText(PersonagemInstance.getInstance().getPersonagem().getInteligencia());
        mSabedoriaField.setText(PersonagemInstance.getInstance().getPersonagem().getSabedoria());
        mCarismaField.setText(PersonagemInstance.getInstance().getPersonagem().getCarisma());
        mForca2Field.setText(PersonagemInstance.getInstance().getPersonagem().getForca2());
        mDestreza2Field.setText(PersonagemInstance.getInstance().getPersonagem().getDestreza2());
        mConstituicao2Field.setText(PersonagemInstance.getInstance().getPersonagem().getConstituicao2());
        mInteligencia2Field.setText(PersonagemInstance.getInstance().getPersonagem().getInteligencia2());
        mSabedoria2Field.setText(PersonagemInstance.getInstance().getPersonagem().getSabedoria2());
        mCarisma2Field.setText(PersonagemInstance.getInstance().getPersonagem().getCarisma2());
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
    public void onPause(){
        Toast.makeText(context, "onPause" , Toast.LENGTH_SHORT).show();
        gravaPersonagem();
        super.onPause();
    }

    public interface OnPageSelectedListener {
        void onPageSelected();
    }
}
