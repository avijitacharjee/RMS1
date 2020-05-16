package com.avijit.rms1.data.remote.responses;

public class CompanyUserStoreResponse extends BaseModel{
    String confirm_msg;

    public String getConfirm_msg() {
        return confirm_msg;
    }

    public void setConfirm_msg(String confirm_msg) {
        this.confirm_msg = confirm_msg;
    }
}
