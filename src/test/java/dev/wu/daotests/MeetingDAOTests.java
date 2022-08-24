package dev.wu.daotests;

import dev.wu.daos.MeetingDAO;
import dev.wu.daos.MeetingDAOPostgres;
import dev.wu.entities.Meeting;
import dev.wu.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeetingDAOTests {

    static MeetingDAO meetingDAO = new MeetingDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "create table meeting(\n" +
                    "id serial primary key,\n" +
                    "location varchar(20) not null,\n" +
                    "date int,\n" +
                    "summary varchar(200)\n" +
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
            String sql = "drop table meeting";
            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_new_meeting_test(){
        Meeting meeting = new Meeting(0, "Sotenbori", 10192022, "Discussing complaint here!");
        Meeting createdMeeting = meetingDAO.createMeeting(meeting);
        Assertions.assertEquals("Discussing complaint here!", createdMeeting.getSummary());
    }

    @Test
    @Order(2)
    void get_all_meetings_test(){
        List<Meeting> meetings = meetingDAO.viewAllMeetings();
        Assertions.assertNotEquals(0, meetings.size()); // should have dummy meeting
        System.out.println(meetings);
    }
}
