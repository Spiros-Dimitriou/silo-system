
public class Silo4Controller implements Runnable {

	private Silo4Driver s4d;
	private Silo4SoftwareRepresentative s4sr;
	private String job;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	private String HEAT_SILO = new String("HEAT_SILO");
	private String MIX_SILO = new String("MIX_SILO");
	private float reqTemp;
	
	public Silo4Controller(Silo4Driver s4d, Silo4SoftwareRepresentative s4sr, float reqTemp) {
		this.s4d = s4d;
		this.s4sr = s4sr;
		this.reqTemp = reqTemp;
	}

	public synchronized void fill() throws InterruptedException  {
		
		s4d.openInput();
		
		s4sr.waitToFill();
		
		s4d.closeInput();
	}
	
	public synchronized void empty() throws InterruptedException {
		s4d.openOutput();
		
		s4sr.waitToEmpty();
		
		s4d.closeOutput();
	}
	
	public synchronized void heat() throws InterruptedException {
		s4d.enableResistor();
		
		while (s4sr.temperature < reqTemp)
			Thread.sleep(500);
		
		s4d.disableResistor();
		
		s4sr.temperatureReached();
	}
	
	private synchronized void mix() throws InterruptedException {
		s4d.enableMixer();
		
		s4sr.waitToMix();
		
		s4d.disableMixer();
		
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
				else if (job.equals(MIX_SILO))
					mix();
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