package com.app.dbrah_Provider.model;

import android.content.Context;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.dbrah_Provider.BR;
import com.app.dbrah_Provider.R;

import java.io.Serializable;

public class LoginModel extends BaseObservable implements Serializable {
    private String phone_code;
    private String phone;
    private String email;
    private String password;
    private String type;
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();

    public LoginModel() {
        phone_code = "+20";
        phone = "";
        email = "";
        password = "";
    }

    public boolean isDataValid(Context context) {
        if ((type.equals("phone") && !phone.isEmpty()&&
                password.length() >= 6)
                || (type.equals("email") && !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.length() >= 6)) {
            error_phone.set(null);
            error_email.set(null);
            error_password.set(null);

            return true;
        } else {
            if (type.equals("phone")) {
                if (phone.isEmpty()) {
                    error_phone.set(context.getString(R.string.field_required));

                } else {
                    error_phone.set(null);

                }
                if (password.isEmpty()) {
                    error_password.set(context.getString(R.string.field_required));

                } else if (password.length() < 6) {
                    error_password.set(context.getString(R.string.pass_short));

                } else {
                    error_password.set(null);

                }
            } else if (type.equals("email")) {
                if (email.isEmpty()) {
                    error_email.set(context.getString(R.string.field_required));
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    error_email.set(context.getString(R.string.inv_email));
                } else {
                    error_email.set(null);

                }
                if (password.isEmpty()) {
                    error_password.set(context.getString(R.string.field_required));

                } else if (password.length() < 6) {
                    error_password.set(context.getString(R.string.pass_short));

                } else {
                    error_password.set(null);

                }
            }


            return false;
        }
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}