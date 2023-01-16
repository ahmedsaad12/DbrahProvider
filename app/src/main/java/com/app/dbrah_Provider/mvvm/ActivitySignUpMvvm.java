package com.app.dbrah_Provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.model.CategoryDataModel;
import com.app.dbrah_Provider.model.CategoryModel;
import com.app.dbrah_Provider.model.CountryModel;
import com.app.dbrah_Provider.model.EditAccountModel;
import com.app.dbrah_Provider.model.NationalitiesModel;
import com.app.dbrah_Provider.model.SettingDataModel;
import com.app.dbrah_Provider.model.SignUpModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.remote.Api;
import com.app.dbrah_Provider.share.Common;
import com.app.dbrah_Provider.tags.Tags;

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
    private MutableLiveData<List<NationalitiesModel.Data>> onNationalitiesSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<SettingDataModel.Data> onDataSuccess;

    public ActivitySignUpMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<NationalitiesModel.Data>> getOnNationalitiesSuccess() {
        if (onNationalitiesSuccess==null){
            onNationalitiesSuccess=new MutableLiveData<>();
        }
        return onNationalitiesSuccess;
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
    public MutableLiveData<SettingDataModel.Data> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
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

    public void getNationalities(){
        Api.getService(Tags.base_url).getNationalities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<NationalitiesModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<NationalitiesModel> response) {

                        if (response.isSuccessful() && response.body()!=null){
                            if (response.body().getData()!=null && response.body().getStatus()==200){
                                getOnNationalitiesSuccess().setValue(response.body().getData());

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error",e.toString());
                    }
                });
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
        RequestBody nationality_id_part = Common.getRequestBodyText(signUpModel.getNationality_id()+"");
        RequestBody town_id_part = Common.getRequestBodyText(signUpModel.getTown_id()+"");
        RequestBody latitude_part = Common.getRequestBodyText(signUpModel.getLatitude());
        RequestBody longitude_part = Common.getRequestBodyText(signUpModel.getLongitude());
        RequestBody vat_number = Common.getRequestBodyText(signUpModel.getVat_num());
        RequestBody password = Common.getRequestBodyText(signUpModel.getPassword());
        List<RequestBody> category_ids = getRequestBodyList(signUpModel.getCategoryList());
        List<MultipartBody.Part> commercial_records_images = getMultipartBodyList(signUpModel.getCommercial_images(), "commercial_records_images[]", context);
        Log.e("size",commercial_records_images.size()+" ");
        MultipartBody.Part image = null;
        if (signUpModel.getImage() != null && !signUpModel.getImage().isEmpty()) {
            image = Common.getMultiPartImage(context, Uri.parse(signUpModel.getImage()), "image");
        }

        Api.getService(Tags.base_url).signUp(phone, phone_code, email, name, vat_number, password, category_ids,nationality_id_part,town_id_part,latitude_part,longitude_part, commercial_records_images, image)
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
                        Log.e("sttt",response.code()+" "+response.body().getStatus()+" "+response.body().getMessage());
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                userModelMutableLiveData.postValue(response.body());
                            }
                        }else if (response.body().getStatus() == 406) {
                            Toast.makeText(context, R.string.ph_found, Toast.LENGTH_LONG).show();
                        } else if (response.body().getStatus() == 407) {
                            Toast.makeText(context, R.string.em_found, Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getStatus() == 408) {
                            Toast.makeText(context, R.string.vat_found, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error", e.toString());
                    }
                });

    }

    public void update(Context context, EditAccountModel model, UserModel userModel) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody provider_id = Common.getRequestBodyText(userModel.getData().getId());
        RequestBody phone = Common.getRequestBodyText(model.getPhone());
        RequestBody phone_code = Common.getRequestBodyText(model.getPhone_code());
        RequestBody email = Common.getRequestBodyText(model.getEmail());
        RequestBody password = Common.getRequestBodyText(model.getPassword());
        MultipartBody.Part image = null;
        if (model.getImage() != null && !model.getImage().isEmpty()) {
            image = Common.getMultiPartImage(context, Uri.parse(model.getImage()), "image");
        }

        Api.getService(Tags.base_url).update(provider_id, phone, phone_code, email, password, image)
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
    public void getSettings(Context context) {
        Api.getService(Tags.base_url)
                .getSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SettingDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SettingDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getOnDataSuccess().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
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
