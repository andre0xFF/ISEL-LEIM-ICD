package patterns.behavioral.observer;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * “Observ­er is a behav­ioral design pat­tern that lets you define a sub­scrip­tion 
 * mech­a­nism to noti­fy mul­ti­ple objects about any events that hap­pen to the 
 * object they’re observing”
 *
 * Excerpt From: Alexander Shvets. “Dive Into Design Patterns”. Apple Books. 
 */
public final class Publisher {

    // https://docs.oracle.com/javase/7/docs/api/java/util/HashSet.html
    HashMap<Object, LinkedHashSet<Subscriber<?>>> subscribers = new HashMap<>();

    /**
     * Sets subscriber to a context.
     * @param subscriber
     * @param context
     */
    public void subscribe(Object context, Subscriber<?> subscriber) {

        LinkedHashSet<Subscriber<?>> contextSubscribers = this.subscribers.getOrDefault(
            context,
            new LinkedHashSet<Subscriber<?>>()
        );

        contextSubscribers.add(subscriber);
        this.subscribers.put(context, contextSubscribers);

    }

    /**
     * Unsets a subscriber from a context.
     * @param subscriber
     * @param context
     */
    public void unsubscribe(Object context, Subscriber<?> subscriber) {

        LinkedHashSet<Subscriber<?>> contextSubscribers = this.subscribers.get(context);

        if (contextSubscribers == null) {
            return;
        }

        contextSubscribers.remove(subscriber);

        if (contextSubscribers.size() == 0) {
            this.subscribers.remove(context);
        }

    }

    /**
     * Triggers a context publish on all subscribers.
     * @param context
     */
    public void publish(Object context) {

        LinkedHashSet<Subscriber<?>> contextSubscribers = this.subscribers.get(context);

        for (Subscriber subscriber : contextSubscribers) {
            subscriber.onPublish(context);
        }

    }
}

class Test {

    public static void main(String[] args) {

        class Subscriber1 implements Subscriber<String> {

            @Override
            public void onPublish(String context) {

                System.out.println("Subscriber1 object is a subscriber of String class");
                System.out.println(context);

            }

        }

        class Subscriber2 implements Subscriber<Integer> {

            @Override
            public void onPublish(Integer context) {

                System.out.println("Subscriber2 object is a subscriber of Integer class");
                System.out.println(context);

            }

        }

        Publisher observer = new Publisher();

        String contextInstance1 = "hello world 1";
        String contextInstance2 = "hello world 2";
        Integer contextInstance3 = 0;

        Subscriber1 subscriber1Instance1 = new Subscriber1();
        Subscriber1 subscriber1Instance2 = new Subscriber1();
        Subscriber2 subscriber2Instance2 = new Subscriber2();

        observer.subscribe(contextInstance1, subscriber1Instance1);
        observer.subscribe(contextInstance1, subscriber1Instance2);
        observer.subscribe(contextInstance2, subscriber1Instance1);
        observer.subscribe(contextInstance3, subscriber2Instance2);

        System.out.println();

        observer.publish(contextInstance1);
        observer.publish(contextInstance2);
        observer.publish(contextInstance3);

    }
    
}
