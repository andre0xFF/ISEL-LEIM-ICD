package controllers.commands;

import behavioral.command.Command;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import controllers.Controller;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(ServerControllerCommand.class),
                @JsonSubTypes.Type(ClientControllerCommand.class),
        }
)
@JsonRootName(value = "CommandController")
public interface CommandController extends Command<Controller> {

}
