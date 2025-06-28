package com.js.storage.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.js.storage.entitys.Map;
import com.js.storage.entitys.Storage;

import java.util.List;

@Dao
public interface MapDao {

    @Query("select * from Map")
    List<Map> getAll();

    @Insert
    void insert(Map map);

    @Update
    void update(Map map);

    @Delete
    void delete(Map map);


    @Query("SELECT * FROM Map WHERE mapId IN (:ids)")
    List<Map> loadAllByIds(int[] ids);


    @Query("SELECT * FROM Map WHERE name LIKE :search AND unit = :unit AND mapActive = :active")
    public List<Map> findBySearch(String search, String unit, boolean active );

    @Query("SELECT * FROM Map WHERE unit = :unitx AND mapActive = :active" )
    public List<Map> findBySearchMap( boolean active, String unitx );


}
