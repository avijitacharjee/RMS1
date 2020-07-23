package com.avijit.rms1.data.remote.model;

import com.avijit.rms1.data.remote.responses.BaseModel;

public class Good extends BaseModel {

    /**
     * name : Rice
     * unit : 1
     * created_by : 1
     * updated_at : 2020-07-23T13:58:54.000000Z
     * created_at : 2020-07-23T13:58:54.000000Z
     * id : 1
     */

    private String name;
    private String unit;
    private String created_by;
    private String updated_at;
    private String created_at;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
