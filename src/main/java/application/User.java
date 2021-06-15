package application;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import domain.Student;
import domain.Teacher;

@JsonSubTypes({
    @JsonSubTypes.Type(value = Student.class, name="student"),
    @JsonSubTypes.Type(value = Teacher.class, name="teacher"),
})
public interface User {
    
}
