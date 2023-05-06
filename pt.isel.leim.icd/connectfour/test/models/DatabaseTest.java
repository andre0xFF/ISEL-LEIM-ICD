package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseTest {

    private Model.Database database;

    @BeforeEach
    void setUp() {
        this.database = new Model.Database();
    }

    @Test
    void shouldLoadModel() throws IOException {
        Model model = this.database.load(Users.class);

        assertTrue(model instanceof Users);
    }

    @Test
    void shouldSaveModel() throws IOException {
        Users users = new Users();

        users.register(
                "janesmith",
                new char[]{'p', 'a', 's', 's', '4', '5', '6'}
        );

        users.register(
                "johndoe",
                new char[]{'a', 'b', 'c', '1', '2', '3'}
        );

        users.register(
                "bobsmith",
                new char[]{'q', 'w', 'e', 'r', 't', 'y'}
        );

        this.database.save(users);
    }
}
