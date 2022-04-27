package com.apps.dbrah_Provider.model;

import java.io.Serializable;

public class SingleMessageModel extends StatusResponse implements Serializable {
    private MessageModel data;

    public MessageModel getData() {
        return data;
    }
}
