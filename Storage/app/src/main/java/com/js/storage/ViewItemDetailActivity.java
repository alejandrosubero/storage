package com.js.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.js.storage.entitys.Storage;
import com.js.storage.repositories.StorageDao;
import com.js.storage.repositories.StorageDataBase;

public class ViewItemDetailActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    ImageView itemDeatailImage;
    TextView itemDetailName , itemDetailType, itemDetailDescription, itemDetailNumber, itemDetailUse;
    TextView itemDetailStoreSectionArea, itemDetailStoreSectionType, itemDetailStoreSectionSections;

    private Storage storage;
    protected Context context;
    private StorageDao storageDao;

    private Dialog dialog;
    protected Button okay;
    protected Button cancel;
    protected TextView TextViewMessage, ItemDetailTitleView;
    protected FloatingActionButton mAddFab;
    protected String call = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_detail);
        context = ViewItemDetailActivity.this;

        this.startedView();
        this.eventListener();

        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra("object");
        call = intent.getStringExtra("her");
        fillView(storage);
    }


    private void startedView(){
        itemDeatailImage = findViewById(R.id.itemDeatailImage);
        itemDetailName = findViewById(R.id.itemDetailName);
        itemDetailNumber= findViewById(R.id.itemDetailNumber);
        itemDetailType= findViewById(R.id.itemDetailType);
        itemDetailUse= findViewById(R.id.itemDetailUse);
        itemDetailDescription = findViewById(R.id.itemDetailDescription);
        itemDetailStoreSectionArea = findViewById(R.id.itemDetailStoreSectionArea);
        itemDetailStoreSectionType = findViewById(R.id.itemDetailStoreSectionType);
        itemDetailStoreSectionSections = findViewById(R.id.itemDetailStoreSectionSections);
        ItemDetailTitleView  = findViewById(R.id.ItemDetailTitleView);
        mAddFab = findViewById(R.id.add_fab);
        storageDao = StorageDataBase.getDBIstance(this).storageDao();
        this.startedDialog();
    }



    private void fillView(Storage storage){

//        Log.d("******** Actiones ===> ", ""+storage.getAlertAmounts());
        String type = storage.getType().trim().toLowerCase();
        String comparator = "Material".toLowerCase();
        if (type.equals(comparator)) {
//        if(storage.getType().equals("Material")){
            ItemDetailTitleView.setText("Material Detail");
        }else {
            ItemDetailTitleView.setText("Tool Detail");
        }

        if(storage.getItemImage() != null){
            itemDeatailImage.setImageBitmap(DataConverter.convertArrayToImage(storage.getItemImage()));
        }
        if( storage.getItemName() !=null){
            itemDetailName.setText(storage.getItemName());
        }
        if(storage.getItemType() !=null){
            itemDetailType.setText(storage.getItemType());
        }
        if(storage.getItemDescription()!=null){
            itemDetailDescription.setText(storage.getItemDescription());
        }
        if(storage.getStoreArea()!=null){
            itemDetailStoreSectionArea.setText(storage.getStoreArea());
        }
        if(storage.getStoreType()!=null){
            itemDetailStoreSectionType.setText(storage.getStoreType());
        }
        if(storage.getStoreSecction()!=null){
            itemDetailStoreSectionSections.setText(storage.getStoreSecction());
        }
        if(storage.getItemNumber()!=null){
            itemDetailNumber.setText(storage.getItemNumber());
        }
        if(storage.getItemUse()!=null){
            itemDetailUse.setText(storage.getItemUse());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Edid");
        menu.add("Delete");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.toString()) {
            case "Edid":  {
                String type = storage.getType().trim().toLowerCase();
                String comparator = "Material".toLowerCase();
//
                if (type.equals(comparator)) {
                    Intent intent = new Intent(context, NewMaterialActivity.class);
                    intent.putExtra("valor", "edid");
                    intent.putExtra("object", storage);
                    startActivity(intent);
                    return true;
                }else {
                    Intent intent = new Intent(context, NewItemView.class);
                    intent.putExtra("valor", "edid");
                    intent.putExtra("object", storage);
                    startActivity(intent);
                    return true;
                }
            }
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
    }

    private void eventListener() {

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShowStoreList.class);
                intent.putExtra("valor", "List");
                storageDao.delete(storage);
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


        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                int nun = Integer.parseInt(call);
                if(nun == 1){
                    intent = new Intent(view.getContext(), ShowStoreList.class);
                    intent.putExtra("valor", "List");
                    intent.putExtra("returnView", "3");
                } else {
                    intent = new Intent(view.getContext(), ShowListSearchActivity.class);
                }
                startActivity(intent);

            }
        });
    }

}

//        Log.d("******** call ===> ", ""+call);