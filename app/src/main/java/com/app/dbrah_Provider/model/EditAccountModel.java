package com.app.dbrah_Provider.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.dbrah_Provider.BR;
import com.app.dbrah_Provider.R;

import java.util.ArrayList;
import java.util.List;

public class EditAccountModel extends BaseObservable {
    private String image;
    private String store_name;
    private String phone_code;
    private String phone;
    private String email;
    private String password;
    private String repeat_password;

    public ObservableField<String> error_store_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_repeat_password = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();

    public boolean isDAtaValid(Context context) {
        if (password.equals(repeat_password) &&
                !store_name.isEmpty() &&
                !email.isEmpty() &&
                !phone.isEmpty()) {

            error_store_name.set(null);
            error_email.set(null);
            error_phone.set(null);
            error_password.set(null);
            error_repeat_password.set(null);
            return true;
        } else {
            if (store_name.isEmpty()) {
                error_store_name.set(context.getString(R.string.field_required));
            }else {
                error_store_name.set(null);

            }
            if (email.trim().isEmpty()) {
                error_email.set(context.getString(R.string.field_required));

            } else {
                error_email.set(null);

            }
            if (phone.trim().isEmpty()) {
                error_phone.set(context.getString(R.string.field_required));

            } else {
                error_phone.set(null);

            }
            return false;
        }
    }

    public EditAccountModel() {
        this.image = "";
        notifyPropertyChanged(BR.image);
        this.store_name = "";
        notifyPropertyChanged(BR.store_name);
        this.email = "";
        notifyPropertyChanged(BR.email);
        this.password = "";
        notifyPropertyChanged(BR.password);
        this.repeat_password = "";
        notifyPropertyChanged(BR.repeat_password);
        this.phone_code = "+20";
        notifyPropertyChanged(BR.phone_code);
        this.phone = "";
        notifyPropertyChanged(BR.phone);

    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;

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
