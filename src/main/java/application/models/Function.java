package application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import functions.client.Authentication;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.WRAPPER_OBJECT, 
    property = "type"
)
@JsonSubTypes(
    value = {
        // @Type(value = FunctionTest1.class),
        // @Type(value = FunctionTest2.class),
        @Type(value = Authentication.class), 
    }
)
@JsonInclude(value = Include.NON_EMPTY)
@JsonRootName(value = "Function")
public interface Function<T> extends patterns.behavioral.command.Command<T> {

    // @JsonProperty(required = false)
    // Result getResult();

    @JsonIgnore
    void setReceiver(T receiver);

    // @JsonTypeInfo(
    //     use = JsonTypeInfo.Id.NAME, 
    //     include = JsonTypeInfo.As.WRAPPER_OBJECT, 
    //     property = "type"
    // )
    // @JsonSubTypes(value = {
    //     @Type(value = ResultTest1.class),
    //     @Type(value = ResultTest2.class),
    // })
    // @JsonInclude(value = Include.NON_EMPTY)
    // public interface Result {

    // }
}
