package application.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import functions.FunctionTest1;
import functions.FunctionTest2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.WRAPPER_OBJECT, 
    property = "type"
)
@JsonSubTypes(value = {
    @Type(value = FunctionTest1.class),
    @Type(value = FunctionTest2.class),
})
@JsonInclude(value = Include.NON_EMPTY)
@JsonRootName(value = "function")
public interface Function {

    @JsonProperty(required = false)
    Result<? extends Function> getResult();
    
    void execute();

    public interface Result<F extends Function> {

    }

}
