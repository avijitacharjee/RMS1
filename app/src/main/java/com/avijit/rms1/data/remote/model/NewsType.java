package com.avijit.rms1.data.remote.model;

import java.sql.Timestamp;

/**
 * Created by Avijit Acharjee on 6/5/2020 at 11:12 AM.
 * Email: avijitach@gmail.com.
 */
public class NewsType {
    private String id;
    private String news_types;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNews_types() {
        return news_types;
    }

    public void setNews_types(String news_types) {
        this.news_types = news_types;
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
