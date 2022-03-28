package com.apps.dbrah_Provider.uis.activity_home.order_module.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.FragmentPreviousOrdersBinding;


public class FragmentPreviousOrders extends Fragment {
    private FragmentPreviousOrdersBinding binding;
    public static FragmentPreviousOrders newInstance() {
        FragmentPreviousOrders fragment = new FragmentPreviousOrders();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_previous_orders, container, false);
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