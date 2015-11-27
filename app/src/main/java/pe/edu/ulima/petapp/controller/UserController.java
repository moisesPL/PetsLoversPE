package pe.edu.ulima.petapp.controller;

import pe.edu.ulima.petapp.dao.User;

public class UserController {

    private static UserController instance;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserController getInstance(){
        if(instance==null)
            instance = new UserController();
        return instance;
    }

    public UserController() {
        user = new User();
    }
}
