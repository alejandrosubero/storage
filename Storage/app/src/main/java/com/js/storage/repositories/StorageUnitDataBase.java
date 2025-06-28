package com.js.storage.repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;
import com.js.storage.entitys.Unit;

@Database(entities = Unit.class,
        version = 1,
        exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class StorageUnitDataBase extends RoomDatabase {

    private static StorageUnitDataBase storageDB = null;

    public abstract UnitDao unitDao();


    public static synchronized StorageUnitDataBase getDBIstance(Context context) {
        if (storageDB == null) {
            storageDB = Room.databaseBuilder(context.getApplicationContext(),
                            StorageUnitDataBase.class,
                            "UNITSTORAGE19B2")
                    .allowMainThreadQueries()
                    .build();
        }
        return storageDB;
    }
}
