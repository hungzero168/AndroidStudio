package com.example.foodapp.Model;

import java.util.List;

public class Banner {
    private List<Integer> bannerImages;

    public Banner(List<Integer> bannerImages) {
        this.bannerImages = bannerImages;
    }

    public List<Integer> getBannerImages() {
        return bannerImages;
    }
}
