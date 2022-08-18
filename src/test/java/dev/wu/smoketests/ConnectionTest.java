package dev.wu.smoketests;

import dev.wu.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest {
    @Test
    void connection_available() {
        Connection connection = ConnectionUtil.createConnection();
        System.out.println(connection);
        Assertions.assertNotNull(connection);
    }
}
