package pt.isel.icd.database;

public interface Database {

    Entity load(Class<?> type);

    boolean save(Entity entity);
}
