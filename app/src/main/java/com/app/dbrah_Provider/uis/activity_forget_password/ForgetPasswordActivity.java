package com.app.dbrah_Provider.uis.activity_forget_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.ActivityForgetPasswordBinding;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_reset_password.ResetPasswordActivity;

public class ForgetPasswordActivity extends BaseActivity {
    private ActivityForgetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());
        binding.setTitle(getString(R.string.forget_password));
        binding.llBack.setOnClickListener(view -> finish());
        binding.btnSend.setOnClickListener(view -> {
            Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

    }
}