package messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import messages.Message;

public class StartGame implements Message {

    private final String controllerName;

    @JsonCreator
    public StartGame(@JsonSetter("controllerName") String controllerName) {
        this.controllerName = controllerName;
    }

    @Override
    public String getControllerName() {
        return controllerName;
    }
}
