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
        import com.tcc.natha.gerenciadordd.models.personagem.PersonagemInstance;
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
        PersonagemInstance.getInstance();

        //BD
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    mDatabase.child("Personagens").child(persoID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            PersonagemInstance.getInstance().setPersonagem(dataSnapshot.getValue(Personagem.class));
                            //Carrega os dados do Personagem nesse fragment
                            loadFields();

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
        if(PersonagemInstance.getInstance().getPersonagem()!= null
                && PersonagemInstance.getInstance().getPersonagem().getNomePerso() != null
                && PersonagemInstance.getInstance().getPersonagem().getNomePerso().length() > 0){
            Personagem personagem = PersonagemInstance.getInstance().getPersonagem();
            personagem.setResForca(mResForcaField.getText().toString());
            personagem.setResForcaBool(mResForcaBoolField.isChecked());
            personagem.setResSabedoria(mResSabedoriaField.getText().toString());
            personagem.setResSabedoriaBool(mResSabedoriaBoolField.isChecked());
            personagem.setResConstituicao(mResConstituicaoField.getText().toString());
            personagem.setResConstituicaoBool(mResConstituicaoBoolField.isChecked());
            personagem.setResCarisma(mResCarismaField.getText().toString());
            personagem.setResCarismaBool(mResCarismaBoolField.isChecked());
            personagem.setResDestreza(mResDestrezaField.getText().toString());
            personagem.setResDestrezaBool(mResDestrezaBoolField.isChecked());
            personagem.setResInteligencia(mResInteligenciaField.getText().toString());
            personagem.setResInteligenciaBool(mResInteligenciaBoolField.isChecked());
            personagem.setAcrobacia(mAcrobaciaField.getText().toString());
            personagem.setAcrobaciaBool(mAcrobaciaBoolField.isChecked());
            personagem.setArcanismo(mArcanismoField.getText().toString());
            personagem.setArcanismoBool(mArcanismoBoolField.isChecked());
            personagem.setAtletismo(mAtletismoField.getText().toString());
            personagem.setAtletismoBool(mAtletismoBoolField.isChecked());
            personagem.setAtuacao(mAtuacaoField.getText().toString());
            personagem.setAtuacaoBool(mAtuacaoBoolField.isChecked());
            personagem.setBlefar(mBlefarField.getText().toString());
            personagem.setBlefarBool(mBlefarBoolField.isChecked());
            personagem.setFurtividade(mFurtividadeField.getText().toString());
            personagem.setFurtividadeBool(mFurtividadeBoolField.isChecked());
            personagem.setHistoria(mHistoriaField.getText().toString());
            personagem.setHistoriaBool(mHistoriaBoolField.isChecked());
            personagem.setIntimidacao(mIntimidacaoField.getText().toString());
            personagem.setIntimidacaoBool(mIntimidacaoBoolField.isChecked());
            personagem.setIntuicao(mIntuicaoField.getText().toString());
            personagem.setIntuicaoBool(mIntuicaoBoolField.isChecked());
            personagem.setInvestigacao(mInvestigacaoField.getText().toString());
            personagem.setInvestigacaoBool(mInvestigacaoBoolField.isChecked());
            personagem.setLidarAnimais(mLidarAnimaisField.getText().toString());
            personagem.setLidarAnimaisBool(mLidarAnimaisBoolField.isChecked());
            personagem.setMedicina(mMedicinaField.getText().toString());
            personagem.setMedicinaBool(mMedicinaBoolField.isChecked());
            personagem.setNatureza(mNaturezaField.getText().toString());
            personagem.setNaturezaBool(mNaturezaBoolField.isChecked());
            personagem.setPercepcao(mPercepcaoField.getText().toString());
            personagem.setPercepcaoBool(mPercepcaoBoolField.isChecked());
            personagem.setPredestinacao(mPredestinacaoField.getText().toString());
            personagem.setPredestinacaoBool(mPredestinacaoBoolField.isChecked());
            personagem.setPersuasao(mPersuasaoField.getText().toString());
            personagem.setPersuasaoBool(mPersuasaoBoolField.isChecked());
            personagem.setReligiao(mReligiaoField.getText().toString());
            personagem.setReligiaoBool(mReligiaoBoolField.isChecked());
            personagem.setSobrevivencia(mSobrevivenciaField.getText().toString());
            personagem.setSobrevivenciaBool(mSobrevivenciaBoolField.isChecked());
            PersonagemInstance.getInstance().setPersonagem(personagem);
            mDatabase.child("Personagens").child(persoID).setValue(PersonagemInstance.getInstance().getPersonagem());
            PersonagemItem personagemItem = new PersonagemItem(persoID,PersonagemInstance.getInstance().getPersonagem().getNomePerso(), PersonagemInstance.getInstance().getPersonagem().getClasse(), PersonagemInstance.getInstance().getPersonagem().getNivel());
            mDatabase.child("Users").child(user.getUid()).child("Personagens").child(persoID).setValue(personagemItem);
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
    public void onPause(){
        Toast.makeText(context, "onPause2" , Toast.LENGTH_SHORT).show();
        gravaPersonagem();
        super.onPause();
    }

    public interface OnPageSelectedListener {
        void onPageSelected();
    }

    private void loadFields(){
        mResForcaField.setText(PersonagemInstance.getInstance().getPersonagem().getResForca());
        mResForcaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isResForcaBool());
        mResSabedoriaField.setText(PersonagemInstance.getInstance().getPersonagem().getResSabedoria());
        mResSabedoriaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isResSabedoriaBool());
        mResConstituicaoField.setText(PersonagemInstance.getInstance().getPersonagem().getResConstituicao());
        mResConstituicaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isResConstituicaoBool());
        mResCarismaField.setText(PersonagemInstance.getInstance().getPersonagem().getResCarisma());
        mResCarismaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isResCarismaBool());
        mResDestrezaField.setText(PersonagemInstance.getInstance().getPersonagem().getResDestreza());
        mResDestrezaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isResDestrezaBool());
        mResInteligenciaField.setText(PersonagemInstance.getInstance().getPersonagem().getResInteligencia());
        mResInteligenciaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isResInteligenciaBool());
        mAcrobaciaField.setText(PersonagemInstance.getInstance().getPersonagem().getAcrobacia());
        mAcrobaciaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isAcrobaciaBool());
        mArcanismoField.setText(PersonagemInstance.getInstance().getPersonagem().getArcanismo());
        mArcanismoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isArcanismoBool());
        mAtletismoField.setText(PersonagemInstance.getInstance().getPersonagem().getAtletismo());
        mAtletismoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isAtletismoBool());
        mAtuacaoField.setText(PersonagemInstance.getInstance().getPersonagem().getAtuacao());
        mAtuacaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isAtuacaoBool());
        mBlefarField.setText(PersonagemInstance.getInstance().getPersonagem().getBlefar());
        mBlefarBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isBlefarBool());
        mFurtividadeField.setText(PersonagemInstance.getInstance().getPersonagem().getFurtividade());
        mFurtividadeBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isFurtividadeBool());
        mHistoriaField.setText(PersonagemInstance.getInstance().getPersonagem().getHistoria());
        mHistoriaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isHistoriaBool());
        mIntimidacaoField.setText(PersonagemInstance.getInstance().getPersonagem().getIntimidacao());
        mIntimidacaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isIntimidacaoBool());
        mIntuicaoField.setText(PersonagemInstance.getInstance().getPersonagem().getIntuicao());
        mIntuicaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isIntuicaoBool());
        mInvestigacaoField.setText(PersonagemInstance.getInstance().getPersonagem().getInvestigacao());
        mInvestigacaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isInvestigacaoBool());
        mLidarAnimaisField.setText(PersonagemInstance.getInstance().getPersonagem().getLidarAnimais());
        mLidarAnimaisBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isLidarAnimaisBool());
        mMedicinaField.setText(PersonagemInstance.getInstance().getPersonagem().getMedicina());
        mMedicinaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isMedicinaBool());
        mNaturezaField.setText(PersonagemInstance.getInstance().getPersonagem().getNatureza());
        mNaturezaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isNaturezaBool());
        mPercepcaoField.setText(PersonagemInstance.getInstance().getPersonagem().getPercepcao());
        mPercepcaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isPercepcaoBool());
        mPredestinacaoField.setText(PersonagemInstance.getInstance().getPersonagem().getPredestinacao());
        mPredestinacaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isPredestinacaoBool());
        mPersuasaoField.setText(PersonagemInstance.getInstance().getPersonagem().getPersuasao());
        mPersuasaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isPersuasaoBool());
        mReligiaoField.setText(PersonagemInstance.getInstance().getPersonagem().getReligiao());
        mReligiaoBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isReligiaoBool());
        mSobrevivenciaField.setText(PersonagemInstance.getInstance().getPersonagem().getSobrevivencia());
        mSobrevivenciaBoolField.setChecked(PersonagemInstance.getInstance().getPersonagem().isSobrevivenciaBool());
    }
}
