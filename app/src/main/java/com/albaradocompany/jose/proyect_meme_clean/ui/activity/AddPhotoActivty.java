package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.interactor.AvatarInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.AddPhotoViewPagerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.AvatarsFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.CamGallFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by jose on 20/04/2017.
 */

public class AddPhotoActivty extends BaseActivty implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.add_photo_pageviewer)
    ViewPager viewPager;
    @BindView(R.id.add_photo_tablayout)
    TabLayout tabLayout;

    @BindColor(R.color.color_login)
    int ppColor;
    @BindColor(R.color.light_gray)
    int ssColor;

    @Inject
    AvatarInteractor avatarInteractor;
    UIComponent component;
    private int action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        intialize();
    }

    private void intialize() {
        getBundles();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CamGallFragment(this, action));
        fragments.add(new AvatarsFragment(this, action));
        viewPager.setAdapter(new AddPhotoViewPagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.camera_dark));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.avatar_light));

    }

    private void getBundles() {
        Bundle bundle = getIntent().getExtras();
        action = (int) bundle.get("action");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_photo;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
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
}
