package application.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import resources.Student;
import resources.Teacher;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.WRAPPER_OBJECT, 
    property = "type"
)
@JsonSubTypes(
    value = {
        @Type(value = Student.class),
        // @Type(value = Teacher.class),
    }
)
@JsonInclude(value = Include.NON_EMPTY)
@JsonRootName(value = "User")
public interface User {
    
    ArrayList<Role> getRoles();

    String getUsername();

    String getPassword();
}
