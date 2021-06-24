package domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import application.Resource;

public class Message implements Resource {

    private String content;
    private LocalDateTime when;

    @JsonCreator
    public Message(
        @JsonProperty("content") String content,
        @JsonProperty("when") LocalDateTime when
    ) {
        this.content = content;
        this.when = when;
    }
    
    public Message(String content) {
        this(content, LocalDateTime.now());
    }

    public String getContent() {
        return this.content;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getWhen() {
        return this.when;
    }
}
