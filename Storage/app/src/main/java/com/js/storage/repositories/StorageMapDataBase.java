package com.js.storage.repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Map;


@Database(entities = Map.class,
        version = 1,
        exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class StorageMapDataBase extends RoomDatabase {

    private static StorageMapDataBase storageDBMap = null;

    public abstract MapDao mapDao();

    public static synchronized StorageMapDataBase getDBIstance(Context context) {
        if (storageDBMap == null) {
            storageDBMap = Room.databaseBuilder(context.getApplicationContext(),
                            StorageMapDataBase.class,
                            "MAPSTORAGE19B2")
                    .allowMainThreadQueries()
                    .build();
        }
        return storageDBMap;
    }
}
