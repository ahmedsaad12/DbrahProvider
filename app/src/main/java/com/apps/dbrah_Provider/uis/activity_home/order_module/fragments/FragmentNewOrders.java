package com.apps.dbrah_Provider.uis.activity_home.order_module.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import com.apps.dbrah_Provider.R;

import com.apps.dbrah_Provider.adapter.OrderAdapter;
import com.apps.dbrah_Provider.databinding.FragmentOrdersBinding;
import com.apps.dbrah_Provider.model.OrderDataModel;
import com.apps.dbrah_Provider.model.OrderModel;
import com.apps.dbrah_Provider.model.ReviewModel;
import com.apps.dbrah_Provider.mvvm.FragmentOrderMvvm;
import com.apps.dbrah_Provider.uis.activity_base.BaseFragment;
import com.apps.dbrah_Provider.uis.activity_home.HomeActivity;
import com.apps.dbrah_Provider.uis.activity_order_details.OrderDetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class FragmentNewOrders extends BaseFragment {
    private FragmentOrdersBinding binding;
    private HomeActivity activity;
    private OrderAdapter orderAdapter;
private FragmentOrderMvvm fragmentOrderMvvm;
    public static FragmentNewOrders newInstance() {
        FragmentNewOrders fragment = new FragmentNewOrders();

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
        fragmentOrderMvvm= ViewModelProviders.of(this).get(FragmentOrderMvvm.class);

        orderAdapter = new OrderAdapter( activity, this,getLang());
        binding.recViewOrders.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewOrders.setAdapter(orderAdapter);
        fragmentOrderMvvm.getOnOrderStatusSuccess().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    fragmentOrderMvvm.getOrders(getUserModel());
                }
            }
        });
        fragmentOrderMvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                    binding.swipeRefresh.setRefreshing(aBoolean);

            }
        });
        fragmentOrderMvvm.getMutableLiveData().observe(this, new Observer<OrderDataModel.Data>() {
            @Override
            public void onChanged(OrderDataModel.Data data) {
                if(data!=null){
                    if(data.getNews()!=null&&data.getNews().size()>0){
                            orderAdapter.updateList(data.getNews());
                            binding.tvNoData.setVisibility(View.GONE);


                    }
                    else {
                        orderAdapter.updateList(new ArrayList<>());
                        binding.tvNoData.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        fragmentOrderMvvm.getOrders(getUserModel());
binding.swipeRefresh.setOnRefreshListener(() -> fragmentOrderMvvm.getOrders(getUserModel()));

    }

    public void navigateToDetails(OrderModel orderModel) {
        Intent intent=new Intent(activity, OrderDetailsActivity.class);
        intent.putExtra("order_id",orderModel.getId());
        startActivity(intent);
    }

    public void pinOrder(OrderModel orderModel) {
        fragmentOrderMvvm.pinOrder(orderModel.getId(),getUserModel().getData().getId(),activity);
    }
}