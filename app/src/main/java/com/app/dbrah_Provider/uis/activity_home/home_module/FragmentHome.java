package com.app.dbrah_Provider.uis.activity_home.home_module;

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

import com.app.dbrah_Provider.R;

import com.app.dbrah_Provider.databinding.FragmentHomeBinding;
import com.app.dbrah_Provider.model.OrderDataModel;
import com.app.dbrah_Provider.model.StatisticsModel;
import com.app.dbrah_Provider.mvvm.FragmentHomeMvvm;
import com.app.dbrah_Provider.mvvm.GeneralMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseFragment;
import com.app.dbrah_Provider.uis.activity_home.HomeActivity;
import com.app.dbrah_Provider.uis.activity_notification.NotificationActivity;
import com.app.dbrah_Provider.uis.activity_reviews.ReviewsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FragmentHome extends BaseFragment {
    private static final String TAG = FragmentHome.class.getName();
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private FragmentHomeMvvm fragmentHomeMvvm;
    private GeneralMvvm generalMvvm;

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
        Calendar cal = Calendar.getInstance();
       String month=new SimpleDateFormat("MMM").format(cal.getTime());
       binding.setMonth(month);
        fragmentHomeMvvm = ViewModelProviders.of(this).get(FragmentHomeMvvm.class);
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        generalMvvm.getOnStaticRefreshed().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                fragmentHomeMvvm.getStatistics(getUserModel());
            }
        });
        binding.setLang(getLang());
        binding.setModel(getUserModel());
        binding.cardReviews.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ReviewsActivity.class);
            startActivity(intent);
        });
        binding.imgNotification.setOnClickListener(view -> {
            Intent intent = new Intent(activity, NotificationActivity.class);
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