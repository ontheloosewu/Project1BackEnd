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
}
