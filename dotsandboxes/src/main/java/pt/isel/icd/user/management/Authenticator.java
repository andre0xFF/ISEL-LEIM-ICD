package pt.isel.icd.user.management;

import java.util.UUID;

public interface Authenticator {
    boolean isAuthenticated(UUID socketId);
}
