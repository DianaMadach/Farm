package org.example;

import java.util.ArrayList;

public class Crop extends Entity {
    public String cropType;
    public int quantity;
    private static int nextCropId = 1;
    private ArrayList<Crop> crops = new ArrayList<>();

    public Crop(int id, String name, String cropType, int quantity) {
        super(id, name);
        if (id > nextCropId) {
            nextCropId = id + 1;
        }
        this.cropType = cropType;
        this.quantity = quantity;
    }

    public Crop(String name, String cropType, int quantity) {
        super(nextCropId, name);
        nextCropId++;
        this.cropType = cropType;
        this.quantity = quantity;
    }

    public String getDescription() {
        return super.getDescription() + ", Crop Type: " + cropType + ", Quantity: " + quantity;

    }

    public String getCSV() {
        return id + "," + name + "," + cropType + "," + quantity;
    }
    public static void setNextCropId(int nextCropId) {
        Crop.nextCropId = nextCropId;
    }
}
