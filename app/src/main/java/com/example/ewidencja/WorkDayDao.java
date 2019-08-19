package com.example.ewidencja;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkDayDao {

    @Insert
    void insert(WorkDay workDay);

    @Update
    void update(WorkDay workDay);

    @Delete
    void delete(WorkDay workDay);

    @Query("DELETE FROM workday_table")
    void deleteAllWorkDays();

    @Query("SELECT * FROM workday_table ORDER BY id DESC")
    LiveData<List<WorkDay>> getAllWorkDays();
}
