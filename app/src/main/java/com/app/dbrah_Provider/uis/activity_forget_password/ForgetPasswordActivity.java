package com.app.dbrah_Provider.uis.activity_forget_password;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.CountryAdapter;
import com.app.dbrah_Provider.databinding.ActivityForgetPasswordBinding;
import com.app.dbrah_Provider.databinding.DialogCountriesBinding;
import com.app.dbrah_Provider.model.CountryModel;
import com.app.dbrah_Provider.model.ForgotPasswordModel;
import com.app.dbrah_Provider.model.LoginModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivityForgotPasswordMvvm;
import com.app.dbrah_Provider.mvvm.ActivityLoginMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_home.HomeActivity;
import com.app.dbrah_Provider.uis.activity_login.LoginActivity;
import com.app.dbrah_Provider.uis.activity_reset_password.ResetPasswordActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ForgetPasswordActivity extends BaseActivity {
    private ActivityForgetPasswordBinding binding;
    private String phone_code = "";
    private String phone = "";
    private ForgotPasswordModel model;
    private UserModel userModel;
    private ActivityResultLauncher<Intent> launcher;
    private List<CountryModel> countryModelList = new ArrayList<>();
    private CountryAdapter countriesAdapter;

    private ActivityForgotPasswordMvvm activityForgotPasswordMvvm;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        initView();
    }

    private void initView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });
        activityForgotPasswordMvvm = ViewModelProviders.of(this).get(ActivityForgotPasswordMvvm.class);

        userModel = new UserModel();
        model = new ForgotPasswordModel();
        activityForgotPasswordMvvm.send.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                intent.putExtra("type", model.getType());
                if(model.getType().equals("phone")){
                    intent.putExtra("phone_code", model.getPhone_code());
                    intent.putExtra("phone", model.getPhone());
                }
                else{
                intent.putExtra("email", model.getEmail());}
                launcher.launch(intent);
            }
        });
        binding.setModel(model);
        binding.tvWithPhone.setPaintFlags(binding.tvWithPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvWithEmail.setPaintFlags(binding.tvWithEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.setLang(getLang());
        activityForgotPasswordMvvm.getCoListMutableLiveData().observe(this, countryModels -> {
            if (countryModels != null && countryModels.size() > 0) {
                countryModelList.clear();
                countryModelList.addAll(countryModels);
            }
        });
        activityForgotPasswordMvvm.setCountry();
        binding.setLang(getLang());
        binding.setTitle(getString(R.string.forget_password));
        binding.llBack.setOnClickListener(view -> finish());
        binding.btnSend.setOnClickListener(view -> {
            if (model.isDataValid(this)) {
                activityForgotPasswordMvvm.checkuser(this,model);
            }
        });
        binding.tvWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llEmail.setVisibility(View.VISIBLE);
                binding.llPhone.setVisibility(View.GONE);
                model.setType("email");
            }
        });
        binding.tvWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llEmail.setVisibility(View.GONE);
                binding.llPhone.setVisibility(View.VISIBLE);
                model.setType("phone");
            }
        });
        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtPhone.setText("");
                }
            }
        });

        binding.consPhone.setVisibility(View.VISIBLE);
        binding.imFalg.setImageDrawable(getResources().getDrawable(R.drawable.flag_eg));
        model.setPhone_code("+20");
        model.setCode("EG");
        model.setType("phone");
        binding.llEmail.setVisibility(View.GONE);
        binding.arrow.setOnClickListener(view -> dialog.show());
        sortCountries();
        createCountriesDialog();

    }


    private void createCountriesDialog() {

        dialog = new AlertDialog.Builder(this)
                .create();
        countriesAdapter = new CountryAdapter(this);
        countriesAdapter.updateList(countryModelList);
        DialogCountriesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_countries, null, false);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(countriesAdapter);

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
    }

    private void sortCountries() {
        Collections.sort(countryModelList, (country1, country2) -> {
            return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());
        });
    }


    public void setItemData(CountryModel countryModel) {
        dialog.dismiss();
        model.setPhone_code(countryModel.getDialCode());
        model.setCode(countryModel.getCode());
        binding.setModel(model);
        binding.imFalg.setImageResource(countryModel.getFlag());
    }

}