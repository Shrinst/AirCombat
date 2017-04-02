package airwar2.multithreading;

public class PooledThread extends Thread {
	
	private static IDAssigner threadIDAssigner = new IDAssigner(1);
	private ThreadPool pool;
	
	public PooledThread(ThreadPool pool) {
		super(pool, "PooledThread-" + threadIDAssigner.next());	
		this.pool = pool;
	}
	
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
