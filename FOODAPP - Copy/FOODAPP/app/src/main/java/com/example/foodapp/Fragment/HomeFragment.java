package com.example.foodapp.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodapp.Adapter.BannerPagerAdapter;
import com.example.foodapp.Adapter.FoodAdapter;
import com.example.foodapp.Model.Banner;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Presenter.BannerPresenter;
import com.example.foodapp.Presenter.FoodPresenter;
import com.example.foodapp.R;
import com.example.foodapp.View.BannerView;
import com.example.foodapp.View.FoodView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements FoodView, BannerView, com.example.foodapp.View.SearchView {
    private FoodPresenter presenter;
    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private SearchView searchView;
    private ViewPager viewPager;
    private BannerPresenter presenterBanner;
    private BannerPagerAdapter adapterBanner;
    private int currentPage = 0;
    private final long AUTO_SCROLL_DELAY = 3000; // Thời gian tự động lướt (3 giây)
    private final Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);
        viewPager = view.findViewById(R.id.viewPager);



//        hiển thị món
        Food food = new Food(getContext());
        presenter = new FoodPresenter(food, this);
        presenter.loadFoodList();
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


//        hiển thị banner
        List<Integer> bannerImages = new ArrayList<>();
        adapterBanner = new BannerPagerAdapter(getContext(), bannerImages);
        viewPager.setAdapter(adapterBanner);

        runnable = new Runnable() {
            public void run() {
                if (currentPage == adapterBanner.getCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, AUTO_SCROLL_DELAY);
            }
        };

        bannerImages.add(R.drawable.banner1);
        bannerImages.add(R.drawable.banner2);
        bannerImages.add(R.drawable.banner3);
//        List<Integer> bannerImages = Arrays.asList(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3);
        Banner model = new Banner(bannerImages);
        presenterBanner = new BannerPresenter(this, model);
        presenterBanner.loadBanners();



//      Search
        FoodPresenter presenter = new FoodPresenter(food, this, this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                presenter.searchFood(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                presenter.searchFood(s);
                return true;
            }
        });





        return view;
    }

    @Override
    public void showFoodList(List<Food> foodList) {
        ArrayList<Food> foodArrayList = new ArrayList<>(foodList);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FoodAdapter(getContext(), foodArrayList, presenter);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCartFoodList(List<Food> foodList) {

    }

    @Override
    public void showOrderConfirmationSuccess() {

    }

    @Override
    public void showOrderConfirmationFailure() {

    }

    @Override
    public void showBanners(List<Integer> bannerImages) {
        // Sử dụng ViewPager và adapter để hiển thị các banner
        BannerPagerAdapter adapter = new BannerPagerAdapter(getContext(), bannerImages);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void showSearchResults(List<Food> results) {
        adapter.updateData(new ArrayList<>(results));
        adapter.notifyDataSetChanged();
    }

//    banner
    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, AUTO_SCROLL_DELAY);
    }
// banner
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}