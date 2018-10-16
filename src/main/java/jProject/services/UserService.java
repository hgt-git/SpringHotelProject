package jProject.services;


import jProject.forms.RegisterForm;
import jProject.models.UserData;


public interface UserService {

    String addNewUser(RegisterForm RegForm);
    Iterable<UserData> getAllUsers();
    boolean isUnique(String email);
    UserData getUserId(String name);

}
