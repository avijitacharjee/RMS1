package com.avijit.rms1.data.remote.model;

import com.avijit.rms1.data.remote.responses.BaseModel;

public class User extends BaseModel {
    private String name;
    private String email;
    private String phone;
    private String nid;
    private String updated_at;
    private String created_at;
    private String id;
    private String password;

    public User(String name, String email, String phone, String nid, String updated_at, String created_at, String id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nid = nid;

        this.updated_at = updated_at;
        this.created_at = created_at;
        this.id = id;
    }
    public User(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
