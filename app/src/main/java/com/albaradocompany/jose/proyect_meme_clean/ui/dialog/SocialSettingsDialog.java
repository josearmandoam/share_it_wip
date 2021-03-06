package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.EditProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SocialSettingsPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSocialSettingsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 11/05/2017.
 */

public class SocialSettingsDialog extends DialogFragment implements AbsSocialSettingsPresenter.Navigator, AbsSocialSettingsPresenter.View {

    private AlertDialog dialog;
    AbsSocialSettingsPresenter presenter;
    @BindView(R.id.social_settings_sw_facebook)
    Switch facebookSwitch;
    @BindView(R.id.social_settings_sw_twitter)
    Switch twitterSwitch;
    @BindView(R.id.social_settings_sw_instagram)
    Switch instagramSwitch;
    @BindView(R.id.social_settings_sw_email)
    Switch emailSwitch;
    @BindView(R.id.social_settings_sw_whatsapp)
    Switch whatsappSwitch;
    @BindView(R.id.social_settings_sw_website)
    Switch websiteSwitch;

    @OnClick(R.id.social_settings_ibtn_eye)
    public void onEyeClicked(View view) {
        presenter.onConfirmClicked();
    }

    @Inject
    UserSharedImp userSharedImp;
    Context activity;

    public SocialSettingsDialog(Context activity) {
        this.activity = activity;
        getComponent().inject(this);
//        dialog.getWindow().setLayout((int) (getWidth() / 1.5), (int) (getWidth() / 1.5));
    }

    private void initializePresenter() {
        presenter = new SocialSettingsPresenter(getActivity());
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.updateSwitchs();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_social_settings, null);
        dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        ButterKnife.bind(this, view);
        initializePresenter();
        updateSwitchs();
        return dialog;
    }

    private int getWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override
    public void dimissDialog() {
        saveSwitchChanges();
        dialog.dismiss();

    }

    @Override
    public void updateSwitchs() {
        checkPrivacitySharePreferences();
    }

    private void checkPrivacitySharePreferences() {
        /*is get...Privacity returns true the field is public*/
        if (userSharedImp.getWebsitePrivacity()) {
            websiteSwitch.setChecked(true);
        } else {
            websiteSwitch.setChecked(false);
        }
        if (userSharedImp.getWhatsappPrivacity()) {
            whatsappSwitch.setChecked(true);
        } else {
            whatsappSwitch.setChecked(false);
        }
        if (userSharedImp.getFacebookPrivacity()) {
            facebookSwitch.setChecked(true);
        } else {
            facebookSwitch.setChecked(false);
        }
        if (userSharedImp.getInstagramPrivacity()) {
            instagramSwitch.setChecked(true);
        } else {
            instagramSwitch.setChecked(false);
        }
        if (userSharedImp.getEmailPrivacity()) {
            emailSwitch.setChecked(true);
        } else {
            emailSwitch.setChecked(false);
        }
        if (userSharedImp.getTwitterPrivacity()) {
            twitterSwitch.setChecked(true);
        } else {
            twitterSwitch.setChecked(false);
        }
    }

//    private void checkPrivacity() {
//        if (userSharedImp.isPrivacityPublic()) {
//            emailSwitch.setChecked(false);
//            facebookSwitch.setChecked(false);
//            instagramSwitch.setChecked(false);
//            whatsappSwitch.setChecked(false);
//            websiteSwitch.setChecked(false);
//            twitterSwitch.setChecked(false);
//            userSharedImp.savePrivacityStatus("false");
//        } else {
//            emailSwitch.setChecked(true);
//            facebookSwitch.setChecked(true);
//            instagramSwitch.setChecked(true);
//            whatsappSwitch.setChecked(true);
//            websiteSwitch.setChecked(true);
//            twitterSwitch.setChecked(true);
//            userSharedImp.savePrivacityStatus("true");
//        }
//    }

    protected UIComponent getComponent() {
        return ((EditProfileActivity) activity).component();
    }

    private void saveSwitchChanges() {
        if (emailSwitch.isChecked()) {
            userSharedImp.saveEmailPrivacity("true");
        } else {
            userSharedImp.saveEmailPrivacity("false");
        }
        if (facebookSwitch.isChecked()) {
            userSharedImp.saveFacebookPrivacity("true");
        } else {
            userSharedImp.saveFacebookPrivacity("false");
        }
        if (instagramSwitch.isChecked()) {
            userSharedImp.saveInstagramPrivacity("true");
        } else {
            userSharedImp.saveInstagramPrivacity("false");
        }
        if (whatsappSwitch.isChecked()) {
            userSharedImp.saveWhatsappPrivacity("true");
        } else {
            userSharedImp.saveWhatsappPrivacity("false");
        }
        if (websiteSwitch.isChecked()) {
            userSharedImp.saveWebsitePrivacity("true");
        } else {
            userSharedImp.saveWebsitePrivacity("false");
        }
        if (twitterSwitch.isChecked()) {
            userSharedImp.saveTwitterPrivacity("true");
        } else {
            userSharedImp.saveTwitterPrivacity("false");
        }
    }
}
