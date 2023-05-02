package com.app.dbrah_Provider.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.dbrah_Provider.BR;
import com.app.dbrah_Provider.R;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class NewPasswordModel extends BaseObservable {
    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    private String phone = "";
    private String email = "";
    private String type = "";
    private String phone_code;
    private String password;
    private String repeat_password;

    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_repeat_password = new ObservableField<>();
    private Pattern pattern;


    public boolean isDataValid(Context context) {

        pattern = Pattern.compile(PASSWORD_PATTERN);

        if (
                password.length() >= 6
                        && pattern.matcher(password).matches()
                        && repeat_password.length() >= 6
                        && password.equals(repeat_password)

        ) {

            error_password.set(null);
            error_repeat_password.set(null);


            return true;
        } else {


            if (password.isEmpty()) {
                error_password.set(context.getString(R.string.field_required));
            } else {
                if (!pattern.matcher(password).matches()) {
                    error_password.set(context.getResources().getString(R.string.password_weak));
                    error_password.notifyChange();
                } else {
                    error_password.set(null);
                }
            }
            if (repeat_password.isEmpty()) {
                error_repeat_password.set(context.getString(R.string.field_required));
            } else if (!repeat_password.equals(password)) {
                error_repeat_password.set(context.getString(R.string.repeat_password_must));
            } else {
                error_repeat_password.set(null);
            }


            return false;
        }
    }

    public NewPasswordModel() {


        password = "";
        notifyPropertyChanged(BR.password);
        repeat_password = "";
        notifyPropertyChanged(BR.repeat_password);

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }
}