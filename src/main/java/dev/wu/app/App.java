package dev.wu.app;

import com.google.gson.Gson;
import dev.wu.daos.ComplaintDAOPostgres;
import dev.wu.daos.MeetingDAOPostgres;
import dev.wu.daos.ResidentDAOPostgres;
import dev.wu.dtos.LoginCredentials;
import dev.wu.entities.Complaint;
import dev.wu.entities.Resident;
import dev.wu.services.*;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class App {

    public static ComplaintService complaintService = new ComplaintServiceImpl(new ComplaintDAOPostgres());

    public static MeetingService meetingService = new MeetingServiceImpl(new MeetingDAOPostgres());

    public static ResidentService residentService = new ResidentServiceImpl(new ResidentDAOPostgres());

    public static LoginService loginService = new LoginServiceImpl(new ResidentDAOPostgres());

    public static void main(String[] args) {
        Javalin app = Javalin.create(config->{
            config.enableDevLogging();
            config.enableCorsForAllOrigins();
        });

        Handler createComplaintHandler = ctx -> {
            String body = ctx.body();
            Gson gson = new Gson();
            Complaint complaint = gson.fromJson(body, Complaint.class);
            Complaint newComplaint = App.complaintService.newValidComplaint(complaint);
            String json = gson.toJson(newComplaint);

            ctx.status(201);
            ctx.result(json);
        };

        app.post("/complaints", createComplaintHandler);

        Handler viewAllMeetingsHandler = ctx -> {
            Gson gson = new Gson();
            String json = gson.toJson(App.meetingService.viewAllMeetings());
            ctx.result(json);
        };

        app.get("/meetings", viewAllMeetingsHandler);

        Handler registerUserHandler = ctx -> {
            String body = ctx.body();
            Gson gson = new Gson();
            Resident resident = gson.fromJson(body, Resident.class);
            Resident registeredResident = App.residentService.newValidUser(resident);
            String json = gson.toJson(registeredResident);

            ctx.status(201);
            ctx.result(json);
        };

        app.post("/register", registerUserHandler);

        app.post("/login", ctx ->  {
            String body = ctx.body();
            Gson gson = new Gson();
            LoginCredentials credentials = gson.fromJson(body, LoginCredentials.class);

            Resident resident = loginService.validateUser(credentials.getUsername(), credentials.getPassword());
            String residentJson = gson.toJson(resident);
            ctx.result(residentJson);
        });

        app.start();
    }
}
