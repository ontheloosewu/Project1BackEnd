package dev.wu.services;

import dev.wu.entities.Resident;

import java.util.List;

public interface ResidentService {

    Resident newValidUser(Resident resident);

    boolean validApprovalByUsername(String username);

    List<Resident> getAllUsers();
}
