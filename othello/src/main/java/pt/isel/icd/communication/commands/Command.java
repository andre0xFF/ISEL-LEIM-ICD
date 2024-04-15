package pt.isel.icd.communication.commands;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A command is a data transfer object that is sent over the network.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonRootName("Command")
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = PingCommand.class),
                @JsonSubTypes.Type(value = PongCommand.class),
        }
)
public interface Command {

}
