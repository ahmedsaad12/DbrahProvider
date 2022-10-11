package com.app.dbrah_Provider.uis.activity_reset_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.ActivityResetPasswordBinding;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_new_password.NewPasswordActivity;

public class ResetPasswordActivity extends BaseActivity {
    private ActivityResetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_reset_password);
        initView();
    }

    private void initView() {
        binding.setLang(getLang());
        binding.setTitle(getString(R.string.reset_password));
        binding.llBack.setOnClickListener(view -> finish());
        binding.btnDone.setOnClickListener(view -> {
            Intent intent=new Intent(ResetPasswordActivity.this, NewPasswordActivity.class);
            startActivity(intent);
        });
    }
}