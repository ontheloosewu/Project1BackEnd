package dev.wu.daos;

import dev.wu.entities.Resident;
import dev.wu.entities.UserType;
import dev.wu.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResidentDAOPostgres implements ResidentDAO{
    @Override
    public Resident registerUser(Resident resident) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "insert into resident values (default, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, resident.getUsername());
            preparedStatement.setString(2, resident.getPassword());
            preparedStatement.setString(3, resident.getUserType().toString());

            preparedStatement.execute();

            return resident;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Resident getResidentByUsername(String username) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from resident where username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Resident resident = new Resident();
            resident.setId(rs.getInt("id"));
            resident.setUsername(rs.getString("username"));
            resident.setPassword(rs.getString("password"));
            resident.setUserType(UserType.valueOf(rs.getString("usertype")));

            return resident;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Resident> getAllUsers() {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select id, username, usertype from resident";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Resident> residents = new ArrayList();
            while(resultSet.next()){
                Resident resident = new Resident();
                resident.setId(resultSet.getInt("id"));
                resident.setUsername(resultSet.getString("username"));
                resident.setUserType(UserType.valueOf(resultSet.getString("usertype")));
                residents.add(resident);
            }

            return residents;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean approveRegistrationByUsername(String username) {
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "update resident set usertype = ? where username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, UserType.REGISTERED.toString());
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
