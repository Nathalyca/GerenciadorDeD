package com.tcc.natha.gerenciadordd.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.natha.gerenciadordd.fragments.personagem.EditPersonagemFragment;
import com.tcc.natha.gerenciadordd.fragments.personagem.ResistenciaPericiaFragment;

public class PersonagemPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "EditPersonagemFragment";
    public PersonagemPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

    switch (position){
            case 0:
                return new EditPersonagemFragment();
            case 1:
                return new ResistenciaPericiaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
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