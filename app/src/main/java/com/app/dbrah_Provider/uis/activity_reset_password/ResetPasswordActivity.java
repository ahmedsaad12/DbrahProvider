package com.app.dbrah_Provider.uis.activity_reset_password;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.ActivityResetPasswordBinding;
import com.app.dbrah_Provider.model.ForgotPasswordModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivityResetPasswordMvvm;
import com.app.dbrah_Provider.mvvm.ActivityReviewsMvvm;
import com.app.dbrah_Provider.mvvm.ActivityVerificationMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_forget_password.ForgetPasswordActivity;
import com.app.dbrah_Provider.uis.activity_new_password.NewPasswordActivity;

public class ResetPasswordActivity extends BaseActivity {
    private ActivityResetPasswordBinding binding;
    private String type;
    private ActivityResetPasswordMvvm mvvm;
    private String phone_code;
    private String phone;
    private String email;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
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
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });
        mvvm = ViewModelProviders.of(this).get(ActivityResetPasswordMvvm.class);
mvvm.getSucess().observe(this, new Observer<Boolean>() {
    @Override
    public void onChanged(Boolean aBoolean) {
        Intent intent = new Intent(ResetPasswordActivity.this, NewPasswordActivity.class);
        intent.putExtra("type",type);
        if(type.equals("phone")){
            intent.putExtra("phone_code", phone_code);
            intent.putExtra("phone", phone);
        }
        else{
            intent.putExtra("email", email);}
        launcher.launch(intent);
    }

});
        mvvm.getSmsCode().observe(this, code -> {
            binding.edtCode.setText(code);
        });
        if (type.equals("phone")) {
            mvvm.sendSmsCode(getLang(), phone_code,phone, this);
        }
        binding.setLang(getLang());
        binding.setTitle(getString(R.string.reset_password));
        binding.llBack.setOnClickListener(view -> finish());
        binding.btnDone.setOnClickListener(view -> {

            if(type.equals("phone")){
                mvvm.checkValidCode(binding.edtCode.getText().toString(),phone_code,phone,this);
            }
            else{
mvvm.checkCode(email,binding.edtCode.getText().toString(),this);
            }


//            Intent intent = new Intent(ResetPasswordActivity.this, NewPasswordActivity.class);
//            startActivity(intent);
        });
        binding.edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.edtCode.length()==6) {
                    binding.btnDone.setEnabled(true);
                }
                else{
                    binding.btnDone.setEnabled(false);
                }
            }
        });
    }
}