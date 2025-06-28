package com.js.storage.entitys;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.gson.Gson;
import com.js.storage.pojos.CoverImage;
import com.js.storage.security.EncryptAES;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "Storage")
public class Storage implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "itemName")
    String itemName;

    @ColumnInfo(name = "itemType")
    String itemType;

    @ColumnInfo(name = "itemDescription")
    String itemDescription;

    @ColumnInfo(name = "itemActive")
    boolean itemActive;

    @ColumnInfo(name = "itemNumber")
    String itemNumber;

    @ColumnInfo(name = "itemUse")
    String itemUse;

    @ColumnInfo(name = "storeArea")
    String storeArea;

    @ColumnInfo(name = "storeType")
    String storeType;

    @ColumnInfo(name = "storeSecction")
    String storeSecction;

    @ColumnInfo(name = "statusCloud")
    String statusCloud;

    @ColumnInfo(name = "type")
    String type;

    @ColumnInfo(name = "category")
    String category;

    @ColumnInfo(name = "alertAmounts")
    int alertAmounts;

    @ColumnInfo(name = "showAlert")
    boolean showAlert;

    @ColumnInfo(name = "user")
    String user;//1

    @ColumnInfo(name = "keyId")
    String keyId;

    @ColumnInfo(name = "note")
    String note;//1

    @ColumnInfo(name = "statusInventario")
    String statusInventario;//1

    @ColumnInfo(name = "dateSave")
    Date dateSave;

    @ColumnInfo(name = "unit")
    String unit;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] itemImage;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] storeImage;

    @ColumnInfo(name = "iDate")
    public Date iDate;

    @ColumnInfo(name = "itemImageString")
    String itemImageString;

    @ColumnInfo(name = "itemImageBase64String")
    String itemImageBase64String;

    public Storage() {
    }


    public static Storage dencryptAndCreateNew(Storage st) {
        Storage storage = new Storage();

        if (st.getItemName() != null) {
            storage.setItemName(EncryptAES.decryptAES(st.getItemName()));
        }
        if (st.getItemType() != null) {
            storage.setItemType(EncryptAES.decryptAES(st.getItemType()));
        }
        if (st.getItemDescription() != null) {
            storage.setItemDescription(EncryptAES.decryptAES(st.getItemDescription()));
        }
        if (st.getItemNumber() != null) {
            storage.setItemNumber(EncryptAES.decryptAES(st.getItemNumber()));
        }
        storage.setItemActive(st.isItemActive());

        if (st.getStoreArea() != null) {
            storage.setStoreArea(EncryptAES.decryptAES(st.getStoreArea()));
        }

        if (st.getStoreType() != null) {
            storage.setStoreType(EncryptAES.decryptAES(st.getStoreType()) );
        }

        if (st.getStoreSecction() != null) {
            storage.setStoreSecction(EncryptAES.decryptAES(st.getStoreSecction()) );
        }

        if (st.getStoreImage() != null) {
            storage.setStoreImage(st.getStoreImage());
        }

        if (st.getItemImage() != null) {
            storage.setItemImage(st.getItemImage());
        }

        if (st.getItemImageString() != null) {
            storage.setItemImageString(EncryptAES.decryptAES(st.getItemImageString()));
        }

        if (st.getDateSave() != null) {
            storage.setDateSave(st.getDateSave());
        }


        if (st.getItemUse() != null) {
            storage.setItemUse(EncryptAES.decryptAES(st.getItemUse()) );
        }

        if (st.getUnit() != null) {
            storage.setUnit(EncryptAES.decryptAES(st.getUnit()) );
        }

        if (st.getType() != null) {
            storage.setType(EncryptAES.decryptAES(st.getType()) );
        }

        if (st.getStatusCloud() != null) {
            storage.setStatusCloud(EncryptAES.decryptAES(st.getStatusCloud()) );
        } else {
            storage.setStatusCloud("N");
        }

        if (st.getKeyId() != null) {
            storage.setKeyId(st.getKeyId());
        }

        if (st.getItemImageBase64String() != null) {
            storage.setItemImageBase64String(EncryptAES.decryptAES(st.getItemImageBase64String()) );
        }
        if(!st.isShowAlert()){
            storage.setShowAlert(false);
        }else{
            storage.setShowAlert(st.isShowAlert());
        }

        String type = storage.getType().trim().toLowerCase();
        String comparator = "Material".toLowerCase();

        if (type.equals(comparator)) {
            storage.setAlertAmounts(st.alertAmounts);
        }else {
            storage.setAlertAmounts(0);
        }
        return storage;
    }


    public static Storage encryptAndCreateNew(Storage st) {
        Storage storage = new Storage();

        if (st.getItemName() != null) {
            storage.setItemName(EncryptAES.encryptAES(st.getItemName()));
        }
        if (st.getItemType() != null) {
            storage.setItemType(EncryptAES.encryptAES(st.getItemType()));
        }
        if (st.getItemDescription() != null) {
            storage.setItemDescription(EncryptAES.encryptAES(st.getItemDescription()));
        }
        if (st.getItemNumber() != null) {
            storage.setItemNumber(EncryptAES.encryptAES(st.getItemNumber()));
        }
        storage.setItemActive(st.isItemActive());

        if (st.getStoreArea() != null) {
            storage.setStoreArea(EncryptAES.encryptAES(st.getStoreArea()));
        }

        if (st.getStoreType() != null) {
            storage.setStoreType(EncryptAES.encryptAES(st.getStoreType()) );
        }

        if (st.getStoreSecction() != null) {
            storage.setStoreSecction(EncryptAES.encryptAES(st.getStoreSecction()) );
        }

        if (st.getStoreImage() != null) {
            storage.setStoreImage(st.getStoreImage());
        }

        if (st.getItemImage() != null) {
            storage.setItemImage(st.getItemImage());
        }

        if (st.getDateSave() != null) {
            storage.setDateSave(st.getDateSave());
        }

        if (st.getItemUse() != null) {
            storage.setItemUse(EncryptAES.encryptAES(st.getItemUse()) );
        }

        if (st.getUnit() != null) {
            storage.setUnit(EncryptAES.encryptAES(st.getUnit()) );
        }

        if (st.getType() != null) {
            storage.setType(EncryptAES.encryptAES(st.getType()) );
        }

        if (st.getStatusCloud() != null) {
            storage.setStatusCloud( EncryptAES.encryptAES(st.getStatusCloud()) );
        } else {
            storage.setStatusCloud("N");
        }

        if (st.getKeyId() != null) {
            storage.setKeyId(st.getKeyId());
        }

        if (st.getItemImageString() != null) {
            storage.setItemImageString(EncryptAES.encryptAES(st.getItemImageString()));
        }

        if (st.getItemImageBase64String() != null) {
            storage.setItemImageBase64String(EncryptAES.encryptAES(st.getItemImageBase64String()));
        }
        if(!st.isShowAlert()){
            storage.setShowAlert(false);
        }else{
            storage.setShowAlert(st.isShowAlert());
        }

        String type = st.getType().trim().toLowerCase();
        String comparator = "Material".toLowerCase();

        if (type.equals(comparator) && st.getAlertAmounts() > 0) {
            storage.setAlertAmounts(st.alertAmounts);
        }else {
            storage.setAlertAmounts(0);
        }

        return storage;
    }

    @Exclude
    public static HashMap<String, Object> toMap(Storage st) {

        HashMap<String, Object> result = new HashMap<>();

        if( st.getUid() >= 0 ){ result.put("uid", st.getUid());}
        result.put("itemActive", st.isItemActive());
        result.put("showAlert", st.isShowAlert());
        if( st.getItemName() !=null){result.put("itemName",st.getItemName());}

        if( st.getItemType() !=null){result.put("itemType", st.getItemType());}

        if(  st.getItemDescription() !=null){result.put("itemDescription", st.getItemDescription());}

        if( st.getItemNumber() !=null){ result.put("itemNumber", st.getItemNumber());}

        if( st.getItemUse() !=null){ result.put("itemUse", st.getItemUse());}

        if( st.getStoreArea() !=null){ result.put("storeArea", st.getStoreArea());}

        if( st.getStoreType()!=null){result.put("storeType", st.getStoreType());}

        if( st.getStoreSecction() !=null){result.put("storeSecction", st.getStoreSecction());}

        if(st.getStatusCloud() !=null){ result.put("statusCloud", st.getStatusCloud());}

        if( st.getType()!=null){ result.put("type", st.getType());}

        if( st.getCategory() !=null){ result.put("category", st.getCategory());}

        if( st.getAlertAmounts() >= 0 ){  result.put("alertAmounts", st.getAlertAmounts());}

        if(st.getUser() !=null){ result.put("user", st.getUser());}

        if( st.getKeyId() !=null){   result.put("keyId", st.getKeyId());}

        if( st.getNote() !=null){ result.put("note", st.getNote());}

        if( st.getStatusInventario() !=null){result.put("statusInventario", st.getStatusInventario());}

        if( st.getDateSave() !=null){result.put("dateSave", st.getDateSave());}

        if( st.getUnit() !=null){ result.put("unit", st.getUnit());}

        if( st.getItemImage() !=null){  result.put("itemImage", st.getItemImage());}

        if( st.getStoreImage() !=null){  result.put("storeImage", st.getStoreImage());}

        if(st.getiDate() !=null){ result.put("iDate", st.getiDate());}

        if( st.getItemImageString() !=null){ result.put("itemImageString", st.getItemImageString());}

        if(st.getItemImageBase64String() !=null){
            result.put("itemImageBase64String", st.getItemImageBase64String());
        }

        return result;
    }

    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        if( this.uid >= 0 ){
            result.put("uid", this.uid);
        }

        result.put("itemActive", this.itemActive);
        result.put("showAlert", this.showAlert);

        if( this.itemName!=null){result.put("itemName", this.itemName);}

        if( this.itemType !=null){result.put("itemType", this.itemType);}

        if(  this.itemDescription!=null){result.put("itemDescription", this.itemDescription);}

        if( this.itemNumber !=null){ result.put("itemNumber", this.itemNumber);}

        if( this.itemUse !=null){ result.put("itemUse", this.itemUse);}

        if( this.storeArea !=null){ result.put("storeArea", this.storeArea);}

        if(  this.storeType!=null){result.put("storeType", this.storeType);}

        if( this.storeSecction!=null){result.put("storeSecction", this.storeSecction);}

        if(this.statusCloud !=null){ result.put("statusCloud", this.statusCloud);}

        if(  this.type!=null){ result.put("type", this.type);}

        if( this.category!=null){ result.put("category", this.category);}

        if(  this.alertAmounts >= 0 ){  result.put("alertAmounts", this.alertAmounts);}

        if( this.user !=null){ result.put("user", this.user);}

        if( this.keyId !=null){   result.put("keyId", this.keyId);}

        if( this.note !=null){ result.put("note", this.note);}

        if( this.statusInventario !=null){result.put("statusInventario", this.statusInventario);}

        if( this.dateSave !=null){result.put("dateSave", this.dateSave);}

        if( this.unit !=null){ result.put("unit", this.unit);}

        if( this.itemImage !=null){  result.put("itemImage", this.itemImage);}

        if( this.storeImage !=null){  result.put("storeImage", this.storeImage);}

        if( this.iDate !=null){ result.put("iDate", this.iDate);}

        if( this.itemImageString !=null){ result.put("itemImageString", this.itemImageString);}

        if(this.itemImageBase64String !=null){result.put("itemImageBase64String", this.itemImageBase64String);}

        return result;
    }


    // add //1
    public static Storage createNew(Storage st) {
        Storage storage = new Storage();

        if (st.getItemName() != null) {
            storage.setItemName(st.getItemName());
        }
        if (st.getItemType() != null) {
            storage.setItemType(st.getItemType());
        }
        if (st.getItemDescription() != null) {
            storage.setItemDescription(st.getItemDescription());
        }
        if (st.getItemNumber() != null) {
            storage.setItemNumber(st.getItemNumber());
        }
        storage.setItemActive(st.isItemActive());

        if (st.getStoreArea() != null) {
            storage.setStoreArea(st.getStoreArea());
        }

        if (st.getStoreType() != null) {
            storage.setStoreType(st.getStoreType());
        }

        if (st.getStoreSecction() != null) {
            storage.setStoreSecction(st.getStoreSecction());
        }

        if (st.getStoreImage() != null) {
            storage.setStoreImage(st.getStoreImage());
        }

        if (st.getItemImage() != null) {
            storage.setItemImage(st.getItemImage());
        }

        if (st.getItemImageString() != null) {
            storage.setItemImageString(st.getItemImageString());
        }

        if (st.getDateSave() != null) {
            storage.setDateSave(st.getDateSave());
        }

        if (st.getItemNumber() != null) {
            storage.setItemNumber(st.getItemNumber());
        }

        if (st.getItemUse() != null) {
            storage.setItemUse(st.getItemUse());
        }

        if (st.getUnit() != null) {
            storage.setUnit(st.getUnit());
        }

        if (st.getType() != null) {
            storage.setType(st.getType());
        }

        if (st.getStatusCloud() != null) {
            storage.setStatusCloud(st.getStatusCloud());
        } else {
            storage.setStatusCloud("N");
        }

        if (st.getKeyId() != null) {
            storage.setKeyId(st.getKeyId());
        }

        if (st.getItemImageBase64String() != null) {
            storage.setItemImageBase64String(st.getItemImageBase64String());
        }
        if(!st.isShowAlert()){
            storage.setShowAlert(false);
        }else{
            storage.setShowAlert(st.isShowAlert());
        }

        return storage;
    }

    public void convert(byte[] by, String stringImage) {
        Gson gson = new Gson();
        CoverImage convert = null;
        if (by != null) {
            convert = new CoverImage(by);
            String itemListInGson = gson.toJson(convert);
            this.itemImageString = itemListInGson;

        } else {
            convert = gson.fromJson(stringImage, CoverImage.class);
            this.itemImage = convert.getItemImage();
        }
    }

    public String getItemImageBase64String() {
        return itemImageBase64String;
    }

    public void setItemImageBase64String(String itemImageBase64String) {
        this.itemImageBase64String = itemImageBase64String;
    }

    public String getItemImageString() {
        return itemImageString;
    }

    public void setItemImageString(String itemImageString) {
        this.itemImageString = itemImageString;
    }

    public String getStatusCloud() {
        return statusCloud;
    }

    public void setStatusCloud(String statusCloud) {
        this.statusCloud = statusCloud;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAlertAmounts() {
        return alertAmounts;
    }

    public void setAlertAmounts(int alertAmounts) {
        this.alertAmounts = alertAmounts;
    }

    public boolean isShowAlert() {
        return showAlert;
    }

    public void setShowAlert(boolean showAlert) {
        this.showAlert = showAlert;
    }

    public String getStatusInventario() {
        return statusInventario;
    }

    public void setStatusInventario(String statusInventario) {
        this.statusInventario = statusInventario;
    }

    public String getItemUse() {
        return itemUse;
    }

    public void setItemUse(String itemUse) {
        this.itemUse = itemUse;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getiDate() {
        return iDate;
    }

    public void setiDate(Date iDate) {
        this.iDate = iDate;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public boolean isItemActive() {
        return itemActive;
    }

    public void setItemActive(boolean itemActive) {
        this.itemActive = itemActive;
    }

    public String getStoreArea() {
        return storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreSecction() {
        return storeSecction;
    }

    public void setStoreSecction(String storeSecction) {
        this.storeSecction = storeSecction;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public byte[] getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(byte[] storeImage) {
        this.storeImage = storeImage;
    }

    public Date getDateSave() {
        return dateSave;
    }

    public void setDateSave(Date dateSave) {
        this.dateSave = dateSave;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "uid=" + uid +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemActive=" + itemActive +
                ", itemNumber='" + itemNumber + '\'' +
                ", itemUse='" + itemUse + '\'' +
                ", storeArea='" + storeArea + '\'' +
                ", storeType='" + storeType + '\'' +
                ", storeSecction='" + storeSecction + '\'' +
                ", statusCloud='" + statusCloud + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", alertAmounts=" + alertAmounts +
                ", iDate=" + iDate +
                ", keyId=" + keyId +
                ", dateSave=" + dateSave +
                ", unit='" + unit + '\'' +
                '}';
    }
}
