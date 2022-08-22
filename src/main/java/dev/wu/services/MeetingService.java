package dev.wu.services;

import dev.wu.entities.Meeting;

import java.util.List;

public interface MeetingService {

    List<Meeting> viewAllMeetings();

    Meeting createValidNewMeeting(Meeting meeting);
}
