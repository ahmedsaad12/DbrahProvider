package com.apps.dbrah_Provider.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.apps.dbrah_Provider.BR;
import com.apps.dbrah_Provider.R;

import java.util.ArrayList;
import java.util.List;


public class SignUpModel extends BaseObservable {
    private String main_image;
    private String store_name;
    private String phone_code;
    private String phone;
    private String email;
    private List<String> categoryList;
    private String vat_num;
    private String password;
    private String repeat_password;
    private String second_image;

    public ObservableField<String> error_store_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_vat_num = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_repeat_password = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();




    public boolean isDataValid(Context context) {
        if (!store_name.trim().isEmpty()
                &&!email.isEmpty()
                &&!phone.isEmpty()
                &&!vat_num.isEmpty()
                &&password.length() >= 6
                &&repeat_password.length() >= 6
                &&password.equals(repeat_password)
        ) {
           error_store_name.set(null);
           error_email.set(null);
           error_phone.set(null);
           error_vat_num.set(null);
           error_password.set(null);
           error_repeat_password.set(null);

            return true;
        } else {

            if (store_name.trim().isEmpty()) {
                error_store_name.set(context.getString(R.string.field_required));

            } else {
                error_store_name.set(null);

            }
            if (email.trim().isEmpty()) {
                error_email.set(context.getString(R.string.field_required));

            } else {
                error_email.set(null);

            }

            if (phone.isEmpty()) {
                error_phone.set(context.getString(R.string.field_required));

            } else {
                error_phone.set(null);

            }
            if (vat_num.isEmpty()){
                error_vat_num.set(context.getString(R.string.field_required));
            }else {
                error_vat_num.set(null);
            }

            if (password.isEmpty()){
                error_password.set(context.getString(R.string.field_required));
            }else {
                error_password.set(null);
            }
            if (repeat_password.isEmpty()){
                error_repeat_password.set(context.getString(R.string.field_required));
            }else {
                error_repeat_password.set(null);
            }
            if (categoryList.size()>0){

            }else {
                Toast.makeText(context,R.string.choose_category, Toast.LENGTH_SHORT).show();
            }

            return false;
        }
    }

    public SignUpModel() {
        this.main_image = "";
        store_name="";
        email="";
        password="";
        repeat_password="";
        vat_num="";
        phone_code = "+20";
        phone = "";
        categoryList = new ArrayList<>();
        this.second_image="";
    }

    @Bindable
    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
        notifyPropertyChanged(BR.main_image);
    }

    @Bindable
    public String getSecond_image() {
        return second_image;
    }

    public void setSecond_image(String second_image) {
        this.second_image = second_image;
        notifyPropertyChanged(BR.second_image);
    }

    @Bindable
    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
        notifyPropertyChanged(BR.store_name);
    }

    @Bindable
    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
        notifyPropertyChanged(BR.phone_code);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
        notifyPropertyChanged(BR.categoryList);
    }

    @Bindable
    public String getVat_num() {
        return vat_num;
    }

    public void setVat_num(String vat_num) {
        this.vat_num = vat_num;
        notifyPropertyChanged(BR.vat_num);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getRepeat_password() {
        return repeat_password;
    }

    public void setRepeat_password(String repeat_password) {
        this.repeat_password = repeat_password;
        notifyPropertyChanged(BR.repeat_password);
    }
}