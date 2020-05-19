package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.Relief;

public class ReliefStoreResponse extends BaseModel{
    private Relief data;
    private String confirm_msg;

    public Relief getData() {
        return data;
    }

    public void setData(Relief data) {
        this.data = data;
    }

    public String getConfirm_msg() {
        return confirm_msg;
    }

    public void setConfirm_msg(String confirm_msg) {
        this.confirm_msg = confirm_msg;
    }
}
