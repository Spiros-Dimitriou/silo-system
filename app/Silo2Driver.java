
public class Silo2Driver implements Runnable {
	private String job;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	private String HEAT_SILO = new String("HEAT_SILO");
	private Silo2SoftwareRepresentative s2sr;
	
	public Silo2Driver(Silo2SoftwareRepresentative s2sr2) {
		this.s2sr = s2sr2;
	}

	public synchronized void openInput() {
		System.out.println("IN2 Opened");
		s2sr.empty = false;
		this.job = FILL_SILO;
		notify();
	}
	
	public void closeInput() {
		System.out.println("IN2 Closed");
	}
	
	public synchronized void openOutput() {
		System.out.println("OUT2 Opened");
		s2sr.full = false;
		this.job = EMPTY_SILO;
		notify();
	}
	
	public void closeOutput() {
		System.out.println("OUT2 Closed");
	}
	
	public void fullCapacityReached() {
		s2sr.fullCapacityReached();
	}
	
	public void lowestCapacityReached() {
		s2sr.lowestCapacityReached();
	}
	
	public synchronized void enableResistor() {
		System.out.println("RESISTOR2 Enabled");
		s2sr.resistor = true;
		this.job = HEAT_SILO;
		notify();
	}
	
	public void disableResistor() {
		System.out.println("RESISTOR2 Disabled");
		s2sr.resistor = false;
	}
	
	public void readTemp() {
		s2sr.temperature += 10.0;
		System.out.format("Silo 2 temp: %.1f%n", s2sr.temperature);
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
					while (s2sr.resistor == true) {
						readTemp();
						Thread.sleep(300);
					}
				}
					
			}
		} catch (InterruptedException e) {}
	}
}
