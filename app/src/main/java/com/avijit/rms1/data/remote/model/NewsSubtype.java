package com.avijit.rms1.data.remote.model;

import java.sql.Timestamp;

/**
 * Created by Avijit Acharjee on 6/5/2020 at 11:33 AM.
 * Email: avijitach@gmail.com.
 */
public class NewsSubtype {
    private String id;
    private String news_subtypes;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNews_subtypes() {
        return news_subtypes;
    }

    public void setNews_subtypes(String news_subtypes) {
        this.news_subtypes = news_subtypes;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
