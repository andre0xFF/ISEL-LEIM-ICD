package pt.isel.icd.communication;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pt.isel.icd.game.management.*;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.user.management.CreateUserCommand;
import pt.isel.icd.user.management.DeleteUserCommand;
import pt.isel.icd.user.management.UpdateUserCommand;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonRootName("Command")
@JsonSubTypes(
        {
                // Game Management Commands
                @JsonSubTypes.Type(value = JoinGameCommand.class),
                @JsonSubTypes.Type(value = LeaveGameCommand.class),
                @JsonSubTypes.Type(value = PlacePieceCommand.class),
                @JsonSubTypes.Type(value = ShowBoardCommand.class),
                @JsonSubTypes.Type(value = UpdateBoardCommand.class),

                // User Management Commands
                @JsonSubTypes.Type(value = CreateUserCommand.class),
                @JsonSubTypes.Type(value = DeleteUserCommand.class),
                @JsonSubTypes.Type(value = UpdateUserCommand.class),
        }
)
public interface ConnectionCommand<T extends Receiver> extends Command<T> {

    UUID connectionIdentifier();
    void connectionIdentifier(UUID connectionIdentifier);

    default boolean requiresAuthentication() {
        return true;
    }
}
