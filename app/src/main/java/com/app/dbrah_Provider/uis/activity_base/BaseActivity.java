package com.app.dbrah_Provider.uis.activity_base;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.dbrah_Provider.databinding.ToolbarBinding;
import com.app.dbrah_Provider.language.Language;
import com.app.dbrah_Provider.model.ChatUserModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.model.UserSettingsModel;
import com.app.dbrah_Provider.preferences.Preferences;

import io.paperdb.Paper;


public class BaseActivity extends AppCompatActivity {
    public static final String READ_PERM =  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)?
            Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_EXTERNAL_STORAGE ;

    public static final String WRITE_PERM = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String CAM_PERM = Manifest.permission.CAMERA;
    public static final String FINELOCPerm = Manifest.permission.ACCESS_FINE_LOCATION;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {


    }

    protected String getLang() {
        Paper.init(this);
        String lang = Paper.book().read("lang", "ar");
        return lang;
    }

    protected UserModel getUserModel() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserData(this);
    }

    protected void setUserModel(UserModel userModel) {
        Preferences preferences = Preferences.getInstance();
        preferences.createUpdateUserData(this, userModel);
    }


    public void setUserSettings(UserSettingsModel userSettingsModel) {
        Preferences preferences = Preferences.getInstance();
        preferences.create_update_user_settings(this, userSettingsModel);
    }

    public UserSettingsModel getUserSettings() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserSettings(this);
    }


    protected void setUpToolbar(ToolbarBinding binding, String title, int background, int arrowTitleColor) {
        binding.setLang(getLang());
        binding.setTitle(title);
        binding.llBack.setColorFilter(ContextCompat.getColor(this, arrowTitleColor));
        binding.tvTitle.setTextColor(ContextCompat.getColor(this, arrowTitleColor));
        binding.toolbar.setBackgroundResource(background);
        binding.llBack.setOnClickListener(v -> finish());
    }
    public void clearRoomId() {
        Preferences preferences = Preferences.getInstance();
        preferences.clearRoomId(this);
    }

    public void setRoomId(ChatUserModel order_id) {
        Preferences preferences = Preferences.getInstance();
        preferences.create_update_room(this, order_id);
    }


    public void clearUserData() {
        Preferences preferences = Preferences.getInstance();
        preferences.clearUserData(this);
    }


}