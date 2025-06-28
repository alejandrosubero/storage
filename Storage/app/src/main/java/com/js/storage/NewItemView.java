package com.js.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.js.storage.dataModel.DataConverter;
import com.js.storage.entitys.Storage;
import com.js.storage.repositories.StorageDao;
import com.js.storage.repositories.StorageDataBase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class NewItemView extends AppCompatActivity {

    private Button btnSave;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 200;
    private Bitmap imageBitmap;
    private ImageView imageView;
    private EditText itemSectionName, itemSectionType, itemSectionDescription, itemSectionUse, itemSectionNumber;
    private EditText storeSectionArea, storeSectionType, storeSectionSections;
    private StorageDao storageDao;
    protected String intentResult;
    protected TextView newItemTitleView;
    protected Storage storageR;
    protected Context context;
    private String unit;
    protected FloatingActionButton add_fab_new;

    private Dialog dialogAdd;
    private Button addItem, addMaterial; //btn_AddItem, btn_AddMaterial
    private TextView textViewAddTiTles;
    private TextView textViewMessageAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_view);
        context = NewItemView.this;
        unit = MainActivity.unitSelecte;

        Intent intent = getIntent();
        intentResult = intent.getStringExtra("valor");

        this.startedView();

        if (intentResult.equals("edid")) {
            storageR = (Storage) intent.getSerializableExtra("object");
            newItemTitleView.setText("Edid Tool");
            btnSave.setText("Update");
            this.startEdid(storageR);
        } else {
            newItemTitleView.setText("New Tool");
            btnSave.setText("Save");
        }
        this.eventListener();
    }


    private void cleanForm() {
        itemSectionName.setText("");
        itemSectionType.setText("");
        itemSectionDescription.setText("");
        storeSectionArea.setText("");
        storeSectionType.setText("");
        storeSectionSections.setText("");
        itemSectionUse.setText("");
        itemSectionNumber.setText("");
        imageBitmap = null;
//        imageView.setImageDrawable(getResources().getDrawable(R.drawable.photot));
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.photot, getApplicationContext().getTheme()));
//        imageView.setImageBitmap(imageBitmap);
//        "@drawable/photot"
    }

    private void startedView() {
        startDialogAdd();
        btnSave = findViewById(R.id.saveItem);
        imageView = findViewById(R.id.itemImage);
        imageBitmap = null;
        itemSectionName = findViewById(R.id.itemSectionName);
        add_fab_new = findViewById(R.id.add_fab_new);
        itemSectionType = findViewById(R.id.itemSectionType);
        itemSectionDescription = findViewById(R.id.itemSectionDescription);
        itemSectionUse = findViewById(R.id.itemSectionUse);
        itemSectionNumber = findViewById(R.id.itemSectionNumber);
        storeSectionArea = findViewById(R.id.storeSectionArea);
        storeSectionType = findViewById(R.id.storeSectionType);
        storeSectionSections = findViewById(R.id.storeSectionSections);
        newItemTitleView = findViewById(R.id.newItemTitleView);
        storageDao = StorageDataBase.getDBIstance(this).storageDao();
    }


    private void startEdid(Storage storageRecibe) {

        if (storageRecibe.getItemImage() != null) {
            imageView.setImageBitmap(DataConverter.convertArrayToImage(storageRecibe.getItemImage()));
        }
        if (storageRecibe.getItemName() != null) {
            itemSectionName.setText(storageRecibe.getItemName());
        }
        if (storageRecibe.getItemType() != null) {
            itemSectionType.setText(storageRecibe.getItemType());
        }
        if (storageRecibe.getItemDescription() != null) {
            itemSectionDescription.setText(storageRecibe.getItemDescription());
        }
        if (storageRecibe.getStoreArea() != null) {
            storeSectionArea.setText(storageRecibe.getStoreArea());
        }
        if (storageRecibe.getStoreType() != null) {
            storeSectionType.setText(storageRecibe.getStoreType());
        }
        if (storageRecibe.getStoreSecction() != null) {
            storeSectionSections.setText(storageRecibe.getStoreSecction());
        }

        if (storageRecibe.getItemUse() != null) {
            itemSectionUse.setText(storageRecibe.getItemUse());
        }

        if (storageRecibe.getItemNumber() != null) {
            itemSectionNumber.setText(storageRecibe.getItemNumber());
        }
    }

    private void eventListener() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentResult.equals("edid")) {
                    update();
                } else {
                    save();
                }
            }
        });

        add_fab_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(view.getContext(), MainActivity.class);
