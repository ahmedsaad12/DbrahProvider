package com.app.dbrah_Provider.uis.activity_home.order_module;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.MyPagerAdapter;
import com.app.dbrah_Provider.databinding.FragmentHomeBinding;
import com.app.dbrah_Provider.databinding.FragmentOrderBinding;
import com.app.dbrah_Provider.mvvm.GeneralMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseFragment;
import com.app.dbrah_Provider.uis.activity_home.HomeActivity;
import com.app.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentCurrentOrders;
import com.app.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentNewOrders;
import com.app.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentPreviousOrders;

import java.util.ArrayList;
import java.util.List;


public class FragmentOrder extends BaseFragment {
    private HomeActivity activity;
    private FragmentOrderBinding binding;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private MyPagerAdapter pagerAdapter;
    private GeneralMvvm generalMvvm;

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
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        generalMvvm.getOrderpage().observe(activity, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.pager.setCurrentItem(integer);
            }
        });
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles.add(getString(R.string.new_));
        titles.add(getString(R.string.current));
        titles.add(getString(R.string.prev));
        binding.tab.setupWithViewPager(binding.pager);

        fragmentList.add(FragmentNewOrders.newInstance());
        fragmentList.add(FragmentCurrentOrders.newInstance());
        fragmentList.add(FragmentPreviousOrders.newInstance());
        pagerAdapter = new MyPagerAdapter(getChildFragmentManager(), PagerAdapter.POSITION_UNCHANGED, fragmentList, titles);

        binding.pager.setAdapter(pagerAdapter);
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        for (int i = 0; i < binding.tab.getTabCount(); i++) {
            Log.e("i", i + "");
            View view = ((ViewGroup) binding.tab.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(16, 0, 16, 0);
        }
    }


}