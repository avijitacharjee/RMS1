package com.avijit.rms1.data.remote.model;

import com.avijit.rms1.data.remote.responses.BaseModel;

public class ReliefRequest extends BaseModel {
    private String user_id;
    private String name;
    private String address;
    private String phone;
    private String earner_person;
    private String family_members;

    private String request_time;
    private String request_type;
    private String request_for;
    private String family_no;
    private String created_by;
    private String contact_no;
    private String nid;
    private String id;
    private String created_at;
    private String updated_at;
    private String status;
    private String requested_by;
    private String requested_person_contact;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequested_by() {
        return requested_by;
    }

    public void setRequested_by(String requested_by) {
        this.requested_by = requested_by;
    }

    public String getRequested_person_contact() {
        return requested_person_contact;
    }

    public void setRequested_person_contact(String requested_person_contact) {
        this.requested_person_contact = requested_person_contact;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getRequest_for() {
        return request_for;
    }

    public void setRequest_for(String request_for) {
        this.request_for = request_for;
    }

    public String getFamily_no() {
        return family_no;
    }

    public void setFamily_no(String family_no) {
        this.family_no = family_no;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFamily_members() {
        return family_members;
    }

    public void setFamily_members(String family_members) {
        this.family_members = family_members;
    }
}
