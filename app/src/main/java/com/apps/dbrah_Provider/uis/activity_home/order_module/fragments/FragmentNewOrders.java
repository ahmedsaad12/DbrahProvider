package com.apps.dbrah_Provider.uis.activity_home.order_module.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.FragmentCurrentOrdersBinding;
import com.apps.dbrah_Provider.databinding.FragmentNewOrdersBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseFragment;


public class FragmentNewOrders extends BaseFragment {
    private FragmentNewOrdersBinding binding;

    public static FragmentNewOrders newInstance() {
        FragmentNewOrders fragment = new FragmentNewOrders();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_current_orders, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
    }
}