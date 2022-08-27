package dev.wu.controllers;

import com.google.gson.Gson;
import dev.wu.entities.Resident;
import dev.wu.services.ResidentService;
import io.javalin.http.Handler;

public class ResidentController {

    private final Gson gson = new Gson();

    private ResidentService residentService;

    public ResidentController (ResidentService residentService) {
        this.residentService = residentService;
    }

    public Handler registerUserHandler = ctx -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Resident resident = gson.fromJson(body, Resident.class);
        Resident registeredResident = residentService.newValidUser(resident);
        String json = gson.toJson(registeredResident);

        ctx.status(201);
        ctx.result(json);
    };

    public Handler getAllUsers = ctx -> {
        Gson gson = new Gson();
        String json = gson.toJson(residentService.getAllUsers());
        ctx.result(json);
    };

    public Handler approveRegistrationHandler = ctx -> {
        String username = ctx.pathParam("userName");
        boolean registeredResident = residentService.validApprovalByUsername(username);

        ctx.status(201);
        ctx.result(String.valueOf(registeredResident));
    };
}
