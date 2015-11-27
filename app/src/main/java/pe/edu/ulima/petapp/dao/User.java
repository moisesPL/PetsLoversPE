package pe.edu.ulima.petapp.dao;

public class User {

    private String userName;
    private String userEmail;
    private String userCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userCode='" + userCode + '\'' +
                '}';
    }

    public User(String userName, String userEmail, String userCode) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCode = userCode;
    }

    public User(){}

}
