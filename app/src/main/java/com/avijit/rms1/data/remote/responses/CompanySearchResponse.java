package com.avijit.rms1.data.remote.responses;

import com.avijit.rms1.data.remote.model.CompanySuggestion1;
import com.avijit.rms1.data.remote.model.CompanySuggestion2;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanySearchResponse extends BaseModel{


    @SerializedName("companySuggestion1")
    @Expose
    private List<CompanySuggestion1> companySuggestion1 = null;
    @SerializedName("companySuggestion2")
    @Expose
    private List<CompanySuggestion2> companySuggestion2 = null;
    @SerializedName("string_1")
    @Expose
    private String string1;
    @SerializedName("string_2")
    @Expose
    private String string2;

    public List<CompanySuggestion1> getCompanySuggestion1() {
        return companySuggestion1;
    }

    public void setCompanySuggestion1(List<CompanySuggestion1> companySuggestion1) {
        this.companySuggestion1 = companySuggestion1;
    }

    public List<CompanySuggestion2> getCompanySuggestion2() {
        return companySuggestion2;
    }

    public void setCompanySuggestion2(List<CompanySuggestion2> companySuggestion2) {
        this.companySuggestion2 = companySuggestion2;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }
}
