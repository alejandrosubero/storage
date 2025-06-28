package com.js.storage.pojos;

import java.util.Date;

public class StoragePojo {

    int uid;
    String itemName;
    String itemType;
    String itemDescription;
    String itemNumber;
    boolean itemActive;
    String storeArea;
    String storeType;
    String storeSecction;
    byte [] itemImage;
    byte [] storeImage;
    Date dateSave;


    public StoragePojo() {
    }

    public StoragePojo(String itemName, String itemType, String itemDescription, String itemNumber, String storeArea, String storeType, String storeSecction, byte[] itemImage) {

        this.itemName = itemName;

        this.itemType = itemType;

        this.itemDescription = itemDescription;

        this.itemNumber = itemNumber;

        this.itemActive = itemActive;

        this.storeArea = storeArea;

        this.storeType = storeType;

        this.storeSecction = storeSecction;

        this.itemImage = itemImage;

        this.dateSave = dateSave;
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
}
