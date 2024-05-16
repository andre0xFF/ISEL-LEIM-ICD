package pt.isel.icd.communication;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pt.isel.icd.game.management.*;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.user.management.*;

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
                @JsonSubTypes.Type(value = PlaceGamePieceCommand.class),
                @JsonSubTypes.Type(value = ShowGameBoardCommand.class),
                @JsonSubTypes.Type(value = UpdateGameBoardCommand.class),

                // User Management Commands
                @JsonSubTypes.Type(value = AuthenticateUserCommand.class),
                @JsonSubTypes.Type(value = AuthenticateUserResponseCommand.class),
                @JsonSubTypes.Type(value = CreateUserCommand.class),
                @JsonSubTypes.Type(value = CreateUserResponseCommand.class),
                @JsonSubTypes.Type(value = DeleteUserCommand.class),
                @JsonSubTypes.Type(value = UpdateUserCommand.class),
                @JsonSubTypes.Type(value = ReadUserProfileCommand.class),
                @JsonSubTypes.Type(value = ReadUserProfileResponseCommand.class)
        }
)
public interface SimpleSocketCommand<T extends Receiver> extends Command<T> {

    UUID connectionIdentifier();
    void connectionIdentifier(UUID connectionIdentifier);

    default boolean requiresAuthentication() {
        return true;
    }
}
