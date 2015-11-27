package pe.edu.ulima.petapp.controller;

import java.util.ArrayList;

import pe.edu.ulima.petapp.dao.Sms;

public class SmsController {

    private ArrayList<Sms> smsArrayList;
    private static SmsController instance;

    public SmsController(){
        smsArrayList = new ArrayList<>();
    }

    public static SmsController getInstance(){
        if(instance==null)
            instance = new SmsController();
        return instance;
    }

    public ArrayList<Sms> getSmsArrayList() {
        return smsArrayList;
    }

    public void setSmsArrayList(ArrayList<Sms> smsArrayList) {
        this.smsArrayList = smsArrayList;
    }
}
