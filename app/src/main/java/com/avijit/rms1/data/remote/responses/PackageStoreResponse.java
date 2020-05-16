package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.Package;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageStoreResponse extends BaseModel{


    @SerializedName("data")
    @Expose
    private Package data;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public Package getData() {
        return data;
    }

    public void setData(Package data) {
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
