package com.js.storage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.js.storage.backup.FileManager;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;
import com.js.storage.entitys.Unit;
import com.js.storage.repositories.StorageUnitDataBase;
import com.js.storage.repositories.UnitDao;

import java.io.File;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private CardView cardViewNewItem;
    private CardView cardViewSearch;
    private CardView cardViewList;
    private CardView cardBackup;
    private Dialog dialog;

    private Dialog dialogAdd;
    private Button addItem, addMaterial; //btn_AddItem, btn_AddMaterial
    private TextView textViewAddTiTles;
    private TextView textViewMessageAdd;
    private ImageView btn_ing_closeAdd;

    private UnitDao unitDao;
    private Dialog unitDialog;
    private ImageView btn_ing_closeUnit;
    protected TextView unitName;
    private CardView cardUnit;
    private Button add_btn_unit, cancel_btn_unit;
    private EditText storageUnitName;
    public static String unitSelecte;

    private Button btn_selectUnitAction, btn_AddUnitAction;
    private Dialog actionsUnitDialog;

    boolean permissionGranted;
    protected FileManager fileManager;
    int REQUEST_CODE = 200;
    protected Context context;

    private String intentResult;
    private String unitNameRecibe;
    private Unit unitObject;
    private ImageView unitImage;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        this.startedView();
        this.eventListener();

        Intent intent = getIntent();
        intentResult = intent.getStringExtra("valor");
        unitObject = (Unit) intent.getSerializableExtra("object");

        if (intentResult != null && !intentResult.equals("")) {
            unitSelecte = unitObject.getName();
            unitName.setText(unitSelecte);
            if (unitObject.getUnitImage() != null) {
                unitImage.setImageBitmap(DataConverter.convertArrayToImage(unitObject.getUnitImage()));
            }
        } else {
            this.getUnitOrSet();
        }

    }

    private void getUnitOrSet() {
        List<Unit> lista = unitDao.getAll();
//        Unit unit = null;
        if (lista != null && lista.size() > 0) {
            for ( Unit unit :lista) {
                if(unit.isDefaultunit()){
                    String nameUnit = unit.getName();
                    unitName.setText(nameUnit);
                    unitSelecte = nameUnit;
                    if (unit.getUnitImage() != null) {
                        unitImage.setImageBitmap(DataConverter.convertArrayToImage(unit.getUnitImage()));
                    }
                }
            }
        } else {
            unitDialog.show();
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == event.KEYCODE_BACK) {
////            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("Exit app Store");
//            builder.setMessage("Do you Want to Exit")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int which) {
//                            Intent intent = new Intent(Intent.ACTION_MAIN);
//                            intent.addCategory(Intent.CATEGORY_HOME);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    })
//                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .setInverseBackgroundForced(true);
//            builder.show();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void startedView() {
        verificarPermisos();
        unitDao = StorageUnitDataBase.getDBIstance(this).unitDao();
        fileManager = new FileManager();
        unitName = findViewById(R.id.unitName);
        cardViewNewItem = findViewById(R.id.cardAdd);
        cardViewList = findViewById(R.id.cardlist);
        cardViewSearch = findViewById(R.id.cardSearch);
        cardBackup = findViewById(R.id.cardBackup);
        cardUnit = findViewById(R.id.cardUnit);
        unitImage = findViewById(R.id.unitImage);
        intentResult = "";
        unitNameRecibe = "";
        unitObject = null;
        this.startDialogUnit();
        dialog = new Dialog(this);
        this.startDialogAdd();
        this.startDialogUnitActions();
        this.createFolder();
    }

    private void createFolder() {
        String file_paht = (getExternalFilesDir(null) + File.separator + "archivos" + File.separator);
        if (permissionGranted) {
            if (!fileManager.checkFolder(file_paht)) {
                fileManager.createFolder(file_paht);
            }
        }
    }

    private void eventListener() {

        cardUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionsUnitDialog.show();
            }
        });

        cardViewNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.show();
            }
        });

        cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShowStoreList.class);
                intent.putExtra("valor", "List");
                startActivity(intent);
            }
        });

        cardViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShowListSearchActivity.class);
                intent.putExtra("valor", "List");
                startActivity(intent);
            }
        });

        cardBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BackupViewActivity.class);
