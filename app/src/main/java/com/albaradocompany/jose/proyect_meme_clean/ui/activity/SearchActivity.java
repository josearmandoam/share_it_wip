package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.SearchRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SearchPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSearchPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.miguelcatalan.materialsearchview.MaterialSearchView.REQUEST_VOICE;

public class SearchActivity extends BaseActivty implements MaterialSearchView.OnQueryTextListener, MaterialSearchView.SearchViewListener, AbsSearchPresenter.View, AbsSearchPresenter.Navigator {
    private static final String USERID = "userId";
    @BindView(R.id.search_mSearch_view)
    MaterialSearchView searchView;
    @BindView(R.id.search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.search_pbr)
    ProgressBar progressBar;
    @BindView(R.id.search_lyt_container)
    RelativeLayout container;

    @Inject
    GetUserBDImp getUserBDImp;

    AbsSearchPresenter presenter;
    private UIComponent component;
    UserBD userBD;
    SearchRecyclerAdapter adapter;
    ShowSnackBarImp showSnackBarImp;
    private SearchRecyclerAdapter.onRowClickListener onSearchRowListener = new SearchRecyclerAdapter.onRowClickListener() {
        @Override
        public void onFollowClicked(String s, String xUserId, String xUsername, String xProfile) {
            presenter.onFollowClicked(s, xUserId, xUsername, xProfile);
        }

        @Override
        public void onUnFollowClicked(String feedId) {
            presenter.onUnFollowClicked(feedId);
        }

        @Override
        public void onUserClicked(String userId) {
            presenter.onUserClicked(userId, userBD.userId);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private boolean isVoiceAvailable() {
        PackageManager pm = getApplicationContext().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        return activities.size() != 0;
    }

    private void initialize() {
        component().inject(this);

        userBD = getUserBDImp.getUsers().get(0);
        showSnackBarImp = new ShowSnackBarImp(this);

        presenter = new SearchPresenter(this);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();

        configureSearchView();

    }

    private void configureSearchView() {
        if (isVoiceAvailable())
            searchView.setVoiceSearch(true);
        searchView.setBackground(getDrawable(R.drawable.roundedbutton_bluenocorner));
        searchView.setBackIcon(getDrawable(R.drawable.back));
        searchView.setCloseIcon(getDrawable(R.drawable.close_dark));
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchView.setMenuItem(menu.findItem(R.id.search_menu_item_search));
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean hideToolbar() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.onSubmitClicked(query, userBD.userId);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(SearchActivity.this, "QUERY CHANGED", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VOICE && resultCode == RESULT_OK) {
            List<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSearchViewShown() {

    }

    @Override
    public void onSearchViewClosed() {

    }

    @Override
    public void configureToolbar() {
        toolbar.setTitle(R.string.search_hint);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.roundedbutton_bluenocorner));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void showLoading() {
        container.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(Exception e) {
        showSnackBarImp.show(e.getMessage(), BuildConfig.COLOR_RED);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBarImp.show(getString(R.string.noInternetAvailable), BuildConfig.COLOR_RED);
    }

    @Override
    public void updateRecycler(String userId, List<User> users, List<Feed> feeds) {
        if (adapter != null) {
            adapter.clear();
            adapter.updateData(users, feeds);
            adapter.notifyDataSetChanged();
        } else {
            adapter = new SearchRecyclerAdapter(this, userId, users, feeds, onSearchRowListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showFollowSuccess() {
        presenter.updateFeeds(userBD.userId);
    }

    @Override
    public void showUnFollowSuccess() {
        presenter.updateFeeds(userBD.userId);
    }

    @Override
    public void updateRecyclerWithNewFeeds(String userId, List<Feed> feeds) {
        if (adapter != null) {
            adapter.clearFeeds();
            adapter.setNewFeeds(feeds);
            adapter.notifyDataSetChanged();
        }
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
    public void navigateToProfile() {
        openProfile(this);
    }

    private static void openProfile(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void navigateToUserDetail(String xUserId) {
        openUserDetail(this, xUserId);
    }

    @Override
    public void navigateToBack() {
        onBackPressed();
    }

    public static void openUserDetail(Context ctx, String userId) {
        Intent intent = new Intent(ctx, UserActivity.class);
        intent.putExtra(USERID, userId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.onBackClicked();
                break;
        }
        return true;
    }
}
