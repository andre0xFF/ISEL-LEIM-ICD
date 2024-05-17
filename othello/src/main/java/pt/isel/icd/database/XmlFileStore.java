package pt.isel.icd.database;

import pt.isel.icd.serialization.Serializer;

import java.io.File;
import java.io.IOException;

public class XmlFileStore implements Database {

    private final Serializer xmlSerializer;
    private String fileStorePath;

    public XmlFileStore(Serializer existingXmlSerializer) {
        xmlSerializer = existingXmlSerializer;
    }


    @Override
    public <T extends Entity> T load(Entity entity) {
        File file = getFile(entity.name());

        try {
            return (T) xmlSerializer.deserialize(file, entity.getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Entity entity) {
        File file = getFile(entity.name());

        try {
            xmlSerializer.serialize(file, entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private File getFile(String entity) {
        if (fileStorePath == null) {
            throw new IllegalStateException("File store path not set");
        }

        String path = String.format("%s/%s.xml", fileStorePath, entity);
        File file = new File(path);

        if (!file.exists()) {
            throw new IllegalStateException("File does not exist");
        }

        return file;
    }

    public void setFileStorePath(String existingFileStorePath) {
        fileStorePath = existingFileStorePath;
    }
}
