package com.app.dbrah_Provider.uis.activity_sign_up;

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
import android.text.Html;
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
import com.app.dbrah_Provider.adapter.SpinnerCategoryAdapter;
import com.app.dbrah_Provider.adapter.SpinnerNationalityAdapter;
import com.app.dbrah_Provider.adapter.SpinnerTownAdapter;
import com.app.dbrah_Provider.databinding.ActivitySignUpBinding;
import com.app.dbrah_Provider.databinding.DialogCountriesBinding;
import com.app.dbrah_Provider.databinding.DialogInformationBinding;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.model.CountryModel;
import com.app.dbrah_Provider.model.NationalitiesModel;
import com.app.dbrah_Provider.model.SelectedLocation;
import com.app.dbrah_Provider.model.SettingDataModel;
import com.app.dbrah_Provider.model.SignUpModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivitySignUpMvvm;
import com.app.dbrah_Provider.preferences.Preferences;
import com.app.dbrah_Provider.share.Common;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;
import com.app.dbrah_Provider.uis.activity_home.HomeActivity;
import com.app.dbrah_Provider.uis.activity_login.LoginActivity;
import com.app.dbrah_Provider.uis.activity_map.MapActivity;
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
    private SelectedLocation selectedLocation;
    private ActivitySignUpMvvm activitySignUpMvvm;
    private Preferences preferences;
    private ActivityResultLauncher<Intent> launcher;
//    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private String type;
    private Uri uri = null;
    private SpinnerNationalityAdapter spinnerNationalityAdapter;
    private SpinnerTownAdapter spinnerTownAdapter;
    private List<NationalitiesModel.Data.Town> townModelList;
    private SettingDataModel.Data setting;

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
        townModelList = new ArrayList<>();
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
        activitySignUpMvvm.getOnDataSuccess().observe(this, model -> {
            setting = model;
            if (setting != null) {
                binding.imageInfo.setVisibility(View.VISIBLE);
            }
        });

        binding.setModel(model);

        activitySignUpMvvm.getOnNationalitiesSuccess().observe(this, nationalities -> {
            if (spinnerNationalityAdapter != null) {
                nationalities.add(0, new NationalitiesModel.Data("اختر الجنسية", "Choose nationality"));
                spinnerNationalityAdapter.updateList(nationalities);
                townModelList.add(new NationalitiesModel.Data.Town("اختر المدينة", "Choose town"));
                spinnerTownAdapter.updateList(townModelList);

            }

        });

        activitySignUpMvvm.getNationalities();

        spinnerNationalityAdapter = new SpinnerNationalityAdapter(this, getLang());
        binding.spinnerNationality.setAdapter(spinnerNationalityAdapter);
        binding.spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    townModelList.clear();
                    townModelList.add(new NationalitiesModel.Data.Town("اختر المدينة", "Choose town"));
                    model.setNationality_id(0);
                    spinnerTownAdapter.updateList(townModelList);

                } else {
                    model.setNationality_id(Integer.parseInt(activitySignUpMvvm.getOnNationalitiesSuccess().getValue().get(i).getId()));
                    townModelList.clear();
                    townModelList.add(new NationalitiesModel.Data.Town("اختر المدينة", "Choose town"));
                    townModelList.addAll(activitySignUpMvvm.getOnNationalitiesSuccess().getValue().get(i).getTowns());
                    spinnerTownAdapter.updateList(townModelList);
                }
                binding.setModel(model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMapActivity();
            }
        });
        spinnerTownAdapter = new SpinnerTownAdapter(this, getLang());
        binding.spinnerTown.setAdapter(spinnerTownAdapter);
        binding.spinnerTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    model.setTown_id(0);
                } else {
                    model.setTown_id(Integer.parseInt(townModelList.get(i).getId()));
                }
                binding.setModel(model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        binding.imageInfo.setOnClickListener(v -> {
            openSheetInfo();

        });
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
                } else if (selectedReq == 100 && result.getData() != null) {
                    if (result.getData().hasExtra("location")) {
                        selectedLocation = (SelectedLocation) result.getData().getSerializableExtra("location");
                        model.setLatitude(String.valueOf(selectedLocation.getLat()));
                        model.setLongitude(String.valueOf(selectedLocation.getLng()));
                        model.setAddress(selectedLocation.getAddress());
                        binding.setModel(model);

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
        model.setCode("EG");
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
        activitySignUpMvvm.getSettings(this);

    }

    private void navigateToMapActivity() {
        selectedReq = 100;
        Intent intent = new Intent(this, MapActivity.class);
        launcher.launch(intent);
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
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

        if (ContextCompat.checkSelfPermission(this, write_permission)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission)
                == PackageManager.PERMISSION_GRANTED
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
                e.printStackTrace();
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
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
        model.setCode(countryModel.getCode());
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

    private void openSheetInfo() {
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg2);
        DialogInformationBinding informationBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_information, null, false);
        dialog.setView(informationBinding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        informationBinding.tvDetails.setText(Html.fromHtml(setting.getProvider_info()));
        informationBinding.tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

}