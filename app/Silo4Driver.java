import java.util.Scanner;

public class Silo4Driver implements Runnable {
	
	private String job;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	private String HEAT_SILO = new String("HEAT_SILO");
	private String MIX_SILO = new String("MIX_SILO");
	private Silo4SoftwareRepresentative s4sr;
	
	public Silo4Driver(Silo4SoftwareRepresentative s4sr) {
		this.s4sr = s4sr;
	}

	public synchronized void openInput() {
		System.out.println("IN4 Opened");
		s4sr.empty = false;
		this.job = FILL_SILO;
		notify();
	}
	
	public void closeInput() {
		System.out.println("IN4 Closed");
	}
	
	public synchronized void openOutput() {
		System.out.println("OUT4 Opened");
		s4sr.full = false;
		this.job = EMPTY_SILO;
		notify();
	}
	
	public void closeOutput() {
		System.out.println("OUT4 Closed");
	}
	
	public void fullCapacityReached() {
		s4sr.fullCapacityReached();
	}
	
	public void lowestCapacityReached() {
		s4sr.lowestCapacityReached();
	}
	
	public synchronized void enableResistor() {
		System.out.println("RESISTOR4 Enabled");
		s4sr.resistor = true;
		this.job = HEAT_SILO;
		notify();
	}
	
	public void disableResistor() {
		System.out.println("RESISTOR4 Disabled");
		s4sr.resistor = false;
	}
	
	public void readTemp() {
		s4sr.temperature += 10.0;
		System.out.format("Silo 4 temp: %.1f%n", s4sr.temperature);
	}
	
	public synchronized void enableMixer() {
		System.out.println("MIXER4 Enabled");
		this.job = MIX_SILO;
		notify();
	}
	
	public void disableMixer() {
		System.out.println("MIXER4 Disabled");
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
				
				else if (job.equals(HEAT_SILO)) {
					while (s4sr.resistor == true) {
						readTemp();
						Thread.sleep(300);
					}
				}
				else if (job.equals(MIX_SILO)) {
					System.out.println("Press \"ENTER\" to finish mixing...");
					   @SuppressWarnings("resource")
					Scanner scanner = new Scanner(System.in);
					   scanner.nextLine();
					   s4sr.mixingDone();
				}
					
			}
		} catch (InterruptedException e) {}
	}
	
}
