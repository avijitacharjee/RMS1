package com.avijit.rms1.data.remote.responses;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class BaseModel {
    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
