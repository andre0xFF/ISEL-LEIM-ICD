package messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;

public class SubmitPlayerDetails implements Message {

    private final String controllerName;

    @JsonCreator
    public SubmitPlayerDetails(@JsonSetter("controllerName") String controllerName) {
        this.controllerName = controllerName;
    }

    @Override
    public String getControllerName() {
        return controllerName;
    }
}
