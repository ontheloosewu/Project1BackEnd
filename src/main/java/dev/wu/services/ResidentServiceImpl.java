package dev.wu.services;

import dev.wu.daos.ResidentDAO;
import dev.wu.entities.Resident;
import dev.wu.entities.UserType;
import dev.wu.exceptions.DuplicateUsernameException;

import java.util.List;

public class ResidentServiceImpl implements ResidentService{

    private final ResidentDAO residentDAO;

    public ResidentServiceImpl (ResidentDAO residentDAO) { this.residentDAO = residentDAO; }

    @Override
    public Resident newValidUser(Resident resident) {
        if(residentDAO.getResidentByUsername(resident.getUsername()) != null){
            throw new DuplicateUsernameException(("Username already exists"));
        }
        return this.residentDAO.registerUser(resident);
    }

    @Override
    public List<Resident> getAllUsers() {
        return this.residentDAO.getAllUsers();
    }

    @Override
    public boolean validApprovalByUsername(String username) {
        Resident resident = residentDAO.getResidentByUsername(username);
        if(resident.getUserType() == UserType.REGISTERED || resident.getUserType() == UserType.LIEUTENANT){
            throw new RuntimeException("User is already registered");
        }
        return this.residentDAO.approveRegistrationByUsername(username);
    }
}
