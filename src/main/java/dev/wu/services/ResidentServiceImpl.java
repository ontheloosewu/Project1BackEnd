package dev.wu.services;

import dev.wu.daos.ResidentDAO;
import dev.wu.entities.Resident;
import dev.wu.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResidentServiceImpl implements ResidentService{

    private final ResidentDAO residentDAO;

    public ResidentServiceImpl (ResidentDAO residentDAO) { this.residentDAO = residentDAO; }

    @Override
    public Resident newValidUser(Resident resident) {
        List<String> usernames = new ArrayList();
        try (Connection connection = ConnectionUtil.createConnection()){
            String sql = "select username from resident";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(usernames.contains(resident.getUsername())){
            throw new RuntimeException(("Username already exists"));
        }
        usernames.add(resident.getUsername());
        return this.residentDAO.registerUser(resident);
    }
}
