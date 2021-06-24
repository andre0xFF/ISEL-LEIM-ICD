package functions;

import application.models.Function;
import functions.results.ResultTest2;

public class FunctionTest2 implements Function {

    private Result<FunctionTest2> result;

    @Override
    public Result<FunctionTest2> getResult() {
        return this.result;

    }

    @Override
    public void execute() {
        this.result = new ResultTest2();

    }

}