//                    intent.putExtra("valor", "List");
//                    intent.putExtra("returnView", "3");
                startActivity(intent);

            }
        });
    }


    private void startDialogAdd() {
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

        this.eventListenerAddDialog();
    }

    private void eventListenerAddDialog() {
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
                    imageView.setImageBitmap(selectedImageBitmap);
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
                imageView.setImageBitmap(imageBitmap);
            } else {
                Toast.makeText(this, "Bitmap null", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void update() {

        if (itemSectionName.getText().toString().isEmpty() || storeSectionArea.getText().toString().isEmpty() || itemSectionNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "the tool data is missing", Toast.LENGTH_SHORT).show();
        } else {
            Storage storage = storageR;
            storage.setItemName(itemSectionName.getText().toString());
            storage.setStoreArea(storeSectionArea.getText().toString());

            if (itemSectionType.getText() != null) {
                storage.setItemType(itemSectionType.getText().toString());
            }
            if (itemSectionDescription.getText() != null) {
                storage.setItemDescription(itemSectionDescription.getText().toString());
            }
            if (storeSectionType.getText() != null) {
                storage.setStoreType(storeSectionType.getText().toString());
            }
            if (storeSectionSections.getText() != null) {
                storage.setStoreSecction(storeSectionSections.getText().toString());
            }
            if (imageBitmap != null) {
                storage.setItemImage(DataConverter.convertImageToArray(imageBitmap));
            }
            if (itemSectionUse.getText() != null) {
                storage.setItemUse(itemSectionUse.getText().toString());
            }
            if (itemSectionNumber.getText() != null) {
                storage.setItemNumber(itemSectionNumber.getText().toString());
            }

            if (storage.getStatusCloud().equals("S")) {
                storage.setStatusCloud("U");
            } else {
                storage.setStatusCloud("N");
            }

            if (unit != null) {
                storage.setUnit(unit);
            }

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


    private void convertToMterial() {

        if (itemSectionName.getText().toString().isEmpty() || storeSectionArea.getText().toString().isEmpty()) {
            Toast.makeText(this, "the tool data is missing", Toast.LENGTH_SHORT).show();
        } else {
            Storage storage = storageR;
            storage.setType("Material");
            storage.setItemName(itemSectionName.getText().toString());
            storage.setStoreArea(storeSectionArea.getText().toString());

            if (itemSectionType.getText() != null) {
                storage.setItemType(itemSectionType.getText().toString());
            }
            if (itemSectionDescription.getText() != null) {
                storage.setItemDescription(itemSectionDescription.getText().toString());
            }
            if (storeSectionType.getText() != null) {
                storage.setStoreType(storeSectionType.getText().toString());
            }
            if (storeSectionSections.getText() != null) {
                storage.setStoreSecction(storeSectionSections.getText().toString());
            }
            if (imageBitmap != null) {
                storage.setItemImage(DataConverter.convertImageToArray(imageBitmap));
            }
            if (itemSectionUse.getText() != null) {
                storage.setItemUse(itemSectionUse.getText().toString());
            }
            if (itemSectionNumber.getText() != null) {
                storage.setItemNumber(itemSectionNumber.getText().toString());
            }

            storage.setUnit(unit);
            storage.setShowAlert(false);
            storage.setAlertAmounts(0);
            storage.setCategory("Variable");
            storage.setStatusCloud("N");
            Date f = new Date();
            storage.setDateSave(f);

            storageDao.update(storage);
            Toast.makeText(this, "Convert and Update is Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, NewMaterialActivity.class);
            intent.putExtra("valor", "edid");
            intent.putExtra("object", storage);

            this.viewDetaillUpdate(intent);
        }
    }


    private void viewDetaillUpdate(Intent intent) {
        context.startActivity(intent);
    }

    private void viewDetaill(Storage storage) {
        Intent intent = new Intent(context, ViewItemDetailActivity.class);
        intent.putExtra("object", storage);
        context.startActivity(intent);
    }

    private void save() {
        if (itemSectionName.getText().toString().isEmpty() || storeSectionArea.getText().toString().isEmpty() || itemSectionNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "the tool data is missing", Toast.LENGTH_SHORT).show();
        } else {
            Storage storage = new Storage();
            storage.setItemName(itemSectionName.getText().toString());
            storage.setStoreArea(storeSectionArea.getText().toString());
            storage.setType("Tool");
            storage.setStatusCloud("N");
            storage.setAlertAmounts(0);

            if (itemSectionType.getText() != null) {
                storage.setItemType(itemSectionType.getText().toString());
            }
            if (itemSectionDescription.getText() != null) {
                storage.setItemDescription(itemSectionDescription.getText().toString());
            }
            if (storeSectionType.getText() != null) {
                storage.setStoreType(storeSectionType.getText().toString());
            }
            if (storeSectionSections.getText() != null) {
                storage.setStoreSecction(storeSectionSections.getText().toString());
            }
            if (itemSectionUse.getText() != null) {
                storage.setItemUse(itemSectionUse.getText().toString());
            }
            if (itemSectionNumber.getText() != null) {
                storage.setItemNumber(itemSectionNumber.getText().toString());
            }
            if (imageBitmap != null) {
                storage.setItemImage(DataConverter.convertImageToArray(imageBitmap));
            }

            if (unit != null) {
                storage.setUnit(unit);
            }

            Date f = new Date();
            storage.setDateSave(f);
            storage.setItemActive(true);
            storageDao.insert(storage);
            Toast.makeText(this, "Insertion is Successful", Toast.LENGTH_SHORT).show();
            cleanForm();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Convert tool to Material");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.toString()) {
            case "Convert tool to Material":  {
                this.convertToMterial();
//                Intent intent = new Intent(context, ShowStoreList.class);
//                intent.putExtra("valor", "List");
//                context.startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}

//        Log.d("******** Actiones ===> ", ""+unit);