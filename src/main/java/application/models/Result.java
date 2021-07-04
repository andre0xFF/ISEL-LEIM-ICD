package application.models;

import java.util.HashMap;

public class Result {
    
    public static final Integer OK = 200;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer UNAUTHENTICATED = 401;
    public static final Integer UNAUTHORIZED = 403;
    public static final Integer NOT_FOUND = 404;
    
    private Integer code;

    public Result(Integer code) {
        this.code = code;

    }

    public Integer getCode() {
        return this.code;

    }
}
