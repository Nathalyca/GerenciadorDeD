package com.tcc.natha.gerenciadordd.fragments.aventura;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.tcc.natha.gerenciadordd.models.aventura.Aventura;
import com.tcc.natha.gerenciadordd.models.aventura.AventuraItem;
import com.tcc.natha.gerenciadordd.models.aventura.SequencialAventura;

public class EditAventuraFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EditAventuraFragment";
    private Context context;
    private DatabaseReference mDatabase;
    private EditText mNomeAventField;
    private EditText mNumAventTextField;
    private Button gravaButton;
    private Aventura avent;
    private SequencialAventura seqAvent;
    private String aventID;
    private View view;
    private FirebaseUser user;

    public EditAventuraFragment() {

    }

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
        Bundle b = getActivity().getIntent().getExtras();
        seqAvent = new SequencialAventura();
        if(b!= null){
            seqAvent.setSeqCodAventura(b.getInt("seqAventura", 0));
            aventID = b.getString("aventID", null);
        }
        avent = new Aventura();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_aventura, container, false);
        context = getActivity().getApplicationContext();

        // Views
        mNomeAventField = (EditText) view.findViewById(R.id.field_nomeavent);
        mNumAventTextField = (EditText) view.findViewById(R.id.text_numaventtext);
        mNumAventTextField.setText(seqAvent.getSeqCodAventura()+"");

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
                    mDatabase.child("Aventuras").child(aventID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            avent = dataSnapshot.getValue(Aventura.class);
                            if (avent != null) {
                                mNomeAventField.setText(avent.getNomeAventura());
                                mNumAventTextField.setText(avent.getSeqAventura()+"");
                            }else{
                                avent = new Aventura(user.getUid());
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


    public void gravaAventura(){
        if(avent != null && mNomeAventField.getText().toString() != null && mNomeAventField.getText().toString().length() > 0 ){
            avent.setNomeAventura(mNomeAventField.getText().toString());
            avent.setSeqAventura(seqAvent.getSeqCodAventura());
            mDatabase.child("Aventuras").child(aventID).setValue(avent);
            mDatabase.child("SequencialAventura").setValue(seqAvent);
            AventuraItem aventuraItem = new AventuraItem(aventID, avent.getNomeAventura(), avent.getSeqAventura());
            mDatabase.child("Users").child(user.getUid()).child("Aventuras").child(aventID).setValue(aventuraItem);
            Toast.makeText(context, R.string.gravadoSucesso , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.gravar_button) {
            gravaAventura();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        gravaAventura();
    }
}
