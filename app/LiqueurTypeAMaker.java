
public class LiqueurTypeAMaker implements Runnable{

	private PipeResourceMonitor pipe;
	private Silo1SoftwareRepresentative s1sr;
	private Silo1Controller s1c;
	private Silo4SoftwareRepresentative s4sr;
	private Silo4Controller s4c;
	private String FILL_SILO = new String("FILL_SILO");
	private String EMPTY_SILO = new String("EMPTY_SILO");
	private String HEAT_SILO = new String("HEAT_SILO");
	private String MIX_SILO = new String("MIX_SILO");
	private PowerResourceMonitor power;
	
	public LiqueurTypeAMaker(Silo1SoftwareRepresentative s1sr, Silo1Controller s1c, Silo4Controller s4c, Silo4SoftwareRepresentative s4sr, PipeResourceMonitor pipe, PowerResourceMonitor power) {
		this.s1sr = s1sr;
		this.s1c = s1c;
		this.s4sr = s4sr;
		this.s4c = s4c;
		this.pipe = pipe;
		this.power = power;
	}

	public void run() {
		try {
			// Prepare for first batch
			s1c.startJob(FILL_SILO);
			s4c.startJob(EMPTY_SILO);
			
			while(true) {
				
				s4sr.resetProperties();			// Reset temperature and mixing state
				
				s1sr.waitToFill();
				s4sr.waitToEmpty();
				
				pipe.requestPipe();				// Request pipe access
				
				s4c.startJob(FILL_SILO);		//
				Thread.sleep(100);				//
				s1c.startJob(EMPTY_SILO);		// Liqueur is being transferred Silo1->Silo4
				s4sr.waitToFill();				//
				
				pipe.releasePipe();				// Release pipe
				
				s1c.startJob(FILL_SILO); 		// Get Silo 1 ready
				
				s4c.startJob(HEAT_SILO, 50); 	// Heat Silo 4
				s4sr.waitToHeat(50);			//
				
				power.requestPower();
				
				s4c.startJob(MIX_SILO); 		// Mix Silo 4
				s4sr.waitToMix();				//
				
				power.releasePower();
				
				// Type A Liqueur is ready
				
				s4c.startJob(EMPTY_SILO);
				
			}
		} catch (InterruptedException e) {}
	}

}