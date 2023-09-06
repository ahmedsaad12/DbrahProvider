package com.app.dbrah_Provider.uis.activity_previous_offers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.dbrah_Provider.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_previous_offer);
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        mvvm = ViewModelProviders.of(this).get(ActivityPreviousOffersMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.refused_offers), R.color.white, R.color.black);
        binding.toolbar.llBack.setOnClickListener(view -> finish());


    }


}