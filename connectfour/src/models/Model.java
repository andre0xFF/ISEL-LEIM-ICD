package models;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A model is a data transfer object that is stored in the database.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = Users.class)
        }
)
@JsonRootName("Model")
public interface Model {

    /**
     * A database loads and saves models in XML format.
     */
    class Database {

        public static final String DEFAULT_XML_DATABASE_PATH = "res/data/%s.xml";
        private final XmlMapper xmlMapper = new XmlMapper();

        public Database() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            JavaTimeModule module = (JavaTimeModule) new JavaTimeModule().addSerializer(
                    LocalDateTime.class,
                    new LocalDateTimeSerializer(formatter)
            );

            xmlMapper.registerModule(module);
        }

        /**
         * Saves a model to the database.
         *
         * @param model The model to save.
         * @throws IOException If the model could not be saved.
         */
        public void save(Model model) throws IOException {
            String name = model.getClass().getSimpleName();
            File file = new File(String.format(DEFAULT_XML_DATABASE_PATH, name));

            this.xmlMapper.writeValue(file, model);
        }

        /**
         * Loads a model from the database.
         *
         * @param clazz The class of the model to load.
         * @return The loaded model.
         * @throws IOException If the model could not be loaded.
         */
        public Model load(Class<Users> clazz) throws IOException {
            File file = new File(
                    String.format(
                            DEFAULT_XML_DATABASE_PATH,
                            clazz.getSimpleName()
                    )
            );

            return this.xmlMapper.readValue(file, clazz);
        }
    }
}
