package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Farm {
    ArrayList<Animal> animalList = new ArrayList<>();
    ArrayList<Crop> cropList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    File animalFile = new File("animals.txt");
    File cropFile = new File("crops.txt");


    public Farm() {
        //Load data from files if they exist
        try {
            FileReader animalFR = new FileReader(animalFile);
            BufferedReader animalBR = new BufferedReader(animalFR);
            String animalLine = animalBR.readLine();

            while (animalLine != null) {
                String[] animalVariables = animalLine.split(",");
                int animalId = Integer.parseInt(animalVariables[0]);
                String animalName = animalVariables[1];
                String animalSpecies = animalVariables[2];
                Animal animal = new Animal(animalId, animalName, animalSpecies);
                animalList.add(animal);
                animalLine = animalBR.readLine();
            }

            FileReader cropFR = new FileReader(cropFile);
            BufferedReader cropBR = new BufferedReader(cropFR);
            String cropLine = cropBR.readLine();

            while (cropLine != null) {
                String[] cropVariables = cropLine.split(",");
                int cropId = Integer.parseInt(cropVariables[0]);
                String cropName = cropVariables[1];
                String cropType = cropVariables[2];
                int cropQuantity = Integer.parseInt(cropVariables[3]);
                Crop crop = new Crop(cropId, cropName, cropType, cropQuantity);
                cropList.add(crop);
                cropLine = cropBR.readLine();
            }

            animalBR.close();
            cropBR.close();
        } catch (FileNotFoundException e) {
            // Add som initial animals and crops if the file doesn't exist
            animalList.add(new Animal("Cow", "Dairy"));
            animalList.add(new Animal("Pig", "Swine"));
            cropList.add(new Crop("Wheat", "Grain", 50));
            cropList.add(new Crop("Corn", "Grain", 30));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void MainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. View crops");
            System.out.println("2. Add crop");
            System.out.println("3. Remove crop");
            System.out.println("4. View animals");
            System.out.println("5. Add animal");
            System.out.println("6. Feed animal");
            System.out.println("7. Remove animal");
            System.out.println("8. Save");
            System.out.println("9. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewCrops();
                    break;
                case 2:
                    addCrop();
                    break;
                case 3:
                    removeCrop();
                    break;
                case 4:
                    viewAnimals();
                    break;
                case 5:
                    addAnimal();
                    break;
                case 6:
                    feedAnimal();
                    break;
                case 7:
                    removeAnimal();
                    break;
                case 8:
                    save();
                    break;
                case 9:
                    running = false;
                    System.out.println("Good bye!");
                    break;
                default:
                    System.out.println("Invalid choice, please choose a number.");
                    break;
            }
        }
    }

    private void viewCrops() {
        System.out.println("Crops on the farm:");
        for (Crop crop : cropList) {
            System.out.println(crop.getDescription());
        }
    }

    private void addCrop() {
        System.out.println("Enter the name for the new crop:");
        String name = scanner.nextLine();
        name = scanner.nextLine();
        System.out.println("Enter the crop type for the new crop:");
        String cropType = scanner.nextLine();
        System.out.println("Enter the quantity for the new crop:");
        int quantity = scanner.nextInt();

        Crop newCrop = new Crop(name, cropType, quantity);

        // Check if a crop with the same name already exists
        boolean cropExists = false;
        for (Crop crop : cropList) {
            if (crop.name.equals(name)) {
                crop.quantity += quantity;
                cropExists = true;
                break;
            }
        }

        if (!cropExists) {
            cropList.add(newCrop);
        }
        System.out.println("Crop added to the farm.");
    }

    private void removeCrop() {
        System.out.println("Enter the ID of the crop to remove:");
        int id = scanner.nextInt();

        for (Crop crop : cropList) {
            if (crop.getId() == id) {
                cropList.remove(crop);
                System.out.println("Crop removed from the farm.");
                break;
            }
            System.out.println("Crop with ID " + id + " not found.");
        }
    }

    private void viewAnimals() {
        System.out.println("Animals on the farm:");
        for (Animal animal : animalList) {
            System.out.println(animal.getDescription());
        }
    }

    private void addAnimal() {
        System.out.println("Enter the name of the new animal:");
        String name = scanner.nextLine();
        name = scanner.nextLine();
        System.out.println("Enter the species of the new animal:");
        String species = scanner.nextLine();
        Animal newAnimal = new Animal(name, species);
        animalList.add(newAnimal);
        System.out.println("Animal added to the farm.");
    }

    private void feedAnimal() {
        System.out.println("Enter the ID of the animal to feed:");
        int animalId = scanner.nextInt();
        System.out.println("Enter the ID of the crop to feed it with:");
        int cropId = scanner.nextInt();

        Animal selectedAnimal = null;
        for (Animal animal : animalList) {
            if (animal.getId() == animalId) {
                selectedAnimal = animal;
                break;
            }
        }

        Crop selectedCrop = null;
        for (Crop crop : cropList) {
            if (crop.getId() == cropId) {
                selectedCrop = crop;
                break;
            }
        }

        if (selectedAnimal != null && selectedCrop != null) {
            selectedAnimal.Feed(selectedCrop);
        } else {
            System.out.println("Animal or crop not found.");
        }
    }

    private void removeAnimal() {
        System.out.println("Enter the ID of the animal to remove:");
        int id = scanner.nextInt();

        for (Animal animal : animalList) {
            if (animal.getId() == id) {
                animalList.remove(animal);
                System.out.println("Animal removed from the farm.");
                break;
            }
            System.out.println("Animal with ID " + id + " not found.");
        }
    }


    private void save() {
        try {
            FileWriter fw = new FileWriter(animalFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0; i < animalList.size(); i++) {
                bw.write(animalList.get(i).getCSV());
                if (i < animalList.size() - 1) {
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FileWriter fw = new FileWriter(cropFile);
            BufferedWriter bw = new BufferedWriter(fw);
                for(int i = 0; i < cropList.size(); i++) {
                    bw.write(cropList.get(i).getCSV());
                    if (i < cropList.size() - 1) {
                        bw.newLine();
                    }
                }
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}