package com.tcc.natha.gerenciadordd.fragments.aventura;

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
import com.tcc.natha.gerenciadordd.models.aventura.AventuraItem;
import com.tcc.natha.gerenciadordd.models.aventura.SequencialAventura;

import java.util.ArrayList;
import java.util.List;

public class AventuraFragment extends Fragment implements View.OnClickListener, EditAventuraFragment.OnFragmentInteractionListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private static final String TAG = "AventuraFragment";
    private View view;
    private Button criaAventuraButton;
    private Button procuraAventuraButton;
    private SequencialAventura seqAvent;
    private int sequencialDefault = 1;
    private OnFragmentInteractionListener mListener;
    private static FirebaseUser user;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static DatabaseReference mDatabase;
    private ListView listaAvent;
    private List<AventuraItem> avent = new ArrayList<>();

    public AventuraFragment() {

    }

    public static AventuraFragment newInstance(String param1, String param2) {
        AventuraFragment fragment = new AventuraFragment();
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("SequencialAventura").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                seqAvent = dataSnapshot.getValue(SequencialAventura.class);
                if (seqAvent != null) {
                    seqAvent.setSeqCodAventura(seqAvent.getSeqCodAventura() + 1);
                } else {
                    seqAvent = new SequencialAventura();
                    seqAvent.setSeqCodAventura(sequencialDefault);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aventura, container, false);
        getActivity().setTitle("Aventura");

        // Buttons
        criaAventuraButton = (Button) view.findViewById(R.id.cria_aven_button);
        criaAventuraButton.setOnClickListener(this);
        procuraAventuraButton = (Button) view.findViewById(R.id.proc_aven_button);
        procuraAventuraButton.setOnClickListener(this);


        // Lista
        listaAvent = (ListView) view.findViewById(R.id.listaAventura);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            avent = new ArrayList<>();
                            for (DataSnapshot child: dataSnapshot.getChildren()) {
                                AventuraItem aventuraItem = child.getValue(AventuraItem.class);
                                avent.add(aventuraItem);
                            }
                            ArrayAdapter<AventuraItem> adapter = new ArrayAdapter<AventuraItem>(getActivity().getApplicationContext(),
                                    android.R.layout.simple_list_item_1, avent);
                            listaAvent.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    mDatabase.child("Users").child(user.getUid()).child("Aventuras").addValueEventListener(postListener);
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        listaAvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), avent.get(position).getNomeAventura(), Toast.LENGTH_SHORT ).show();

                //pega as fragment para remover
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                List<Fragment> fragmentList = fragmentManager.getFragments();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                for (Fragment fragment: fragmentList ) {
                    if(fragment != null){
                        transaction.remove(fragment);
                    }
                }
                transaction.commit();
                transaction = getFragmentManager().beginTransaction();
                Fragment viewPageAventura = new ViewPageAventura();
                Bundle bundle = new Bundle();
                bundle.putInt("seqAventura", seqAvent.getSeqCodAventura());
                getActivity().getIntent().removeExtra("seqAventura");
                bundle.putString("aventID", avent.get(position).getIdAventura());
                getActivity().getIntent().removeExtra("aventID");
                getActivity().getIntent().putExtras(bundle);
                transaction.replace(R.id.headlines_fragment, viewPageAventura);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

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
                        mDatabase.child("Users").child(user.getUid()).child("Aventuras").child(avent.get(position).getIdAventura()).removeValue();
                        mDatabase.child("Aventuras").child(avent.get(position).getIdAventura()).removeValue();
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
        if (i == R.id.cria_aven_button) {
            //pega as fragment para remover
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            List<Fragment> fragmentList = fragmentManager.getFragments();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            for (Fragment fragment : fragmentList) {
                if (fragment != null) {
                    transaction.remove(fragment);
                }
            }
            transaction.commit();
            Bundle bundle = new Bundle();
            bundle.putInt("seqAventura", seqAvent.getSeqCodAventura());
            getActivity().getIntent().removeExtra("seqAventura");
            bundle.putString("aventID", mDatabase.child("Aventuras").push().getKey());
            getActivity().getIntent().removeExtra("aventID");
            getActivity().getIntent().putExtras(bundle);
            // Fragments
            transaction = getFragmentManager().beginTransaction();
            Fragment editAventuraFragment = new EditAventuraFragment();
            transaction.replace(R.id.headlines_fragment, editAventuraFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        if (i == R.id.proc_aven_button) {
            //pega as fragment para remover
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            List<Fragment> fragmentList = fragmentManager.getFragments();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            for (Fragment fragment : fragmentList) {
                if (fragment != null) {
                    transaction.remove(fragment);
                }
            }
            transaction.commit();
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
