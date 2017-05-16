package memory_share;

public class HiHello
{
    static boolean S1 = false;

    public static void main(String[] args) {
        new Thread(new Thread1(), "Thread 1").start();
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {}

        new Thread(new Thread2(), "Thread 2").start();
    }

    private static class Thread1 implements Runnable
    {
        public void run()
        {
            // Thread1 may be reading the value of HiHello.S1 from cache won't exit
            // the loop despite the fact that the value has been changed already
            while (!HiHello.S1) {}
            System.out.println("HELLO!");
        }
    }

    private static class Thread2 implements Runnable
    {
        public void run()
        {
            HiHello.S1 = true;
            System.out.println("HI!");
        }
    }
}
