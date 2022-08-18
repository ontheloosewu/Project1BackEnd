package dev.wu.app;

import com.google.gson.Gson;
import dev.wu.daos.ComplaintDAOPostgres;
import dev.wu.daos.MeetingDAOPostgres;
import dev.wu.entities.Complaint;
import dev.wu.services.ComplaintService;
import dev.wu.services.ComplaintServiceImpl;
import dev.wu.services.MeetingService;
import dev.wu.services.MeetingServiceImpl;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class App {

    public static ComplaintService complaintService = new ComplaintServiceImpl(new ComplaintDAOPostgres());

    public static MeetingService meetingService = new MeetingServiceImpl(new MeetingDAOPostgres());

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

        app.get("/complaints", viewAllMeetingsHandler);

        app.start();
    }
}
