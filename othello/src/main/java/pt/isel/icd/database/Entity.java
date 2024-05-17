package pt.isel.icd.database;

public interface Entity {

    default String name() {
        return this.getClass().getName();
    }
}
