package pt.isel.icd.database;

import pt.isel.icd.serialization.Serializer;

import java.io.File;
import java.io.IOException;

public class XmlFileStore implements Database {

    private final Serializer xmlSerializer;

    public XmlFileStore(Serializer existingXmlSerializer) {
        xmlSerializer = existingXmlSerializer;
    }

    @Override
    public Entity load(Class<?> type) {
        File file = getFile(type.getSimpleName());

        try {
            return (Entity) xmlSerializer.deserialize(file, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Entity entity) {
        File file = getFile(entity.toString());

        try {
            xmlSerializer.serialize(file, entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private static File getFile(String entity) {
        String path = String.format("src/main/resources/user/management/%s.xml", entity);
        File file = new File(path);

        if (!file.exists()) {
            throw new IllegalStateException("File does not exist");
        }

        return file;
    }
}
