package com.apps.dbrah_Provider.model;

import java.io.Serializable;

public class SettingModel extends StatusResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data implements Serializable{
        private String id;
        private String title;
        private String logo;
        private String terms;
        private String privacy;
        private String facebook;
        private String insta;
        private String twitter;
        private String snapchat;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getLogo() {
            return logo;
        }

        public String getTerms() {
            return terms;
        }

        public String getPrivacy() {
            return privacy;
        }

        public String getFacebook() {
            return facebook;
        }

        public String getInsta() {
            return insta;
        }

        public String getTwitter() {
            return twitter;
        }

        public String getSnapchat() {
            return snapchat;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
