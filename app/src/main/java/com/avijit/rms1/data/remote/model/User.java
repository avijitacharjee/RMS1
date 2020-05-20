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
    private String tbl_user_types_id;
    private String email_verified_at;
    private String role;
    private String birth_cirtificate;
    private String address;
    private String image;
    private String approved;
    private String status;
    private String is_delete;
    private String created_by;
    private String updated_by;


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

    public User(String name, String email, String phone, String nid, String updated_at, String created_at, String id, String password, String tbl_user_types_id, String email_verified_at, String role, String birth_cirtificate, String address, String image, String approved, String status, String is_delete, String created_by, String updated_by) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nid = nid;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.id = id;
        this.password = password;
        this.tbl_user_types_id = tbl_user_types_id;
        this.email_verified_at = email_verified_at;
        this.role = role;
        this.birth_cirtificate = birth_cirtificate;
        this.address = address;
        this.image = image;
        this.approved = approved;
        this.status = status;
        this.is_delete = is_delete;
        this.created_by = created_by;
        this.updated_by = updated_by;
    }


    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBirth_cirtificate() {
        return birth_cirtificate;
    }

    public void setBirth_cirtificate(String birth_cirtificate) {
        this.birth_cirtificate = birth_cirtificate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getTbl_user_types_id() {
        return tbl_user_types_id;
    }

    public void setTbl_user_types_id(String tbl_user_types_id) {
        this.tbl_user_types_id = tbl_user_types_id;
    }

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
