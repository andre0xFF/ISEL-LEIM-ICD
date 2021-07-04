package application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import application.models.Resource;
import application.models.Result;
import patterns.behavioral.command.Command;

@JsonInclude(value = Include.NON_EMPTY)
public class Message {

    private Command<?> command;
    private Result result;
    private Resource resource;

    @JsonCreator(mode = Mode.PROPERTIES)
    public Message(Command<?> command) {
        this.command = command;

    }

    @JsonCreator(mode = Mode.PROPERTIES)
    public Message(Command<?> command, Result result) {
        this(command);
        this.result = result;

    }

    @JsonCreator(mode = Mode.PROPERTIES)
    public Message(Command<?> command, Result result, Resource resource) {
        this(command, result);
        this.resource = resource;

    }

    // @JsonSubTypes(
    //     value = {
    //         @Type(value = FunctionTest1.class),
    //         @Type(value = FunctionTest2.class),
    //         @Type(value = SendMessage.class),
    //     }
    // )
    public Command<?> getCommand() {
        return this.command;
    }

    public Result getResult() {
        return this.result;

    }

    // @JsonSubTypes(
    //     value = {
    //         @Type(value = FunctionTest1.class),
    //         @Type(value = FunctionTest2.class),
    //         @Type(value = SendMessage.class),
    //     }
    // )
    public Resource getResource() {
        return this.resource;

    }

    @JsonIgnore
    public Boolean hasResult() {
        return this.result != null;

    }

    @JsonIgnore
    public Boolean hasResource() {
        return this.resource != null;

    }
    
}
