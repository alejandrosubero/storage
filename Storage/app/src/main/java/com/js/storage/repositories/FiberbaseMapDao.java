package com.js.storage.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.js.storage.entitys.Map;
import com.js.storage.entitys.Unit;

import java.util.HashMap;

public class FiberbaseMapDao {

    private DatabaseReference databaseReference;

    public FiberbaseMapDao() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Map.class.getSimpleName());
    }

    public Task<Void> add(Map map) {
        return databaseReference.push().setValue(map);
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


}
