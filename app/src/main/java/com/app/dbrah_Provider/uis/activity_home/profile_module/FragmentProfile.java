package com.app.dbrah_Provider.uis.activity_home.profile_module;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.model.SettingDataModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.FragmentProfileMvvm;
import com.app.dbrah_Provider.uis.activity_addaccount.AddAccountActivity;
import com.app.dbrah_Provider.uis.activity_app.AppActivity;
import com.app.dbrah_Provider.uis.activity_bank_account.BanksActivity;
import com.app.dbrah_Provider.uis.activity_base.BaseFragment;
import com.app.dbrah_Provider.databinding.FragmentProfileBinding;
import com.app.dbrah_Provider.uis.activity_contact_us.ContactUsActivity;
import com.app.dbrah_Provider.uis.activity_control_products.ControlProductsActivity;
import com.app.dbrah_Provider.uis.activity_edit_account.EditAccountActivity;
import com.app.dbrah_Provider.uis.activity_home.HomeActivity;
import com.app.dbrah_Provider.uis.activity_login.LoginActivity;
import com.app.dbrah_Provider.uis.activity_suggest_new_product.SuggestNewProductActivity;


public class FragmentProfile extends BaseFragment {
    private static final String TAG = FragmentProfile.class.getName();
    private HomeActivity activity;
    private FragmentProfileBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;
    private FragmentProfileMvvm mvvm;
    private UserModel userModel;
    private SettingDataModel.Data setting;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                binding.setModel(getUserModel());
            } else if (req == 2 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                String lang = result.getData().getStringExtra("lang");
                activity.refreshActivity(lang);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        Log.e(TAG, "onViewCreated: ");

    }

    private void initView() {
        userModel = getUserModel();
        mvvm = ViewModelProviders.of(this).get(FragmentProfileMvvm.class);
        mvvm.getOnDataSuccess().observe(activity, model -> {
            setting = model;
        });
        mvvm.getSettings(activity);
        if (userModel != null) {
            binding.setModel(userModel);
        }
        binding.setLang(getLang());
        binding.tvLang.setOnClickListener(view -> {
            if (getLang().equals("en")) {
                activity.refreshActivity("ar");
            } else {
                activity.refreshActivity("en");
            }
        });
        binding.llControlProducts.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ControlProductsActivity.class);
            startActivity(intent);
        });
        binding.llSuggestNewProduct.setOnClickListener(view -> {
            Intent intent = new Intent(activity, SuggestNewProductActivity.class);
            startActivity(intent);
        });
        binding.llContactUs.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ContactUsActivity.class);
            startActivity(intent);
        });
        binding.llEditAccount.setOnClickListener(view -> {
            Intent intent = new Intent(activity, EditAccountActivity.class);
            startActivity(intent);
        });
        binding.llAddAccount.setOnClickListener(view -> {
            Intent intent = new Intent(activity, AddAccountActivity.class);
            startActivity(intent);
        });
        binding.llBankAccounts.setOnClickListener(view -> {
            Intent intent = new Intent(activity, BanksActivity.class);
            startActivity(intent);
        });
        binding.llTerms.setOnClickListener(view -> navigateToAppActivity("terms", setting.getTerms_en()));

        binding.llPrivacy.setOnClickListener(view -> navigateToAppActivity("privacy", setting.getPrivacy_en()));

        binding.llLogOut.setOnClickListener(view -> {
            if (userModel == null) {
                logout();
            } else {
                mvvm.logout(activity, userModel);

            }
        });
        binding.llcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(getUserModel().getData().getProvider_code());
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("text label", getUserModel().getData().getProvider_code());
                    clipboard.setPrimaryClip(clip);

                }
                Toast.makeText(activity, getResources().getString(R.string.copy), Toast.LENGTH_SHORT).show();

            }
        });

        mvvm.logout.observe(this, aBoolean -> {
            if (aBoolean) {
                logout();
            }
        });


    }

    private void navigateToAppActivity(String type, String url) {
        Intent intent = new Intent(activity, AppActivity.class);
        intent.putExtra("data", type);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private void navigateToLoginActivity() {
        req = 1;
        Intent intent = new Intent(activity, LoginActivity.class);
        launcher.launch(intent);


    }

    private void logout() {
        clearUserModel(activity);
        userModel = getUserModel();
        binding.setModel(null);
        binding.icon.setVisibility(View.VISIBLE);
        navigateToLoginActivity();
    }

}