//                intent.putExtra("valor", "List");
                startActivity(intent);
            }
        });
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

    private void startDialogAdd() {
        dialogAdd = new Dialog(this);
        dialogAdd.setContentView(R.layout.add_new_dialog);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogAdd.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialogAdd.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogAdd.setCancelable(false);

        btn_ing_closeAdd = dialogAdd.findViewById(R.id.btn_ing_closeAdd);
        btn_ing_closeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.dismiss();
            }
        });

        addItem = dialogAdd.findViewById(R.id.btn_AddItem);
        addMaterial = dialogAdd.findViewById(R.id.btn_AddMaterial);
        textViewAddTiTles = dialogAdd.findViewById(R.id.textViewAddTiTles);
        textViewMessageAdd = dialogAdd.findViewById(R.id.textViewMessageAdd);

        textViewAddTiTles.setText("Select for add");
        textViewMessageAdd.setText("Select the element do you want to create");
        addItem.setText("New Tool");
        addMaterial.setText("New Material");

        this.eventListenerAddDialog();
    }

    private void eventListenerAddDialog() {
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewItemView.class);
                intent.putExtra("valor", "new");
                startActivity(intent);
                dialogAdd.dismiss();
            }
        });


        addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewMaterialActivity.class);
                intent.putExtra("valor", "new");
                startActivity(intent);
                dialogAdd.dismiss();
            }
        });
    }


    private void startDialogUnit() {
        if (unitSelecte == null) {
            unitSelecte = "";
        }
        unitDialog = new Dialog(this);
        unitDialog.setContentView(R.layout.unit_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            unitDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        unitDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unitDialog.setCancelable(false);
        storageUnitName = unitDialog.findViewById(R.id.storageUnitName);
        add_btn_unit = unitDialog.findViewById(R.id.add_btn_unit);
        cancel_btn_unit = unitDialog.findViewById(R.id.cancel_btn_unit);

        this.eventListenerUnitDialog();
    }

    private void eventListenerUnitDialog() {

        add_btn_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Unit unit = new Unit();
                List<Unit> lista = unitDao.getAll();
                String name = storageUnitName.getText().toString();
//                Log.d("******** Actiones ===> ", ""+name);
                unit.setName(name);
                unit.setDefaultunit(true);

                if(lista.size()> 0){
                    for (Unit unix: lista) {
                        unix.setDefaultunit(false);
                    }
                }
                unitDao.insert(unit);
                Toast.makeText(context, "the unit is save", Toast.LENGTH_LONG).show();
                unitSelecte = unit.getName();
                unitName.setText(unitSelecte);
                unitDialog.dismiss();
            }
        });


        cancel_btn_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "cancel", Toast.LENGTH_LONG).show();
                unitDialog.dismiss();
            }
        });
    }


    private void startDialogUnitActions() {
        actionsUnitDialog = new Dialog(this);
        actionsUnitDialog.setContentView(R.layout.unit_action_manager_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionsUnitDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        actionsUnitDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actionsUnitDialog.setCancelable(false);

        btn_ing_closeUnit = actionsUnitDialog.findViewById(R.id.btn_ing_closeUnit);
        btn_ing_closeUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionsUnitDialog.dismiss();
            }
        });
        btn_selectUnitAction = actionsUnitDialog.findViewById(R.id.btn_selectUnitAction);
        btn_AddUnitAction = actionsUnitDialog.findViewById(R.id.btn_AddUnitAction);
        this.eventListenerUnitActionsDialog();
    }

    private void eventListenerUnitActionsDialog() {
        btn_selectUnitAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SelectUnitActivity.class);
                startActivity(intent);
                actionsUnitDialog.dismiss();
            }
        });


        btn_AddUnitAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionsUnitDialog.dismiss();
                Intent intent = new Intent(view.getContext(), AddUnitActivity.class);
                startActivity(intent);
            }
        });
    }

}