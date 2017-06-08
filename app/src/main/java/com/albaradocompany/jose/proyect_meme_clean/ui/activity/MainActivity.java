package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
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
    private static final String NOT_SEEN = "not seen";
    private static final String ACTIVITY_OPEN = "false";
    private static final String ACTIVITY_DESTROYED = "true";
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
    GetUserBDImp db;

    private UIComponent component;
    private FeedFragment feedFragment;
    AbsMainPresenter presenter;
    private NotificationFragment notificationFragment;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.onNotificationsReceived(intent);
        }
    };

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
        userSharedImp.saveActivityStatus(ACTIVITY_OPEN);
        presenter = new MainPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();

        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), getFragments()));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setTabTextColors(ssColor, ppColor);
        tabLayout.setBackgroundColor(BuildConfig.COLOR_RED);
        tabLayout.getTabAt(ADD_NEW_FRAGMENT).setIcon(getResources().getDrawable(R.mipmap.cam_gray));
        tabLayout.getTabAt(FEED_FRAGMENT).setIcon(getResources().getDrawable(R.mipmap.home_white));
        tabLayout.getTabAt(NOTIFICATION_FRAGMENT).setIcon(getResources().getDrawable(R.mipmap.not_unselected));

        viewPager.setCurrentItem(FEED_FRAGMENT);

        presenter.checkNewNotificationsReceived();
        checkDataReceive();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        userSharedImp.saveActivityStatus(ACTIVITY_OPEN);
        super.onResume();
//        feedFragment.parentResume();
        registerReceiver(mMessageReceiver, new IntentFilter("broadcast"));
        presenter.checkNewNotificationsReceived();
    }

    @Override
    protected void onPause() {
        super.onPause();
        userSharedImp.saveActivityStatus(ACTIVITY_DESTROYED);
        unregisterReceiver(mMessageReceiver);
    }

    @Override
    protected void onDestroy() {
        userSharedImp.saveActivityStatus(ACTIVITY_DESTROYED);
        super.onDestroy();
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    public List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<Fragment>();
        List<UserBD> userBDs = db.getUsers();
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
        switch (tab.getPosition()) {
            case FEED_FRAGMENT:
                tab.setText("Home");
                break;
            case ADD_NEW_FRAGMENT:
                tab.setText("New");
                break;
            case NOTIFICATION_FRAGMENT:
                tab.setText("Chat");
                break;
        }
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.getIcon().setColorFilter(ssColor, PorterDuff.Mode.SRC_IN);
        switch (tab.getPosition()) {
            case FEED_FRAGMENT:
                tab.setText("");
                break;
            case ADD_NEW_FRAGMENT:
                tab.setText("");
                break;
            case NOTIFICATION_FRAGMENT:
                tab.setText("");
                break;
        }
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (tab.getPosition() == NOTIFICATION_FRAGMENT)
            tab.setIcon(getDrawable(R.mipmap.not_select));
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
    public void updateNotifications() {
//        Toast.makeText(this, "new notification", Toast.LENGTH_SHORT).show();
        presenter.checkNewNotificationsReceived();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void notifyNewNotificationReceived() {
//        checkNotificationsStatus();
        tabLayout.getTabAt(NOTIFICATION_FRAGMENT).setIcon(getDrawable(R.mipmap.new_not_notselect));
    }

    @Override
    public void notifyNoNewNotificationReceived() {
        tabLayout.getTabAt(NOTIFICATION_FRAGMENT).setIcon(getDrawable(R.mipmap.not_unselected));
    }
    //
//    @Override
//    public void showNewNotification(String userId) {
//        notificationFragment.checkNewNotificationsReceived(userId);
//    }
}
