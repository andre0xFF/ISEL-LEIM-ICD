package functions;

import application.models.Function;
import functions.results.ResultTest1;

public class FunctionTest1 implements Function {

    private Result<FunctionTest1> result;

    @Override
    public Result<FunctionTest1> getResult() {
        return this.result;

    }

    @Override
    public void execute() {
        this.result = new ResultTest1();

    }

}