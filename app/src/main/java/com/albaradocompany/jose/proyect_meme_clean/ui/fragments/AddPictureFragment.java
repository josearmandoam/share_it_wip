package com.albaradocompany.jose.proyect_meme_clean.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.albaradocompany.jose.proyect_meme_clean.R;

import butterknife.ButterKnife;

/**
 * Created by jose on 20/05/2017.
 */

public class AddPictureFragment extends Fragment {

    public AddPictureFragment() {
    }

    public static AddPictureFragment newInstance() {

        Bundle args = new Bundle();

        AddPictureFragment fragment = new AddPictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_picture, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
