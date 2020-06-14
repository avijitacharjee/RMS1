package com.avijit.rms1.data.remote.model;

/**
 * Created by Avijit Acharjee on 6/8/2020 at 6:36 PM.
 * Email: avijitach@gmail.com.
 */
public class News {

    /**
     * id : 1
     * news_types_id : 1
     * news_subtypes_id : 1
     * news_title : 1
     * news : 3
     * news_image :
     * subnews : Dhaka
     * subnews_image :
     * reporters_id : pending
     * reporters_name : 1
     * reporters_email : avb@gmail.com
     * status : pending
     * date : 20-02-20
     * created_at : 2020-06-08 12:04:57
     * updated_at : 2020-06-08 12:04:57
     */

    private String id;
    private String news_types_id;
    private String news_subtypes_id;
    private String news_title;
    private String news;
    private String news_image;
    private String subnews;
    private String subnews_image;
    private String reporters_id;
    private String reporters_name;
    private String reporters_email;
    private String status;
    private String date;
    private String created_at;
    private String updated_at;
    private String news_types;
    private String news_subtypes;

    public String getNews_types() {
        return news_types;
    }

    public void setNews_types(String news_types) {
        this.news_types = news_types;
    }

    public String getNews_subtypes() {
        return news_subtypes;
    }

    public void setNews_subtypes(String news_subtypes) {
        this.news_subtypes = news_subtypes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNews_types_id() {
        return news_types_id;
    }

    public void setNews_types_id(String news_types_id) {
        this.news_types_id = news_types_id;
    }

    public String getNews_subtypes_id() {
        return news_subtypes_id;
    }

    public void setNews_subtypes_id(String news_subtypes_id) {
        this.news_subtypes_id = news_subtypes_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public String getSubnews() {
        return subnews;
    }

    public void setSubnews(String subnews) {
        this.subnews = subnews;
    }

    public String getSubnews_image() {
        return subnews_image;
    }

    public void setSubnews_image(String subnews_image) {
        this.subnews_image = subnews_image;
    }

    public String getReporters_id() {
        return reporters_id;
    }

    public void setReporters_id(String reporters_id) {
        this.reporters_id = reporters_id;
    }

    public String getReporters_name() {
        return reporters_name;
    }

    public void setReporters_name(String reporters_name) {
        this.reporters_name = reporters_name;
    }

    public String getReporters_email() {
        return reporters_email;
    }

    public void setReporters_email(String reporters_email) {
        this.reporters_email = reporters_email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
