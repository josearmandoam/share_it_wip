package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.MainViewPagerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.AddPictureFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.ChatFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.FeedFragment;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserBD;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;

public class MainActivity extends BaseActivty implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.main_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;

    @BindColor(android.R.color.white)
    int ppColor;
    @BindColor(android.R.color.darker_gray)
    int ssColor;

    @Inject
    UserSharedImp userSharedImp;
    @Inject
    GetUserBDImp getUserBDImp;

    private UIComponent component;
    private FeedFragment feedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialilze();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initialilze() {
        component().inject(this);
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), getFragments()));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.camera_light));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.send_dark));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.avatar_light));

        viewPager.setCurrentItem(1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedFragment.parentResume();
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    public List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<Fragment>();
        feedFragment = FeedFragment.newInstance(getUserBDImp.getUserBD(userSharedImp.getUserID()));
        list.add(AddPictureFragment.newInstance());
        list.add(feedFragment);
        list.add(ChatFragment.newInstance());
        return list;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tab.getIcon().setColorFilter(ppColor, PorterDuff.Mode.SRC_IN);
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.getIcon().setColorFilter(ssColor, PorterDuff.Mode.SRC_IN);
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        tab.getIcon().setColorFilter(ppColor, PorterDuff.Mode.SRC_IN);
        viewPager.setCurrentItem(tab.getPosition());
    }

    public UIComponent component() {
        if (component == null) {
            component = DaggerUIComponent.builder()
                    .rootComponent(((App) getApplication()).getComponent())
                    .uIModule(new UIModule(getApplicationContext()))
                    .mainModule(((App) getApplication()).getMainModule())
                    .build();
        }
        return component;
    }
}
