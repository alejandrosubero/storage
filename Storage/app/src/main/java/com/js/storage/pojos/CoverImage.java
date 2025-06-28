package com.js.storage.pojos;

import java.io.Serializable;

public class CoverImage implements Serializable  {
    int uid;
    byte [] itemImage;

    public CoverImage() {
    }

    public CoverImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }
}
