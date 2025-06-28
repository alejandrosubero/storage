package com.js.storage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;
import com.js.storage.repositories.StorageDao;
import com.js.storage.repositories.StorageDataBase;
import com.js.storage.repositories.StorageUnitDataBase;
import com.js.storage.repositories.UnitDao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

public class NewMaterialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected Button saveMaterial;
    protected ImageView materialImage;
    protected EditText materialSectionName, materialSectionNumber;
    protected EditText materialSectionType, materialSectionUse, materialSectionDescription;
    protected Spinner spinner;
    protected String spinnerSelection;
    private static final String[] paths = {"Variable", "Fixed"};
    protected FloatingActionButton helpCategory;
    protected EditText materialAlertNumber;
    protected FloatingActionButton helpAlert;
    protected CheckBox simpleCheckBox;
    protected FloatingActionButton helpShowAlert;

    protected EditText storeSectionAreaMaterial, storeSectionTypeMaterial, storeSectionSectionsMaterial;

    private Bitmap imageBitmap;
    private StorageDao storageDao;
    private UnitDao unitDao;
    protected String intentResult;
    protected TextView newMaterialTitleView;
    protected Storage storageR;
    protected Context context;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 200;

    private String unit;
    private TextView infoMessage;
    private Button info_btn;
    private Dialog dialogInfo;

    private Dialog dialogAdd;
    private Button addItem, addMaterial; //btn_AddItem, btn_AddMaterial
    private TextView textViewAddTiTles;
    private TextView textViewMessageAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_material);
        context = NewMaterialActivity.this;
        unit = MainActivity.unitSelecte;

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        intentResult = intent.getStringExtra("valor");
        this.startedView();

        if (intentResult.equals("edid")) {
            storageR = (Storage) intent.getSerializableExtra("object");
            newMaterialTitleView.setText("Edit Material");
            saveMaterial.setText("Update");
            this.startEdid(storageR);
        } else {
            newMaterialTitleView.setText("New Material");
            saveMaterial.setText("Add");
        }
        this.eventListener();
    }


    private void startedView() {

        saveMaterial = findViewById(R.id.saveMaterial);
        materialImage = findViewById(R.id.materialImage);
        newMaterialTitleView = findViewById(R.id.newMaterialTitleView);

        materialSectionName = findViewById(R.id.materialSectionName);
        materialSectionNumber = findViewById(R.id.materialSectionNumber);

        materialSectionType = findViewById(R.id.materialSectionType);
        materialSectionUse = findViewById(R.id.materialSectionUse);
        materialSectionDescription = findViewById(R.id.materialSectionDescription);

        spinner = (Spinner) findViewById(R.id.spinner);
        materialAlertNumber = findViewById(R.id.materialAlertNumber);
        materialAlertNumber.setText("0");

        helpCategory = findViewById(R.id.helpCategory);
        helpAlert = findViewById(R.id.helpAlert);
        helpShowAlert = findViewById(R.id.helpShowAlert);

        simpleCheckBox = (CheckBox) findViewById(R.id.simpleCheckBox);
        storeSectionAreaMaterial = findViewById(R.id.storeSectionAreaMaterial);
        storeSectionTypeMaterial = findViewById(R.id.storeSectionTypeMaterial);
        storeSectionSectionsMaterial = findViewById(R.id.storeSectionSectionsMaterial);
        this.startDialogAddImage();
        this.startedSpinner();
        this.startInfoDialog();
        this.eventListenerHelp();
        imageBitmap = null;
        storageDao = StorageDataBase.getDBIstance(this).storageDao();
        unitDao = StorageUnitDataBase.getDBIstance(this).unitDao();
    }

    private void startedSpinner() {
        spinnerSelection = "";
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewMaterialActivity.this, android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        spinnerSelection = paths[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }



    private void eventListenerHelp() {

        helpCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoMessage.setText("Indicates if the product can reach 0 in inventory (Variable) or if it must always exist (Fixed)");
                dialogInfo.show();
            }
        });

        helpAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoMessage.setText("Minimum quantity of item to generate an alert");
                dialogInfo.show();
            }
        });

        helpShowAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoMessage.setText("Activate the alert for the item.");
                dialogInfo.show();
            }
        });
    }

    private void eventListener() {

        materialImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.show();
            }
        });

        saveMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentResult.equals("edid")) {
                    update();
                } else {
                    save();
                }
            }
        });

    }



    private void startDialogAddImage() {
        dialogAdd = new Dialog(this);
        dialogAdd.setContentView(R.layout.add_new_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogAdd.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialogAdd.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogAdd.setCancelable(false);
        addItem = dialogAdd.findViewById(R.id.btn_AddItem);
        addMaterial = dialogAdd.findViewById(R.id.btn_AddMaterial);
        textViewAddTiTles = dialogAdd.findViewById(R.id.textViewAddTiTles);
        textViewMessageAdd = dialogAdd.findViewById(R.id.textViewMessageAdd);

        textViewAddTiTles.setText("Add an Image");
        textViewMessageAdd.setText("Select an image");
        addItem.setText("Camera");
        addMaterial.setText("Gallery");

        this.eventListenerAddDialogImage();
    }

    private void eventListenerAddDialogImage() {

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                dialogAdd.dismiss();
            }
        });

        addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
                dialogAdd.dismiss();
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            imageBitmap = null;
            Bitmap selectedImageBitmap = null;
            Uri selectedImageUri = data.getData();

            try {
                selectedImageBitmap =  DataConverter.reduceBitmap(context, selectedImageUri, 500, 500);
//                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                if (selectedImageUri != null) {
                    materialImage.setImageBitmap(selectedImageBitmap);
                    imageBitmap = selectedImageBitmap;
                } else {
                    Toast.makeText(this, "Bitmap null", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            if (imageBitmap != null) {
                materialImage.setImageBitmap(imageBitmap);
            } else {
                Toast.makeText(this, "Bitmap null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void save() {
        if (materialSectionName.getText().toString().isEmpty() || storeSectionAreaMaterial.getText().toString().isEmpty() || materialSectionNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "the Material data is missing", Toast.LENGTH_SHORT).show();
        } else {
            Storage storage = new Storage();
            storage.setItemName(materialSectionName.getText().toString());
            storage.setStoreArea(storeSectionAreaMaterial.getText().toString());
            storage.setType("Material");
            storage.setStatusCloud("N");

            if (materialSectionType.getText() != null) {
                storage.setItemType(materialSectionType.getText().toString());
            }
            if (materialSectionDescription.getText() != null) {
                storage.setItemDescription(materialSectionDescription.getText().toString());
            }
            if (storeSectionTypeMaterial.getText() != null) {
                storage.setStoreType(storeSectionTypeMaterial.getText().toString());
            }
            if (storeSectionSectionsMaterial.getText() != null) {
                storage.setStoreSecction(storeSectionSectionsMaterial.getText().toString());
            }
            if (materialSectionUse.getText() != null) {
                storage.setItemUse(materialSectionUse.getText().toString());
            }
            if (materialSectionNumber.getText() != null) {
                storage.setItemNumber(materialSectionNumber.getText().toString());
            }
            if (materialAlertNumber.getText() != null) {
                int alertNumber = Integer.parseInt(materialAlertNumber.getText().toString());
                storage.setAlertAmounts(alertNumber);
            }
            if (imageBitmap != null) {
                storage.setItemImage(DataConverter.convertImageToArray(imageBitmap));
            }
            if (unit != null) {
                storage.setUnit(unit);
            }
            //check current state of a check box (true or false)
            Boolean checkBoxState = simpleCheckBox.isChecked();
            if (checkBoxState != null) {
                storage.setShowAlert(checkBoxState);
            }
            if (spinnerSelection != null) {
                storage.setCategory(spinnerSelection);
            }
            Date f = new Date();
            storage.setDateSave(f);
            storage.setItemActive(true);
            storageDao.insert(storage);
            Toast.makeText(this, "Insertion is Successful", Toast.LENGTH_SHORT).show();
            cleanForm();
        }
    }

    private void update() {

        if (materialSectionName.getText().toString().isEmpty() || storeSectionAreaMaterial.getText().toString().isEmpty() || materialSectionNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "the Material data is missing", Toast.LENGTH_SHORT).show();
        } else {
            Storage storage = storageR;
            storage.setItemName(materialSectionName.getText().toString());
            storage.setStoreArea(storeSectionAreaMaterial.getText().toString());
//            storage.setType("Material");

            if (materialSectionType.getText() != null) {
                storage.setItemType(materialSectionType.getText().toString());
            }
            if (materialSectionDescription.getText() != null) {
                storage.setItemDescription(materialSectionDescription.getText().toString());
            }
            if (storeSectionTypeMaterial.getText() != null) {
                storage.setStoreType(storeSectionTypeMaterial.getText().toString());
            }
            if (storeSectionSectionsMaterial.getText() != null) {
                storage.setStoreSecction(storeSectionSectionsMaterial.getText().toString());
            }
            if (materialSectionUse.getText() != null) {
                storage.setItemUse(materialSectionUse.getText().toString());
            }
            if (materialSectionNumber.getText() != null) {
                storage.setItemNumber(materialSectionNumber.getText().toString());
            }
            if (imageBitmap != null) {
                storage.setItemImage(DataConverter.convertImageToArray(imageBitmap));
            }
            if (unit != null) {
                storage.setUnit(unit);
            }

            Boolean checkBoxState = simpleCheckBox.isChecked();
            if (checkBoxState != null) {
                storage.setShowAlert(checkBoxState);
            }
            if (spinnerSelection != null) {
                storage.setCategory(spinnerSelection);
            }
            if(materialAlertNumber != null){
                int num = Integer.parseInt(materialAlertNumber.getText().toString());
                storage.setAlertAmounts(num);
            }

            if(storage.getStatusCloud().equals("S")){
                storage.setStatusCloud("U");
            }else{
                storage.setStatusCloud("N");
            }

            storage.setItemActive(true);
            Date f = new Date();
            storage.setDateSave(f);
            storageDao.update(storage);
            Toast.makeText(this, "The Update is Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, ViewItemDetailActivity.class);
            intent.putExtra("object", storage);
            intent.putExtra("her", "1");
            this.viewDetaillUpdate(intent);
        }
    }

    private void viewDetaillUpdate(Intent intent) {
        context.startActivity(intent);
    }

    private void cleanForm() {
        imageBitmap = null;
//        materialImage.setImageBitmap(imageBitmap);
        materialImage.setImageDrawable(getResources().getDrawable(R.drawable.photot, getApplicationContext().getTheme()));

        materialSectionName.setText("");
        materialSectionType.setText("");
        materialSectionNumber.setText("");
        materialSectionUse.setText("");
        materialSectionDescription.setText("");

        materialAlertNumber.setText("");
        simpleCheckBox.setChecked(false);

        storeSectionAreaMaterial.setText("");
        storeSectionTypeMaterial.setText("");
        storeSectionSectionsMaterial.setText("");

        this.startedSpinner();
    }

    private void startEdid(Storage storageRecibe){

        if(storageRecibe.getItemImage() != null){
            materialImage.setImageBitmap(DataConverter.convertArrayToImage(storageRecibe.getItemImage()));
        }

        if( storageRecibe.getItemName() !=null){
            materialSectionName.setText(storageRecibe.getItemName());
        }
        if(storageRecibe.getItemType() !=null){
            materialSectionType.setText(storageRecibe.getItemType());
        }
        if(storageRecibe.getItemNumber() != null){
            materialSectionNumber.setText(storageRecibe.getItemNumber());
        }
        if(storageRecibe.getItemUse() != null){
            materialSectionUse.setText(storageRecibe.getItemUse());
        }
        if(storageRecibe.getItemDescription()!=null){
            materialSectionDescription.setText(storageRecibe.getItemDescription());
        }
        if(storageRecibe.getAlertAmounts() > 0){
            String valor = ""+storageRecibe.getAlertAmounts();
            materialAlertNumber.setText(valor);
        }
        if(storageRecibe.isShowAlert()){
            simpleCheckBox.setChecked(storageRecibe.isShowAlert());
        }else {
            simpleCheckBox.setChecked(false);
        }

        if(storageRecibe.getStoreArea()!=null){
            storeSectionAreaMaterial.setText(storageRecibe.getStoreArea());
        }
        if(storageRecibe.getStoreType()!=null){
            storeSectionTypeMaterial.setText(storageRecibe.getStoreType());
        }
        if(storageRecibe.getStoreSecction()!=null){
            storeSectionSectionsMaterial.setText(storageRecibe.getStoreSecction());
        }
        if(storageRecibe.getCategory() != null){
            spinner.setSelection(getIndex(spinner, storageRecibe.getCategory()));
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
        infoMessage  = dialogInfo.findViewById(R.id.infoMessage);
        info_btn = dialogInfo.findViewById(R.id.info_btn);

        this.eventListenerInfoDialog();
    }

    private void eventListenerInfoDialog(){
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogInfo.dismiss();
            }
        });
    }


}
//        Log.d("******** Actiones ===> ", ""+unit);