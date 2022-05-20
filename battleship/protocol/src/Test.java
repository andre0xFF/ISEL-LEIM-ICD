import com.fasterxml.jackson.core.JsonProcessingException;
import messages.Message;
import messages.SubmitCredentials;
import sessions.Server;

public class Test {
    public static void main(String[] args) throws JsonProcessingException {
        SubmitCredentials submitCredentials = new SubmitCredentials("AccountController", "a", "123");

        String output = Server.OBJECT_MAPPER.writeValueAsString(submitCredentials);
        String input = output;

        Message message = Server.OBJECT_MAPPER.readValue(input, Message.class);
        System.out.println(message);
    }
}
