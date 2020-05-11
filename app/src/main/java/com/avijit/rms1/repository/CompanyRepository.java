package com.avijit.rms1.repository;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.CompanyApi;
import com.avijit.rms1.data.remote.responses.CompanyResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyRepository {
    private static CompanyRepository companyRepository;
    public static CompanyRepository getInstance(){
        if(companyRepository==null){
            companyRepository= new CompanyRepository();
        }
        return companyRepository;
    }
    private CompanyApi companyApi;
    public CompanyRepository(){
        companyApi = RetrofitService.createService(CompanyApi.class);
    }
    public MutableLiveData<CompanyResponse> getCompanies(){
        final MutableLiveData<CompanyResponse> companyResponseMutableLiveData = new MutableLiveData<>();
        companyApi.getCompanyList().enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                if(response.isSuccessful()){
                    companyResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {

            }
        });
        return companyResponseMutableLiveData;
    }
}
