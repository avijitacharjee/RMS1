package com.avijit.rms1.data.remote.responses;


import com.avijit.rms1.data.remote.model.Division;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationResponse {

    @SerializedName("locations")
    @Expose
    private List<Division> divisions = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public List<Division> getLocations() {
        return divisions;
    }

    public void setLocations(List<Division> locations) {
        this.divisions = locations;
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
