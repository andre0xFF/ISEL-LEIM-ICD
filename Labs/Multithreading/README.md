# Notes about multithreading in java

## Commands and tools

Platform dependent:
* `sleep(0)`, may or may not sleep
* `yield()`, may or may not yield and maybe something is wrong with the design
* `setPriority()`, maybe cause starvation

**jstack** command allows to get a stack trace of all running threads
**jps** command tells the running java proccess id's and is platform independent
**jconsole** the java monitoring and management console found in the /bin

### Sleeping threads
```java
Thread.sleep(1000);
```
Sleeping threads may be interrupted but exception must be handled.

When the thread sleeps if the interrupted state is already set the thread will wake up straight away.

`interrupted()`, checks for and clears the interrupt. True if the thread received an interrupt.

`isInterrupted()`, checks for but doesn't clear the interrupt. True if the thread received an interrupt.

### Alive thread

`isAlive()`

### ThreadLocal

Used to share static global variables between all threads in which each one may have unique values instead of sharing the same value.

ThreadLocal persists until the thread dies or no instances of ThreadLocal left.

ThreadLocal can have memory leaks. Catch all exceptions and remove the ThreadLocal when thread dies.

## Sharing memory across threads

Key points:
* Need to consider how to share data at the design stage
* Sharing mutable state and working with data other threads may modify or have modified can be thread unsafe
* The Java Memory Model defines a set of guarantees which, when applied to a program, ensure memory interactions between threads occur in a specified deterministic fashion
* Shared variables may be read from cache and won't have the same value across multiple threads
* JVM or the CPU can reorder the code to make it faster to execute

### Volatile

In C/C++ it means do not cache.

Finals cannot be volatile because their value are not expected to change.

Arrays and object references can be marked volatile but doesn't affect the contents.

Volatile prevents optimizations based on program order from the JVM.

The memory state which was visible to a writer of a volatile variable must at least be visible to any reader of the same variable (memory fence).

64 bit primitives are not thread safe unless volatile, because may be split in two 32 bit operations to read or write. Or AtomicLong.

## Mutual Exclusion (mutex)

`synchronized(object) {}` only one thread at the time can use the object. When one thread is using the object the other will have to wait. When the object is released there is no order guarantee to each thread will use next use the object. Possible under load some threads may never get chosen.

`Thread.holdsLock(object)` check if we own the monitor (debug)

### Deadlock

When threads cannot make progress because they need various mutex and those are held by another threads that won't release because they are waiting for other mutex that is beeing held by the same or other thread.

There is no way to solve a deadlock, the user will need to restart the application until it happens again.

How to detect? No progress being made. GUI might no longer be responsive. `jstack`

```java
* Thread 1
synchronized(object1) {

    synchronized(object2) {

    }
}

* Thread 2
synchronized(object2) {

    synchronized(object1) {

    }
}
```

One can solve this by taking the mutex in the same order.

#### Try-lock paradigm

If a thread can't acquire the mutex after some time it will release all mutexes. Try again. Hope another thread will succeed in the meantime.

### Livelock

Thread in livelock can back off and retry, wait, do some other work or try to resolve the situation.

### Starvation

When threads are not getting enough execution time to carry out their tasks or sometimes some getting significantly more than others.

## Communication

`wait()` `notify()` `notifyAll()` - Provides basic signalling

Threads that want a signal call wait and sleep until another threads calls `notify()` or `notifyAll()`

Used in wait sets `synchronized(objects) {}`

Dangers: Forgetting to synchronize or synchronizing on the wrong object
