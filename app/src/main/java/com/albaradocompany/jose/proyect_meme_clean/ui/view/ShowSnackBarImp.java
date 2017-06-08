package com.albaradocompany.jose.proyect_meme_clean.ui.view;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.usecase.ShowSnackBar;

/**
 * Created by jose on 01/05/2017.
 */

public class ShowSnackBarImp implements ShowSnackBar {
    Activity activity;
    View root;

    public ShowSnackBarImp(View layout) {
        root = layout;
    }

    public ShowSnackBarImp(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void show(String msg, int color) {
        final Snackbar snackbar;
        if (activity != null)
             snackbar = Snackbar.make(activity.getCurrentFocus(), msg, Snackbar.LENGTH_LONG);
        else
            snackbar = Snackbar.make(root, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(color);
        View view = snackbar.getView();

        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        Typeface font = Typeface.create("casual", Typeface.BOLD);
        tv.setTypeface(font);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        snackbar.show();
    }
}
