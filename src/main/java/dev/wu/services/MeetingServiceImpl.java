package dev.wu.services;

import dev.wu.daos.MeetingDAO;
import dev.wu.entities.Meeting;

import java.util.List;

public class MeetingServiceImpl implements MeetingService{

    private final MeetingDAO meetingDao;

    public MeetingServiceImpl (MeetingDAO meetingDAO){ this.meetingDao = meetingDAO; }
    @Override
    public List<Meeting> viewAllMeetings() {
        return this.meetingDao.viewAllMeetings();
    }

    @Override
    public Meeting createValidNewMeeting(Meeting meeting) {
        if(meeting.getDate() < System.currentTimeMillis()/1000){
            throw new RuntimeException("Meeting date must be in the FUTURE.");
        }
        return this.meetingDao.createMeeting(meeting);
    }
}
