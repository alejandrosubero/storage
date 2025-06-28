package com.js.storage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SearchView;

import com.js.storage.entitys.Storage;
import com.js.storage.recycleView.SearchViewListRecycle;
import com.js.storage.repositories.StorageDao;
import com.js.storage.repositories.StorageDataBase;

import java.util.ArrayList;
import java.util.List;

public class ShowListSearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<Storage> storages;
    private RecyclerView recyclerView;
    private SearchViewListRecycle searchViewListRecycle;

//    private androidx.appcompat.widget.SearchView txtSearch;
    private SearchView txtSearch;
    private static Context context;
    private StorageDao storageDao;
    private String find;
    private String unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unit = MainActivity.unitSelecte;
        setContentView(R.layout.activity_show_list_search);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        this.startedView();
    }


    private void startedView(){
        context = ShowListSearchActivity.this;
        find ="";
        recyclerView = findViewById(R.id.storgeRecyclerViewSearch);
        storageDao = StorageDataBase.getDBIstance(this).storageDao();
        txtSearch = findViewById(R.id.txtSearch);


        
        txtSearch.setOnQueryTextListener(this);

    }


    private void showList(List<Storage> list){
        searchViewListRecycle = new SearchViewListRecycle(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchViewListRecycle);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        find = s;
//        storages = storageDao.findBySearch(s,true);
        storages = storageDao.findBySearchUnit(s,true,unit);
        this.showList(storages);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if(s.length() == 0 || s.equals("") ||  s.equals(" ")){
            storages = new ArrayList<>();
            this.showList(storages);
        }

        return false;
    }

    public void viewDetaill(Storage st){
        Intent intent = new Intent(context, ViewItemDetailActivity.class);
        intent.putExtra("object", st);
        intent.putExtra("her", "2");
        context.startActivity(intent);
    }


}