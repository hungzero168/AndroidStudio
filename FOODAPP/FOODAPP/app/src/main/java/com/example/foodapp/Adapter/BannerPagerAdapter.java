package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foodapp.R;

import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Integer> bannerImages;

    public BannerPagerAdapter(Context context, List<Integer> bannerImages) {
        this.context = context;
        this.bannerImages = bannerImages;
    }

    @Override
    public int getCount() {
        return bannerImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.banner_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.bannerImageView);
        imageView.setImageResource(bannerImages.get(position));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

