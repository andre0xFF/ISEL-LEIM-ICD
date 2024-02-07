package pt.isel.icd.messaging;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A message is a data transfer object that is sent over the network.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonRootName("Message")
public interface Message {

}
