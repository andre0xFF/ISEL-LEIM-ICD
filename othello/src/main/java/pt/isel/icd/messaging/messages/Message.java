package pt.isel.icd.messaging.messages;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A message is a data transfer object that is sent over the network.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonRootName("Message")
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = PingMessage.class),
                @JsonSubTypes.Type(value = PongMessage.class),
        }
)
public interface Message {

}
