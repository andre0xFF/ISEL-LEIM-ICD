package messages;

public class LaunchAttack implements Message {

    private final String controllerName;

    public LaunchAttack(String controllerName) {
        this.controllerName = controllerName;
    }

    @Override
    public String getControllerName() {
        return controllerName;
    }
}
