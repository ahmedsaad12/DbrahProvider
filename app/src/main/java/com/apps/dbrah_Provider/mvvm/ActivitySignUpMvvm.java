package com.apps.dbrah_Provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.model.CategoryDataModel;
import com.apps.dbrah_Provider.model.CategoryModel;
import com.apps.dbrah_Provider.model.CountryModel;
import com.apps.dbrah_Provider.model.SignUpModel;
import com.apps.dbrah_Provider.model.UserModel;
import com.apps.dbrah_Provider.remote.Api;
import com.apps.dbrah_Provider.share.Common;
import com.apps.dbrah_Provider.tags.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivitySignUpMvvm extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Boolean> isLoadingCategory;
    public MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CountryModel>> coListMutableLiveData;
    private MutableLiveData<List<CategoryModel>> onCategoryDataSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivitySignUpMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<CountryModel>> getCoListMutableLiveData() {
        if (coListMutableLiveData == null) {
            coListMutableLiveData = new MutableLiveData<>();
        }
        return coListMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoadingCategory() {
        if (isLoadingCategory == null) {
            isLoadingCategory = new MutableLiveData<>();
        }
        return isLoadingCategory;
    }

    public MutableLiveData<List<CategoryModel>> getOnCategoryDataSuccess() {
        if (onCategoryDataSuccess == null) {
            onCategoryDataSuccess = new MutableLiveData<>();
        }
        return onCategoryDataSuccess;
    }

    public void setCountry() {
        CountryModel[] countries = new CountryModel[]{
                new CountryModel("EG", "Egypt", "+20", R.drawable.flag_eg, "EGP"), new CountryModel("SA", "Saudi Arabia", "+966", R.drawable.flag_sa, "SAR")};
        coListMutableLiveData.postValue(new ArrayList<>(Arrays.asList(countries)));
    }

    public void getCategory() {
        getIsLoadingCategory().setValue(true);
        Api.getService(Tags.base_url).getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getIsLoadingCategory().setValue(false);

                                getOnCategoryDataSuccess().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }

    public void signUp(Context context, SignUpModel signUpModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody phone = Common.getRequestBodyText(signUpModel.getPhone());
        RequestBody phone_code = Common.getRequestBodyText(signUpModel.getPhone_code());
        RequestBody email = Common.getRequestBodyText(signUpModel.getEmail());
        RequestBody name = Common.getRequestBodyText(signUpModel.getStore_name());
        RequestBody vat_number = Common.getRequestBodyText(signUpModel.getVat_num());
        RequestBody password = Common.getRequestBodyText(signUpModel.getPassword());
        List<RequestBody> categories = getRequestBodyList(signUpModel.getCategoryList());
        List<MultipartBody.Part> commercial_records = getMultipartBodyList(signUpModel.getCommercial_images(), "images[]", context);

        MultipartBody.Part image = null;
        if (signUpModel.getImage() != null && !signUpModel.getImage().isEmpty()) {
            image = Common.getMultiPartImage(context, Uri.parse(signUpModel.getImage()), "image");
        }

        Api.getService(Tags.base_url).signUp(phone, phone_code, email, name, vat_number, password, categories, commercial_records, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                userModelMutableLiveData.postValue(response.body());
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error", e.toString());
                    }
                });

    }

    public void update(Context context, SignUpModel signUpModel, UserModel userModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody provider_id = Common.getRequestBodyText(userModel.getData().getId());
        RequestBody phone = Common.getRequestBodyText(signUpModel.getPhone());
        RequestBody phone_code = Common.getRequestBodyText(signUpModel.getPhone_code());
        RequestBody email = Common.getRequestBodyText(signUpModel.getEmail());
        RequestBody name = Common.getRequestBodyText(signUpModel.getStore_name());
        RequestBody vat_number = Common.getRequestBodyText(signUpModel.getVat_num());
        RequestBody password = Common.getRequestBodyText(signUpModel.getPassword());
        List<RequestBody> categories = getRequestBodyList(signUpModel.getCategoryList());
        MultipartBody.Part image = null;
        if (signUpModel.getImage() != null && !signUpModel.getImage().isEmpty()) {
            image = Common.getMultiPartImage(context, Uri.parse(signUpModel.getImage()), "image");
        }

        Api.getService(Tags.base_url).update(provider_id, phone, phone_code, email, name, vat_number, password, categories, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                userModelMutableLiveData.postValue(response.body());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error", e.toString());
                    }
                });
    }

    private List<MultipartBody.Part> getMultipartBodyList(List<String> uriList, String name, Context context) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        for (int i = 0; i < uriList.size(); i++) {
            Uri uri = Uri.parse(uriList.get(i));
            MultipartBody.Part part = Common.getMultiPart(context, uri, name);
            partList.add(part);

        }
        return partList;
    }

    private List<RequestBody> getRequestBodyList(List<CategoryModel> categoryList) {
        List<RequestBody> partList = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            RequestBody category = Common.getRequestBodyText(categoryList.get(i).getId());
            partList.add(category);

        }
        return partList;
    }
}
