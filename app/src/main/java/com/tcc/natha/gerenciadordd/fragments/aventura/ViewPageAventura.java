package com.tcc.natha.gerenciadordd.fragments.aventura;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcc.natha.gerenciadordd.R;
import com.tcc.natha.gerenciadordd.adapters.AventuraPagerAdapter;
import com.tcc.natha.gerenciadordd.adapters.PersonagemPagerAdapter;
import com.tcc.natha.gerenciadordd.fragments.personagem.ViewPagePersonagem;

import java.util.List;

public class ViewPageAventura extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View view;
    private OnFragmentInteractionListener mListener;

    // ViewPager
    private AventuraPagerAdapter mAventuraPagerAdapter;
    private ViewPager mViewPager;
    private static final String TAG = "ViewPageAventura";

    public ViewPageAventura() {

    }

    public static ViewPageAventura newInstance(String param1, String param2) {
        ViewPageAventura fragment = new ViewPageAventura();
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
        mAventuraPagerAdapter = new AventuraPagerAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_page_aventura, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.containerAventura);
        mViewPager.setAdapter(mAventuraPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                List<Fragment> fragmentList = fragmentManager.getFragments();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                for (Fragment fragment: fragmentList ) {
                    if(fragment != null){
                        fragment.onPause();
                    }
                }
                transaction.commit();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
