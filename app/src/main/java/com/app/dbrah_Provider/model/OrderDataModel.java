package com.app.dbrah_Provider.model;

import java.io.Serializable;
import java.util.List;

public class OrderDataModel extends StatusResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }
    public class Data implements Serializable{
        private List<OrderModel> news;
        private List<OrderModel> current;
        private List<OrderModel> previous;

        public List<OrderModel> getNews() {
            return news;
        }

        public List<OrderModel> getCurrent() {
            return current;
        }

        public List<OrderModel> getPrevious() {
            return previous;
        }
    }
}
