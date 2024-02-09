package com.example.foodapp.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Presenter.FeedbackPresenter;
import com.example.foodapp.R;
import com.example.foodapp.View.FeedbackView;


public class PhanHoiFragment extends Fragment implements FeedbackView {
    private EditText feedbackEditText;
    private Button submitFeedbackButton;
    private FeedbackPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phan_hoi, container, false);

        feedbackEditText = view.findViewById(R.id.feedbackEditText);
        submitFeedbackButton = view.findViewById(R.id.submitFeedbackButton);
        presenter = new FeedbackPresenter(this, new DatabaseHelper(requireContext()), requireContext());


        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackEditText.getText().toString();
                presenter.saveFeedback(feedback);
                feedbackEditText.setText("");
                Toast.makeText(getContext(), "Phản hồi thành công", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void showFeedbackSaved() {

    }
}