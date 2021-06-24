package resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import domain.Student;
import domain.Teacher;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.WRAPPER_OBJECT, 
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Student.class),
    @JsonSubTypes.Type(value = Teacher.class),
})
public interface User {
    
    @JsonProperty
    public String getId();
}
