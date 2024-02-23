package org.example;

import java.util.ArrayList;

public class Animal extends Entity {
    private final String species;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private static int nextAnimalId = 1;


    public Animal(int id, String name, String species) {
        super(id, name);
        if (id > nextAnimalId) {
            nextAnimalId = id + 1;
        }
        this.species = species;
    }

    public Animal(String name, String species) {
        super(nextAnimalId, name);
        nextAnimalId++;
        this.species = species;
    }

    public String getDescription() {
        return super.getDescription() + ", Species: " + species;
    }

    public void Feed(Crop crop) {
        if (crop.quantity > 0) {
            crop.quantity--;
            System.out.println("Fed " + name + " with " + crop.cropType + ". You have " + crop.quantity + " " + crop.cropType + " left.");
        } else {
            System.out.println("There is no " + crop.cropType + " left. Try something else.");
        }
    }

    public String getCSV() {
        return id + "," + name + "," + species;
    }
    public static void setNextAnimalId(int nextAnimalId) {
        Animal.nextAnimalId = nextAnimalId;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }
}
