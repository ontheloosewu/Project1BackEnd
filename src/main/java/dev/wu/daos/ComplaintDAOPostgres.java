package dev.wu.daos;

import dev.wu.entities.Complaint;
import dev.wu.entities.Priority;
import dev.wu.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAOPostgres implements ComplaintDAO{
    @Override
    public Complaint newComplaint(Complaint complaint) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "insert into complaint values (default, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, complaint.getCompText());
            preparedStatement.setString(2, complaint.getPriority().toString());
            preparedStatement.setInt(3, complaint.getMeetingId());

            preparedStatement.execute();

            return complaint;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Complaint> viewComplaints() {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from complaint";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Complaint> complaints = new ArrayList();
            while(resultSet.next()){
                Complaint complaint = new Complaint();
                complaint.setComplaintId(resultSet.getInt("id"));
                complaint.setCompText(resultSet.getString("comptext"));
                complaint.setPriority(Priority.valueOf(resultSet.getString("priority")));
                complaint.setMeetingId(resultSet.getInt("meetingid"));
                complaints.add(complaint);
            }

            return complaints;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Complaint getComplaintById(int id) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from complaint where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Complaint complaint = new Complaint();
            complaint.setComplaintId(resultSet.getInt("id"));
            complaint.setCompText(resultSet.getString("comptext"));
            complaint.setPriority(Priority.valueOf(resultSet.getString("priority")));
            complaint.setMeetingId(resultSet.getInt("meetingid"));

            preparedStatement.execute();

            return complaint;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Complaint updateComplaint(Complaint complaint) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "update complaint set comptext = ?, priority = ?, meetingid = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, complaint.getCompText());
            preparedStatement.setString(2, complaint.getPriority().toString());
            preparedStatement.setInt(3, complaint.getMeetingId());
            preparedStatement.setInt(4, complaint.getComplaintId());

            preparedStatement.executeUpdate();

            return complaint;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
