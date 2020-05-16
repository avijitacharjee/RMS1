package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.User;

import java.util.StringTokenizer;

public class UserStoreResponse extends BaseModel {
    private User data;
    private String message;
    private String error;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
