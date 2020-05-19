package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.UserType;

import java.util.List;

public class UserTypeResponse extends BaseModel {
    private List<UserType> data;
    private String Message;
    private boolean error;


    public List<UserType> getData() {
        return data;
    }

    public void setData(List<UserType> data) {
        this.data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
