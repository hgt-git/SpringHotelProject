package jProject.services;


import jProject.forms.RegisterForm;
import jProject.models.UserData;
import jProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    @Override
    public String addNewUser(RegisterForm RegForm) {
        UserData n = new UserData();
        n.setFirstName(RegForm.getFirstName());
        n.setLastName(RegForm.getLastName());
        n.setEmail(RegForm.getEmail());
        n.setPassword(passwordEncoder.encode(RegForm.getPassword()));
        n.setAuthority("USER");
        n.setEnabled(true);
        n.setRegisterdOn(Date.valueOf(LocalDate.now()));
        userRepository.save(n);
        return "Saved";
    }
    @Override
    public boolean isUnique(String email){

       if(userRepository.emailCount(email)>0)
        return true;
       else
        return false;
    }

    @Override
    public UserData getUserId(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public Iterable<UserData> getAllUsers() {
        return userRepository.findAll();
    }



}
