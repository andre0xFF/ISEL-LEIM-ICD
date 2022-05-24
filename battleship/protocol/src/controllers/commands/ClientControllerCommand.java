package controllers.commands;

import behavioral.command.Command;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import controllers.ClientController;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(PongCommand.class)
        }
)
@JsonRootName(value = "ServerControllerCommand")
public interface ClientControllerCommand extends CommandController  {

    @JsonIgnore
    void setController(ClientController controller);
}
