package com.apps.dbrah_Provider.uis.activity_reset_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.ActivityResetPasswordBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;
import com.apps.dbrah_Provider.uis.activity_new_password.NewPasswordActivity;

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
        setUpToolbar(binding.toolbar, getString(R.string.reset_password), R.color.white, R.color.black);

        binding.btnDone.setOnClickListener(view -> {
            Intent intent=new Intent(ResetPasswordActivity.this, NewPasswordActivity.class);
            startActivity(intent);
        });
    }
}