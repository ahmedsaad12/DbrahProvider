package com.app.dbrah_Provider.uis.activity_edit_account;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.adapter.AddCommercialRecordAdapter;
import com.app.dbrah_Provider.adapter.CategoryAdapter;
import com.app.dbrah_Provider.adapter.CountryAdapter;
import com.app.dbrah_Provider.adapter.EditAccountCategoryAdapter;
import com.app.dbrah_Provider.adapter.SpinnerCategoryAdapter;
import com.app.dbrah_Provider.databinding.ActivityEditAccountBinding;
import com.app.dbrah_Provider.databinding.DialogCountriesBinding;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.model.CountryModel;
import com.app.dbrah_Provider.model.EditAccountModel;
import com.app.dbrah_Provider.model.SignUpModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivitySignUpMvvm;
import com.app.dbrah_Provider.preferences.Preferences;
import com.app.dbrah_Provider.share.Common;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_sign_up.SignUpActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditAccountActivity extends BaseActivity {
    private ActivityEditAccountBinding binding;
    private String phone_code = "";
    private String phone = "";
    private List<CountryModel> countryModelList = new ArrayList<>();
    private CountryAdapter countriesAdapter;
    private AlertDialog dialog;
    private EditAccountModel model;
    private UserModel userModel;
    private ActivitySignUpMvvm activitySignUpMvvm;
    private Preferences preferences;
    private ActivityResultLauncher<Intent> launcher;

    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;
    private SpinnerCategoryAdapter spinnerCategoryAdapter;
    private List<CategoryModel> categoryModelList;
    private List<CategoryModel> selectedCategoryList;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_account);
        initView();
    }

    private void initView() {
        categoryModelList = new ArrayList<>();
        selectedCategoryList = new ArrayList<>();
        preferences = Preferences.getInstance();
        model = new EditAccountModel();
        userModel = getUserModel();
        spinnerCategoryAdapter = new SpinnerCategoryAdapter(categoryModelList, this, getLang());
        binding.spinnerCategory.setAdapter(spinnerCategoryAdapter);
        setUpToolbar(binding.toolbar, getString(R.string.edit_profile), R.color.white, R.color.black);
        activitySignUpMvvm = ViewModelProviders.of(this).get(ActivitySignUpMvvm.class);
        activitySignUpMvvm.getOnCategoryDataSuccess().observe(this, categoryModels -> {
            if (spinnerCategoryAdapter != null) {
                categoryModels.add(0, new CategoryModel("اختر القسم", "Choose category"));
                categoryModelList.clear();
                categoryModelList.addAll(categoryModels);
                spinnerCategoryAdapter.updateList(categoryModels);
            }
        });
        activitySignUpMvvm.getCategory();
        phone_code = userModel.getData().getPhone_code();
        phone = userModel.getData().getPhone();

        model.setPhone_code(phone_code);
        model.setPhone(phone);
        model.setStore_name(userModel.getData().getName());

        if (userModel.getData().getEmail() != null) {
            model.setEmail(userModel.getData().getEmail());
        }
        if (userModel.getData().getImage() != null) {
            String url = userModel.getData().getImage();
            Picasso.get().load(Uri.parse(url)).into(binding.image);
            binding.icon.setVisibility(View.GONE);
        }

        binding.setModel(model);

        activitySignUpMvvm.getCoListMutableLiveData().observe(this, countryModels -> {
            if (countryModels != null && countryModels.size() > 0) {
                countryModelList.clear();
                countryModelList.addAll(countryModels);
            }
        });

        activitySignUpMvvm.setCountry();

        activitySignUpMvvm.userModelMutableLiveData.observe(this, userModel -> {
            setUserModel(userModel);
            setResult(RESULT_OK);
            finish();
        });

        categoryAdapter = new CategoryAdapter(selectedCategoryList, this, getLang());
        binding.recViewCategory.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategory.setAdapter(categoryAdapter);
        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryModel model = categoryModelList.get(i);
                if (model.getId() != null) {
                    if (!isItemInCategoryList(model)) {
                        selectedCategoryList.add(model);
                        categoryAdapter.notifyItemInserted(selectedCategoryList.size() - 1);
                      EditAccountActivity.this.model.setCategoryList(selectedCategoryList);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (selectedReq == READ_REQ) {
                    binding.icon.setVisibility(View.GONE);

                    uri = result.getData().getData();
                    File file = new File(Common.getImagePath(this, uri));
                    Picasso.get().load(file).fit().into(binding.image);
                    binding.lLogo.setVisibility(View.GONE);

                } else if (selectedReq == CAMERA_REQ) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    binding.icon.setVisibility(View.GONE);
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {
                        String path = Common.getImagePath(this, uri);

                        if (path != null) {
                            Picasso.get().load(new File(path)).fit().into(binding.image);
                            binding.lLogo.setVisibility(View.GONE);
                        } else {
                            Picasso.get().load(uri).fit().into(binding.image);
                            binding.lLogo.setVisibility(View.GONE);


                        }
                    }
                }
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
        binding.imFalg.setImageDrawable(getResources().getDrawable(R.drawable.flag_eg));
        model.setPhone_code("+20");

        sortCountries();
        createCountriesDialog();

        binding.btnConfirm.setOnClickListener(view -> {
            if (model.isDAtaValid(this)) {
                activitySignUpMvvm.update(EditAccountActivity.this, model, userModel);
            }
        });

        binding.flImage.setOnClickListener(view -> {
            openSheet();
        });

        binding.flGallery.setOnClickListener(view -> {
            closeSheet();
            checkReadPermission();
        });

        binding.flCamera.setOnClickListener(view -> {
            closeSheet();
            checkCameraPermission();
        });

        binding.arrow.setOnClickListener(view -> dialog.show());
        binding.btnCancel.setOnClickListener(view -> closeSheet());
        updatecategory();
    }

    private void updatecategory() {
        if(userModel!=null){
            for(int i=0;i<userModel.getData().getCategories().size();i++){
                CategoryModel categoryModel=userModel.getData().getCategories().get(i).getCategory();
            try {
                if(categoryModel.getId()!=null) {
                    selectedCategoryList.add(categoryModel);
                }
            }
            catch (Exception e){

            }

            }
            categoryAdapter.notifyDataSetChanged();
            EditAccountActivity.this.model.setCategoryList(selectedCategoryList);

        }
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

    public void openSheet() {
        binding.expandLayout.setExpanded(true, true);
    }

    public void closeSheet() {
        binding.expandLayout.collapse(true);

    }

    public void checkReadPermission() {
        closeSheet();
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    public void checkCameraPermission() {

        closeSheet();

        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }

    private void SelectImage(int req) {
        selectedReq = req;
        Intent intent = new Intent();

        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");

            launcher.launch(intent);

        } else if (req == CAMERA_REQ) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            } catch (SecurityException e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }
    public void deleteSelectedCategory(int adapterPosition) {
        selectedCategoryList.remove(adapterPosition);
        categoryAdapter.notifyItemRemoved(adapterPosition);
        if (selectedCategoryList.size() > 0) {
            int pos = getItemSpinnerPos(selectedCategoryList.get(selectedCategoryList.size() - 1));

            if (pos != -1) {
                binding.spinnerCategory.setSelection(pos);
            } else {
                binding.spinnerCategory.setSelection(0);
            }
        } else {
            binding.spinnerCategory.setSelection(0);

        }
    }

    private boolean isItemInCategoryList(CategoryModel diseaseModel) {
        for (CategoryModel model : selectedCategoryList) {
            Log.e("D;dlldld",diseaseModel.getId()+" "+model.getId());
            if (diseaseModel.getId().equals(model.getId())) {
                return true;
            }
        }
        return false;
    }

    private int getItemSpinnerPos(CategoryModel diseaseModel) {
        int pos = -1;
        for (int index = 0; index < categoryModelList.size(); index++) {
            CategoryModel model = categoryModelList.get(index);
            if (model.getId() == diseaseModel.getId()) {
                pos = index;
                return pos;
            }
        }
        return pos;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }


    public void setItemData(CountryModel countryModel) {
        dialog.dismiss();
        model.setPhone_code(countryModel.getDialCode());
        binding.setModel(model);
        binding.imFalg.setImageResource(countryModel.getFlag());
    }

}