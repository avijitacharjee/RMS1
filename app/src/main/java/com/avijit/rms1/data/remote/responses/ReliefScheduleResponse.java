package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.ReliefSchedule;
import com.avijit.rms1.data.remote.model.ReliefScheduleResponseData;

import java.util.List;

public class ReliefScheduleResponse extends BaseModel{
    private List<ReliefScheduleResponseData> data;
    private String message;

    public List<ReliefScheduleResponseData> getData() {
        return data;
    }

    public void setData(List<ReliefScheduleResponseData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
