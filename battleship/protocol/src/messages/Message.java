package messages;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(StartGame.class),
                @JsonSubTypes.Type(SubmitCredentials.class),
                @JsonSubTypes.Type(SubmitPlayerDetails.class)
        }
)
@JsonRootName(value = "Message")
public interface Message {

        @JacksonXmlProperty(isAttribute = true)
        String getControllerName();
}
