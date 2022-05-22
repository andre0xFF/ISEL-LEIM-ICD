package models_bkp.ships.components;

public class ShipDamage {
    private final boolean[] damages;

    public ShipDamage(int length) {
        this.damages = new boolean[length];

        initialize();
    }

    private void initialize() {
        for (boolean damage : damages) {
            damage = false;
        }
    }

    public boolean[] getDamages() {
        return damages;
    }

    public boolean setDamage(int index) {
        if (index > damages.length) {
            return false;
        }

        damages[index] = true;

        return damages[index];
    }

    public boolean isDestroyed() {
        for (boolean damage : getDamages()) {
            if (!damage) {
                return false;
            }
        }

        return true;
    }
}
