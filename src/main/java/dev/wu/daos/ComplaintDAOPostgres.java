package dev.wu.daos;

import dev.wu.entities.Complaint;
import dev.wu.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
