
public class Silo2Controller implements Runnable {
	private Silo2Driver s2d;
	private Silo2SoftwareRepresentative s2sr;
	private String job;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	private String HEAT_SILO = new String("HEAT_SILO");
	private float reqTemp = 50f;
	
	public Silo2Controller(Silo2Driver s2d, Silo2SoftwareRepresentative s2sr) {
		this.s2d = s2d;
		this.s2sr = s2sr;
	}

	private synchronized void fill() throws InterruptedException  {
		
		s2d.openInput();
		
		s2sr.waitToFill();
		
		s2d.closeInput();
	}
	
	private synchronized void empty() throws InterruptedException {
		s2d.openOutput();
		
		s2sr.waitToEmpty();
		
		s2d.closeOutput();
	}
	
	private synchronized void heat() throws InterruptedException {
		s2d.enableResistor();
		
		while (s2sr.temperature < reqTemp)
			Thread.sleep(500);
		
		s2d.disableResistor();
		
		s2sr.temperatureReached();
	}

	public synchronized void run() {
		try {
			while (true) {
				wait();
				if (job.equals(FILL_SILO))
					fill();
				else if (job.equals(EMPTY_SILO))
					empty();
				else if (job.equals(HEAT_SILO))
					heat();
			}
		} catch (InterruptedException e) {}
		
	}

	public synchronized void startJob(String job) {
		this.job = job;
		notify();
	}
	
	public synchronized void startJob(String job, float reqTemp) {
		this.job = job;
		this.reqTemp = reqTemp;
		notify();
	}
}
