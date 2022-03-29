package com.apps.dbrah_Provider.uis.activity_preview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.databinding.ActivityPreviewBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;

public class PreviewActivity extends BaseActivity {
    private ActivityPreviewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_preview);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.preview), R.color.white, R.color.black);
    }
}