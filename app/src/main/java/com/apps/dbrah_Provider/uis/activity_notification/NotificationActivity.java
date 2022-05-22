package com.apps.dbrah_Provider.uis.activity_notification;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.NotificationAdapter;
import com.apps.dbrah_Provider.databinding.ActivityNotificationBinding;
import com.apps.dbrah_Provider.mvvm.ActivityNotificationMvvm;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;

import java.util.ArrayList;


public class NotificationActivity extends BaseActivity {

    private ActivityNotificationBinding binding;
    private NotificationAdapter adapter;
    private ActivityNotificationMvvm activityNotificationMvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        initView();


    }


    private void initView() {
        activityNotificationMvvm = ViewModelProviders.of(this).get(ActivityNotificationMvvm.class);
        activityNotificationMvvm.getIsLoading().observe(this, loading -> {
            binding.swipeRefresh.setRefreshing(loading);

        });
        activityNotificationMvvm.getNotification().observe(this, list -> {
            if (list.size() > 0) {
                adapter.updateList(list);
                binding.cardNoData.setVisibility(View.GONE);
            } else {
                adapter.updateList(new ArrayList<>());
                binding.cardNoData.setVisibility(View.VISIBLE);

            }
        });
        setUpToolbar(binding.toolbar, getString(R.string.notifications), R.color.white, R.color.black);
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(this);
        binding.recView.setAdapter(adapter);
        activityNotificationMvvm.getNotifications(getUserModel(),getLang());

        binding.swipeRefresh.setOnRefreshListener(() -> activityNotificationMvvm.getNotifications(getUserModel(),getLang()));


//        EventBus.getDefault().register(this);

    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onNewNotificationListener(NotModel model) {
//        activityNotificationMvvm.getNotifications(getUserModel());
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//    }
}