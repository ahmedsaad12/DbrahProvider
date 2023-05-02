package com.app.dbrah_Provider.uis.activity_new_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.ActivityNewPasswordBinding;
import com.app.dbrah_Provider.model.NewPasswordModel;
import com.app.dbrah_Provider.mvvm.ActivityResetPasswordMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

public class NewPasswordActivity extends BaseActivity {
    private ActivityNewPasswordBinding binding;
    private String type;
    private String phone_code;
    private String phone;
    private String email;
    private NewPasswordModel newPasswordModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_password);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("phone")) {
            phone_code = intent.getStringExtra("phone_code");
            phone = intent.getStringExtra("phone");
        } else {
            email = intent.getStringExtra("email");
        }
    }


    private void initView() {
        newPasswordModel=new NewPasswordModel();
        binding.setModel(newPasswordModel);
        binding.setLang(getLang());
        binding.setTitle(getString(R.string.reset_password));
        binding.llBack.setOnClickListener(view -> finish());
    }
}