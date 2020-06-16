
public class Silo1Controller implements Runnable {
	
	private Silo1Driver s1d;
	private Silo1SoftwareRepresentative s1sr;
	private String job;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	
	public Silo1Controller(Silo1Driver s1d, Silo1SoftwareRepresentative s1sr) {
		this.s1d = s1d;
		this.s1sr = s1sr;
	}

	public void fill() throws InterruptedException  {
		
		s1d.openInput();
		
		s1sr.waitToFill();
		
		s1d.closeInput();
	}
	
	public void empty() throws InterruptedException {
		
		s1d.openOutput();
		
		s1sr.waitToEmpty();
		
		s1d.closeOutput();
	}

	public synchronized void run() {
		try {
			while (true) {
				wait();
				if (job.equals(FILL_SILO))
					fill();
				else if (job.equals(EMPTY_SILO))
					empty();
			}
		} catch (InterruptedException e) {}
		
	}

	public synchronized void startJob(String job) {
		this.job = job;
		notify();
	}
	
}
