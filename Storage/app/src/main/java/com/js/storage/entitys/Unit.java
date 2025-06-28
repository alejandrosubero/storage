package com.js.storage.entitys;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Unit")
public class Unit  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int unitId;

    @ColumnInfo(name = "unitName")
    private String name;

    @ColumnInfo(name = "unitImage", typeAffinity = ColumnInfo.BLOB)
    byte[] unitImage;

    @ColumnInfo(name = "unitActive")
    boolean unitActive;

    @ColumnInfo(name = "defaultunit")
    boolean defaultunit;

    public Unit() {
        unitActive = true;
    }

    public Unit(String name) {
        this.name = name;
        this.unitActive = true;
    }

    public Unit unitDefaultSet(String name) {
        Unit u = new Unit(name);
        u.setDefaultunit(true);
        return u;
    }

    public boolean isDefaultunit() {
        return defaultunit;
    }

    public void setDefaultunit(boolean defaultunit) {
        this.defaultunit = defaultunit;
    }

    public boolean isUnitActive() {
        return unitActive;
    }

    public void setUnitActive(boolean unitActive) {
        this.unitActive = unitActive;
    }

    public byte[] getUnitImage() {
        return unitImage;
    }

    public void setUnitImage(byte[] unitImage) {
        this.unitImage = unitImage;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
