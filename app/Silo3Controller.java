
public class Silo3Controller implements Runnable {
	
	private Silo3Driver s3d;
	private Silo3SoftwareRepresentative s3sr;
	private String job;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	private String MIX_SILO = new String("MIX_SILO");
	
	public Silo3Controller(Silo3Driver s3d, Silo3SoftwareRepresentative s3sr) {
		this.s3d = s3d;
		this.s3sr = s3sr;
	}

	public synchronized void fill() throws InterruptedException  {
		
		s3d.openInput();
		
		s3sr.waitToFill();
		
		s3d.closeInput();
	}
	
	public synchronized void empty() throws InterruptedException {
		s3d.openOutput();
		
		s3sr.waitToEmpty();
		
		s3d.closeOutput();
	}
	
	private synchronized void mix() throws InterruptedException {
		s3d.enableMixer();
		
		s3sr.waitToMix();
		
		s3d.disableMixer();
		
	}

	public synchronized void run() {
		try {
			while (true) {
				wait();
				if (job.equals(FILL_SILO))
					fill();
				else if (job.equals(EMPTY_SILO))
					empty();
				else if (job.equals(MIX_SILO))
					mix();
			}
		} catch (InterruptedException e) {}
		
	}

	public synchronized void startJob(String job) {
		this.job = job;
		notify();
	}
}
