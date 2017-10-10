package com.tcc.natha.gerenciadordd.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.tcc.natha.gerenciadordd.adapters.ViewPagePersonagem;
import com.tcc.natha.gerenciadordd.models.PersonagemItem;

import java.util.ArrayList;
import java.util.List;


public class PersonagemFragment extends Fragment implements View.OnClickListener,  EditPersonagemFragment.OnFragmentInteractionListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "PersonagemFragment";

    private List<PersonagemItem> perso = new ArrayList<>();

    private Button criaPersButton;
    private ListView listaPerso;

    private View view;

    private static FirebaseUser user;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static DatabaseReference mDatabase;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PersonagemFragment() {
        // Required empty public constructor
    }

    public static PersonagemFragment newInstance(String param1, String param2) {
        PersonagemFragment fragment = new PersonagemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personagem, container, false);
        getActivity().setTitle("Personagem");
        Log.d(TAG, "onCreateView");


        // Buttons
        criaPersButton = (Button) view.findViewById(R.id.cria_pers_button);
        criaPersButton.setOnClickListener(this);


        // Lista
        listaPerso = (ListView) view.findViewById(R.id.listaPerso);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d(TAG, "onAuthStateChanged:email:" + user.getEmail());
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            perso = new ArrayList<>();
                            for (DataSnapshot child: dataSnapshot.getChildren()) {
                                PersonagemItem personagemItem = child.getValue(PersonagemItem.class);
                                perso.add(personagemItem);
                                System.out.println("child.getKey: " + child.getKey());
                            }


                            ArrayAdapter<PersonagemItem> adapter = new ArrayAdapter<PersonagemItem>(getActivity().getApplicationContext(),
                                    android.R.layout.simple_list_item_1, perso);

                            listaPerso.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    };

                    mDatabase.child("Users").child(user.getUid()).child("Personagens").addValueEventListener(postListener);
                } else {

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        listaPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");
                Toast.makeText(getActivity().getApplicationContext(), perso.get(position).getNomePerso(), Toast.LENGTH_SHORT ).show();
                Log.d(TAG, perso.get(position).getNomePerso());

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
                Fragment viewPagePersonagem = new ViewPagePersonagem();
                Bundle bundle = new Bundle();
                bundle.putString("persoID", perso.get(position).getPersoID());
                getActivity().getIntent().removeExtra("persoID");
                getActivity().getIntent().putExtras(bundle);
                transaction.replace(R.id.headlines_fragment, viewPagePersonagem);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        listaPerso.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Log.d(TAG, "OnItemLongClickListener");
                Toast.makeText(getActivity().getApplicationContext(), perso.get(position).getNomePerso(), Toast.LENGTH_SHORT ).show();
                Log.d(TAG, perso.get(position).getNomePerso());

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Deletar Personagem");
                alert.setMessage("Você tem certeza que deseja deletar "+perso.get(position).getNomePerso()+" ?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mDatabase.child("Users").child(user.getUid()).child("Personagens").child(perso.get(position).getPersoID()).removeValue();
                        mDatabase.child("Personagens").child(perso.get(position).getPersoID()).removeValue();

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
                // -------------------------------
                return true;
            }
        });
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
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        Log.d(TAG, "onClick");

        if (i == R.id.cria_pers_button) {
            Log.d(TAG, "chama grava personagem");

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

            Bundle bundle = new Bundle();
            bundle.putString("persoID", mDatabase.child("Personagens").push().getKey());
            getActivity().getIntent().removeExtra("persoID");
            getActivity().getIntent().putExtras(bundle);

            // Fragments
            transaction = getFragmentManager().beginTransaction();
            Fragment viewPagePersonagem = new ViewPagePersonagem();

            transaction.replace(R.id.headlines_fragment, viewPagePersonagem);

            transaction.addToBackStack(null);
            transaction.commit();

            /*
            Fragment fragment = new Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt(key, value);
            fragment.setArguments(bundle);
             */
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
