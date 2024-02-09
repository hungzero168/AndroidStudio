package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.foodapp.Model.OrderInfo;
import com.example.foodapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderInfo> orderList;

    public OrderAdapter(Context context, List<OrderInfo> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_history_item, null);
        }

        OrderInfo order = orderList.get(position);

        TextView orderIdTextView = view.findViewById(R.id.orderIdTextView);
        TextView orderName = view.findViewById(R.id.orderName);
        TextView orderNumberPhone = view.findViewById(R.id.orderNumberPhone);
        TextView orderMenu = view.findViewById(R.id.orderMenu);
        TextView orderDateTimeTextView = view.findViewById(R.id.orderDateTimeTextView);
        TextView thanhtoan = view.findViewById(R.id.thanhtoan);
        TextView orderTotal = view.findViewById(R.id.orderTotal);

        orderIdTextView.setText(String.valueOf(order.getOrderId()));
        orderName.setText(order.getUserName());
        orderNumberPhone.setText(order.getUserPhoneNumber());
        orderMenu.setText(order.getFoodName());
        orderDateTimeTextView.setText(order.getOrderDate() + ", " + order.getOrderTime());
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedPrice = decimalFormat.format(order.getTotalAmount());
        orderTotal.setText("Tổng tiền " + formattedPrice + "K");
        thanhtoan.setText(order.getThanhtoan());

        return view;
    }
}
