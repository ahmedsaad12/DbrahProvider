package com.apps.dbrah_Provider.uis.activity_home.cart_module;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.FragmentCartBinding;
import com.apps.dbrah_Provider.databinding.FragmentHomeBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseFragment;
import com.apps.dbrah_Provider.uis.activity_home.HomeActivity;


public class FragmentCart extends BaseFragment {
    private static final String TAG = FragmentCart.class.getName();
    private HomeActivity activity;
    private FragmentCartBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
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