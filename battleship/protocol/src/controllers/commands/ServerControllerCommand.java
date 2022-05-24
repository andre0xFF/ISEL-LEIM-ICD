package controllers.commands;

import behavioral.command.Command;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import controllers.ServerController;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(PingCommand.class),
                @JsonSubTypes.Type(Authenticate.class)
        }
)
@JsonRootName(value = "ServerControllerCommand")
public interface ServerControllerCommand extends CommandController  {

    @JsonIgnore
    void setController(ServerController controller);
}
