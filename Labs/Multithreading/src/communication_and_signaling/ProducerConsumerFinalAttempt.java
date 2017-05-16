package signalling.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

// For discussion only: this is not intended to be run
public class ProducerConsumerFinalAttempt
{
	final Queue<Object> queue = new LinkedList<>(); // Mock work as Object
	
	void producer(Object work) {
		synchronized(queue) {
			queue.offer(work);
			queue.notify();
		}
	}
	
	Object consumer() {
		Object work;
		
		synchronized(queue) {
			while (!Thread.currentThread().isInterrupted() && queue.isEmpty()) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			
			work = queue.poll(); // Returns null if interrupted but no work
		}
		
		return work; // Here caller will proceed work and check isInterrupted
	}
}
