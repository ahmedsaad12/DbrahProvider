package com.apps.dbrah_Provider.uis.activity_home.home_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.dbrah_Provider.R;

import com.apps.dbrah_Provider.databinding.FragmentHomeBinding;
import com.apps.dbrah_Provider.model.OrderDataModel;
import com.apps.dbrah_Provider.model.StatisticsModel;
import com.apps.dbrah_Provider.mvvm.FragmentHomeMvvm;
import com.apps.dbrah_Provider.uis.activity_base.BaseFragment;
import com.apps.dbrah_Provider.uis.activity_home.HomeActivity;
import com.apps.dbrah_Provider.uis.activity_reviews.ReviewsActivity;

import java.util.ArrayList;


public class FragmentHome extends BaseFragment {
    private static final String TAG = FragmentHome.class.getName();
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private FragmentHomeMvvm fragmentHomeMvvm;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        fragmentHomeMvvm = ViewModelProviders.of(this).get(FragmentHomeMvvm.class);
        binding.setLang(getLang());
        binding.setModel(getUserModel());
        binding.cardReviews.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ReviewsActivity.class);
            startActivity(intent);
        });
        fragmentHomeMvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.progBar.setVisibility(View.VISIBLE);
                    binding.view.setVisibility(View.VISIBLE);
                } else {
                    binding.progBar.setVisibility(View.GONE);
                    binding.view.setVisibility(View.GONE);
                }

            }
        });
        fragmentHomeMvvm.getMutableLiveData().observe(this, new Observer<StatisticsModel>() {
            @Override
            public void onChanged(StatisticsModel statisticsModel) {
                if (statisticsModel != null) {
                    binding.setStatisticModel(statisticsModel);
                }
            }
        });
        fragmentHomeMvvm.getStatistics(getUserModel());
    }


}