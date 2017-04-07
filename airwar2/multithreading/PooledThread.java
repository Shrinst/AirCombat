package airwar2.multithreading;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class PooledThread extends Thread {

	private static IDAssigner threadIDAssigner = new IDAssigner(1);
	private ThreadPool pool;

	/**
	 * Sets the variables
	 * 
	 * @param pool
	 *            the pool
	 */
	public PooledThread(ThreadPool pool) {
		super(pool, "PooledThread-" + threadIDAssigner.next());
		this.pool = pool;
	}

	/**
	 * Runs the thread task
	 */
	@Override
	public void run() {
		while (!isInterrupted()) {
			Runnable task = null;
			try {
				task = pool.getTask();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (task == null) {
				return; // exits the method
			}
			try {
				task.run();
			} catch (Throwable t) {
				pool.uncaughtException(this, t);
			}

		}
	}
}
