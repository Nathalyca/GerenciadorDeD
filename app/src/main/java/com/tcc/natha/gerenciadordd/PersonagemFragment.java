package com.tcc.natha.gerenciadordd;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PersonagemFragment extends Fragment implements View.OnClickListener,
        GravaPersonagemFragment.OnFragmentInteractionListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "PersonagemFragment";

    private Button criaPersButton;

    private View view;

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

        Log.d(TAG, "onCreateView");

        Log.d(TAG, "View: "+view);

        // Fragments
        Fragment itemFragment = new ItemFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        Log.d(TAG, "onCreateView:" + "transaction.replace(R.id.personagem_fragment, itemFragment);");
        transaction.replace(R.id.personagem_fragment, itemFragment);

        transaction.addToBackStack(null);
        transaction.commit();

        // Buttons
        criaPersButton = (Button) view.findViewById(R.id.cria_pers_button);
        criaPersButton.setOnClickListener(this);

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

            // Fragments
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment gravaPersonagemFragment = new GravaPersonagemFragment();

            transaction.replace(R.id.headlines_fragment, gravaPersonagemFragment);

            transaction.addToBackStack(null);
            transaction.commit();
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
