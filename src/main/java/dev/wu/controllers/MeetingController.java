package dev.wu.controllers;

import com.google.gson.Gson;
import dev.wu.entities.Meeting;
import dev.wu.services.MeetingService;
import io.javalin.http.Handler;

public class MeetingController {

    private final Gson gson = new Gson();

    private MeetingService meetingService;

    public MeetingController (MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    public Handler viewAllMeetingsHandler = ctx -> {
        Gson gson = new Gson();
        String json = gson.toJson(meetingService.viewAllMeetings());
        ctx.result(json);
    };

    public Handler createMeetingHandler = ctx -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Meeting meeting = gson.fromJson(body, Meeting.class);
        Meeting newMeeting = meetingService.createValidNewMeeting(meeting);
        String json = gson.toJson(newMeeting);

        ctx.status(201);
        ctx.result(json);
    };
}
