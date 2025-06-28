package com.js.storage.repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;
import com.js.storage.entitys.Unit;


@Database(entities = Storage.class,
        version = 1,
        exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class StorageDataBase extends RoomDatabase {

    private String databaseName = "STORAGE19B2";

    private static StorageDataBase storageDB = null;

    public abstract StorageDao storageDao();




    public static synchronized StorageDataBase getDBIstance(Context context) {
        if (storageDB == null) {
            storageDB = Room.databaseBuilder(context.getApplicationContext(),
                            StorageDataBase.class,
                            "STORAGE19B2")
                    .allowMainThreadQueries()
                    .build();
        }
        return storageDB;
    }


}

