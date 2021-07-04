package application.models;

public class Permissions {

    private Boolean canOwnerRead;
    private Boolean canOwnerWrite;
    private Boolean canRoleRead;
    private Boolean canRoleWrite;
    private Boolean canUsersRead;
    private Boolean canUserWrite;

    public Permissions(
        Boolean canOwnerRead,
        Boolean canOwnerWrite,
        Boolean canRoleRead,
        Boolean canRoleWrite,
        Boolean canUsersRead,
        Boolean canUserWrite
    ) {
        this.canOwnerRead = canOwnerRead;
        this.canOwnerWrite = canOwnerWrite;
        this.canRoleRead = canRoleRead;
        this.canRoleWrite = canRoleWrite;
        this.canUsersRead = canUsersRead;
        this.canUserWrite = canUserWrite;
    }

    public Permissions() {
        this.setOwnerPrivate();
    }

    public Boolean getCanOwnerRead() {
        return canOwnerRead;
    }

    public Boolean getCanOwnerWrite() {
        return canOwnerWrite;
    }

    public Boolean getCanRoleRead() {
        return canRoleRead;
    }

    public Boolean getCanRoleWrite() {
        return canRoleWrite;
    }

    public Boolean getCanUsersRead() {
        return canUsersRead;
    }

    public Boolean getCanUserWrite() {
        return canUserWrite;
    }

    public void setPublic() {
        this.canOwnerRead = true;
        this.canOwnerWrite = true;
        this.canRoleRead = true;
        this.canRoleWrite = true;
        this.canUsersRead = true;
        this.canUserWrite = true;
    }

    public void setOwnerPrivate() {
        this.canOwnerRead = true;
        this.canOwnerWrite = true;
        this.canRoleRead = false;
        this.canRoleWrite = false;
        this.canUsersRead = false;
        this.canUserWrite = false;
    }

    public void setRolePrivate() {
        this.canOwnerRead = true;
        this.canOwnerWrite = true;
        this.canRoleRead = true;
        this.canRoleWrite = true;
        this.canUsersRead = false;
        this.canUserWrite = false;
    }
    
}
