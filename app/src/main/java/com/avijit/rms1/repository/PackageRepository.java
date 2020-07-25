package com.avijit.rms1.repository;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.PackageApi;
import com.avijit.rms1.data.remote.model.Package;
import com.avijit.rms1.data.remote.model.PackageGood;
import com.avijit.rms1.data.remote.responses.NetworkResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Avijit Acharjee on 7/24/2020 at 1:46 PM.
 * Email: avijitach@gmail.com.
 */
public class PackageRepository {
    private static final String TAG = "PackageRepository";
    private static PackageApi packageApi;
    private static PackageRepository packageRepository;
    public static PackageRepository getInstance(){
        if(packageRepository==null){
            packageRepository = new PackageRepository();
        }
        return packageRepository;
    }
    private PackageRepository(){
        packageApi= RetrofitService.createService(PackageApi.class);
    }
    public MutableLiveData<NetworkResponse<List<Package>>> getAllPackages(){
        MutableLiveData<NetworkResponse<List<Package>>> result = new MutableLiveData<>();
        NetworkResponse<List<Package>> failed = new NetworkResponse<>();
        failed.setNetworkIsSuccessful(false);
        packageApi.getAllPackages().enqueue(new Callback<NetworkResponse<List<Package>>>() {
            @Override
            public void onResponse(Call<NetworkResponse<List<Package>>> call, Response<NetworkResponse<List<Package>>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(failed);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<List<Package>>> call, Throwable t) {
                result.setValue(failed);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<Package>> create(Package pkg){
        MutableLiveData<NetworkResponse<Package>> result = new MutableLiveData<>();
        NetworkResponse<Package> err = new NetworkResponse<>();
        err.setNetworkIsSuccessful(false);
        packageApi.storePackage(pkg).enqueue(new Callback<NetworkResponse<Package>>() {
            @Override
            public void onResponse(Call<NetworkResponse<Package>> call, Response<NetworkResponse<Package>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(err);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<Package>> call, Throwable t) {
                result.setValue(err);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<Package>> update(String id,Package pkg){
        MutableLiveData<NetworkResponse<Package>> result = new MutableLiveData<>();
        NetworkResponse<Package> err = new NetworkResponse<>();
        err.setNetworkIsSuccessful(false);
        packageApi.update(id,pkg).enqueue(new Callback<NetworkResponse<Package>>() {
            @Override
            public void onResponse(Call<NetworkResponse<Package>> call, Response<NetworkResponse<Package>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(err);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<Package>> call, Throwable t) {
                result.setValue(err);
            }
        });
        return result;
    }
    public MutableLiveData<NetworkResponse<Package>> delete(String id){
        MutableLiveData<NetworkResponse<Package>> result = new MutableLiveData<>();
        NetworkResponse<Package> err = new NetworkResponse<>();
        err.setNetworkIsSuccessful(false);
        packageApi.delete(id).enqueue(new Callback<NetworkResponse<Package>>() {
            @Override
            public void onResponse(Call<NetworkResponse<Package>> call, Response<NetworkResponse<Package>> response) {
                if(response.isSuccessful()){
                    result.setValue(response.body());
                }
                else {
                    result.setValue(err);
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse<Package>> call, Throwable t) {
                result.setValue(err);
            }
        });
        return result;
    }


}
