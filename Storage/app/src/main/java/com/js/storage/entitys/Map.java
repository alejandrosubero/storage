package com.js.storage.entitys;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Map")
public class Map  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int mapId;

    @ColumnInfo(name = "unit")
    private String unit;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "mapStringImage")
    private String mapStringImage;

    @ColumnInfo(name = "mapImage", typeAffinity = ColumnInfo.BLOB)
    private byte[] mapImage;

    @ColumnInfo(name = "mapActive")
    boolean mapActive;


    public Map() {
        this.mapActive = true;
    }

    public Map(String unit, byte[] mapImage) {
        this.unit = unit;
        this.mapImage = mapImage;
        this.mapActive = true;
    }

    public String getMapStringImage() {
        return mapStringImage;
    }

    public Map(String unit, String name, byte[] mapImage) {
        this.unit = unit;
        this.name = name;
        this.mapImage = mapImage;
        this.mapActive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMapActive() {
        return mapActive;
    }

    public void setMapActive(boolean mapActive) {
        this.mapActive = mapActive;
    }

    public void setMapStringImage(String mapStringImage) {
        this.mapStringImage = mapStringImage;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public byte[] getMapImage() {
        return mapImage;
    }

    public void setMapImage(byte[] mapImage) {
        this.mapImage = mapImage;
    }
}
