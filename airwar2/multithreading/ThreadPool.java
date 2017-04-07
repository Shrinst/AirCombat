package airwar2.multithreading;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class ThreadPool extends ThreadGroup {

	private boolean alive;
	private List<Runnable> taskQueue;
	private int id;

	private static IDAssigner poolIDAssigner = new IDAssigner(1);

	/**
	 * Sets the list of threads
	 * 
	 * @param numThreads
	 *            the amount of threads
	 */
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

	/**
	 * Will run the thread
	 * 
	 * @param task
	 *            the thread
	 */
	public synchronized void runTask(Runnable task) {
		if (!alive) {
			throw new IllegalStateException("ThreadPool-" + id + " is dead");
		}
		if (task != null) {
			taskQueue.add(task);
			notify(); // Find the null thread and wake it up
		}
	}

	/**
	 * Will close the thread
	 */
	public synchronized void close() {
		if (!alive) {
			return;
		}
		alive = false;
		taskQueue.clear();
		interrupt();
	}

	/**
	 * Will close the thread, waits for the thread to finish its job
	 */
	public void join() {
		synchronized (this) {
			alive = false;
			notifyAll();
		}

		Thread[] threads = new Thread[activeCount()];
		int count = enumerate(threads); // Give a more a accurate way to know
										// how many threads are running

		for (int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the first thread of the queue and erase it
	 * 
	 * @return the first thread of the stack
	 * @throws InterruptedException
	 */
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
