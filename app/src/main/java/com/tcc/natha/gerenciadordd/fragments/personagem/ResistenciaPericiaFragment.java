package com.tcc.natha.gerenciadordd.fragments.personagem;

        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
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
        import com.tcc.natha.gerenciadordd.models.personagem.PersonagemItem;

        import java.util.List;


public class ResistenciaPericiaFragment extends Fragment implements View.OnClickListener{
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
    private EditText mResForcaField;
    private CheckBox mResForcaBoolField;
    private EditText mResSabedoriaField;
    private CheckBox mResSabedoriaBoolField;
    private EditText mResConstituicaoField;
    private CheckBox mResConstituicaoBoolField;
    private EditText mResCarismaField;
    private CheckBox mResCarismaBoolField;
    private EditText mResDestrezaField;
    private CheckBox mResDestrezaBoolField;
    private EditText mResInteligenciaField;
    private CheckBox mResInteligenciaBoolField;
    private EditText mAcrobaciaField;
    private CheckBox mAcrobaciaBoolField;
    private EditText mArcanismoField;
    private CheckBox mArcanismoBoolField;
    private EditText mAtletismoField;
    private CheckBox mAtletismoBoolField;
    private EditText mAtuacaoField;
    private CheckBox mAtuacaoBoolField;
    private EditText mBlefarField;
    private CheckBox mBlefarBoolField;
    private EditText mFurtividadeField;
    private CheckBox mFurtividadeBoolField;
    private EditText mHistoriaField;
    private CheckBox mHistoriaBoolField;
    private EditText mIntimidacaoField;
    private CheckBox mIntimidacaoBoolField;
    private EditText mIntuicaoField;
    private CheckBox mIntuicaoBoolField;
    private EditText mInvestigacaoField;
    private CheckBox mInvestigacaoBoolField;
    private EditText mLidarAnimaisField;
    private CheckBox mLidarAnimaisBoolField;
    private EditText mMedicinaField;
    private CheckBox mMedicinaBoolField;
    private EditText mNaturezaField;
    private CheckBox mNaturezaBoolField;
    private EditText mPercepcaoField;
    private CheckBox mPercepcaoBoolField;
    private EditText mPredestinacaoField;
    private CheckBox mPredestinacaoBoolField;
    private EditText mPersuasaoField;
    private CheckBox mPersuasaoBoolField;
    private EditText mReligiaoField;
    private CheckBox mReligiaoBoolField;
    private EditText mSobrevivenciaField;
    private CheckBox mSobrevivenciaBoolField;
    private Button gravaButton;
    private Personagem pers;
    private View view;
    private FirebaseUser user;
    private String persoID;

    public ResistenciaPericiaFragment() {
    }

