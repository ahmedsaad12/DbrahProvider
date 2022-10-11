package com.app.dbrah_Provider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleOrderDataModel extends StatusResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable {
        private OrderModel order;
        private OrderModel.Offers offer;

        public OrderModel getOrder() {
            return order;
        }
        public OrderModel.Offers getOffer() {
            return offer;
        }

    }
}
