package com.example.foodapp.Presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Model.Feedback;
import com.example.foodapp.View.FeedbackView;

public class FeedbackPresenter {
    private FeedbackView view;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private Context context;
    public FeedbackPresenter(FeedbackView view, DatabaseHelper dbHelper, Context context) {
        this.view = view;
        this.dbHelper = dbHelper;
        this.context = context;  // Initialize context first
        this.sharedPreferences = context.getSharedPreferences("dangnhap", MODE_PRIVATE);  // Then use context to initialize sharedPreferences
    }


    public void saveFeedback(String feedbackText) {
        sharedPreferences = context.getSharedPreferences("dangnhap", MODE_PRIVATE);
        String id = sharedPreferences.getString("taikhoan", "");
        int userId = dbHelper.getUserIdByUsername(id);
        long newRowId = dbHelper.addFeedback(feedbackText, userId);
        if (newRowId > -1) {
            view.showFeedbackSaved();
        }
    }

}

