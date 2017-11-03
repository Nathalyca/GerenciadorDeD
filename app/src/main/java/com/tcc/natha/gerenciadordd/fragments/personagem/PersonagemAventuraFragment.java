package com.tcc.natha.gerenciadordd.fragments.personagem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.natha.gerenciadordd.R;
import com.tcc.natha.gerenciadordd.fragments.aventura.EditAventuraFragment;
import com.tcc.natha.gerenciadordd.fragments.aventura.ViewPageAventura;
import com.tcc.natha.gerenciadordd.models.aventura.Aventura;
import com.tcc.natha.gerenciadordd.models.aventura.AventuraItem;
import com.tcc.natha.gerenciadordd.models.aventura.SequencialAventura;
import com.tcc.natha.gerenciadordd.models.personagem.Personagem;
import com.tcc.natha.gerenciadordd.models.personagem.PersonagemInstance;

import java.util.ArrayList;
import java.util.List;

public class PersonagemAventuraFragment extends Fragment implements View.OnClickListener, EditAventuraFragment.OnFragmentInteractionListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private static final String TAG = "PersonagemAventuraFragment";
    private View view;
    private Button associarAventuraButton;
    private SequencialAventura seqAvent;
    private OnFragmentInteractionListener mListener;
    private static FirebaseUser user;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static DatabaseReference mDatabase;
    private ListView listaAvent;
    private List<AventuraItem> avent = new ArrayList<>();
    private EditText mNumAventTextField;
    private String persoID;
    private Context context;

    public PersonagemAventuraFragment() {

    }

    public static PersonagemAventuraFragment newInstance(String param1, String param2) {
        PersonagemAventuraFragment fragment = new PersonagemAventuraFragment();
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
        avent = new ArrayList<AventuraItem>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jogador_aventura, container, false);

        // Buttons
        associarAventuraButton = (Button) view.findViewById(R.id.associa_aven_button);
        associarAventuraButton.setOnClickListener(this);
        mNumAventTextField = (EditText) view.findViewById(R.id.field_numjogadoraventtext);

        // Lista
        listaAvent = (ListView) view.findViewById(R.id.listaPersonagemAventura);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    mDatabase.child("Personagens").child(persoID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            PersonagemInstance.getInstance().setPersonagem(dataSnapshot.getValue(Personagem.class));


                                avent = PersonagemInstance.getInstance().getPersonagem().getAventuras();
                                if(avent == null){
                                    avent = new ArrayList<AventuraItem>();
                                    Personagem personagem =  PersonagemInstance.getInstance().getPersonagem();
                                    personagem.setAventuras(avent);
                                    PersonagemInstance.getInstance().setPersonagem(personagem);
                                }

                            if(avent == null){
                                avent = new ArrayList<AventuraItem>();
                            }
                            ArrayAdapter<AventuraItem> adapter = new ArrayAdapter<AventuraItem>(context,
                                    android.R.layout.simple_list_item_1, avent);
                            listaAvent.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);

        listaAvent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), avent.get(position).getNomeAventura(), Toast.LENGTH_SHORT ).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Deletar Aventura");
                alert.setMessage("Você tem certeza que deseja deletar "+avent.get(position).getNomeAventura()+" ?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        avent.remove(position);
                        mDatabase.child("Personagens").child(persoID).setValue(PersonagemInstance.getInstance().getPersonagem());
                        ArrayAdapter<AventuraItem> adapter = new ArrayAdapter<AventuraItem>(getActivity().getApplicationContext(),
                                android.R.layout.simple_list_item_1, avent);
                        listaAvent.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.associa_aven_button) {
            associaJogadorAventura();
        }
    }

    private void associaJogadorAventura() {
        if(mNumAventTextField.getText() != null && mNumAventTextField.getText().toString().length() > 0){
            seqAvent = new SequencialAventura();
            seqAvent.setSeqCodAventura(Integer.parseInt(mNumAventTextField.getText().toString()));


            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Aventura aventura = new Aventura();
                    AventuraItem aventuraItem = new AventuraItem();
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        aventura = child.getValue(Aventura.class);
                        if(aventura.getSeqAventura() == seqAvent.getSeqCodAventura()){
                            aventuraItem = new AventuraItem(child.getKey(), aventura.getNomeAventura(), aventura.getSeqAventura());
                            break;
                        }
                    }
                    if(aventura.getSeqAventura() == 0 || aventura.getSeqAventura() != seqAvent.getSeqCodAventura()){
                        Toast.makeText(getActivity().getApplicationContext(), "Numero Aventura não encontrado", Toast.LENGTH_SHORT ).show();
                    }
                    boolean aventuraJaAssociada = false;
                    if(PersonagemInstance.getInstance().getPersonagem().getAventuras() != null){
                        for(AventuraItem aventuraItemPers: PersonagemInstance.getInstance().getPersonagem().getAventuras()){
                            if(aventuraItemPers.getSeqAventura() == seqAvent.getSeqCodAventura()){
                                Toast.makeText(getActivity().getApplicationContext(), "Numero Aventura já associada", Toast.LENGTH_SHORT ).show();
                                aventuraJaAssociada = true;
                                break;
                            }
                        }
                    }else{
                        Personagem personagem = PersonagemInstance.getInstance().getPersonagem();
                        personagem.setAventuras(new ArrayList<AventuraItem>());
                        PersonagemInstance.getInstance().setPersonagem(personagem);
                    }

                    if(!aventuraJaAssociada && aventuraItem != null){
                        avent.add(aventuraItem);
                        ArrayAdapter<AventuraItem> adapter = new ArrayAdapter<AventuraItem>(getActivity().getApplicationContext(),
                                android.R.layout.simple_list_item_1, avent);
                        listaAvent.setAdapter(adapter);
                        Personagem personagem = PersonagemInstance.getInstance().getPersonagem();
                        personagem.setAventuras(avent);
                        PersonagemInstance.getInstance().setPersonagem(personagem);
                        mDatabase.child("Personagens").child(persoID).setValue(PersonagemInstance.getInstance().getPersonagem());
                        Toast.makeText(getActivity().getApplicationContext(), "Numero Aventura associado", Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mDatabase.child("Aventuras").addValueEventListener(postListener);

        }else{
            Toast.makeText(getActivity().getApplicationContext(), "Numero Aventura não informado", Toast.LENGTH_SHORT ).show();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
