package com.js.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.js.storage.entitys.Map;
import com.js.storage.entitys.Unit;
import com.js.storage.recycleView.MapViewListRecycle;
import com.js.storage.recycleView.UnitViewListRecycle;
import com.js.storage.repositories.MapDao;
import com.js.storage.repositories.StorageMapDataBase;
import com.js.storage.repositories.StorageUnitDataBase;
import com.js.storage.repositories.UnitDao;

import java.util.List;

public class MapListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    List<Map> storages;
    SearchView SelectMapTxtBuscar;
    MapViewListRecycle mapViewListRecycle;
    MapDao mapDao;
    private static Context context;
    private String unit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        context = MapListActivity.this;
        unit = MainActivity.unitSelecte;
        this.startedView();
//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);
//        }
        this.showList();
    }

    private void startedView() {
        recyclerView = findViewById(R.id.selectMapStorgeRecyclerView);
        SelectMapTxtBuscar = findViewById(R.id.SelectMapTxtBuscar);
        mapDao = StorageMapDataBase.getDBIstance(context).mapDao();
        SelectMapTxtBuscar.setOnQueryTextListener(this);
    }

    private void showList() {
        storages = mapDao.findBySearchMap(true, unit);
        mapViewListRecycle = new MapViewListRecycle(storages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mapViewListRecycle);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mapViewListRecycle.filter(s);
        return false;
    }

    public void detailSee(Map map) {
        Intent intent = new Intent(context, DetaillMapActivity.class);
        intent.putExtra("object", map);
        intent.putExtra("valor", "unit");
        context.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("Edid");
        menu.add("Add Map");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.toString()) {
            case "Add Map":  {
                Intent intent = new Intent(context, AddMapActivity.class);
//                intent.putExtra("object", map);
//                intent.putExtra("valor", "unit");
                context.startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
//                     Log.d("******** Actiones ===> ", ""+keyId);