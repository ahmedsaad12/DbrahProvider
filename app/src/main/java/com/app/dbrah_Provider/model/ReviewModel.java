package com.app.dbrah_Provider.model;

import java.io.Serializable;

public class ReviewModel implements Serializable {
    private String id ;
            private String provider_id;
            private String user_id;
            private String text;
            private String rate;
            private String created_at;
            private String updated_at;
            private UserModel.Data user;

    public String getId() {
        return id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getText() {
        return text;
    }

    public String getRate() {
        return rate;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public UserModel.Data getUser() {
        return user;
    }
}
