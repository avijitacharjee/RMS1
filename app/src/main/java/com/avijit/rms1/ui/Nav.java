package com.avijit.rms1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;

import com.avijit.rms1.R;
import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.CompanyApi;
import com.avijit.rms1.data.remote.api.CoronaSummaryApi;
import com.avijit.rms1.data.remote.responses.CompanyResponse;
import com.avijit.rms1.data.remote.responses.CompanySearchResponse;
import com.avijit.rms1.data.remote.responses.CompanyStoreResponse;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;
import com.avijit.rms1.repository.CompanyRepository;
import com.avijit.rms1.repository.CoronaSummaryRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Nav extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        CoronaSummaryApi coronaSummaryApi =RetrofitService.createService(CoronaSummaryApi.class);
        coronaSummaryApi.getCoronaSummary().enqueue(new Callback<CoronaSummaryResponse>() {
            @Override
            public void onResponse(Call<CoronaSummaryResponse> call, Response<CoronaSummaryResponse> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<CoronaSummaryResponse> call, Throwable t) {

            }
        });
        CompanyApi companyApi = RetrofitService.createService(CompanyApi.class);
        companyApi.storeCompany("Abc").enqueue(new Callback<CompanyStoreResponse>() {
            @Override
            public void onResponse(Call<CompanyStoreResponse> call, Response<CompanyStoreResponse> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<CompanyStoreResponse> call, Throwable t) {

            }
        });


    }
}
