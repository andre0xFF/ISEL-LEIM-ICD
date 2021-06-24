package application.models;

import application.models.Function.Result;

public interface Invoker<F extends Function, R extends Result<F>> {
    
    public void onInvoke(F function);
    public void onResult(F function, R result);
}
