package com.js.storage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.js.storage.backup.FileManager;
import com.js.storage.entitys.Storage;
import com.js.storage.repositories.StorageDao;
import com.js.storage.repositories.StorageDataBase;

import java.io.File;
import java.util.List;

public class BackupViewActivity extends AppCompatActivity {


    int REQUEST_CODE = 200;
    boolean permissionGranted;

    protected String archivo = "archivo";
    protected String carpeta = "archivos";

    protected File file;
    protected String file_paht = "";

    protected Button bottonCreate, bottonImport, bottonNumberRowDB, bottonCloud;
    private Context context;

    protected FileManager fileManager;
    private StorageDao storageDao;

    //DIALOG'S
    protected Button cancelBkDialog, okCreateBackupDialog;
    protected EditText dataBaseName;
    protected ImageView imageViewBk;
    protected TextView textViewBackupTiTle, textViewMessageBackup;
    protected int hambletActiones;

    protected Button okNbDialog;
    protected TextView textVieRowNumber;
    private Dialog dialogBackup, dialogNumber;

    private String unit;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_view);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        unit = MainActivity.unitSelecte;
        this.startView();
    }


    private void startView() {
        permissionGranted = false;
        hambletActiones = 0;
        context = BackupViewActivity.this;
        fileManager = new FileManager();

        file_paht = (getExternalFilesDir(null) + File.separator + carpeta+ File.separator );

        this.verificarPermisos();
        this.startDialogNumber();
        this.startDialogBackup();
        bottonImport = findViewById(R.id.bottonImport);
        bottonCreate = findViewById(R.id.bottonCreate);
        bottonNumberRowDB = findViewById(R.id.bottonNumberRowDB);
        bottonCloud = findViewById(R.id.bottonCloud);
        storageDao = StorageDataBase.getDBIstance(this).storageDao();
        this.eventListener();

//        File root = android.os.Environment.getExternalStorageDirectory();
//        file_paht = (root.getAbsolutePath() + File.separator + carpeta+ File.separator );

        if(permissionGranted){
            if(!fileManager.checkFolder(file_paht)){
                fileManager.createFolder(file_paht);
//                Toast.makeText(context,"SE CREO....",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verificarPermisos() {
        int permissionWRITE_EXTERNAL = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionREAD_EXTERNAL = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionWRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED && permissionREAD_EXTERNAL == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }


    private void eventListenerNumberDialog(){
        okNbDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNumber.dismiss();
            }
        });
    }



    private void eventListener() {
        bottonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewBk.setImageResource(R.drawable.backup_icon);
                textViewBackupTiTle.setText("Database Backup");
                textViewMessageBackup.setText("Enter the name for the backup file.");
                hambletActiones = 0;
                dialogBackup.show();
            }
        });

        bottonImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewBk.setImageResource(R.drawable.backup_recovery_icon2);
                textViewBackupTiTle.setText("Import database Backup");
                textViewMessageBackup.setText("Enter the name of the backup file.");
                hambletActiones = 1;
                dialogBackup.show();
            }
        });

        bottonNumberRowDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberRow = ""+storageDao.getAll().size();
                textVieRowNumber.setText(numberRow);
                dialogNumber.show();
            }
        });


        bottonCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CloudActivity.class);
//                intent.putExtra("valor", "new");
                startActivity(intent);
            }
        });
    }


    private void startDialogNumber() {
        dialogNumber = new Dialog(this);
        dialogNumber.setContentView(R.layout.row_number_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogNumber.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialogNumber.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogNumber.setCancelable(false);
        okNbDialog = dialogNumber.findViewById(R.id.btnNumberOk);
        textVieRowNumber = dialogNumber.findViewById(R.id.textVieRowNumber);
        this.eventListenerNumberDialog();
    }


    private void startDialogBackup() {
        dialogBackup = new Dialog(this);
        dialogBackup.setContentView(R.layout.backup_2_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogBackup.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialogBackup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogBackup.setCancelable(false);
        // imagen
        imageViewBk = dialogBackup.findViewById(R.id.imageViewbk);
        //titulo del dialogo
        textViewBackupTiTle = dialogBackup.findViewById(R.id.textViewBackupTiTle);
        // mensaje del dialogo
        textViewMessageBackup = dialogBackup.findViewById(R.id.textViewMessageBackup);
        // nombre del archivo
        dataBaseName = dialogBackup.findViewById(R.id.dataBaseName);
        //  Botones
        cancelBkDialog = dialogBackup.findViewById(R.id.btn_cancelbk);
        okCreateBackupDialog = dialogBackup.findViewById(R.id.btn_okaybk);
        this.eventListenerBackupDialog();
    }

    private void eventListenerBackupDialog(){

        cancelBkDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {dialogBackup.dismiss();}
        });

        okCreateBackupDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = dataBaseName.getText().toString() + ".txt";
                Gson gson = new Gson();
//                Log.d("******** hambletActiones = ", ""+hambletActiones);

                if(hambletActiones == 0){
                    if( fileManager.createNewFile(file_paht ,name)){
                        if( fileManager.fileExist(file_paht ,name)){
                            List<Storage> itemList = storageDao.getAll();
                            String itemListInGson = gson.toJson(itemList);
                            fileManager.writeInFile(file_paht ,name,itemListInGson );
                        }
                    }
                }else{
//                    Log.d("******** file name nd paht = ", "name: "+name + " paht: "+ file_paht);
                    String newBackupDataBase = fileManager.readFile(file_paht ,name);
                    Storage[] lista = gson.fromJson(newBackupDataBase,  Storage[].class);
                    for (Storage st: lista) {
                        Storage as = Storage.createNew(st);
                        if(as.getUnit() == null){
                            as.setUnit(unit);
                        }
                        storageDao.insert(as);
                    }
                }
                dialogBackup.dismiss();
            }
        });
    }
}