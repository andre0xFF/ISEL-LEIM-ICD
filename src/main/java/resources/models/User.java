package resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import resources.Student;
import resources.Teacher;

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
