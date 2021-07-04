package application.models;

public interface Resource {
    
    User getOwner();

    Role getRole();

    Permissions getPermissions();
}
