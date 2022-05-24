package controllers.commands;

import behavioral.command.Command;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import controllers.Controller;
import controllers_bkp.messages.Authenticate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(Authenticate.class),
                @JsonSubTypes.Type(PingCommand.class),
                @JsonSubTypes.Type(PongCommand.class)
        }
)
@JsonRootName(value = "CommandController")
public interface CommandController extends Command<Controller> {

    @JsonIgnore
    void setController(Controller controller);
}
