package com.js.storage.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.js.storage.entitys.Storage;

import java.util.List;

@Dao
public interface StorageDao {

    @Query("select * from Storage")
    List<Storage> getAll();

    @Insert
    void insert(Storage storage);

    @Update
    void update(Storage storage);

    @Delete
    void delete(Storage storage);


    @Query("SELECT * FROM Storage WHERE uid IN (:ids)")
    List<Storage> loadAllByIds(int[] ids);


    @Query("SELECT * FROM Storage WHERE unit LIKE :search " + "OR itemType LIKE :search "
            + "OR itemDescription LIKE :search "+ "OR storeArea LIKE :search " + "OR storeType LIKE :search "
            + "OR storeSecction LIKE :search " + "OR itemUse LIKE :search "+ "AND itemActive = :active")
    public List<Storage> findBySearch(String search, boolean active );

    @Query("SELECT * FROM Storage WHERE unit = :unitx AND itemName LIKE :search " + "OR itemType LIKE :search "
            + "OR itemDescription LIKE :search "+ "OR storeArea LIKE :search " + "OR storeType LIKE :search "
            + "OR storeSecction LIKE :search " + "OR itemUse LIKE :search " +"AND itemActive = :active" )
    public List<Storage> findBySearchUnit(String search, boolean active, String unitx );

    @Query("SELECT * FROM Storage WHERE unit = :unit " + "AND itemActive = :active")
    List<Storage> getAllUnit(String unit, boolean active );

}
