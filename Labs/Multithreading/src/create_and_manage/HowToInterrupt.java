package create_and_manage;

import java.util.Random;

public class HowToInterrupt {

    public static class Bell implements Runnable {

        private int count = 0;

        @Override
        public void run() {
            System.out.println("Bell is running");

            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("Bell rang! " + this.count);
                }

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    this.count++;
                    Thread.currentThread().interrupt();
                }

            }
        }
    }

    public static class BellKiller implements Runnable {

        private final Thread bell;

        public BellKiller(Thread bell) {
            this.bell = bell;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(new Random().nextInt(10));
            } catch (InterruptedException e) {}

            this.bell.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Bell bell = new Bell();
        Thread bell_thread = new Thread(bell);

        bell_thread.start();

        Thread.sleep(5);

        for (int i = 0; i < 5; i++) {
            new Thread(new BellKiller(bell_thread)).start();
        }

        Thread.sleep(1600);

        System.out.println(bell_thread.isAlive());
    }
}
