package com.tcc.natha.gerenciadordd.fragments.aventura;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tcc.natha.gerenciadordd.R;
import com.tcc.natha.gerenciadordd.models.aventura.SequencialAventura;

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

    public AventuraFragment() {

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
