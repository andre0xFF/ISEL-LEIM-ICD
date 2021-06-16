package application;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import domain.Login;
import domain.Logout;
import domain.SendMessage;

public interface Function {

    Result getResult();
    void setResult(Result result);
}
