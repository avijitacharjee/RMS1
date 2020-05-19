package com.avijit.rms1.data.remote.model;

import com.avijit.rms1.data.remote.responses.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Relief extends BaseModel {
    private String id;
    private String division_id;
    private String district_id;
    private String area_id;
    private String address;
    private String nid;
    private String members_in_family;
    private String earning_members;
    private String lat;
    @SerializedName("long")
    @Expose
    private String longitude;
    private String image;
    private String contact_no;
    private String date_given;
    private String given_by;
    private String given_to;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getMembers_in_family() {
        return members_in_family;
    }

    public void setMembers_in_family(String members_in_family) {
        this.members_in_family = members_in_family;
    }

    public String getEarning_members() {
        return earning_members;
    }

    public void setEarning_members(String earning_members) {
        this.earning_members = earning_members;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getDate_given() {
        return date_given;
    }

    public void setDate_given(String date_given) {
        this.date_given = date_given;
    }

    public String getGiven_by() {
        return given_by;
    }

    public void setGiven_by(String given_by) {
        this.given_by = given_by;
    }

    public String getGiven_to() {
        return given_to;
    }

    public void setGiven_to(String given_to) {
        this.given_to = given_to;
    }
}
