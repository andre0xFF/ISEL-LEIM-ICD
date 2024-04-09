package pt.isel.icd.messaging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isel.icd.messaging.messages.Message;
import pt.isel.icd.patterns.observer.Subscriber;
import pt.isel.icd.serialization.XMLSerializer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ConnectionTest {

    private Connection connection;
    private SocketFacade socketFacade;
    private XMLSerializer<Message> serializer;
    private Subscriber<Message> subscriber;
    private Message message;
    private SchemaValidator schemaValidator;

    @BeforeEach
    void setUp() {
        message = mock(Message.class);
        socketFacade = mock(SocketFacade.class);
        serializer = mock(XMLSerializer.class);
        subscriber = mock(Subscriber.class);
        connection = new Connection(socketFacade);
        schemaValidator = mock(SchemaValidator.class);
    }

    @Test
    void isConnected() {
        when(socketFacade.isConnected()).thenReturn(true);
        assertTrue(connection.isConnected());
    }

    @Test
    void close() throws IOException {
        connection.close();
        verify(socketFacade, times(1)).close();
    }
}
