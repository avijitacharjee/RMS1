package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.CoronaSummary;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoronaSummaryResponse {
    @SerializedName("data")
    @Expose
    private CoronaSummary data;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public CoronaSummary getData() {
        return data;
    }

    public void setData(CoronaSummary data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
