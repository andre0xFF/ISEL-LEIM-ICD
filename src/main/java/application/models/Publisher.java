package application.models;

import application.models.Function.Result;

interface Topic {

}

public interface Publisher<T extends Topic> {

    public void subscribe(Subscriber<T> subscriber);

    public void unsubscribe(Subscriber<T> subscriber);

    public void notifySubscribers();
}

class FunctionTest3 implements Function, Topic {

    @Override
    public Result<? extends Function> getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }

}
