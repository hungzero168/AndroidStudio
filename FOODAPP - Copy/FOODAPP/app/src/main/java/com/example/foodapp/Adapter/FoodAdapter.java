package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Fragment.HomeFragment;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Presenter.FoodPresenter;
import com.example.foodapp.R;
import com.example.foodapp.View.FoodDetailActivity;
import com.example.foodapp.View.FoodView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    private ArrayList<Food> foodArrayList;
    private FoodPresenter presenter;


    public FoodAdapter(Context context, ArrayList<Food> foodArrayList, FoodPresenter presenter) {
        this.context = context;
        this.foodArrayList = foodArrayList;
        this.presenter = presenter;
    }

    public FoodAdapter(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }

    public void updateQuantity(int foodId, int newQuantity) {
        if (foodArrayList != null) {
            for (Food food : foodArrayList) {
                if (food.getId() == foodId) {
                    food.setSoLuong(newQuantity);
                    notifyDataSetChanged(); // Cập nhật giao diện ngay lập tức
                    break;
                }
            }
        }
    }




    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        Food food = foodArrayList.get(position);
        holder.TenDoAn.setText(food.getTenDoAn());
        holder.GiaDoAn.setText(food.getGiaDoAn() + "K");
        holder.GiaKhuyenMai.setText(food.getGiaKhuyenMai() + "K");
        if (food.getAnhDoAn() != 0) {
            holder.AnhDoAn.setImageResource(food.getAnhDoAn());
        } else {
            holder.AnhDoAn.setImageResource(R.drawable.giohang);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedItemId = food.getId(); // Lấy id của món ăn khi ấn vào item

                // Tạo Intent để chuyển đến Activity Chi Tiết và chuyển dữ liệu món ăn
                Intent intent = new Intent(view.getContext(), FoodDetailActivity.class);
                intent.putExtra("food_id", clickedItemId); // Truyền id của món ăn

                // Mở Activity Chi Tiết
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public void updateData(ArrayList<Food> newData) {
        foodArrayList.clear();
        foodArrayList.addAll(newData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TenDoAn, GiaDoAn, GiaKhuyenMai;
        ImageView AnhDoAn;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TenDoAn = itemView.findViewById(R.id.txtTenDoAn);
            GiaDoAn = itemView.findViewById(R.id.txtGiaDoAn);
            GiaKhuyenMai = itemView.findViewById(R.id.txtGiaKhuyeMai);
            AnhDoAn = itemView.findViewById(R.id.imgDoAan);
            layout = itemView.findViewById(R.id.itemFood);
        }
    }
}