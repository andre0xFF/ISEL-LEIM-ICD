package pt.isel.icd.serialization;

import pt.isel.icd.game.management.*;
import pt.isel.icd.user.management.*;

/**
 * Registers all command types with the CommandSerializer.
 */
public class CommandRegistry {

    public static void registerAll(CommandSerializer serializer) {
        // User management
        serializer.register("AuthenticateUserCommand", el -> {
            AuthenticateUserCommand cmd = new AuthenticateUserCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("AuthenticateUserResponseCommand", el -> {
            AuthenticateUserResponseCommand cmd =
                new AuthenticateUserResponseCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("CreateUserCommand", el -> {
            CreateUserCommand cmd = new CreateUserCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("CreateUserResponseCommand", el -> {
            CreateUserResponseCommand cmd = new CreateUserResponseCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("ReadUserProfileCommand", el -> {
            ReadUserProfileCommand cmd = new ReadUserProfileCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("ReadUserProfileResponseCommand", el -> {
            ReadUserProfileResponseCommand cmd =
                new ReadUserProfileResponseCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("UpdateUserCommand", el -> {
            UpdateUserCommand cmd = new UpdateUserCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("HonorBoardCommand", el -> {
            HonorBoardCommand cmd = new HonorBoardCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("HonorBoardResponseCommand", el -> {
            HonorBoardResponseCommand cmd = new HonorBoardResponseCommand();
            cmd.fromXml(el);
            return cmd;
        });

        // Game management
        serializer.register("JoinGameCommand", el -> {
            JoinGameCommand cmd = new JoinGameCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("JoinGameResponseCommand", el -> {
            JoinGameResponseCommand cmd = new JoinGameResponseCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("LeaveGameCommand", el -> {
            LeaveGameCommand cmd = new LeaveGameCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("LeaveGameResponseCommand", el -> {
            LeaveGameResponseCommand cmd = new LeaveGameResponseCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("PlaceLineCommand", el -> {
            PlaceLineCommand cmd = new PlaceLineCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("PlaceLineResponseCommand", el -> {
            PlaceLineResponseCommand cmd = new PlaceLineResponseCommand();
            cmd.fromXml(el);
            return cmd;
        });
        serializer.register("GameOverCommand", el -> {
            GameOverCommand cmd = new GameOverCommand();
            cmd.fromXml(el);
            return cmd;
        });
    }
}
