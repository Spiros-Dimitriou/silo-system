import java.util.Scanner;

public class SiloSystemApplication {
	
	static Silo1SoftwareRepresentative s1sr;
	static Silo1Driver s1d;
	static Silo1Controller s1c;
	
	static Silo4SoftwareRepresentative s4sr;
	static Silo4Driver s4d;
	static Silo4Controller s4c;
	
	static Silo2SoftwareRepresentative s2sr;
	static Silo2Driver s2d;
	static Silo2Controller s2c;
	
	static Silo3SoftwareRepresentative s3sr;
	static Silo3Driver s3d;
	static Silo3Controller s3c;
	
	static PipeResourceMonitor pipe;
	static PowerResourceMonitor power;
	
	static LiqueurTypeAMaker lam;
	static LiqueurTypeBMaker lbm;
	
	static Scanner sc = new Scanner(System.in);
	static int selection;

	public static void main(String[] args) {
		
		pipe = new PipeResourceMonitor();
		power = new PowerResourceMonitor();
	
		System.out.println("Menu : ");
		System.out.println("1)Make liqueur Type A");
		System.out.println("2)Make liqueur Type B");
		System.out.println("3)Make both");
		selection = sc.nextInt();
		
		switch (selection)
		{
			case 1 :
				startLiqueurTypeA();
				break;
			
	        case 2 :
	    		startLiqueurTypeB();
				break;
			
	        case 3 :
	    		startLiqueurTypeA();
	    		startLiqueurTypeB();
		}
	}

	private static void startLiqueurTypeB() {
		
		s2sr = new Silo2SoftwareRepresentative();
		s2d = new Silo2Driver(s2sr);
		s2c = new Silo2Controller(s2d, s2sr);
		
		s3sr = new Silo3SoftwareRepresentative();
		s3d = new Silo3Driver(s3sr);
		s3c = new Silo3Controller(s3d, s3sr);
		
		lbm = new LiqueurTypeBMaker(s2sr, s2c, s3c, s3sr, pipe, power);
		
		new Thread(s2d).start();
		new Thread(s2c).start();

		new Thread(s3d).start();
		new Thread(s3c).start();
		
		new Thread(lbm).start();
	}

	private static void startLiqueurTypeA() {
		
		s1sr = new Silo1SoftwareRepresentative();
		s1d = new Silo1Driver(s1sr);
		s1c = new Silo1Controller(s1d, s1sr);
		
		s4sr = new Silo4SoftwareRepresentative();
		s4d = new Silo4Driver(s4sr);
		s4c = new Silo4Controller(s4d, s4sr, 50f);
		
		lam = new LiqueurTypeAMaker(s1sr, s1c, s4c, s4sr, pipe, power);
		
		new Thread(s1d).start();
		new Thread(s1c).start();

		new Thread(s4d).start();
		new Thread(s4c).start();
		
		new Thread(lam).start();
	}

}
