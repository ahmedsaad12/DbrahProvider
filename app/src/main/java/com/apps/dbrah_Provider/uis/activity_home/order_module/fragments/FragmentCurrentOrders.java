package com.apps.dbrah_Provider.uis.activity_home.order_module.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.dbrah_Provider.R;

import com.apps.dbrah_Provider.adapter.OrderAdapter;
import com.apps.dbrah_Provider.databinding.FragmentOrdersBinding;
import com.apps.dbrah_Provider.model.OrderDataModel;
import com.apps.dbrah_Provider.model.OrderModel;
import com.apps.dbrah_Provider.mvvm.FragmentOrderMvvm;
import com.apps.dbrah_Provider.mvvm.GeneralMvvm;
import com.apps.dbrah_Provider.uis.activity_base.BaseFragment;
import com.apps.dbrah_Provider.uis.activity_current_prev_order_details.CurrentPreviousOrderDetailsActivity;
import com.apps.dbrah_Provider.uis.activity_home.HomeActivity;

import java.util.ArrayList;


public class FragmentCurrentOrders extends BaseFragment {
    private FragmentOrdersBinding binding;
    private HomeActivity activity;
    private OrderAdapter orderAdapter;
    private FragmentOrderMvvm fragmentOrderMvvm;
    private GeneralMvvm generalMvvm;
    private ActivityResultLauncher<Intent> launcher;

    public static FragmentCurrentOrders newInstance() {
        FragmentCurrentOrders fragment = new FragmentCurrentOrders();

        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {

                fragmentOrderMvvm.getOrders(getUserModel());
                generalMvvm.getOnPreOrderRefreshed().postValue(true);
                generalMvvm.getOnStaticRefreshed().setValue(true);

            }
        });
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
        fragmentOrderMvvm = ViewModelProviders.of(this).get(FragmentOrderMvvm.class);
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        generalMvvm.getOrderpage().observe(activity, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    fragmentOrderMvvm.getOrders(getUserModel());
                }
            }
        });
        generalMvvm.getOnCurrentOrderRefreshed().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                fragmentOrderMvvm.getOrders(getUserModel());
            }
        });
        orderAdapter = new OrderAdapter(activity, this, getLang());
        binding.recViewOrders.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewOrders.setAdapter(orderAdapter);
        fragmentOrderMvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.swipeRefresh.setRefreshing(aBoolean);

            }
        });
        fragmentOrderMvvm.getMutableLiveData().observe(this, new Observer<OrderDataModel.Data>() {
            @Override
            public void onChanged(OrderDataModel.Data data) {
                if (data != null) {
                    if (data.getCurrent() != null && data.getCurrent().size() > 0) {
                        orderAdapter.updateList(data.getCurrent());
                        binding.tvNoData.setVisibility(View.GONE);


                    } else {
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
        Intent intent = new Intent(activity, CurrentPreviousOrderDetailsActivity.class);
        intent.putExtra("order_id", orderModel.getId());
        launcher.launch(intent);
    }
}