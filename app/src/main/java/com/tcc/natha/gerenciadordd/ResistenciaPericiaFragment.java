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
        import com.tcc.natha.gerenciadordd.models.Personagem;
        import com.tcc.natha.gerenciadordd.models.PersonagemItem;


public class ResistenciaPericiaFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EditPersonagemFragment";
    private Context context;
    private DatabaseReference mPersReference;
    private DatabaseReference mDatabase;

    private EditText mResForcaField;

    private EditText mResSabedoriaField;

    private EditText mResConstituicaoField;

    private EditText mResCarismaField;

    private EditText mResDestrezaField;

    private EditText mResInteligenciaField;

    private EditText mAcrobaciaField;

    private EditText mArcanismoField;

    private EditText mAtletismoField;

    private EditText mAtuacaoField;

    private EditText mBlefarField;

    private EditText mFurtividadeField;

    private EditText mHistoriaField;

    private EditText mIntimidacaoField;

    private EditText mIntuicaoField;

    private EditText mInvestigacaoField;

    private EditText mLidarAnimaisField;

    private EditText mMedicinaField;

    private EditText mNaturezaField;

    private EditText mPercepcaoField;

    private EditText mPredestinacaoField;

    private EditText mPersuasaoField;

    private EditText mReligiaoField;

    private EditText mSobrevivenciaField;

    private Button gravaButton;

    private Personagem pers;

    private View view;

    private FirebaseUser user;
    private String persoID;

    public ResistenciaPericiaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResistenciaPericiaFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        Log.d(TAG, "Bundle:" + b);
        if(b!= null){
            persoID = b.getString("persoID", null);
        }
        Log.d(TAG, "persoID:" + persoID);
        pers = new Personagem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_resistencia_pericia, container, false);

        Log.d(TAG, "onCreate");

        context = getActivity().getApplicationContext();

        // Views
        mResForcaField = (EditText) view.findViewById(R.id.field_resForca);
        mResSabedoriaField  = (EditText) view.findViewById(R.id.field_resSabedoria);
        mResConstituicaoField  = (EditText) view.findViewById(R.id.field_resConstituicao);
        mResCarismaField  = (EditText) view.findViewById(R.id.field_resCarisma);
        mResDestrezaField  = (EditText) view.findViewById(R.id.field_resDestreza);
        mResInteligenciaField  = (EditText) view.findViewById(R.id.field_resInteligencia);
        mAcrobaciaField  = (EditText) view.findViewById(R.id.field_acrobacia);
        mArcanismoField = (EditText) view.findViewById(R.id.field_arcanismo);
        mAtletismoField  = (EditText) view.findViewById(R.id.field_atletismo);
        mAtuacaoField = (EditText) view.findViewById(R.id.field_atuacao);
        mBlefarField = (EditText) view.findViewById(R.id.field_blefar);
        mFurtividadeField  = (EditText) view.findViewById(R.id.field_furtividade);
        mHistoriaField  = (EditText) view.findViewById(R.id.field_historia);
        mIntimidacaoField  = (EditText) view.findViewById(R.id.field_intimidacao);
        mIntuicaoField  = (EditText) view.findViewById(R.id.field_intuicao);
        mInvestigacaoField  = (EditText) view.findViewById(R.id.field_investigacao);
        mLidarAnimaisField  = (EditText) view.findViewById(R.id.field_lidarAnimais);
        mMedicinaField  = (EditText) view.findViewById(R.id.field_medicina);
        mNaturezaField  = (EditText) view.findViewById(R.id.field_natureza);
        mPercepcaoField  = (EditText) view.findViewById(R.id.field_percepcao);
        mPredestinacaoField  = (EditText) view.findViewById(R.id.field_predestinacao);
        mPersuasaoField  = (EditText) view.findViewById(R.id.field_persuasao);
        mReligiaoField  = (EditText) view.findViewById(R.id.field_religiao);
        mSobrevivenciaField  = (EditText) view.findViewById(R.id.field_sobrevivencia);
        // Buttons
        gravaButton = (Button) view.findViewById(R.id.gravar_button2);
        gravaButton.setOnClickListener(this);

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
                    if(persoID == null){
                        persoID  = mDatabase.child("Personagens").push().getKey();
                        mDatabase.child("Personagens").child(persoID).setValue(pers);
                        Log.d(TAG, "persoID:" + persoID);
                    }

                    mDatabase.child("Personagens").child(persoID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            pers = dataSnapshot.getValue(Personagem.class);
                            if(pers != null){
                                mResForcaField.setText(pers.getResForca());
                                mResSabedoriaField.setText(pers.getResSabedoria());
                                mResConstituicaoField.setText(pers.getResConstituicao());
                                mResCarismaField.setText(pers.getResCarisma());
                                mResDestrezaField.setText(pers.getResDestreza());
                                mResInteligenciaField.setText(pers.getResInteligencia());
                                mAcrobaciaField.setText(pers.getAcrobacia());
                                mArcanismoField.setText(pers.getArcanismo());
                                mAtletismoField.setText(pers.getAtletismo());
                                mAtuacaoField.setText(pers.getAtuacao());
                                mBlefarField.setText(pers.getBlefar());
                                mFurtividadeField.setText(pers.getFurtividade());
                                mHistoriaField.setText(pers.getHistoria());
                                mIntimidacaoField.setText(pers.getIntimidacao());
                                mIntuicaoField.setText(pers.getIntuicao());
                                mInvestigacaoField.setText(pers.getInvestigacao());
                                mLidarAnimaisField.setText(pers.getLidarAnimais());
                                mMedicinaField.setText(pers.getMedicina());
                                mNaturezaField.setText(pers.getNatureza());
                                mPercepcaoField.setText(pers.getPercepcao());
                                mPredestinacaoField.setText(pers.getPredestinacao());
                                mPersuasaoField.setText(pers.getPersuasao());
                                mReligiaoField.setText(pers.getReligiao());
                                mSobrevivenciaField.setText(pers.getSobrevivencia());

                            }
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

        Log.d(TAG, "User" + user);

        pers = new Personagem(user.getUid());

        Log.d(TAG, "grava personagem");

        pers.setResForca(mResForcaField.getText().toString());
        pers.setResSabedoria(mResSabedoriaField.getText().toString());
        pers.setResConstituicao(mResConstituicaoField.getText().toString());
        pers.setResCarisma(mResCarismaField.getText().toString());
        pers.setResDestreza(mResDestrezaField.getText().toString());
        pers.setResInteligencia(mResInteligenciaField.getText().toString());
        pers.setAcrobacia(mAcrobaciaField.getText().toString());
        pers.setArcanismo(mArcanismoField.getText().toString());
        pers.setAtletismo(mAtletismoField.getText().toString());
        pers.setAtuacao(mAtuacaoField.getText().toString());
        pers.setBlefar(mBlefarField.getText().toString());
        pers.setFurtividade(mFurtividadeField.getText().toString());
        pers.setHistoria(mHistoriaField.getText().toString());
        pers.setIntimidacao(mIntimidacaoField.getText().toString());
        pers.setIntuicao(mIntuicaoField.getText().toString());
        pers.setInvestigacao(mInvestigacaoField.getText().toString());
        pers.setLidarAnimais(mLidarAnimaisField.getText().toString());
        pers.setMedicina(mMedicinaField.getText().toString());
        pers.setNatureza(mNaturezaField.getText().toString());
        pers.setPercepcao(mPercepcaoField.getText().toString());
        pers.setPredestinacao(mPredestinacaoField.getText().toString());
        pers.setPersuasao(mPersuasaoField.getText().toString());
        pers.setReligiao(mReligiaoField.getText().toString());
        pers.setSobrevivencia(mSobrevivenciaField.getText().toString());

        mDatabase.child("Personagens").child(persoID).setValue(pers);
        PersonagemItem personagemItem = new PersonagemItem(persoID,pers.getNomePerso(), pers.getClasse(), pers.getNivel());
        mDatabase.child("Users").child(user.getUid()).child("Personagens").child(persoID).setValue(personagemItem);

        Toast.makeText(context, R.string.gravadoSucesso , Toast.LENGTH_SHORT).show();
        Log.d(TAG, "gravado");

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        Log.d(TAG, "onClick");
        if (i == R.id.gravar_button2) {
            Log.d(TAG, "chama grava personagem");
            gravaPersonagem();
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
}
