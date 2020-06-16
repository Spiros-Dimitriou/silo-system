
public class LiqueurTypeBMaker implements Runnable {

	private PipeResourceMonitor pipe;
	private Silo2SoftwareRepresentative s2sr;
	private Silo2Controller s2c;
	private Silo3SoftwareRepresentative s3sr;
	private Silo3Controller s3c;
	String FILL_SILO = new String("FILL_SILO");
	String EMPTY_SILO = new String("EMPTY_SILO");
	private String HEAT_SILO = new String("HEAT_SILO");
	private String MIX_SILO = new String("MIX_SILO");
	private PowerResourceMonitor power;
	
	public LiqueurTypeBMaker(Silo2SoftwareRepresentative s2sr, Silo2Controller s2c, Silo3Controller s3c, Silo3SoftwareRepresentative s3sr, PipeResourceMonitor pipe, PowerResourceMonitor power) {
		this.s2sr = s2sr;
		this.s2c = s2c;
		this.s3sr = s3sr;
		this.s3c = s3c;
		this.pipe = pipe;
		this.power = power;
	}
	
	public synchronized void run() {
		try {
			
			s2c.startJob(FILL_SILO);
			s3c.startJob(EMPTY_SILO);
			
			while(true) {
				
				s2sr.resetProperties();
				s3sr.resetProperties();
			
				s2sr.waitToFill();
				s2c.startJob(HEAT_SILO, 50f);
				s3sr.waitToEmpty();
				s2sr.waitToHeat(50f);
				
				pipe.requestPipe();
				
				s3c.startJob(FILL_SILO);
				s2c.startJob(EMPTY_SILO);
				Thread.sleep(200);
				
				s3sr.waitToFill();
				s2sr.waitToEmpty();

				pipe.releasePipe();
				
				s2c.startJob(FILL_SILO);
				
				power.requestPower();
				
				s3c.startJob(MIX_SILO);
				s3sr.waitToMix();
				
				power.releasePower();
				
				s3c.startJob(EMPTY_SILO);
			}
		} catch (InterruptedException e) {}
	}

}
