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
        Resident resident = new Resident(0, "partygoer5050", "regular", UserType.REGISTERED);
        residentDAO.registerUser(resident);
        Assertions.assertNotEquals(-1, resident.getId());
        System.out.println(resident);
    }

    @Test
    @Order(2)
    void get_resident_by_username_test(){
        Resident resident = residentDAO.getResidentByUsername("partygoer5050");
        Assertions.assertEquals(UserType.REGISTERED, resident.getUserType());
    }
}
