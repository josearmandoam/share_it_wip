package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.MainViewPagerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.FeedFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.NewPictureFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.NotificationFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.MainPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsMainPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;

public class MainActivity extends BaseActivty implements TabLayout.OnTabSelectedListener, AbsMainPresenter.View, AbsMainPresenter.Navigator {
    private static final java.lang.String NOTIFICATION = "notification";
    private static final java.lang.String ACTION = "action";
    private static final int NOTIFICATION_FRAGMENT = 2;
    private static final int ADD_NEW_FRAGMENT = 0;
    private static final int FEED_FRAGMENT = 1;
    private static final String TITLE = "title";
    private static final String MESSAGE = "body";
    private static final String TOKEN = "token";
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
    AbsMainPresenter presenter;
    private NotificationFragment notificationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        initialilze();
    }

    private void checkDataReceive() {
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString(ACTION).equals(NOTIFICATION))
                presenter.onNotificationReceived(getIntent().getExtras());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initialilze() {
        presenter = new MainPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();

        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), getFragments()));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.getTabAt(ADD_NEW_FRAGMENT).setIcon(getResources().getDrawable(R.drawable.camera_light));
        tabLayout.getTabAt(FEED_FRAGMENT).setIcon(getResources().getDrawable(R.drawable.send_dark));
        tabLayout.getTabAt(NOTIFICATION_FRAGMENT).setIcon(getResources().getDrawable(R.drawable.avatar_light));

        viewPager.setCurrentItem(FEED_FRAGMENT);

        checkDataReceive();
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
        List<UserBD> userBDs = getUserBDImp.getUsers();
        feedFragment = FeedFragment.newInstance(userBDs.get(0));
        notificationFragment = NotificationFragment.newInstance(userBDs.get(0).userId, userBDs.get(0).user_name + " " + userBDs.get(0).user_lastname);
        list.add(NewPictureFragment.newInstance());
        list.add(feedFragment);
        list.add(notificationFragment);
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

    @Override
    public void openNotificationFragment(NotificationLine line) {
        viewPager.setCurrentItem(NOTIFICATION_FRAGMENT);
        notificationFragment.notifyNewNotification(line);
//        NotificationFragment.newInstance(extras.get(TITLE),extras.get(MESSAGE),extras.get(TOKEN));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);


    }
    //
//    @Override
//    public void showNewNotification(String userId) {
//        notificationFragment.notifyNewNotification(userId);
//    }
}
