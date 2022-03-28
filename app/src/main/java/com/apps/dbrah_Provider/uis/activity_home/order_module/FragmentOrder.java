package com.apps.dbrah_Provider.uis.activity_home.order_module;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.MyPagerAdapter;
import com.apps.dbrah_Provider.databinding.FragmentHomeBinding;
import com.apps.dbrah_Provider.databinding.FragmentOrderBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseFragment;
import com.apps.dbrah_Provider.uis.activity_home.HomeActivity;
import com.apps.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentCurrentOrders;
import com.apps.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentNewOrders;
import com.apps.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentPreviousOrders;

import java.util.ArrayList;
import java.util.List;


public class FragmentOrder extends BaseFragment {
    private HomeActivity activity;
    private FragmentOrderBinding binding;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private MyPagerAdapter pagerAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {

        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles.add(getString(R.string.new_));
        titles.add(getString(R.string.current));
        titles.add(getString(R.string.prev));
        binding.tab.setupWithViewPager(binding.pager);

    }


}