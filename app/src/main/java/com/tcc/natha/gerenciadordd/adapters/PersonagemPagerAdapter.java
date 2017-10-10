package com.tcc.natha.gerenciadordd.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.tcc.natha.gerenciadordd.fragments.BlankFragment;
import com.tcc.natha.gerenciadordd.fragments.BlankFragment2;
import com.tcc.natha.gerenciadordd.fragments.EditPersonagemFragment;
import com.tcc.natha.gerenciadordd.fragments.ResistenciaPericiaFragment;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PersonagemPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "EditPersonagemFragment";

    public PersonagemPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {

        Log.d(TAG, "position:" + position);
    switch (position){
            case 0:
                return new EditPersonagemFragment();
            case 1:
                return new ResistenciaPericiaFragment();
            case 2:
                return new BlankFragment();
            case 3:
                return new BlankFragment2();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}

