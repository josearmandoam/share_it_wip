package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.UserPhotos;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.UserSavedPhotos;

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
                return new UserPhotos();
            case 1:
                return new UserSavedPhotos();
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
