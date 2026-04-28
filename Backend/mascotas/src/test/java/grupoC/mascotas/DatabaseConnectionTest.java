package grupoC.mascotas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void databaseConnectionLoads() throws Exception {
        assertNotNull(dataSource, "El DataSource no debe ser nulo");

        try (Connection connection = dataSource.getConnection()) {
            assertTrue(connection.isValid(2), "La conexión a la base de datos MySQL no es válida");
        }
    }
}
