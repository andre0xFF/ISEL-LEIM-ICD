package application;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import domain.Login;
import domain.Logout;
import domain.SendMessage;

@JsonRootName(value = "function")
//@JsonSubTypes({
//    @JsonSubTypes.Type(value = SendMessage.class, name="send_message"),
//    @JsonSubTypes.Type(value = Login.class, name="login"),
//    @JsonSubTypes.Type(value = Logout.class, name="logout"),
//})
public interface Function {

    Result getResult();
    void setResult(Result result);
}
