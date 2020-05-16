package com.avijit.rms1.data.remote.model;

import com.avijit.rms1.data.remote.responses.BaseModel;

public class ReliefScheduleResponseData extends BaseModel {
    private String name;
    private String schedule_date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }
}
