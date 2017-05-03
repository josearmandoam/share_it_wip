package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.UserPhotosFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.UserSavedPhotosFragment;

/**
 * Created by jose on 03/05/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    String fragments[] = {"Fragment 1", "Fragment 2"};
    public ViewPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new UserPhotosFragment();
            case 1:
                return new UserSavedPhotosFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}
