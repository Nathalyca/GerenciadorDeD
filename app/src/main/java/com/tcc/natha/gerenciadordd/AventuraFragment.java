package com.tcc.natha.gerenciadordd;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.tcc.natha.gerenciadordd.models.SequencialAventura;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AventuraFragment extends Fragment implements View.OnClickListener, EditAventuraFragment.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment2 newInstance(String param1, String param2) {
        BlankFragment2 fragment = new BlankFragment2();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aventura, container, false);
        getActivity().setTitle("Aventura");
        Log.d(TAG, "onCreateView");
        //transaction.addToBackStack(null);
        //transaction.commit();

        // Buttons
        criaAventuraButton = (Button) view.findViewById(R.id.cria_aven_button);
        criaAventuraButton.setOnClickListener(this);

        procuraAventuraButton = (Button) view.findViewById(R.id.proc_aven_button);
        procuraAventuraButton.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        Log.d(TAG, "onClick");

        if (i == R.id.cria_aven_button) {
            Log.d(TAG, "chama edit aventura");

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

            mDatabase.child("SequencialAventura").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    seqAvent = dataSnapshot.getValue(SequencialAventura.class);
                    if (seqAvent != null) {
                        seqAvent.setSeqCodAventura(seqAvent.getSeqCodAventura() + 1);
                        Log.d(TAG, "seqAvent.getSeqCodAventura()" + seqAvent.getSeqCodAventura());
                        Log.d(TAG, "seqAvent" + seqAvent);

                    } else {
                        seqAvent = new SequencialAventura();
                        seqAvent.setSeqCodAventura(sequencialDefault);
                        Log.d(TAG, "seqAvent.getSeqCodAventura()" + seqAvent.getSeqCodAventura());

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
            Log.d(TAG, "seqAvent.getSeqCodAventura()" + seqAvent.getSeqCodAventura());
            seqAvent = new SequencialAventura();
            Bundle bundle = new Bundle();
            bundle.putInt("seqAventura", seqAvent.getSeqCodAventura());
          //  bundle.putString("seqAventura", seqAvent.getSeqCodAventura() + "");
            getActivity().getIntent().removeExtra("SeqCodAventura");
            getActivity().getIntent().putExtras(bundle);

            // Fragments
            transaction = getFragmentManager().beginTransaction();
            Fragment editAventuraFragment = new EditAventuraFragment();

            transaction.replace(R.id.headlines_fragment, editAventuraFragment);

            transaction.addToBackStack(null);
            transaction.commit();

            /*
            Fragment fragment = new Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt(key, value);
            fragment.setArguments(bundle);
             */
        }

        if (i == R.id.proc_aven_button) {
            Log.d(TAG, "chama edit aventura");

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

          /*  Bundle bundle = new Bundle();
            bundle.putString("persoID", mDatabase.child("Personagens").push().getKey());
            getActivity().getIntent().removeExtra("persoID");
            getActivity().getIntent().putExtras(bundle);
*/
            // Fragments
            transaction = getFragmentManager().beginTransaction();
            Fragment blankfragment2 = new BlankFragment2();

            transaction.replace(R.id.headlines_fragment, blankfragment2);

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
