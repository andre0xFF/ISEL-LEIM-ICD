package mutexes;

import java.util.concurrent.CountDownLatch;

public class BarSimulationFixed
{
    private static final int numberOfThreads = 2;

    public static void main(String[] args)
    {
        for (int i = 1; i <= numberOfThreads; i++)
        {
            String name = "Thread" + i;
            new Thread(new BarPatron(name), name).start();
        }
    }

    private static class BarPatron implements Runnable
    {
        private final String name;

        private static final CountDownLatch cl = new CountDownLatch(
                numberOfThreads);

        // Must be volatile as shared between threads and modified
        private static volatile boolean roundBeingBought = false;

        // We need an object to use as a mutex to synchronize on
        private static final Object mutex = new Object();

        public BarPatron(String name)
        {
            this.name = name;
        }

        public void run()
        {
            enterBarAndOrderDrinks();
        }

        private void enterBarAndOrderDrinks()
        {
            // To prove no race condition
            // TODO: Debug only - remove this!!
            try
            {
                cl.countDown();
                cl.await();
            }
            catch (InterruptedException e) {}

            // Don't want to be seen as a cheapskate - if no drinks being
            // bought take the initiative and buy them!
            boolean shouldBuyDrinks = false;

            synchronized (mutex)
            {
                if (!roundBeingBought) // 1
                {
                    roundBeingBought = true; // 2

                    // So we know this thread is the buyer
                    shouldBuyDrinks = true;
                }
            }

            if (shouldBuyDrinks)
            {
                buyDrinks();
            }
            else
            {
                waitForDrink();
            }
        }

        private void buyDrinks()
        {
            System.out.println(name + " is buying the drinks.");
        }

        private void waitForDrink()
        {
            System.out.println(name + " is waiting for a drink.");
        }
    }
}
