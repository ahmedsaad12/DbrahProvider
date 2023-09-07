package com.app.dbrah_Provider.uis.activity_previous_offers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.PreviousOfferAdapter;
import com.app.dbrah_Provider.databinding.ActivityAddBankAccountBinding;
import com.app.dbrah_Provider.databinding.ActivityPreviousOfferBinding;
import com.app.dbrah_Provider.model.AddBankAccountModel;
import com.app.dbrah_Provider.model.BankModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivityPreviousOffersMvvm;
import com.app.dbrah_Provider.preferences.Preferences;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

public class PreviousOffersActivity extends BaseActivity {
    private ActivityPreviousOfferBinding binding;
    private UserModel userModel;
    private Preferences preferences;
    private ActivityPreviousOffersMvvm mvvm;
    private PreviousOfferAdapter previousOfferAdapter;
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_previous_offer);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderID", 0);
    }

    private void initView() {
        preferences = Preferences.getInstance();
        previousOfferAdapter = new PreviousOfferAdapter(this, getLang());
        binding.recViewReviews.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewReviews.setAdapter(previousOfferAdapter);
        userModel = preferences.getUserData(this);
        mvvm = ViewModelProviders.of(this).get(ActivityPreviousOffersMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.refused_offers), R.color.white, R.color.black);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        mvvm.getIsLoading().observe(this,aBoolean -> {
            if(aBoolean){
                binding.progBar.setVisibility(View.VISIBLE);

            }
            else{
                binding.progBar.setVisibility(View.GONE);
            }
        });
        mvvm.getListMutableLiveData().observe(this, data -> {
            if (data != null && data.size() > 0) {
                binding.tvNoData.setVisibility(View.GONE);
                previousOfferAdapter.updateList(data);
            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);
            }
        });
        mvvm.getpreviousoffers(userModel, orderId, getLang());

    }


}