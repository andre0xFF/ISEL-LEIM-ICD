package pt.isel.icd.user.management;

import pt.isel.icd.patterns.command.Receiver;

import java.util.UUID;

public interface Authenticator extends Receiver {

    boolean isAuthenticated(UUID connectionIdentifier);
}
