package com.example.foodapp.View.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.foodapp.Fragment.GioHangFragment;
import com.example.foodapp.Fragment.HomeFragment;
import com.example.foodapp.Fragment.LichSuFragment;
import com.example.foodapp.Fragment.LienHeFragment;
import com.example.foodapp.Fragment.PhanHoiFragment;
import com.example.foodapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.MenuBottom);

        bottomNavigationView.setBackground(null);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.giohang:
                    fragment = new GioHangFragment();
                    break;
                case R.id.phanhoi:
                    fragment = new PhanHoiFragment();
                    break;
                case R.id.lienhe:
                    fragment = new LienHeFragment();
                    break;
                case R.id.lichsu:
                    fragment = new LichSuFragment();
                    break;
                default:
                    fragment = new HomeFragment();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        });

    }

}