package dev.wu.daos;

import dev.wu.entities.Resident;

public interface ResidentDAO {

    Resident registerUser(Resident resident);

    Resident getResidentByUsername(String username);
}
