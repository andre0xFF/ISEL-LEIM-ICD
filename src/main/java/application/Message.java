package application;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

class Message {

    Function function;
    
    public Message(Function function) {
        this.function = function;
    }
    
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME, 
            include = JsonTypeInfo.As.WRAPPER_OBJECT, 
            property = "type"
    )
    public Function getFunction() {
        return function;
    }
}