   public static ResistenciaPericiaFragment newInstance(String param1, String param2) {
        ResistenciaPericiaFragment fragment = new ResistenciaPericiaFragment();
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
        view = inflater.inflate(R.layout.fragment_resistencia_pericia, container, false);
        context = getActivity().getApplicationContext();

        // Views
        mResForcaField = (EditText) view.findViewById(R.id.field_resForca);
        mResForcaBoolField = (CheckBox) view.findViewById(R.id.checkBox_resForca);
        mResSabedoriaField  = (EditText) view.findViewById(R.id.field_resSabedoria);
        mResSabedoriaBoolField = (CheckBox) view.findViewById(R.id.checkBox_resSabedoria);
        mResConstituicaoField  = (EditText) view.findViewById(R.id.field_resConstituicao);
        mResConstituicaoBoolField = (CheckBox) view.findViewById(R.id.checkBox_resConstituicao);
        mResCarismaField  = (EditText) view.findViewById(R.id.field_resCarisma);
        mResCarismaBoolField = (CheckBox) view.findViewById(R.id.checkBox_resCarisma);
        mResDestrezaField  = (EditText) view.findViewById(R.id.field_resDestreza);
        mResDestrezaBoolField = (CheckBox) view.findViewById(R.id.checkBox_resDestreza);
        mResInteligenciaField  = (EditText) view.findViewById(R.id.field_resInteligencia);
        mResInteligenciaBoolField = (CheckBox) view.findViewById(R.id.checkBox_resInteligencia);
        mAcrobaciaField  = (EditText) view.findViewById(R.id.field_acrobacia);
        mAcrobaciaBoolField  = (CheckBox) view.findViewById(R.id.checkBox_acrobacia);
        mArcanismoField = (EditText) view.findViewById(R.id.field_arcanismo);
        mArcanismoBoolField = (CheckBox) view.findViewById(R.id.checkBox_arcanismo);
        mAtletismoField  = (EditText) view.findViewById(R.id.field_atletismo);
        mAtletismoBoolField  = (CheckBox) view.findViewById(R.id.checkBox_atletismo);
        mAtuacaoField = (EditText) view.findViewById(R.id.field_atuacao);
        mAtuacaoBoolField = (CheckBox) view.findViewById(R.id.checkBox_atuacao);
        mBlefarField = (EditText) view.findViewById(R.id.field_blefar);
        mBlefarBoolField = (CheckBox) view.findViewById(R.id.checkBox_blefar);
        mFurtividadeField  = (EditText) view.findViewById(R.id.field_furtividade);
        mFurtividadeBoolField  = (CheckBox) view.findViewById(R.id.checkBox_furtividade);
        mHistoriaField  = (EditText) view.findViewById(R.id.field_historia);
        mHistoriaBoolField  = (CheckBox) view.findViewById(R.id.checkBox_historia);
        mIntimidacaoField  = (EditText) view.findViewById(R.id.field_intimidacao);
        mIntimidacaoBoolField  = (CheckBox) view.findViewById(R.id.checkBox_intimidacao);
        mIntuicaoField  = (EditText) view.findViewById(R.id.field_intuicao);
        mIntuicaoBoolField  = (CheckBox) view.findViewById(R.id.checkBox_intuicao);
        mInvestigacaoField  = (EditText) view.findViewById(R.id.field_investigacao);
        mInvestigacaoBoolField  = (CheckBox) view.findViewById(R.id.checkBox_investigacao);
        mLidarAnimaisField  = (EditText) view.findViewById(R.id.field_lidarAnimais);
        mLidarAnimaisBoolField  = (CheckBox) view.findViewById(R.id.checkBox_lidarAnimais);
        mMedicinaField  = (EditText) view.findViewById(R.id.field_medicina);
        mMedicinaBoolField  = (CheckBox) view.findViewById(R.id.checkBox_medicina);
        mNaturezaField  = (EditText) view.findViewById(R.id.field_natureza);
        mNaturezaBoolField  = (CheckBox) view.findViewById(R.id.checkBox_natureza);
        mPercepcaoField  = (EditText) view.findViewById(R.id.field_percepcao);
        mPercepcaoBoolField  = (CheckBox) view.findViewById(R.id.checkBox_percepcao);
        mPredestinacaoField  = (EditText) view.findViewById(R.id.field_predestinacao);
        mPredestinacaoBoolField  = (CheckBox) view.findViewById(R.id.checkBox_predestinacao);
        mPersuasaoField  = (EditText) view.findViewById(R.id.field_persuasao);
        mPersuasaoBoolField  = (CheckBox) view.findViewById(R.id.checkBox_persuasao);
        mReligiaoField  = (EditText) view.findViewById(R.id.field_religiao);
        mReligiaoBoolField  = (CheckBox) view.findViewById(R.id.checkBox_religiao);
        mSobrevivenciaField  = (EditText) view.findViewById(R.id.field_sobrevivencia);
        mSobrevivenciaBoolField  = (CheckBox) view.findViewById(R.id.checkBox_sobrevivencia);
        gravaButton = (Button) view.findViewById(R.id.gravar_button2);
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
                                mResForcaField.setText(pers.getResForca());
                                mResForcaBoolField.setChecked(pers.isResForcaBool());
                                mResSabedoriaField.setText(pers.getResSabedoria());
                                mResSabedoriaBoolField.setChecked(pers.isResSabedoriaBool());
                                mResConstituicaoField.setText(pers.getResConstituicao());
                                mResConstituicaoBoolField.setChecked(pers.isResConstituicaoBool());
                                mResCarismaField.setText(pers.getResCarisma());
                                mResCarismaBoolField.setChecked(pers.isResCarismaBool());
                                mResDestrezaField.setText(pers.getResDestreza());
                                mResDestrezaBoolField.setChecked(pers.isResDestrezaBool());
                                mResInteligenciaField.setText(pers.getResInteligencia());
                                mResInteligenciaBoolField.setChecked(pers.isResInteligenciaBool());
                                mAcrobaciaField.setText(pers.getAcrobacia());
                                mAcrobaciaBoolField.setChecked(pers.isAcrobaciaBool());
                                mArcanismoField.setText(pers.getArcanismo());
                                mArcanismoBoolField.setChecked(pers.isArcanismoBool());
                                mAtletismoField.setText(pers.getAtletismo());
                                mAtletismoBoolField.setChecked(pers.isAtletismoBool());
                                mAtuacaoField.setText(pers.getAtuacao());
                                mAtuacaoBoolField.setChecked(pers.isAtuacaoBool());
                                mBlefarField.setText(pers.getBlefar());
                                mBlefarBoolField.setChecked(pers.isBlefarBool());
                                mFurtividadeField.setText(pers.getFurtividade());
                                mFurtividadeBoolField.setChecked(pers.isFurtividadeBool());
                                mHistoriaField.setText(pers.getHistoria());
                                mHistoriaBoolField.setChecked(pers.isHistoriaBool());
                                mIntimidacaoField.setText(pers.getIntimidacao());
                                mIntimidacaoBoolField.setChecked(pers.isIntimidacaoBool());
                                mIntuicaoField.setText(pers.getIntuicao());
                                mIntuicaoBoolField.setChecked(pers.isIntuicaoBool());
                                mInvestigacaoField.setText(pers.getInvestigacao());
                                mInvestigacaoBoolField.setChecked(pers.isInvestigacaoBool());
                                mLidarAnimaisField.setText(pers.getLidarAnimais());
                                mLidarAnimaisBoolField.setChecked(pers.isLidarAnimaisBool());
                                mMedicinaField.setText(pers.getMedicina());
                                mMedicinaBoolField.setChecked(pers.isMedicinaBool());
                                mNaturezaField.setText(pers.getNatureza());
                                mNaturezaBoolField.setChecked(pers.isNaturezaBool());
                                mPercepcaoField.setText(pers.getPercepcao());
                                mPercepcaoBoolField.setChecked(pers.isPercepcaoBool());
                                mPredestinacaoField.setText(pers.getPredestinacao());
                                mPredestinacaoBoolField.setChecked(pers.isPredestinacaoBool());
                                mPersuasaoField.setText(pers.getPersuasao());
                                mPersuasaoBoolField.setChecked(pers.isPersuasaoBool());
                                mReligiaoField.setText(pers.getReligiao());
                                mReligiaoBoolField.setChecked(pers.isReligiaoBool());
                                mSobrevivenciaField.setText(pers.getSobrevivencia());
                                mSobrevivenciaBoolField.setChecked(pers.isSobrevivenciaBool());
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
        if(pers!= null && pers.getNomePerso() != null && pers.getNomePerso().length() > 0){
            pers.setResForca(mResForcaField.getText().toString());
            pers.setResForcaBool(mResForcaBoolField.isChecked());
            Log.d(TAG, String.valueOf(mResForcaBoolField.isChecked()));
            pers.setResSabedoria(mResSabedoriaField.getText().toString());
            pers.setResSabedoriaBool(mResSabedoriaBoolField.isChecked());
            pers.setResConstituicao(mResConstituicaoField.getText().toString());
            pers.setResConstituicaoBool(mResConstituicaoBoolField.isChecked());
            pers.setResCarisma(mResCarismaField.getText().toString());
            pers.setResCarismaBool(mResCarismaBoolField.isChecked());
            pers.setResDestreza(mResDestrezaField.getText().toString());
            pers.setResDestrezaBool(mResDestrezaBoolField.isChecked());
            pers.setResInteligencia(mResInteligenciaField.getText().toString());
            pers.setResInteligenciaBool(mResInteligenciaBoolField.isChecked());
            pers.setAcrobacia(mAcrobaciaField.getText().toString());
            pers.setAcrobaciaBool(mAcrobaciaBoolField.isChecked());
            pers.setArcanismo(mArcanismoField.getText().toString());
            pers.setArcanismoBool(mArcanismoBoolField.isChecked());
            pers.setAtletismo(mAtletismoField.getText().toString());
            pers.setAtletismoBool(mAtletismoBoolField.isChecked());
            pers.setAtuacao(mAtuacaoField.getText().toString());
            pers.setAtuacaoBool(mAtuacaoBoolField.isChecked());
            pers.setBlefar(mBlefarField.getText().toString());
            pers.setBlefarBool(mBlefarBoolField.isChecked());
            pers.setFurtividade(mFurtividadeField.getText().toString());
            pers.setFurtividadeBool(mFurtividadeBoolField.isChecked());
            pers.setHistoria(mHistoriaField.getText().toString());
            pers.setHistoriaBool(mHistoriaBoolField.isChecked());
            pers.setIntimidacao(mIntimidacaoField.getText().toString());
            pers.setIntimidacaoBool(mIntimidacaoBoolField.isChecked());
            pers.setIntuicao(mIntuicaoField.getText().toString());
            pers.setIntuicaoBool(mIntuicaoBoolField.isChecked());
            pers.setInvestigacao(mInvestigacaoField.getText().toString());
            pers.setInvestigacaoBool(mInvestigacaoBoolField.isChecked());
            pers.setLidarAnimais(mLidarAnimaisField.getText().toString());
            pers.setLidarAnimaisBool(mLidarAnimaisBoolField.isChecked());
            pers.setMedicina(mMedicinaField.getText().toString());
            pers.setMedicinaBool(mMedicinaBoolField.isChecked());
            pers.setNatureza(mNaturezaField.getText().toString());
            pers.setNaturezaBool(mNaturezaBoolField.isChecked());
            pers.setPercepcao(mPercepcaoField.getText().toString());
            pers.setPercepcaoBool(mPercepcaoBoolField.isChecked());
            pers.setPredestinacao(mPredestinacaoField.getText().toString());
            pers.setPredestinacaoBool(mPredestinacaoBoolField.isChecked());
            pers.setPersuasao(mPersuasaoField.getText().toString());
            pers.setPersuasaoBool(mPersuasaoBoolField.isChecked());
            pers.setReligiao(mReligiaoField.getText().toString());
            pers.setReligiaoBool(mReligiaoBoolField.isChecked());
            pers.setSobrevivencia(mSobrevivenciaField.getText().toString());
            pers.setSobrevivenciaBool(mSobrevivenciaBoolField.isChecked());
            mDatabase.child("Personagens").child(persoID).setValue(pers);
            PersonagemItem personagemItem = new PersonagemItem(persoID,pers.getNomePerso(), pers.getClasse(), pers.getNivel());
            mDatabase.child("Users").child(user.getUid()).child("Personagens").child(persoID).setValue(personagemItem);
            Toast.makeText(context, R.string.gravadoSucesso , Toast.LENGTH_SHORT).show();
        }    else{
            Toast.makeText(context, "pers null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.gravar_button2) {
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
