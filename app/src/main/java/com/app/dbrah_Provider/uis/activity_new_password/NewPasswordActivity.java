package com.app.dbrah_Provider.uis.activity_new_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.ActivityNewPasswordBinding;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

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
        binding.setTitle(getString(R.string.reset_password));
        binding.llBack.setOnClickListener(view -> finish());
    }
}