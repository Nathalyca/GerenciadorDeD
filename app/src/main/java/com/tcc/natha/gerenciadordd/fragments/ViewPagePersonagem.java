package com.tcc.natha.gerenciadordd.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcc.natha.gerenciadordd.R;
import com.tcc.natha.gerenciadordd.adapters.PersonagemPagerAdapter;


public class ViewPagePersonagem extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View view;


    private OnFragmentInteractionListener mListener;

    // ViewPager
    private PersonagemPagerAdapter mPersonagemPagerAdapter;
    private ViewPager mViewPager;
    private static final String TAG = "ViewPagePersonagem";

    public ViewPagePersonagem() {

    }

    public static ViewPagePersonagem newInstance(String param1, String param2) {
        ViewPagePersonagem fragment = new ViewPagePersonagem();
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
        mPersonagemPagerAdapter = new PersonagemPagerAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_page_personagem, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mPersonagemPagerAdapter);
        return view;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
