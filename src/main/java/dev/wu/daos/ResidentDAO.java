package dev.wu.daos;

import dev.wu.entities.Resident;

import java.util.List;

public interface ResidentDAO {

    Resident registerUser(Resident resident);

    Resident getResidentByUsername(String username);

    boolean approveRegistrationByUsername(String username);

    List<Resident> getAllUsers();
}
