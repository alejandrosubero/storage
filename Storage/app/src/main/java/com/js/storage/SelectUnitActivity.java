package com.js.storage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.js.storage.entitys.Storage;
import com.js.storage.entitys.Unit;
import com.js.storage.recycleView.StoreViewListRecycle;
import com.js.storage.recycleView.UnitViewListRecycle;
import com.js.storage.repositories.StorageDataBase;
import com.js.storage.repositories.StorageUnitDataBase;
import com.js.storage.repositories.UnitDao;

import java.util.List;

public class SelectUnitActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    UnitDao unitDao;
    List<Unit> storages;
    SearchView SelectUnitTxtBuscar;
    UnitViewListRecycle unitViewListRecycle;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_unit);
        this.startedView();

        context = SelectUnitActivity.this;
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        this.showList();
    }

    private void startedView() {
        recyclerView = findViewById(R.id.selectUnitStorgeRecyclerView);
        SelectUnitTxtBuscar = findViewById(R.id.SelectUnitTxtBuscar);
        unitDao = StorageUnitDataBase.getDBIstance(this).unitDao();
        SelectUnitTxtBuscar.setOnQueryTextListener(this);
    }

    private void showList() {
        storages = unitDao.getAll();
        unitViewListRecycle = new UnitViewListRecycle(storages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(unitViewListRecycle);
    }
    //                     Log.d("******** Actiones ===> ", ""+keyId);

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        unitViewListRecycle.filter(s);
        return false;
    }

    public void selectUnit(Unit st) {
        Log.d("******** Actiones ===> ", "" + st.getUnitId());
        List<Unit> lista = unitDao.getAll();
        if (lista.size() > 0) {
            for (Unit unix : lista) {
                unix.setDefaultunit(false);
            }
        }
        st.setDefaultunit(true);

        unitDao.update(st);

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("object", st);
        intent.putExtra("valor", "unit");
        context.startActivity(intent);
    }

    public void selectUnitv(Unit st, View view) {
        UnitDao unitDaos = StorageUnitDataBase.getDBIstance(view.getContext()).unitDao();
        List<Unit> lista = unitDaos.getAll();

        if (lista.size() > 0) {
            for (Unit unix : lista) {
                if (unix.getName().equals(st.getName())) {
                    unix.setDefaultunit(true);
                }else {
                    unix.setDefaultunit(false);
                }
                unitDaos.update(unix);
//                Log.d("******** Actiones ===> ", ""+unix.getName()+ " isDefault = "+ unix.isDefaultunit());
            }

            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("object", st);
            intent.putExtra("valor", "unit");
            context.startActivity(intent);
        }

    }

}