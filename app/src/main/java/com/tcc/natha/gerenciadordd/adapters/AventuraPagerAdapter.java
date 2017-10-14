package com.tcc.natha.gerenciadordd.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.natha.gerenciadordd.fragments.aventura.EditAventuraFragment;


public class AventuraPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "AventuraPagerAdapter";
    public AventuraPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new EditAventuraFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
