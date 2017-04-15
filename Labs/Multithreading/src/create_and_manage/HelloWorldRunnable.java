package create_and_manage;

import java.util.concurrent.TimeUnit;

public class HelloWorldRunnable {

    public static class Greeter implements Runnable {

        private final String country;

        public Greeter(String country) {
            this.country = country;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            // or another way to sleep
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {}

            System.out.println("Hello " + country + "!");
        }
    }

    public static void main(String[] args) {

        String[] countries = { "France", "India", "China", "USA", "Germany" };
        
        for (String country : countries) {
            new Thread(new Greeter(country)).start();
        }
    }
}
