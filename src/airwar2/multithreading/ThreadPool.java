package airwar2.multithreading;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool extends ThreadGroup {
	
	private boolean alive;
	private List<Runnable> taskQueue;
	private int id;
	
	private static IDAssigner poolIDAssigner = new IDAssigner(1);	

	public ThreadPool(int numThreads) {
		super("Thread Pool - " + poolIDAssigner.next());
		this.id = poolIDAssigner.getCurrentID();
		setDaemon(true); // Exit the group of threads when the main thread exits
		taskQueue = new LinkedList<Runnable>();
		alive = true;
		for (int i = 0; i < numThreads; i++) {
			new PooledThread(this).start();
		}
	}
	
	public synchronized void runTask(Runnable task) {
		if (!alive) {
			throw new IllegalStateException("ThreadPool-" + id + " is dead");
		}
		if (task != null) {
			taskQueue.add(task);
			notify(); // Find the null thread and wake it up
		}
	}
	
	public synchronized void close() {
		if (!alive) {
			return;
		}
		alive = false;
		taskQueue.clear();
		interrupt();
	}
	
	public void join() {
		synchronized (this) {
			alive = false;
			notifyAll();
		}
		
		Thread[] threads = new Thread[activeCount()];
		int count = enumerate(threads); // Give a more a accurate way to know how many threads are running
		
		for (int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
	}
	
	// Returns the first thread of the queue and erase it
	protected synchronized Runnable getTask() throws InterruptedException {
		while (taskQueue.size() == 0) {
			if (!alive) {
				return null;
			}
			wait(); // Waits for the thread stops
		}
		return taskQueue.remove(0);
	}
}
