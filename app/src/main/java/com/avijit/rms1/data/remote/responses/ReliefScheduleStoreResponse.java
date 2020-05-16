package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.ReliefSchedule;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ReliefScheduleStoreResponse extends BaseModel {

    @SerializedName("data")
    @Expose
    private ReliefSchedule data;
    @SerializedName("message")
    @Expose
    private String message;

    public ReliefSchedule getData() {
        return data;
    }

    public void setData(ReliefSchedule data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}