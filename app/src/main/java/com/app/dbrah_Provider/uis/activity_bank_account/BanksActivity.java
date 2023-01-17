package com.app.dbrah_Provider.uis.activity_bank_account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.BanksAdapter;
import com.app.dbrah_Provider.databinding.ActivityBanksBinding;
import com.app.dbrah_Provider.model.BankModel;
import com.app.dbrah_Provider.mvvm.ActivityBanksMvvm;
import com.app.dbrah_Provider.uis.activity_addaccount.AddAccountActivity;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.Objects;


public class BanksActivity extends BaseActivity {

    private ActivityBanksBinding binding;
    private BanksAdapter adapter;
    private ActivityBanksMvvm activityBanksMvvm;
    private ActivityResultLauncher<Intent> launcher;
    private BankModel bankmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_banks);
        initView();


    }


    private void initView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                activityBanksMvvm.getBanks(getUserModel(), getLang());
            }
        });
        activityBanksMvvm = ViewModelProviders.of(this).get(ActivityBanksMvvm.class);
        activityBanksMvvm.send.observe(this, aBoolean -> {
            if (aBoolean) {
                Objects.requireNonNull(activityBanksMvvm.getBank().getValue()).remove(bankmodel);
                adapter.updateList(activityBanksMvvm.getBank().getValue());
            }
        });
        activityBanksMvvm.getIsLoading().observe(this, loading -> {
            binding.swipeRefresh.setRefreshing(loading);

        });
        activityBanksMvvm.getBank().observe(this, list -> {
            if (list.size() > 0) {
                adapter.updateList(list);
                binding.cardNoData.setVisibility(View.GONE);
            } else {
                adapter.updateList(new ArrayList<>());
                binding.cardNoData.setVisibility(View.VISIBLE);

            }
        });
        setUpToolbar(binding.toolbar, getString(R.string.banks), R.color.white, R.color.black);
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BanksAdapter(this);
        binding.recView.setAdapter(adapter);
        activityBanksMvvm.getBanks(getUserModel(), getLang());

        binding.swipeRefresh.setOnRefreshListener(() -> activityBanksMvvm.getBanks(getUserModel(), getLang()));


//        EventBus.getDefault().register(this);

    }

    public void update(BankModel bankModel) {
        Intent intent = new Intent(this, AddAccountActivity.class);
        intent.putExtra("data", bankModel);
        launcher.launch(intent);

    }

    public void deleteBank(BankModel bankModel) {
        this.bankmodel = bankModel;
        activityBanksMvvm.deleteankAccount(this, getUserModel(), bankModel.getId());
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