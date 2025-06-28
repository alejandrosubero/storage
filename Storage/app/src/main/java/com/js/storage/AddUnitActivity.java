package com.js.storage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.js.storage.entitys.Unit;
import com.js.storage.repositories.StorageUnitDataBase;
import com.js.storage.repositories.UnitDao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class AddUnitActivity extends AppCompatActivity {

    private EditText storageUnitNamea;
    private FloatingActionButton floating_addUnit_save;
    private ImageView itemImage;
    private UnitDao unitDao;
    private Context context;
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
        setContentView(R.layout.activity_add_unit);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        this.startActivity();
        this.eventListener();
    }


    private void startActivity() {
        context = AddUnitActivity.this;
        storageUnitNamea = findViewById(R.id.storageUnitNamea);
        floating_addUnit_save = findViewById(R.id.floating_addUnit_save);
        itemImage = findViewById(R.id.itemImage);
        unitDao = StorageUnitDataBase.getDBIstance(this).unitDao();
        imageBitmap = null;
        this.startInfoDialog();
        this.startDialogActions();
    }

    private void eventListener() {
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogactions.show();
            }
        });
        floating_addUnit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUnit(view);
            }
        });
    }

    private void saveUnit(View view) {

        Unit unit = new Unit();
        String name = storageUnitNamea.getText().toString();
        unit.setName(name);
        unit.setDefaultunit(true);
        if (imageBitmap != null) {
            unit.setUnitImage(DataConverter.convertImageToArray(imageBitmap));
        }

        if (!checkName(name)) {
            dialogInfo.show();
        } else {

            List<Unit> lista = unitDao.getAll();
            if(lista.size()> 0){
                for (Unit unix: lista) {
                    unix.setDefaultunit(false);
                }
            }

            unitDao.insert(unit);
            Toast.makeText(context, "the unit is save", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.putExtra("valor", "unit");
            intent.putExtra("object", unit);
//            intent.putExtra("name", unit.getName());
            startActivity(intent);
        }
    }

    private boolean checkName(String name) {
        List<Unit> lista = unitDao.findBySearch(name, true);

        if (lista != null && lista.size() > 0) {
            for (Unit unit : lista) {
                String nameToCheck = unit.getName().toLowerCase();
                if (nameToCheck.equals(name.toLowerCase())) {
                    return false;
                }
            }
        } else {
            Toast.makeText(context, "the list is null", Toast.LENGTH_LONG).show();
        }
        return true;
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
                    Uri selectedImage;
                    InputStream imageStream = null;
                    selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();
                    if (selectedPath != null) {
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        imageBitmap = BitmapFactory.decodeStream(imageStream);
                        if (imageBitmap != null) {
                            itemImage.setImageBitmap(imageBitmap);
                        }
                    }
                }
                break;

            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        itemImage.setImageBitmap(imageBitmap);
                    } else {
                        Toast.makeText(this, "Bitmap null", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

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
        actionsTiTleDialogo.setText("Actions");
        actionsItem.setText("Galeries");
        actionsMaterial.setText("Camera");
        textViewMessageActions.setText("Select one actions for add image");

        this.eventListenerActionsDialog();
    }

    private void eventListenerActionsDialog(){
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

        textViewAddTiTle.setText("Alert of Unit Name");
        infoMessage.setText("The unit name alredy exists");

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
        storageUnitNamea.setText("");
        imageBitmap = null;
    }


}

//            Toast.makeText(context, "the unit name alredy exists", Toast.LENGTH_SHORT).show();

//                Log.d("******** Actiones ===> ", ""+name);