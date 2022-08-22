package dev.wu.daos;

import dev.wu.entities.Meeting;

import java.util.List;

public interface MeetingDAO {

    List<Meeting> viewAllMeetings();

    Meeting createMeeting(Meeting meeting);
}
