package com.avijit.rms1.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.CompanyApi;
import com.avijit.rms1.data.remote.model.CompanyUser;
import com.avijit.rms1.data.remote.responses.CompanyResponse;
import com.avijit.rms1.data.remote.responses.CompanyStoreResponse;
import com.avijit.rms1.data.remote.responses.CompanyUserStoreResponse;
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
    public MutableLiveData<CompanyStoreResponse> addCompany(String companyName){
        final MutableLiveData<CompanyStoreResponse> companyStoreResponseMutableLiveData = new MutableLiveData<>();
        companyApi.storeCompany(companyName).enqueue(new Callback<CompanyStoreResponse>() {
            @Override
            public void onResponse(Call<CompanyStoreResponse> call, Response<CompanyStoreResponse> response) {
                Log.d(TAG, "onResponse: "+ new Gson().toJson(response));
                if(response.isSuccessful()){
                    companyStoreResponseMutableLiveData.setValue(response.body());
                }
                else {
                    companyStoreResponseMutableLiveData.setValue(new CompanyStoreResponse.Builder<CompanyStoreResponse>(CompanyStoreResponse.class).setNetworkIsSuccessful(false));
                }
            }

            @Override
            public void onFailure(Call<CompanyStoreResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                companyStoreResponseMutableLiveData.setValue(new CompanyStoreResponse.Builder<CompanyStoreResponse>(CompanyStoreResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return companyStoreResponseMutableLiveData;
    }
    public MutableLiveData<CompanyUserStoreResponse> addUserToCompany(CompanyUser user){
        final MutableLiveData<CompanyUserStoreResponse> companyUserStoreResponseMutableLiveData = new MutableLiveData<>();
        companyApi.storeUser(user).enqueue(new Callback<CompanyUserStoreResponse>() {
            @Override
            public void onResponse(Call<CompanyUserStoreResponse> call, Response<CompanyUserStoreResponse> response) {
                if(response.isSuccessful()){
                    companyUserStoreResponseMutableLiveData.setValue(response.body());
                }
                Log.d(TAG, "onResponse: "+new Gson().toJson(response));
            }

            @Override
            public void onFailure(Call<CompanyUserStoreResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
            }
        });
        return companyUserStoreResponseMutableLiveData;
    }
}
