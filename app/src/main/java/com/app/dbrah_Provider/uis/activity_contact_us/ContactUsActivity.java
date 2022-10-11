package com.app.dbrah_Provider.uis.activity_contact_us;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.ActivityContactUsBinding;
import com.app.dbrah_Provider.model.ContactUsModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivityContactUsMvvm;
import com.app.dbrah_Provider.preferences.Preferences;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

public class ContactUsActivity extends BaseActivity {
    private ActivityContactUsBinding binding;
    private ContactUsModel contactUsModel;
    private UserModel userModel;
    private Preferences preferences;
    private ActivityContactUsMvvm mvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        initView();

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        mvvm = ViewModelProviders.of(this).get(ActivityContactUsMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);
        binding.toolbar.llBack.setOnClickListener(view -> finish());

        contactUsModel = new ContactUsModel();
        if (userModel != null) {
            contactUsModel.setName(userModel.getData().getName());
            if(userModel.getData().getEmail()!=null) {
                contactUsModel.setEmail(userModel.getData().getEmail());
            }
        }

        binding.setContactModel(contactUsModel);
        binding.btnSend.setOnClickListener(view -> {
            if (contactUsModel.isDataValid(this)) {
                mvvm.contactUs(this, contactUsModel);
            }
        });
        mvvm.send.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(ContactUsActivity.this, getResources().getString(R.string.suc), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}