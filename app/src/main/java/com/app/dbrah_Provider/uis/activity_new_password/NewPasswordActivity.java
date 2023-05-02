package com.app.dbrah_Provider.uis.activity_new_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.ActivityNewPasswordBinding;
import com.app.dbrah_Provider.model.NewPasswordModel;
import com.app.dbrah_Provider.mvvm.ActivityNewPasswordMvvm;
import com.app.dbrah_Provider.mvvm.ActivityResetPasswordMvvm;
import com.app.dbrah_Provider.mvvm.ActivityReviewsMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

public class NewPasswordActivity extends BaseActivity {
    private ActivityNewPasswordBinding binding;
    private String type;
    private String phone_code;
    private String phone;
    private String email;
    private NewPasswordModel newPasswordModel;
    private ActivityNewPasswordMvvm mvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_password);
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
        mvvm = ViewModelProviders.of(this).get(ActivityNewPasswordMvvm.class);
        mvvm.send.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(NewPasswordActivity.this, getResources().getString(R.string.password_changed), Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        newPasswordModel = new NewPasswordModel();
        newPasswordModel.setPhone(phone);
        newPasswordModel.setPhone_code(phone_code);
        newPasswordModel.setType(type);
        newPasswordModel.setEmail(email);
        binding.setModel(newPasswordModel);
        binding.setLang(getLang());
        binding.setTitle(getString(R.string.reset_password));
        binding.llBack.setOnClickListener(view -> finish());
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPasswordModel.isDataValid(NewPasswordActivity.this)){
                    mvvm.newpass(NewPasswordActivity.this,newPasswordModel);
                }
            }
        });
    }
}