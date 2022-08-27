package dev.wu.controllers;

import com.google.gson.Gson;
import dev.wu.entities.Complaint;
import dev.wu.services.ComplaintService;
import io.javalin.http.Handler;

public class ComplaintController {

    private final Gson gson = new Gson();
    private ComplaintService complaintService;
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    public Handler createComplaintHandler = ctx -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Complaint complaint = gson.fromJson(body, Complaint.class);
        Complaint newComplaint = complaintService.newValidComplaint(complaint);
        String json = gson.toJson(newComplaint);

        ctx.status(201);
        ctx.result(json);
    };

    public Handler viewAllComplaintsHandler = ctx -> {
        Gson gson = new Gson();
        String json = gson.toJson(complaintService.getAllComplaints());
        ctx.result(json);
    };

    public Handler updateComplaintHandler = ctx -> {
        int idNum = Integer.parseInt(ctx.pathParam("idNum"));
        if(complaintService.getComplaintById(idNum) == null){
            ctx.status(404);
            ctx.result("Complaint not found.");
        } else {
            String body = ctx.body();
            Gson gson = new Gson();
            Complaint complaint = gson.fromJson(body, Complaint.class);
            Complaint updatedComplaint = complaintService.updateComplaint(complaint);
            String json = gson.toJson(updatedComplaint);
            ctx.status(200);
            ctx.result(json);
        }
    };
}
