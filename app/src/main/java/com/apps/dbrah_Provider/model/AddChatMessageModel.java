package com.apps.dbrah_Provider.model;

import java.io.Serializable;

public class AddChatMessageModel implements Serializable {

    private String type;
    private String text = "";
    private String image;
    private String order_id;

    public AddChatMessageModel(String type, String text, String image, String order_id) {
        this.type = type;
        this.text = text;
        this.image = image;
        this.order_id = order_id;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public String getOrder_id() {
        return order_id;
    }
}
