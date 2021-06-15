package application;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "message")
class Message {

    Function function;

    public Message(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }
}
