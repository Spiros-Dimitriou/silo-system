import java.util.Scanner;

public class Silo3Driver implements Runnable {
	private String job;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	private String MIX_SILO = new String("MIX_SILO");
	private Silo3SoftwareRepresentative s3sr;
	
	public Silo3Driver(Silo3SoftwareRepresentative s3sr) {
		this.s3sr = s3sr;
	}

	public synchronized void openInput() {
		System.out.println("IN3 Opened");
		s3sr.empty = false;
		this.job = FILL_SILO;
		notify();
	}
	
	public void closeInput() {
		System.out.println("IN3 Closed");
	}
	
	public synchronized void openOutput() {
		System.out.println("OUT3 Opened");
		s3sr.full = false;
		this.job = EMPTY_SILO;
		notify();
	}
	
	public void closeOutput() {
		System.out.println("OUT3 Closed");
	}
	
	public void fullCapacityReached() {
		s3sr.fullCapacityReached();
	}
	
	public void lowestCapacityReached() {
		s3sr.lowestCapacityReached();
	}
	
	public synchronized void enableMixer() {
		System.out.println("MIXER3 Enabled");
		this.job = MIX_SILO;
		notify();
	}
	
	public void disableMixer() {
		System.out.println("MIXER3 Disabled");
	}

	public synchronized void run() {
		try {
			while(true) {
				wait();
				
				if (job.equals(FILL_SILO)) {
					Thread.sleep(1000);
					fullCapacityReached();
				}
				
				else if (job.equals(EMPTY_SILO)) {
					Thread.sleep(1000);
					lowestCapacityReached();
				}
				else if (job.equals(MIX_SILO)) {
					System.out.println("Press \"ENTER\" to finish mixing...");
					   @SuppressWarnings("resource")
					Scanner scanner = new Scanner(System.in);
					   scanner.nextLine();
					   s3sr.mixingDone();
				}
					
			}
		} catch (InterruptedException e) {}
	}
	
}
