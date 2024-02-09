package com.example.foodapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Presenter.FeedbackPresenter;
import com.example.foodapp.R;
import com.example.foodapp.View.FeedbackView;


public class LienHeFragment extends Fragment {
    private EditText feedbackEditText;
    private Button submitFeedbackButton;
    private FeedbackPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lien_he, container, false);

        return view;
    }
}