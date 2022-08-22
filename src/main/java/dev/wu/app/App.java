package dev.wu.app;

import com.google.gson.Gson;
import dev.wu.daos.ComplaintDAOPostgres;
import dev.wu.daos.MeetingDAOPostgres;
import dev.wu.daos.ResidentDAOPostgres;
import dev.wu.dtos.LoginCredentials;
import dev.wu.entities.Complaint;
import dev.wu.entities.Meeting;
import dev.wu.entities.Resident;
import dev.wu.exceptions.NoResidentFoundException;
import dev.wu.exceptions.PasswordMismatchException;
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

        Handler viewAllComplaintsHandler = ctx -> {
            Gson gson = new Gson();
            String json = gson.toJson(App.complaintService.getAllComplaints());
            ctx.result(json);
        };

        app.get("/complaints", viewAllComplaintsHandler);

        Handler updateComplaintHandler = ctx -> {
            int idNum = Integer.parseInt(ctx.pathParam("idNum"));
            if(App.complaintService.getComplaintById(idNum) == null){
                ctx.status(404);
                ctx.result("Complaint not found.");
            } else {
                String body = ctx.body();
                Gson gson = new Gson();
                Complaint complaint = gson.fromJson(body, Complaint.class);
                Complaint updatedComplaint = App.complaintService.updateComplaint(complaint);
                String json = gson.toJson(updatedComplaint);
                ctx.status(200);
                ctx.result(json);
            }
        };

        app.put("/complaints/{idNum}", updateComplaintHandler);

        Handler createMeetingHandler = ctx -> {
            String body = ctx.body();
            Gson gson = new Gson();
            Meeting meeting = gson.fromJson(body, Meeting.class);
            Meeting newMeeting = App.meetingService.createValidNewMeeting(meeting);
            String json = gson.toJson(newMeeting);

            ctx.status(201);
            ctx.result(json);
        };

        app.post("/meetings", createMeetingHandler);

        app.exception(NoResidentFoundException.class, (exception, ctx) -> {
            ctx.status(404);
            ctx.result("No resident found with this username");
        });

        app.exception(PasswordMismatchException.class, (exception, ctx) -> {
            ctx.status(400);
            ctx.result("Password is incorrect");
        });

        app.start();
    }
}
