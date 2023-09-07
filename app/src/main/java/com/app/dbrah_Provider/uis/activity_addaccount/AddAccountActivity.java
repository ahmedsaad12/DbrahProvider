package com.app.dbrah_Provider.uis.activity_addaccount;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.ActivityAddBankAccountBinding;
import com.app.dbrah_Provider.model.AddBankAccountModel;
import com.app.dbrah_Provider.model.BankModel;
import com.app.dbrah_Provider.model.ContactUsModel;
import com.app.dbrah_Provider.model.OrderModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivityAddBankAccountMvvm;
import com.app.dbrah_Provider.mvvm.ActivityContactUsMvvm;
import com.app.dbrah_Provider.preferences.Preferences;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

public class AddAccountActivity extends BaseActivity {
    private ActivityAddBankAccountBinding binding;
    private AddBankAccountModel addBankAccountModel;
    private UserModel userModel;
    private Preferences preferences;
    private ActivityAddBankAccountMvvm mvvm;
    private BankModel bankModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_bank_account);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();

        if (intent.getSerializableExtra("data") != null) {
            bankModel = (BankModel) intent.getSerializableExtra("data");
        }
    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        mvvm = ViewModelProviders.of(this).get(ActivityAddBankAccountMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.add_bank_account), R.color.white, R.color.black);
        binding.toolbar.llBack.setOnClickListener(view -> finish());

        addBankAccountModel = new AddBankAccountModel();
        if (bankModel != null) {
            setUpToolbar(binding.toolbar, getString(R.string.edit_bank_account), R.color.white, R.color.black);

            addBankAccountModel.setName(bankModel.getAccount_holder_name());
            addBankAccountModel.setIban(bankModel.getIban());
            addBankAccountModel.setBankname(bankModel.getAccount_name());

        } else if (userModel != null) {
            addBankAccountModel.setName(userModel.getData().getName());

        }

        binding.setContactModel(addBankAccountModel);
        binding.btnSend.setOnClickListener(view -> {
            if (addBankAccountModel.isDataValid(this)) {
                if (bankModel == null) {
                    mvvm.addBankAccount(this, addBankAccountModel, getUserModel());
                } else {
                    mvvm.editdBankAccount(this, addBankAccountModel, getUserModel(), bankModel.getId());

                }
            }
        });
        mvvm.send.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(AddAccountActivity.this, getResources().getString(R.string.suc), Toast.LENGTH_LONG).show();
                if(bankModel!=null){
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
    }


}