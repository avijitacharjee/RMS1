package com.avijit.rms1.data.remote.responses;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class NetworkUtils {

    @Expose(serialize = false, deserialize = false)
    private String networkMessage ;

    @Expose(serialize = false, deserialize = false)
    private String networkError ;

    @Expose(serialize = false, deserialize = false)
    private boolean networkIsSuccessful;



    public String getNetworkMessage() {
        return networkMessage;
    }

    public void setNetworkMessage(String networkMessage) {
        this.networkMessage = networkMessage;
    }

    public String getNetworkError() {
        return networkError;
    }

    public void setNetworkError(String networkError) {
        this.networkError = networkError;
    }

    public boolean isNetworkIsSuccessful() {
        return networkIsSuccessful;
    }

    public void setNetworkIsSuccessful(boolean networkIsSuccessful) {
        this.networkIsSuccessful = networkIsSuccessful;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
