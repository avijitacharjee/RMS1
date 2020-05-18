package com.avijit.rms1.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.local.AppDatabase;
import com.avijit.rms1.data.local.entities.Area;
import com.avijit.rms1.data.local.entities.District;
import com.avijit.rms1.data.local.entities.Division;
import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.LocationApi;
import com.avijit.rms1.data.remote.responses.LocationResponse;
import com.avijit.rms1.ui.AddSchedule;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {
    private static LocationRepository locationRepository;
    private LocationApi locationApi;
    AppDatabase db;
    public static LocationRepository getInstance(Context context){
        if(locationRepository==null){
            locationRepository = new LocationRepository(context);
        }
        return locationRepository;
    }
    public LocationRepository(Context context){
        locationApi = RetrofitService.createService(LocationApi.class);
        db = AppDatabase.getInstance(context);
    }
    public MutableLiveData<Boolean> cacheLocation(){
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        locationApi.getAllLocations().enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                try{
                    if(response.isSuccessful()){
                        List<Division> divisionList = new ArrayList<>();
                        List<District> districtList = new ArrayList<>();
                        List<Area> areaList = new ArrayList<>();
                        for(int i =0;i<response.body().getLocations().size();i++){
                            Division division = new Division(response.body().getLocations().get(i).getDivisionId()+"",response.body().getLocations().get(i).getDivisionName());
                            divisionList.add(division);
                            for (int j = 0; j < response.body().getLocations().get(i).getDistricts().size(); j++) {
                                District district = new District(response.body().getLocations().get(i).getDistricts().get(j).getDistrictId()+"",division.divisionId+"",response.body().getLocations().get(i).getDistricts().get(j).getDistrictName());
                                districtList.add(district);
                                for (int k = 0; k < response.body().getLocations().get(i).getDistricts().get(j).getAreas().size(); k++) {
                                    areaList.add(new Area(district.districtId+"",response.body().getLocations().get(i).getDistricts().get(j).getAreas().get(k).getAreaId()+"",response.body().getLocations().get(i).getDistricts().get(j).getAreas().get(k).getArea(),response.body().getLocations().get(i).getDistricts().get(j).getAreas().get(k).getAreaType()+""));
                                }
                            }
                        }
                        final Division[] divisions = new Division[divisionList.size()];
                        final District[] districts = new District[districtList.size()];
                        final Area[] areas = new Area[areaList.size()];
                        for(int i=0;i<divisionList.size();i++)
                        {
                            divisions[i] = divisionList.get(i);
                        }
                        for(int i=0;i<districtList.size();i++)
                        {
                            districts[i] = districtList.get(i);
                        }
                        for(int i=0;i<areaList.size();i++)
                        {
                            areas[i] = areaList.get(i);
                        }
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                db.divisionDao().deleteAll();
                                db.districtDao().deleteAll();
                                db.areaDao().deleteAll();

                                db.divisionDao().insertAll(divisions);
                                db.districtDao().insertAll(districts);
                                db.areaDao().insert(areas);
                            }
                        });

                    }

                }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });
        return result;
    }
    public LiveData<List<Division>> getAllDivisions(){
        return db.divisionDao().getAll();
    }
    public LiveData<List<District>> getDistrictByDivisionId(String id){
        return db.districtDao().getDistrictByDivisionId(id);
    }
    public LiveData<List<Area>> getAreaByDistrictId(String id){
        return db.areaDao().getAreasByDistrictId(id);
    }
}
