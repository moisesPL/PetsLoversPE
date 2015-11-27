package pe.edu.ulima.petapp.dao;


public class Sms {

    private String body;
    private String number;
    private String id;
    private String time;

    @Override
    public String toString() {
        return "Sms{" +
                "body='" + body + '\'' +
                ", number='" + number + '\'' +
                ", id='" + id + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Sms(String body, String number, String id, String time) {

        this.body = body;
        this.number = number;
        this.id = id;
        this.time = time;
    }
}
