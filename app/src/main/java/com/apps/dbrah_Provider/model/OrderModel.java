package com.apps.dbrah_Provider.model;

import java.io.Serializable;

public class OrderModel implements Serializable {
    private String id;
    private String user_id;
    private String address_id;
    private String category_id;
    private String provider_id;
    private String note;
    private String pin;
    private String status;
    private String total_price;
    private String delivered_time;
    private String created_at;
    private String updated_at;
    private String offer_status_code;
    private String offer_status;
    private String is_pin;
    private UserModel.Data user;
    private AddressModel address;
    private String day;
    private String time;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getNote() {
        return note;
    }

    public String getPin() {
        return pin;
    }

    public String getStatus() {
        return status;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getDelivered_time() {
        return delivered_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getOffer_status_code() {
        return offer_status_code;
    }

    public String getOffer_status() {
        return offer_status;
    }

    public String getIs_pin() {
        return is_pin;
    }

    public UserModel.Data getUser() {
        return user;
    }

    public AddressModel getAddress() {
        return address;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
