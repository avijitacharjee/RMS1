package com.avijit.rms1.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avijit.rms1.data.remote.model.Package;
import com.avijit.rms1.data.remote.responses.NetworkResponse;
import com.avijit.rms1.repository.PackageRepository;

import java.util.List;

/**
 * Created by Avijit Acharjee on 7/24/2020 at 1:38 PM.
 * Email: avijitach@gmail.com.
 */
public class PackageUiViewModel extends ViewModel {
    private PackageRepository packageRepository;
    private MutableLiveData<NetworkResponse<List<Package>>> allPackage;
    public PackageUiViewModel(){
        packageRepository = PackageRepository.getInstance();
    }
    public MutableLiveData<NetworkResponse<List<Package>>> all(){
        if(allPackage==null){
            allPackage=packageRepository.getAllPackages();
        }
        return allPackage;
    }
    public MutableLiveData<NetworkResponse<Package>> update(String id,Package pkg){
        return packageRepository.update(id,pkg);
    }
    public MutableLiveData<NetworkResponse<Package>> delete(String id){
        return packageRepository.delete(id);
    }
    public MutableLiveData<NetworkResponse<Package>> create(Package pkg){
        return packageRepository.create(pkg);
    }
}
