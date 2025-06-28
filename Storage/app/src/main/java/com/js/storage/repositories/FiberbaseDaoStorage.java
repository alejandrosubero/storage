package com.js.storage.repositories;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.js.storage.entitys.Storage;

import java.util.HashMap;


public class FiberbaseDaoStorage {

    private DatabaseReference databaseReference;


    public FiberbaseDaoStorage() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Storage.class.getSimpleName());
    }

    public Task<Void> add(Storage sto) {
        return databaseReference.push().setValue(sto);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) {
        return databaseReference.child(key).removeValue();
    }

    public Query get(String key) {
        if (key == null) {
            return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }

    public Query get() {
        return databaseReference;
    }


    public void add2(Storage sto, StorageDao storageDao, byte[] by) {

//        Storage saveObject = new Storage();
        Storage saveObjectSend = Storage.encryptAndCreateNew(sto);

        databaseReference.push().setValue(saveObjectSend, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                String key =  ref.getKey();
                if(key != null){
                    sto.setKeyId(key);
                    sto.setItemImage(by);
                    storageDao.update(sto);
                }

            }
        });
    }


}

//                Log.d("******** Actiones ===> ", ""+ref.getKey());