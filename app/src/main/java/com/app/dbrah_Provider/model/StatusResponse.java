package com.app.dbrah_Provider.model;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    protected int status;
    protected String msg;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return msg;
    }
}
