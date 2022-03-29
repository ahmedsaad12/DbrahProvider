package com.apps.dbrah_Provider.uis.activity_offer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.ActivityOfferBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;

public class OfferActivity extends BaseActivity {
    private ActivityOfferBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_offer);
        initView();
    }

    private void initView() {

        setUpToolbar(binding.toolbar, getString(R.string.offer), R.color.white, R.color.black);
    }
}