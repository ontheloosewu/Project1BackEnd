package dev.wu.services;

import dev.wu.daos.ResidentDAO;
import dev.wu.entities.Resident;
import dev.wu.exceptions.NoResidentFoundException;
import dev.wu.exceptions.PasswordMismatchException;

public class LoginServiceImpl implements LoginService{

    private final ResidentDAO residentDAO;

    public LoginServiceImpl (ResidentDAO residentDAO) { this.residentDAO = residentDAO; }
    @Override
    public Resident validateUser(String username, String password) {
        Resident resident = this.residentDAO.getResidentByUsername(username);
        if(resident == null){
            throw new NoResidentFoundException("No employee found with that username");
        }
        if(!resident.getPassword().equals(password)){
            throw new PasswordMismatchException("Password is incorrect.");
        }

        return resident;
    }
}
