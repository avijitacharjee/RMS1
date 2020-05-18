package com.avijit.rms1.data.local.daos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.avijit.rms1.data.local.entities.Division;

import java.util.List;

@Dao
public interface DivisionDao {
    @Query("Select * from Division")
    LiveData<List<Division>> getAll();
    @Insert
    void insertAll(Division... divisions);
    @Delete
    void delete(Division division);
    @Query("Delete from Division")
    void deleteAll();
}
