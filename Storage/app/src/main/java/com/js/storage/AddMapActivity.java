package com.js.storage;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Map;
import com.js.storage.repositories.MapDao;
import com.js.storage.repositories.StorageMapDataBase;

import java.util.List;

public class AddMapActivity extends AppCompatActivity {

    private EditText storageMapName;
    private FloatingActionButton floating_addMap_save;
    private ImageView itemImageMap;
    private Context context;
    private MapDao mapDao;
    private String unit;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_FILE = 2;
    private Bitmap imageBitmap;

    private TextView infoMessage, textViewAddTiTle;
    private Button info_btn;
    private Dialog dialogInfo;

    private Dialog dialogactions;
    private Button actionsItem, actionsMaterial;
    private TextView actionsTiTleDialogo, textViewMessageActions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map);
        unit = MainActivity.unitSelecte;
        this.startActivity();
        this.eventListener();
    }

    private void startActivity() {
        context = AddMapActivity.this;
        storageMapName = findViewById(R.id.storageMapName);
        floating_addMap_save = findViewById(R.id.floating_addMap_save);
        itemImageMap = findViewById(R.id.itemImageMap);
        mapDao = StorageMapDataBase.getDBIstance(context).mapDao();
        imageBitmap = null;
        this.startInfoDialog();
        this.startDialogActions();
    }

    private void eventListener() {
        itemImageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogactions.show();
            }
        });
        floating_addMap_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUnit(view);
            }
        });
    }

    private void saveUnit(View view) {

        Map map = null;
        String name = storageMapName.getText().toString();

        if (!checkName(name)) {
            dialogInfo.show();
        } else {
            if (imageBitmap != null) {
                byte[] data = DataConverter.convertImageToArray(imageBitmap);
                map = new Map(unit, name, data);
            }
        }

        mapDao.insert(map);
        Toast.makeText(context, "the Map is save", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(), MapListActivity.class);
        startActivity(intent);
    }

    private boolean checkName(String name) {
        List<Map> lista = mapDao.findBySearch(name, unit, true);

        if (lista != null && lista.size() > 0) {
            for (Map map : lista) {
                String nameToCheck = map.getName().toLowerCase();
                if (nameToCheck.equals(name.toLowerCase())) {
                    return false;
                }
            }
        } else {
            Toast.makeText(context, "the list is null", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void startDialogActions() {
        dialogactions = new Dialog(this);
        dialogactions.setContentView(R.layout.add_new_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogactions.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialogactions.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogactions.setCancelable(false);
        actionsItem = dialogactions.findViewById(R.id.btn_AddItem);
        actionsMaterial = dialogactions.findViewById(R.id.btn_AddMaterial);
        actionsTiTleDialogo = dialogactions.findViewById(R.id.textViewAddTiTles);
        textViewMessageActions = dialogactions.findViewById(R.id.textViewMessageAdd);
        actionsTiTleDialogo.setText("Add Map image");
        actionsItem.setText("Galeries");
//        actionsItem.setVisibility(View.GONE);
        actionsMaterial.setText("Camera");
        textViewMessageActions.setText("Select one actions for add image");

        this.eventListenerActionsDialog();
    }

    private void eventListenerActionsDialog() {
        actionsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGaleries();
                dialogactions.dismiss();
            }
        });

        actionsMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                dialogactions.dismiss();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void openGaleries() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == RESULT_OK && requestCode == SELECT_FILE) {
                    imageBitmap = null;
                    Bitmap selectedImageBitmap = null;
                    Uri selectedImageUri = data.getData();

                    try {
                        selectedImageBitmap =  DataConverter.reduceBitmap(context, selectedImageUri, 500, 500);
                        if (selectedImageUri != null) {
                            itemImageMap.setImageBitmap(selectedImageBitmap);
                            imageBitmap = selectedImageBitmap;
                        } else {
                            Toast.makeText(this, "Bitmap null", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        itemImageMap.setImageBitmap(imageBitmap);
                    } else {
                        Toast.makeText(this, "Bitmap null", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
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
        textViewAddTiTle = dialogInfo.findViewById(R.id.textViewAddTiTle);
        infoMessage = dialogInfo.findViewById(R.id.infoMessage);
        info_btn = dialogInfo.findViewById(R.id.info_btn);

        textViewAddTiTle.setText("Alert of Map Name");
        infoMessage.setText("The Map name alredy exists");

        this.eventListenerInfoDialog();
    }

    private void eventListenerInfoDialog() {
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInfo.dismiss();
            }
        });
    }

    private void cleanForm() {
        storageMapName.setText("");
        imageBitmap = null;
    }

}