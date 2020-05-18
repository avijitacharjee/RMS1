package com.avijit.rms1.data.local.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.avijit.rms1.data.local.entities.District;

import java.util.List;

@Dao
public interface DistrictDao {
    @Query("SELECT * FROM District")
    LiveData<List<District>> getAll();
    @Insert
    void insertAll(District... districts);
    @Query("DELETE FROM District")
    void deleteAll();
    @Query("SELECT * FROM District where divisionId=(:id)")
    LiveData<List<District>> getDistrictByDivisionId(String id);
}
