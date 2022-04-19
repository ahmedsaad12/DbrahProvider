package com.apps.dbrah_Provider.model;

import java.io.Serializable;

public class AddOFFerDataModel implements Serializable {
    private String provider_id;
    private String order_id;
    private String delivery_date_time;
    private String total_price;
    private String note;
    private String offer_details;

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDelivery_date_time() {
        return delivery_date_time;
    }

    public void setDelivery_date_time(String delivery_date_time) {
        this.delivery_date_time = delivery_date_time;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOffer_details() {
        return offer_details;
    }

    public void setOffer_details(String offer_details) {
        this.offer_details = offer_details;
    }
}
