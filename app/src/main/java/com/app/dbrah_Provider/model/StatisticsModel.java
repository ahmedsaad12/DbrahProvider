package com.app.dbrah_Provider.model;

import java.io.Serializable;

public class StatisticsModel implements Serializable {
    private String client_month;
    private String orders;
    private String client_year;
    private String miss_orders;

    public String getClient_month() {
        return client_month;
    }

    public String getOrders() {
        return orders;
    }

    public String getClient_year() {
        return client_year;
    }

    public String getMiss_orders() {
        return miss_orders;
    }
}
