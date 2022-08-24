package dev.wu.daotests;

import dev.wu.daos.ComplaintDAO;
import dev.wu.daos.ComplaintDAOPostgres;
import dev.wu.entities.Complaint;
import dev.wu.entities.Priority;
import dev.wu.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintDAOTests {

    static ComplaintDAO complaintDAO = new ComplaintDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            Statement statement = connection.createStatement();
            String sql = "create table meeting(\n" +
                    "id serial primary key,\n" +
                    "location varchar(20) not null,\n" +
                    "date int,\n" +
                    "summary varchar(200)\n" +
                    ");\n";
            String sql2 = "create table complaint(\n" +
                    "id serial primary key,\n" +
                    "comptext varchar(255) not null,\n" +
                    "priority varchar(10) not null,\n" +
                    "meetingid int references meeting(id)\n" +
                    ");\n";
            String sql3 = "insert into meeting values (-1, 'NO REAL LOCATION', -1, 'NOT A REAL MEETING');";
            statement.addBatch(sql);
            statement.addBatch(sql2);
            statement.addBatch(sql3);
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void teardown() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            Statement statement = connection.createStatement();
            String sql = "drop table complaint";
            String sql2 = "drop table meeting";
            statement.addBatch(sql);
            statement.addBatch(sql2);
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_new_complaint_test(){
        Complaint complaint = new Complaint(0, "There's too much litter in these streets", Priority.UNREVIEWED, -1);
        Complaint loggedComplaint = complaintDAO.newComplaint(complaint);
        Assertions.assertNotEquals(-1, loggedComplaint.getComplaintId());
        System.out.println(loggedComplaint);
    }

    @Test
    @Order(2)
    void get_all_complaints_test(){
        List<Complaint> complaints = complaintDAO.viewComplaints();
        Assertions.assertEquals(1, complaints.size());
    }

    @Test
    @Order(3)
    void get_complaint_by_id_test(){
        Complaint complaint = complaintDAO.getComplaintById(1);
        Assertions.assertEquals(Priority.UNREVIEWED, complaint.getPriority());
    }

    @Test
    @Order(4)
    void update_complaint_test(){
        Complaint newComplaint = new Complaint(1, "There's too much trash in these streets", Priority.UNREVIEWED, -1);
        complaintDAO.updateComplaint(newComplaint);
        Assertions.assertEquals("There's too much trash in these streets", complaintDAO.getComplaintById(1).getCompText());
    }
}
