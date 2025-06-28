package com.js.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Map;
import com.js.storage.entitys.Unit;
import com.js.storage.repositories.MapDao;
import com.js.storage.repositories.StorageMapDataBase;

public class DetaillMapActivity extends AppCompatActivity {

    private ImageView itemImageMapv;
    private TextView mapNameTextView;
    private static Context context;
    private MapDao mapDao;

    private String intentResult;
    private Map mapaDeatail;

    private Dialog dialog;
    private Button okay;
    private Button cancel;
    private TextView TextViewMessage, ItemDetailTitleView;
    private String call = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaill_map);
        this.startView();
        context = DetaillMapActivity.this;
//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);
//        }

        Intent intent = getIntent();
        intentResult = intent.getStringExtra("valor");
        mapaDeatail = (Map) intent.getSerializableExtra("object");

        if(mapaDeatail != null && mapaDeatail.getMapImage() != null){
            Bitmap imageBitmap = DataConverter.convertArrayToImage(mapaDeatail.getMapImage());
            itemImageMapv.setImageBitmap(imageBitmap);
            mapNameTextView.setText(mapaDeatail.getName());
        }
    }

    public void startView(){
        itemImageMapv = findViewById(R.id.itemImageMapv);
        mapNameTextView = findViewById(R.id.MapNameTextView);
        mapDao = StorageMapDataBase.getDBIstance(context).mapDao();
        this.startedDialog();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("Edid");
        menu.add("Delete");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.toString()) {
            case "Delete":  {
                dialog.show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void startedDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        okay = dialog.findViewById(R.id.btn_okay);
        cancel = dialog.findViewById(R.id.btn_cancel);
        TextViewMessage = dialog.findViewById(R.id.textView2);
        TextViewMessage.setText("The selected Item will be completely deleted. Do you want to Delete?");
        okay.setText("Yes");
        cancel.setText("No");
        this.eventListener();
    }

    private void eventListener() {

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShowStoreList.class);
                intent.putExtra("valor", "List");
                mapDao.delete(mapaDeatail);
                Toast.makeText(v.getContext(), "The Item is Deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }



}