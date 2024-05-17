package pt.isel.icd.database;

public interface Database {

    <T extends Entity> T load(Entity entity);

    boolean save(Entity entity);
}
