package dev.wu.services;

import dev.wu.entities.Resident;

public interface LoginService {

    Resident validateUser(String username, String password);
}
