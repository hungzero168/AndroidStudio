package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.DatabaseHelper.DatabaseHelper;
import com.example.foodapp.Model.Food;
import com.example.foodapp.R;

import java.util.List;
import java.util.Locale;

public class CartItemAdapter extends ArrayAdapter<Food> {
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    String user;
    int userId;
    private List<Food> foodList;

    public CartItemAdapter(Context context, List<Food> cartItems) {

        super(context, 0, cartItems);
        this.databaseHelper = new DatabaseHelper(context); // Khởi tạo databaseHelper ở đây
        this.sharedPreferences = context.getSharedPreferences("dangnhap", context.MODE_PRIVATE);
        this.user = sharedPreferences.getString("taikhoan", "");
        this.userId = databaseHelper.getUserIdByUsername(user);
        this.foodList = cartItems;
    }

    public void updateData(List<Food> foodList) {
        this.foodList.clear(); // Remove this line to keep existing items
        this.foodList.addAll(foodList);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item_layout, parent, false);
        }

        Food cartItem = getItem(position);

        ImageView imgCartItem = convertView.findViewById(R.id.imgCartItem);
        TextView txtCartItemName = convertView.findViewById(R.id.txtCartItemName);
        TextView txtCartItemPrice = convertView.findViewById(R.id.txtCartItemPrice);
        TextView txtCartItemQuantity = convertView.findViewById(R.id.txtCartItemQuantity);
        ImageButton imageButtonTru = convertView.findViewById(R.id.btnDecreaseQuantity);
        ImageButton imageButtonCong = convertView.findViewById(R.id.btnIncreaseQuantity);
        ImageButton btnRemoveItem = convertView.findViewById(R.id.btnRemoveItem);


        imgCartItem.setImageResource(cartItem.getAnhDoAn());
        txtCartItemName.setText(cartItem.getTenDoAn());
        txtCartItemPrice.setText(String.format(Locale.getDefault(), "Giá: $%.2f", cartItem.getGiaDoAn()));
        txtCartItemQuantity.setText(String.valueOf(cartItem.getSoLuong()));


        imageButtonTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getSoLuong();
                if (quantity > 1) {
                    quantity--;
                    int soluong = cartItem.getSoLuong();
                    float giadoan = cartItem.getGiaDoAn();
                    float thanhtien = giadoan*soluong;
                    cartItem.setSoLuong(quantity);
                    txtCartItemQuantity.setText(String.valueOf(quantity));
                    databaseHelper.updateCartItemQuantity(userId, cartItem.getId(), quantity, thanhtien);

                }
            }
        });

        imageButtonCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getSoLuong();
                quantity++;
                cartItem.setSoLuong(quantity);
                int soluong = cartItem.getSoLuong();
                float giadoan = cartItem.getGiaDoAn();
                float thanhtien = giadoan*soluong;
                cartItem.setSoLuong(quantity);
                txtCartItemQuantity.setText(String.valueOf(quantity));
                // Gọi hàm cập nhật SQL ở đây
                databaseHelper.updateCartItemQuantity(userId, cartItem.getId(), quantity, thanhtien);
            }
        });


        btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi hàm xóa món ăn khỏi giỏ hàng và cơ sở dữ liệu ở đây
                int foodId = cartItem.getId(); // Lấy ID của món ăn cần xóa
                boolean isDeleted = databaseHelper.deleteCartItem(userId, foodId);

                if (isDeleted) {
                    // Nếu xóa thành công, cập nhật danh sách món ăn trong adapter
                    remove(cartItem); // Loại bỏ món ăn khỏi danh sách trong adapter
                    notifyDataSetChanged(); // Thông báo cho adapter về sự thay đổi dữ liệu
                }
            }
        });





        return convertView;
    }
}
