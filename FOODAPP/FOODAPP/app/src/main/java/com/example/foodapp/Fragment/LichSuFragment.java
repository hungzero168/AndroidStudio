package com.example.foodapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.foodapp.Adapter.OrderAdapter;
import com.example.foodapp.Model.OrderInfo;
import com.example.foodapp.Presenter.OrderPresenter;
import com.example.foodapp.R;
import com.example.foodapp.View.OrderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LichSuFragment extends Fragment implements OrderView {
    private ListView listView;
    private OrderAdapter orderAdapter;
    private List<OrderInfo> orderList;
    private OrderPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_lich_su, container, false);

        listView = view.findViewById(R.id.listView);


        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(getContext(), orderList);
        presenter = new OrderPresenter(this, getContext());
        presenter.loadOrders();
        listView.setAdapter(orderAdapter);


        return view;
    }

    @Override
    public void displayOrders(List<OrderInfo> newOrderList) {

        Collections.sort(newOrderList, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {

                return Integer.compare(o2.getOrderId(), o1.getOrderId());
            }
        });

        orderList.clear();
        orderList.addAll(newOrderList);

        orderAdapter.notifyDataSetChanged();
    }
}