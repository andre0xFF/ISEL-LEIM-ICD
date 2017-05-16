package liveness_and_starvation;

// Deadlock example
public class Driver
{
    public static void main(String[] args)
    {
        MyFile file = new MyFile();
        MyNetworkCon networkCon = new MyNetworkCon();

        Task1 task1 = new Task1(file, networkCon);
        Task2 task2 = new Task2(file, networkCon);

        Thread thread1 = new Thread(task1, "Task 1");
        Thread thread2 = new Thread(task2, "Task 2");

        thread1.start();
        thread2.start();
    }
}

class MyFile
{
    public void access()
    {
        System.out.println("File is being accessed by " + Thread.currentThread().getName());
    }
}

class MyNetworkCon
{
    public void access()
    {
        System.out.println("Network is being accessed by " + Thread.currentThread().getName());
    }
}

class Task1 implements Runnable
{
    private final MyFile file;
    private final MyNetworkCon networkCon;

    public Task1(MyFile file, MyNetworkCon networkCon)
    {
        this.file = file;
        this.networkCon = networkCon;
    }

    public void run()
    {
        System.out.println("Task1 is about to acquire the mutexes");

        synchronized(file) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                // This is a demo, so can ignore it
            }

            synchronized (networkCon) {
                file.access();
                networkCon.access();
            }
        }
    }
}

class Task2 implements Runnable
{
    private final MyFile file;
    private final MyNetworkCon networkCon;

    public Task2(MyFile file, MyNetworkCon networkCon)
    {
        this.file = file;
        this.networkCon = networkCon;
    }

    public void run()
    {
        System.out.println("Task2 is about to acquire the mutexes");

        // Taking the mutexes in the opposite order as Task1 does - BAD!
        synchronized(networkCon) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                // This is a demo, so can ignore it
            }

            synchronized (file) {
                networkCon.access();
                file.access();
            }
        }
    }
}
