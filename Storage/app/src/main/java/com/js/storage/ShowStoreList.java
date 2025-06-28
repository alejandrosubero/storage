package com.js.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import com.js.storage.entitys.Storage;
import com.js.storage.recycleView.AlertViewListRecycle;
import com.js.storage.recycleView.StoreViewListRecycle;
import com.js.storage.repositories.StorageDao;
import com.js.storage.repositories.StorageDataBase;

import java.util.ArrayList;
import java.util.List;

public class ShowStoreList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    StorageDao storageDao;
    List<Storage> storages;
    List<Storage> storagesAlerts;
    SearchView txtBuscar;
    StoreViewListRecycle storeViewListRecycle;
    private static Context context;
    private ImageView btn_ing_close;
    public static Dialog dialog;
    private String unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_store_list);
        unit = MainActivity.unitSelecte;

//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);
//        }
        this.startedView();

        Intent intent = getIntent();
        String result = intent.getStringExtra("valor");
        String returnView = intent.getStringExtra("returnView");

        if (result != null && result.equals("List")) {
            showList(storages);
            int ret = 0;
            if (returnView != null && !returnView.equals("")) {
                ret = Integer.parseInt(returnView);
            }

            if (storagesAlerts != null && storagesAlerts.size() > 0) {
                this.showDialogC(ShowStoreList.this, storagesAlerts);
            }

        }else {
            showList(storages);
        }

    }


    private void startedView() {
        context = ShowStoreList.this;
        recyclerView = findViewById(R.id.storgeRecyclerView);
        txtBuscar = findViewById(R.id.txtBuscar);
        storageDao = StorageDataBase.getDBIstance(this).storageDao();
        txtBuscar.setOnQueryTextListener(this);
        if (storageDao.getAll() != null) {
//            storages = storageDao.getAll();
            storages = storageDao.getAllUnit(unit, true);
        }
        storagesAlerts = this.getAlerts(storages);
    }

    private void showList(List<Storage> storages) {
        storeViewListRecycle = new StoreViewListRecycle(storages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(storeViewListRecycle);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
//        storeViewListRecycle.filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        storeViewListRecycle.filter(s);
        return false;
    }

    public void viewDetaill(Storage st) {
        Intent intent = new Intent(context, ViewItemDetailActivity.class);
        intent.putExtra("object", st);
        intent.putExtra("her", "1");
        context.startActivity(intent);
    }

    public void showDialogC(Activity activity, List<Storage> listAlerts) {

        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.alert_list_dialogo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        btn_ing_close = dialog.findViewById(R.id.btn_ing_close);
        btn_ing_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        RecyclerView recyclerView = dialog.findViewById(R.id.alertDialogRecyclerView);
        AlertViewListRecycle adapterRe = new AlertViewListRecycle(context, listAlerts);
        recyclerView.setAdapter(adapterRe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        dialog.show();
    }

    private List<Storage> getAlerts(List<Storage> sto) {

        List<Storage> storageList = new ArrayList<>();
        for (Storage storage : sto) {
            String type = storage.getType().trim().toLowerCase();
            String comparator = "Material".toLowerCase();
//            if (storage.getType().equals("Material")) {
            if (type.equals(comparator)) {
                String valorNumero = storage.getItemNumber().trim().toLowerCase();
                int number = Integer.parseInt(valorNumero);
                int alertNumero = storage.getAlertAmounts();
                if (storage.isShowAlert() && (number <= alertNumero)) {
                    storageList.add(storage);
                }
            }
        }
        return storageList;
    }

    public void viewAlert(Storage st) {
        Intent intent = new Intent(context, ViewItemDetailActivity.class);
        intent.putExtra("object", st);
        intent.putExtra("her", "1");
        context.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        menu.add("Edid");
        menu.add("Map");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.toString()) {
            case "Map":  {
                Intent intent = new Intent(context, MapListActivity.class);
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