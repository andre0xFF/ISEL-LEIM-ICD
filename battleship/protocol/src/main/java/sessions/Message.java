package sessions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Message {
    private final String schemaName;
    private final String content;

    @JsonCreator
    public Message(@JsonSetter("schemaName") String schemaName, @JsonSetter("content") String content) {
        this.schemaName = schemaName;
        this.content = content;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getContent() {
        return content;
    }
}
