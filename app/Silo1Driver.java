
public class Silo1Driver implements Runnable{
	
	private String job;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	private Silo1SoftwareRepresentative s1sr;
	
	public Silo1Driver(Silo1SoftwareRepresentative s1sr) {
		this.s1sr = s1sr;
	}

	public synchronized void openInput() {
		System.out.println("IN1 Opened");
		s1sr.empty = false;
		this.job = FILL_SILO;
		notify();
	}
	
	public void closeInput() {
		System.out.println("IN1 Closed");
	}
	
	public synchronized void openOutput() {
		System.out.println("OUT1 Opened");
		s1sr.full = false;
		this.job = EMPTY_SILO;
		notify();
	}
	
	public void closeOutput() {
		System.out.println("OUT1 Closed");
	}
	
	public void fullCapacityReached() {
		s1sr.fullCapacityReached();
	}
	
	public void lowestCapacityReached() {
		s1sr.lowestCapacityReached();
	}

	public synchronized void run() {
		try {
			while(true) {
				wait();
				Thread.sleep(2000);
				if (job.equals(FILL_SILO))
					fullCapacityReached();
				else if (job.equals(EMPTY_SILO))
					lowestCapacityReached();
			}
		} catch (InterruptedException e) {}
	}
}
