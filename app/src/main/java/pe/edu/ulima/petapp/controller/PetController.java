package pe.edu.ulima.petapp.controller;

import java.util.ArrayList;
import java.util.List;

import pe.edu.ulima.petapp.dao.Pet;


public class PetController {

    private static PetController instance;
    private ArrayList<Pet> petArray;

    public PetController(){
        petArray = new ArrayList<>();
    }

    public static PetController getInstance(){
        if(instance==null)
            instance = new PetController();
        return instance;
    }

    public ArrayList<Pet> getPetArray() {
        return petArray;
    }

    public void setPetArray(ArrayList<Pet> petArray) {
        this.petArray = petArray;
    }
}
