package dev.wu.app;

import com.google.gson.Gson;
import dev.wu.controllers.ComplaintController;
import dev.wu.controllers.MeetingController;
import dev.wu.controllers.ResidentController;
import dev.wu.daos.ComplaintDAOPostgres;
import dev.wu.daos.MeetingDAOPostgres;
import dev.wu.daos.ResidentDAOPostgres;
import dev.wu.dtos.LoginCredentials;
import dev.wu.entities.Resident;
import dev.wu.exceptions.DuplicateUsernameException;
import dev.wu.exceptions.NoResidentFoundException;
import dev.wu.exceptions.PasswordMismatchException;
import dev.wu.services.*;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class App {

    public static LoginService loginService = new LoginServiceImpl(new ResidentDAOPostgres());

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.enableDevLogging();
            config.enableCorsForAllOrigins();
        });

        ComplaintService complaintService = new ComplaintServiceImpl(new ComplaintDAOPostgres());
        ComplaintController complaintController = new ComplaintController(complaintService);

        MeetingService meetingService = new MeetingServiceImpl(new MeetingDAOPostgres());
        MeetingController meetingController = new MeetingController(meetingService);

        ResidentService residentService = new ResidentServiceImpl(new ResidentDAOPostgres());
        ResidentController residentController = new ResidentController(residentService);


        app.post("/complaints", complaintController.createComplaintHandler);

        app.get("/meetings", meetingController.viewAllMeetingsHandler);

        app.get("/complaints", complaintController.viewAllComplaintsHandler);

        app.put("/complaints/{idNum}", complaintController.updateComplaintHandler);

        app.post("/meetings", meetingController.createMeetingHandler);

        app.post("/register", residentController.registerUserHandler);

        app.patch("/register/{userName}/approve", residentController.approveRegistrationHandler);

        app.post("/login", ctx -> {
            String body = ctx.body();
            Gson gson = new Gson();
            LoginCredentials credentials = gson.fromJson(body, LoginCredentials.class);

            Resident resident = loginService.validateUser(credentials.getUsername(), credentials.getPassword());
            String residentJson = gson.toJson(resident);
            ctx.result(residentJson);
        });

        app.exception(NoResidentFoundException.class, (exception, ctx) -> {
            ctx.status(404);
            ctx.result("No resident found with this username");
        });

        app.exception(PasswordMismatchException.class, (exception, ctx) -> {
            ctx.status(400);
            ctx.result("Password is incorrect");
        });

        app.exception(DuplicateUsernameException.class, (exception, ctx) -> {
            ctx.status(409);
            ctx.result("Username already exists");
        });

        app.start();
    }
}
