package pt.isel.icd.service.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isel.icd.communication.Connection;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerTest {

    private Server server;

    @BeforeEach
    void setUp() throws IOException {
        server = new Server(0);

        server.accept();
    }

    @Test
    public void userIsAdded() throws IOException, InterruptedException {
        Connection connection = new Connection(server.port());

        // Wait for the connection to be established.
        Thread.sleep(500);

        assertTrue(connection.isConnected());
        assertEquals(1, server.connectionsTotal());
    }
}
