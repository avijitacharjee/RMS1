package com.avijit.rms1.data.remote.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyStoreResponse {

    @SerializedName("confirm_msg")
    @Expose
    private String confirmMsg;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("Company_user")
    @Expose
    private String companyUser;

    public String getConfirmMsg() {
        return confirmMsg;
    }

    public void setConfirmMsg(String confirmMsg) {
        this.confirmMsg = confirmMsg;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyUser() {
        return companyUser;
    }

    public void setCompanyUser(String companyUser) {
        this.companyUser = companyUser;
    }
}
