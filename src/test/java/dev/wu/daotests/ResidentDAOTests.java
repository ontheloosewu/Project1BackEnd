package dev.wu.daotests;

import dev.wu.daos.ResidentDAO;
import dev.wu.daos.ResidentDAOPostgres;
import dev.wu.entities.Resident;
import dev.wu.entities.UserType;
import dev.wu.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResidentDAOTests {

    static ResidentDAO residentDAO = new ResidentDAOPostgres();

    @BeforeAll
    static void setup() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "create table resident(\n" +
                    "id serial primary key,\n" +
                    "username varchar(40),\n" +
                    "password varchar(40),\n" +
                    "usertype varchar(20) not null\n" +
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
            String sql = "drop table resident";
            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void register_new_resident_test(){
        Resident resident = new Resident(0, "partygoer5050", "regular", UserType.PENDING);
        residentDAO.registerUser(resident);
        Assertions.assertNotEquals(-1, resident.getId());
    }

    @Test
    @Order(2)
    void get_resident_by_username_test(){
        Resident resident = residentDAO.getResidentByUsername("partygoer5050");
        Assertions.assertEquals(1, resident.getId());
    }

    @Test
    @Order(3)
    void approve_registration_test(){
        residentDAO.approveRegistrationByUsername("partygoer5050");
        Assertions.assertEquals(UserType.REGISTERED, residentDAO.getResidentByUsername("partygoer5050").getUserType());
    }

    @Test
    @Order(4)
    void get_all_users_test(){
        Resident resident = new Resident(0, "partygoer5051", "regular", UserType.PENDING);
        Resident resident2 = new Resident(0, "partygoer5052", "regular", UserType.PENDING);
        Resident resident3 = new Resident(0, "partygoer5053", "regular", UserType.PENDING);
        Resident resident4 = new Resident(0, "partygoer5054", "regular", UserType.PENDING);
        residentDAO.registerUser(resident);
        residentDAO.registerUser(resident2);
        residentDAO.registerUser(resident3);
        residentDAO.registerUser(resident4);
        Assertions.assertEquals(5, residentDAO.getAllUsers().size());
    }
}
