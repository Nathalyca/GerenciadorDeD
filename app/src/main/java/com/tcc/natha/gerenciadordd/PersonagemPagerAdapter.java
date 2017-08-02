package com.tcc.natha.gerenciadordd;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.natha.gerenciadordd.BlankFragment;

import com.tcc.natha.gerenciadordd.BlankFragment2;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PersonagemPagerAdapter extends FragmentPagerAdapter {

    public PersonagemPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new BlankFragment2();
            case 1:
                return new BlankFragment();
            case 2:
                return new EditPersonagemFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
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

