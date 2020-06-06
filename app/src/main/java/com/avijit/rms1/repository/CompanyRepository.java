package com.avijit.rms1.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.CompanyApi;
import com.avijit.rms1.data.remote.model.Company;
import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.responses.CompanyResponse;
import com.avijit.rms1.data.remote.responses.CompanyStoreResponse;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyRepository {
    public final String TAG = "dbg:";
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
                else {
                    companyResponseMutableLiveData.setValue(new CompanyResponse.Builder<CompanyResponse>(CompanyResponse.class).setNetworkIsSuccessful(false));
                }
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                companyResponseMutableLiveData.setValue(new CompanyResponse.Builder<CompanyResponse>(CompanyResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return companyResponseMutableLiveData;
    }
    public MutableLiveData<NetworkResponse<Company>> addCompany(String companyName,String email){
        final MutableLiveData<NetworkResponse<Company>> companyStoreResponseMutableLiveData = new MutableLiveData<>();
        companyApi.storeCompany(companyName,email).enqueue(new Callback<NetworkResponse<Company>>() {
            @Override
            public void onResponse(Call<NetworkResponse<Company>> call, Response<NetworkResponse<Company>> response) {
                Log.d(TAG, "onResponse: "+ new Gson().toJson(response));
                if(response.isSuccessful()){
                    companyStoreResponseMutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<NetworkResponse<Company>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                NetworkResponse<Company> c = new NetworkResponse<>();
                c.setNetworkIsSuccessful(false);
                companyStoreResponseMutableLiveData.setValue(c);
            }
        });
        return companyStoreResponseMutableLiveData;
    }
    public MutableLiveData<NetworkResponse<CompanyUser>> addUserToCompany(String email,String company_id,String role){
        final MutableLiveData<NetworkResponse<CompanyUser>> companyUserStoreResponseMutableLiveData = new MutableLiveData<>();
        companyApi.storeUser(email,company_id,role).enqueue(new Callback<NetworkResponse<CompanyUser>>() {
            @Override
            public void onResponse(Call<NetworkResponse<CompanyUser>> call, Response<NetworkResponse<CompanyUser>> response) {
                if(response.isSuccessful()){
                    companyUserStoreResponseMutableLiveData.setValue(response.body());
                }
                Log.d(TAG, "onResponse: "+new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<NetworkResponse<CompanyUser>> call, Throwable t) {
                companyUserStoreResponseMutableLiveData.setValue(new NetworkResponse<>());
                Log.d(TAG, "onFailure: "+t);
            }
        });
        return companyUserStoreResponseMutableLiveData;
    }
}
