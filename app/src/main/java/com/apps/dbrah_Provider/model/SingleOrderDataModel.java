package com.apps.dbrah_Provider.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleOrderDataModel extends StatusResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable {
        private OrderModel order;

        public OrderModel getOrder() {
            return order;
        }
    }
}
