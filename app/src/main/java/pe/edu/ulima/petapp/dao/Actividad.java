package pe.edu.ulima.petapp.dao;


import java.util.Date;

public class Actividad {

    private Date date;
    private String time;
    private String tipo;
    private int alerta;
    private String petID;
    private String iD;
    private int petPosition;

    public Actividad(Date date, String time, String tipo, int alerta
            , String petID, String iD, int petPosition) {
        this.date = date;
        this.time = time;
        this.tipo = tipo;
        this.alerta = alerta;
        this.petID = petID;
        this.iD = iD;
        this.petPosition = petPosition;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "date=" + date +
                ", time='" + time + '\'' +
                ", tipo='" + tipo + '\'' +
                ", alerta=" + alerta +
                ", petID='" + petID + '\'' +
                ", iD='" + iD + '\'' +
                ", petPosition=" + petPosition +
                '}';
    }

    public int getPetPosition() {
        return petPosition;
    }

    public void setPetPosition(int petPosition) {
        this.petPosition = petPosition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAlerta() {
        return alerta;
    }

    public void setAlerta(int alerta) {
        this.alerta = alerta;
    }

    public String getPetID() {
        return petID;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }


}
