package application.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import functions.FunctionTest1;
import functions.FunctionTest2;
import functions.SendMessage;
import functions.results.ResultTest1;
import functions.results.ResultTest2;
import functions.results.SentMessage;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.WRAPPER_OBJECT, 
    property = "type"
)
@JsonSubTypes(value = {
    @Type(value = FunctionTest1.class),
    @Type(value = FunctionTest2.class),
    @Type(value = SendMessage.class),
})
@JsonInclude(value = Include.NON_EMPTY)
@JsonRootName(value = "function")
public interface Function {

    @JsonProperty(required = false)
    Result<? extends Function> getResult();
    
    void execute();

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, 
        include = JsonTypeInfo.As.WRAPPER_OBJECT, 
        property = "type"
    )
    @JsonSubTypes(value = {
        @Type(value = ResultTest1.class),
        @Type(value = ResultTest2.class),
        @Type(value = SentMessage.class),
    })
    @JsonInclude(value = Include.NON_EMPTY)
    public interface Result<F extends Function> {

    }

}
