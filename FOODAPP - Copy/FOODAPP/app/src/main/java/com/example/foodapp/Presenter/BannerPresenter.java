package com.example.foodapp.Presenter;

import com.example.foodapp.Model.Banner;
import com.example.foodapp.View.BannerView;

import java.util.List;

public class BannerPresenter {
    private BannerView view;
    private Banner model;

    public BannerPresenter(BannerView view, Banner model) {
        this.view = view;
        this.model = model;
    }

    public void loadBanners() {
        List<Integer> bannerImages = model.getBannerImages();
        view.showBanners(bannerImages);
    }
}
