package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.Company;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanyResponse extends BaseModel{


    @SerializedName("companies")
    @Expose
    private List<Company> companies = null;

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
