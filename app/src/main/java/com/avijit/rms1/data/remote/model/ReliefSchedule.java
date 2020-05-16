package com.avijit.rms1.data.remote.model;

import androidx.annotation.NonNull;

import com.avijit.rms1.data.remote.responses.BaseModel;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class ReliefSchedule extends BaseModel {
    @Expose
    private String id;
    @Expose
    private String user_id;
    @Expose
    private String schedule_date;
    @Expose
    private String division_id;
    @Expose
    private String district_id;
    @Expose
    private String area_id;
    @Expose
    private String address;
    @Expose
    private String company_id;
    @Expose
    private String status;
    @Expose
    private String updatedAt;
    @Expose
    private String createdAt;

    public ReliefSchedule(String user_id, String schedule_date, String division_id, String district_id, String area_id, String address, String company_id) {
        this.user_id = user_id;
        this.schedule_date = schedule_date;
        this.division_id = division_id;
        this.district_id = district_id;
        this.area_id = area_id;
        this.address = address;
        this.company_id = company_id;
    }

    public ReliefSchedule() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getDivision_id() {
        return division_id;
    }

    public void setDivision_id(String division_id) {
        this.division_id = division_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

