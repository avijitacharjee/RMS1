package com.avijit.rms1.data.local.daos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.avijit.rms1.data.local.entities.Area;

import java.util.List;

@Dao
public interface AreaDao {
    @Query("SELECT * FROM Area")
    LiveData<List<Area>> getAll();
    @Insert
    void insert(Area... areas);
    @Query("DELETE from Area")
    void deleteAll();
    @Query("SELECT * FROM Area WHERE Area.districtId =(:districtId)")
    LiveData<List<Area>> getAreasByDistrictId(String districtId);
}
