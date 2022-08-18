package dev.wu.daotests;

import dev.wu.daos.ComplaintDAO;
import dev.wu.daos.ComplaintDAOPostgres;
import dev.wu.entities.Complaint;
import dev.wu.entities.Priority;
import dev.wu.utils.ConnectionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ComplaintDAOTests {

    static ComplaintDAO complaintDAO = new ComplaintDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "create table complaint(\n" +
                    "id serial primary key,\n" +
                    "comptext varchar(255) not null,\n" +
                    "priority varchar(10) not null,\n" +
                    "meetingid int references meeting(id)\n" +
                    ");\n";

            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void teardown() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "drop table complaint";
            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void create_new_complaint_test(){
        Complaint complaint = new Complaint(0, "There's too much litter in these streets", Priority.UNREVIEWED, -1);
        Complaint loggedComplaint = complaintDAO.newComplaint(complaint);
        Assertions.assertNotEquals(-1, loggedComplaint.getComplaintId());
        System.out.println(loggedComplaint);
    }
}
