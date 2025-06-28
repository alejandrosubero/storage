package com.js.storage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.SyncTree;
import com.google.firebase.database.core.view.Event;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;
import com.js.storage.entitys.Unit;
import com.js.storage.repositories.FiberbaseDaoStorage;
import com.js.storage.repositories.StorageDao;
import com.js.storage.repositories.StorageDataBase;
import com.js.storage.repositories.StorageUnitDataBase;
import com.js.storage.repositories.UnitDao;
import com.js.storage.security.EncryptAES;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudActivity extends AppCompatActivity {

    private CardView upCloud, updateCloud, donwloadCloud;
    private Context context;
    private StorageDao storageDao;
    private UnitDao unitDao;
    private FiberbaseDaoStorage dao;

    private TextView infoMessage, textViewAddTiTle;
    private Button info_btn;
    private Dialog dialogInfo;
    private Boolean internetConextion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        this.startView();
        this.networkConnectivity();
    }

    private void startView() {
        context = CloudActivity.this;
        internetConextion = false;
        upCloud = findViewById(R.id.upCloud);
        donwloadCloud = findViewById(R.id.donwloadCloud);
        unitDao = StorageUnitDataBase.getDBIstance(this).unitDao();
        storageDao = StorageDataBase.getDBIstance(this).storageDao();
        this.startInfoDialog();
        dao = new FiberbaseDaoStorage();
        this.eventListener();
    }

    private void eventListener() {
        upCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncCloud();
            }
        });

        donwloadCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataInCloud();
            }
        });
    }

    private void getDataInCloud() {
        List<Storage> listLocalStorage = storageDao.getAll();
        HashMap<String, Storage> mapLocalData = this.buildMap(listLocalStorage);

        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    String keyId = data.getKey();
//                     Log.d("******** Actiones ===> ", ""+keyId);
                    Storage emsto = data.getValue(Storage.class);
                    emsto.setKeyId(keyId);
                    Storage g = Storage.dencryptAndCreateNew(emsto);
//                    Storage g = Storage.createNew(emsto);
                    List<Unit> units = unitDao.findBySearch(g.getUnit(), true);

                    if(units != null && units.size() >0){
                        // do nothings.
                    }else{
                        Unit u = new Unit(g.getUnit());
                        unitDao.insert(u);
                    }
                    if (g.getItemImageString()!= null && g.getItemImageString().length() > 0){
                        g.convert(null, g.getItemImageString());
                        g.setItemImageString(null);
                    }
                    if (g.getItemImageBase64String() != null && g.getItemImageBase64String().length()>0){
                        byte[] arrayByte = DataConverter.conertBase64ToBytes(g.getItemImageBase64String());
                       g.setItemImage(arrayByte);
                    }
//                    Log.d("******** Actiones ===> ", ""+g.getItemName());
                    g.setStatusCloud("S");
                    setData(g, mapLocalData);
                    navegateBackupViewActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void syncCloud() {
        List<Storage> listStorage = storageDao.getAll();
        List<Storage> listUp = new ArrayList<>();
        List<Storage> listUpdate = new ArrayList<>();

        for (Storage st1 : listStorage) {
            String status = st1.getStatusCloud().trim();
            if (status.equals("N")) {
                listUp.add(st1);
            }
            if (status.equals("U") && st1.getKeyId() != null) {
                listUpdate.add(st1);
            }
            if (status.equals("U") && st1.getKeyId() == null){
                listUp.add(st1);
            }
        }
        upDataToCloud(listUp);
        updateCloud(listUpdate);
        navegateBackupViewActivity();
    }

    private void upDataToCloud(List<Storage> listStorageFoUp) {

        if (listStorageFoUp.size() > 0) {

            for (Storage upst : listStorageFoUp) {
                byte[] byImagen = null;
                upst.setStatusCloud("S");
                if (upst.getItemImage() != null){
                    byImagen = upst.getItemImage();
                    String imagenbase64 = DataConverter.conertByteToBase64(upst.getItemImage());
                    upst.setItemImage(null);
                    upst.setItemImageBase64String(imagenbase64);
                }
                dao.add2(upst, storageDao, byImagen);

            }
        }
    }

    private void updateCloud(List<Storage> listUpdate) {
        if (listUpdate != null && listUpdate.size() > 0) {
            for (Storage storeForupdate : listUpdate) {
                byte[] itemImage = null;
                if (storeForupdate.getKeyId() != null) {

                    if (storeForupdate.getItemImage() != null){
                        itemImage = storeForupdate.getItemImage();
                        String imagenbase64 = DataConverter.conertByteToBase64(storeForupdate.getItemImage());
                        storeForupdate.setItemImageBase64String(imagenbase64);
                        storeForupdate.setItemImage(null);
                    }
                    storeForupdate.setStatusCloud("S");
                    Storage saveObject = Storage.encryptAndCreateNew(storeForupdate);
                    HashMap<String, Object> map = Storage.toMap(saveObject);
                    Storage storeForupdateForDao = setImagen(itemImage, storeForupdate);

                    dao.update(storeForupdate.getKeyId(), map).addOnSuccessListener(suc -> {
                        Toast.makeText(context, "Record "+ storeForupdate.getKeyId()+ " is update", Toast.LENGTH_SHORT).show();
                        storageDao.update(storeForupdateForDao);
                    }).addOnFailureListener(er -> {
                        Toast.makeText(context, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    });

                }
            }
        }


    }

    private Storage setImagen(byte[] itemImage, Storage storeForupdate){
        storeForupdate.setItemImage(itemImage);
        return storeForupdate;
    }

    private void networkConnectivity() {
        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {
            ConnectivityManager connManager1 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            ConnectivityManager connManager2 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected()) {tipoConexion1 = true;}
            if (mMobile.isConnected()) {tipoConexion2 = true;}

            if (tipoConexion1 == true || tipoConexion2 == true) {
                internetConextion = true;
                textViewAddTiTle.setText("Network Info");
                infoMessage.setText("Estas conectado a internet");
                dialogInfo.show();
            }
        } else {
            textViewAddTiTle.setText("Network Info");
            infoMessage.setText("No estas conectado a internet");
            dialogInfo.show();
        }
    }

    private void startInfoDialog() {
        dialogInfo = new Dialog(this);
        dialogInfo.setContentView(R.layout.info_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogInfo.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialogInfo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogInfo.setCancelable(false);
        infoMessage = dialogInfo.findViewById(R.id.infoMessage);
        textViewAddTiTle = dialogInfo.findViewById(R.id.textViewAddTiTle);
        info_btn = dialogInfo.findViewById(R.id.info_btn);

        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInfo.dismiss();
                if(!internetConextion){
                    Intent intent = new Intent(context, BackupViewActivity.class);
                   startActivity(intent);

                }
            }
        });
    }

    private void navegateBackupViewActivity(){
        Intent intent = new Intent(context, BackupViewActivity.class);
        context.startActivity(intent);
    }


    private void setData(Storage cloudEntyti, HashMap<String, Storage> localData) {

        if (localData.containsKey(cloudEntyti.getKeyId())) {
            storageDao.update(cloudEntyti);
        } else {
            storageDao.insert(cloudEntyti);
        }
    }

    private HashMap<String, Storage> buildMap(List<Storage> list) {
        HashMap<String, Storage> hashMap = new HashMap<>();

        if (list != null && list.size() > 0) {
            for (Storage st1 : list) {
                if (st1.getKeyId() != null) {
                    hashMap.put(st1.getKeyId(), st1);
                }
            }
        }
        return hashMap;
    }

    private void updateData(Storage stupdate) {

        if (stupdate.getKeyId() != null) {
            dao.remove(stupdate.getKeyId()).addOnSuccessListener(suc -> {
                Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er -> {
                Toast.makeText(context, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });

            dao.add(stupdate).addOnSuccessListener(suc -> {
//                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er -> {
                Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private boolean addData(Storage st) {
        List<Boolean> resp = new ArrayList<>();
        dao.add(st).addOnSuccessListener(suc -> {
            resp.add(true);
        }).addOnFailureListener(er -> {
            resp.add(false);
        });
        return resp.get(0);
    }


}