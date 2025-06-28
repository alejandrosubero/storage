package com.js.storage.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.js.storage.entitys.Storage;
import com.js.storage.entitys.Unit;

import java.util.List;

@Dao
public interface UnitDao {

    @Query("select * from Unit")
    List<Unit> getAll();

    @Insert
    void insert(Unit unit);

    @Update
    void update(Unit unit);

    @Delete
    void delete(Unit unit);


    @Query("SELECT * FROM Unit WHERE unitId IN (:ids)")
    List<Unit> loadAllByIds(int[] ids);

    @Query("SELECT * FROM Unit WHERE unitName IN (:name)")
    List<Unit> findByName(String name);

    @Query("SELECT * FROM Unit WHERE unitName LIKE :search " + "AND unitActive = :active")
    public List<Unit> findBySearch(String search, Boolean active);
}
