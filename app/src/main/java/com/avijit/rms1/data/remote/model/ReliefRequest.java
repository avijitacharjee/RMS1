package com.avijit.rms1.data.remote.model;

import com.avijit.rms1.data.remote.responses.BaseModel;

public class ReliefRequest extends BaseModel {
    private String user_id;
    private String name;
    private String address;
    private String phone;
    private String earner_person;
    private String family_member;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEarner_person() {
        return earner_person;
    }

    public void setEarner_person(String earner_person) {
        this.earner_person = earner_person;
    }

    public String getFamily_member() {
        return family_member;
    }

    public void setFamily_member(String family_member) {
        this.family_member = family_member;
    }
}
