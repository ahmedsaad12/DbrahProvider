package com.apps.dbrah_Provider.uis.activity_new_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.ActivityNewPasswordBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;

public class NewPasswordActivity extends BaseActivity {
    private ActivityNewPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_password);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());
        setUpToolbar(binding.toolbar, getString(R.string.reset_password), R.color.white, R.color.black);
    }
}