package com.apps.dbrah_Provider.uis.activity_home.profile_module;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.uis.activity_base.BaseFragment;
import com.apps.dbrah_Provider.databinding.FragmentProfileBinding;
import com.apps.dbrah_Provider.uis.activity_contact_us.ContactUsActivity;
import com.apps.dbrah_Provider.uis.activity_control_products.ControlProductsActivity;
import com.apps.dbrah_Provider.uis.activity_edit_account.EditAccountActivity;
import com.apps.dbrah_Provider.uis.activity_home.HomeActivity;
import com.apps.dbrah_Provider.uis.activity_login.LoginActivity;
import com.apps.dbrah_Provider.uis.activity_sign_up.SignUpActivity;
import com.apps.dbrah_Provider.uis.activity_suggest_new_product.SuggestNewProductActivity;

import java.util.List;


public class FragmentProfile extends BaseFragment {
    private static final String TAG = FragmentProfile.class.getName();
    private HomeActivity activity;
    private FragmentProfileBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;

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
        if (getUserModel() != null) {
            binding.setModel(getUserModel());
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
            Intent intent=new Intent(activity, ControlProductsActivity.class);
            startActivity(intent);
        });
        binding.llSuggestNewProduct.setOnClickListener(view -> {
            Intent intent=new Intent(activity, SuggestNewProductActivity.class);
            startActivity(intent);
        });
        binding.llContactUs.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ContactUsActivity.class);
            startActivity(intent);
        });
        binding.llEditAccount.setOnClickListener(view -> {
            Intent intent=new Intent(activity, EditAccountActivity.class);
            startActivity(intent);
        });
    }

    private void navigateToLoginActivity() {
        req = 1;
        Intent intent = new Intent(activity, LoginActivity.class);
        launcher.launch(intent);

    }


}