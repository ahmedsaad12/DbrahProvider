package com.apps.dbrah_Provider.uis.activity_home.order_module.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.dbrah_Provider.R;

import com.apps.dbrah_Provider.adapter.OrderAdapter;
import com.apps.dbrah_Provider.databinding.FragmentOrdersBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseFragment;
import com.apps.dbrah_Provider.uis.activity_current_prev_order_details.CurrentPreviousOrderDetailsActivity;
import com.apps.dbrah_Provider.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class FragmentCurrentOrders extends BaseFragment {
    private FragmentOrdersBinding binding;
    private HomeActivity activity;
    private OrderAdapter orderAdapter;
    private List<Object> orderList;

    public static FragmentCurrentOrders newInstance() {
        FragmentCurrentOrders fragment = new FragmentCurrentOrders();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList, activity, this,getLang());
        binding.recViewOrders.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewOrders.setAdapter(orderAdapter);
    }

    public void navigateToDetails() {
        Intent intent=new Intent(activity, CurrentPreviousOrderDetailsActivity.class);
        startActivity(intent);
    }
}