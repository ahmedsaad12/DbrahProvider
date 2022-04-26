package com.apps.dbrah_Provider.uis.activity_sign_up;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
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

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.AddCommercialRecordAdapter;
import com.apps.dbrah_Provider.adapter.CategoryAdapter;
import com.apps.dbrah_Provider.adapter.CountryAdapter;
import com.apps.dbrah_Provider.adapter.SpinnerCategoryAdapter;
import com.apps.dbrah_Provider.databinding.ActivitySignUpBinding;
import com.apps.dbrah_Provider.databinding.DialogCountriesBinding;
import com.apps.dbrah_Provider.model.CategoryModel;
import com.apps.dbrah_Provider.model.CountryModel;
import com.apps.dbrah_Provider.model.SignUpModel;
import com.apps.dbrah_Provider.model.UserModel;
import com.apps.dbrah_Provider.mvvm.ActivitySignUpMvvm;
import com.apps.dbrah_Provider.preferences.Preferences;
import com.apps.dbrah_Provider.share.Common;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;
import com.apps.dbrah_Provider.uis.activity_home.HomeActivity;
import com.apps.dbrah_Provider.uis.activity_login.LoginActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignUpActivity extends BaseActivity {
    private ActivitySignUpBinding binding;
    private String phone_code = "";
    private String phone = "";
    private List<CountryModel> countryModelList = new ArrayList<>();
    private CountryAdapter countriesAdapter;
    private CategoryAdapter categoryAdapter;
    private SpinnerCategoryAdapter spinnerCategoryAdapter;
    private List<CategoryModel> categoryModelList;
    private List<CategoryModel> selectedCategoryList;
    private AddCommercialRecordAdapter recordAdapter;
    private List<String> imagesUriList;
    private AlertDialog dialog;
    private SignUpModel model;
    private ActivitySignUpMvvm activitySignUpMvvm;
    private Preferences preferences;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private String type;
    private Uri uri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        phone_code = intent.getStringExtra("phone_code");
        phone = intent.getStringExtra("phone");
    }

    private void initView() {
        preferences = Preferences.getInstance();
        model = new SignUpModel();
        categoryModelList = new ArrayList<>();
        selectedCategoryList = new ArrayList<>();
        imagesUriList = new ArrayList<>();
        recordAdapter = new AddCommercialRecordAdapter(imagesUriList, this);
        binding.recViewImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewImages.setAdapter(recordAdapter);
        spinnerCategoryAdapter = new SpinnerCategoryAdapter(categoryModelList, this, getLang());
        binding.spinnerCategory.setAdapter(spinnerCategoryAdapter);

        setUpToolbar(binding.toolbar, getString(R.string.sign_up), R.color.white, R.color.black);
        activitySignUpMvvm = ViewModelProviders.of(this).get(ActivitySignUpMvvm.class);
//        if (userModel!=null){
//            phone_code=userModel.getData().getPhone_code();
//            phone=userModel.getData().getPhone();
//
//            model.setPhone_code(phone_code);
//            model.setPhone(phone);
//            model.setStore_name(userModel.getData().getName());
//
//            if (userModel.getData().getEmail()!=null){
//                model.setEmail(userModel.getData().getEmail());
//            }
//            if (userModel.getData().getImage()!=null){
//                String url =  userModel.getData().getImage();
//                Picasso.get().load(Uri.parse(url)).into(binding.image);
//                model.setImage(url);
//                binding.icon.setVisibility(View.GONE);
//            }
//        }

        binding.setModel(model);

        activitySignUpMvvm.getOnCategoryDataSuccess().observe(this, categoryModels -> {
            if (spinnerCategoryAdapter != null) {
                categoryModels.add(0, new CategoryModel("اختر القسم", "Choose category"));
                categoryModelList.clear();
                categoryModelList.addAll(categoryModels);
                spinnerCategoryAdapter.updateList(categoryModels);
            }
        });
        activitySignUpMvvm.getCategory();

        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryModel model = categoryModelList.get(i);
                if (model.getId() != null) {
                    if (!isItemInCategoryList(model)) {
                        selectedCategoryList.add(model);
                        categoryAdapter.notifyItemInserted(selectedCategoryList.size() - 1);
                        SignUpActivity.this.model.setCategoryList(selectedCategoryList);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
            navigateToHomeActivity();
        });
//        activitySignUpMvvm.signUp(this,model);

        categoryAdapter = new CategoryAdapter(selectedCategoryList, this, getLang());
        binding.recViewCategory.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false));
        binding.recViewCategory.setAdapter(categoryAdapter);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (selectedReq == READ_REQ) {
                    binding.icon.setVisibility(View.GONE);

                    uri = result.getData().getData();
                    File file = new File(Common.getImagePath(this, uri));
                    if (type.equals("mainImage")) {
                        Picasso.get().load(file).fit().into(binding.image);
                        binding.lLogo.setVisibility(View.GONE);
                        model.setImage(uri.toString());

                    } else if (type.equals("commercialImages")) {
                        binding.iconUpload.setVisibility(View.GONE);
                        binding.llImages.setVisibility(View.VISIBLE);
                        imagesUriList.add(uri.toString());
                        recordAdapter.notifyItemInserted(imagesUriList.size() - 1);
                        model.setCommercial_images(imagesUriList);
//                        Picasso.get().load(file).fit().into(binding.imageRecord);
                    }

                } else if (selectedReq == CAMERA_REQ) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    binding.icon.setVisibility(View.GONE);
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {
                        String path = Common.getImagePath(this, uri);

                        if (path != null) {
                            if (type.equals("mainImage")) {
                                Picasso.get().load(new File(path)).fit().into(binding.image);
                                binding.lLogo.setVisibility(View.GONE);
                                model.setImage(uri.toString());

                            } else if (type.equals("commercialImages")) {
                                imagesUriList.add(uri.toString());
                                recordAdapter.notifyItemInserted(imagesUriList.size() - 1);
                                binding.iconUpload.setVisibility(View.GONE);
                                binding.llImages.setVisibility(View.VISIBLE);
                                model.setCommercial_images(imagesUriList);

                            }

                        } else {
                            if (type.equals("mainImage")) {
                                Picasso.get().load(uri).fit().into(binding.image);
                                binding.lLogo.setVisibility(View.GONE);
                                model.setImage(uri.toString());

                            } else if (type.equals("commercialImages")) {
                                imagesUriList.add(uri.toString());
                                recordAdapter.notifyItemInserted(imagesUriList.size() - 1);
                                model.setCommercial_images(imagesUriList);

                            }

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

        binding.btnSignup.setOnClickListener(view -> {
            if (model.isDataValid(SignUpActivity.this)) {
                activitySignUpMvvm.signUp(SignUpActivity.this, model);
            }
        });

        binding.tvLogin.setPaintFlags(binding.tvLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        binding.iconUpload.setOnClickListener(view -> {
            type = "commercialImages";
            openSheet();
        });
        binding.flImage.setOnClickListener(view -> {
            type = "mainImage";
            openSheet();
        });
        binding.cardAddImage.setOnClickListener(view -> {
            type = "commercialImages";
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

    }

    private void navigateToHomeActivity() {
        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
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

    public void deleteImage(int adapterPosition) {
        if (imagesUriList.size() > 0) {
            imagesUriList.remove(adapterPosition);
            recordAdapter.notifyItemRemoved(adapterPosition);

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
            if (diseaseModel.getId() == model.getId()) {
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
}