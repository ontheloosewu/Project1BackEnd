package dev.wu.daos;

import dev.wu.entities.Meeting;
import dev.wu.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeetingDAOPostgres implements MeetingDAO{
    @Override
    public List<Meeting> viewAllMeetings() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from meeting";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            List<Meeting> meetings = new ArrayList();
            while (rs.next()) {
                Meeting meeting = new Meeting();
                meeting.setMeetingId(rs.getInt("id"));
                meeting.setLocation(rs.getString("location"));
                meeting.setDate(rs.getInt("date"));
                meeting.setSummary(rs.getString("summary"));
                meetings.add(meeting);
            }
            return meetings;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Meeting createMeeting(Meeting meeting) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "insert into meeting values (default, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, meeting.getLocation());
            preparedStatement.setLong(2, meeting.getDate());
            preparedStatement.setString(3, meeting.getSummary());

            preparedStatement.execute();

            return meeting;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
