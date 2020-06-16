import java.util.Scanner;

public class Silo3SoftwareRepresentative {
	boolean full = false;
	boolean empty = true;
	boolean mixed = false;

	public synchronized void waitToFill() throws InterruptedException {	
		while (full == false)
				wait();
	}
	
	public synchronized void waitToEmpty() throws InterruptedException {
		while (empty == false)
			wait();
	}

	public synchronized void fullCapacityReached() {
		full = true;
		empty = false;
		notifyAll();
	}
	
	public synchronized void lowestCapacityReached() {
		full = false;
		empty = true;
		notifyAll();
	}
	
	public synchronized void mixingDone() {
		   mixed = true;
		   notifyAll();
	}

	public synchronized void mixing() {
		System.out.println("Press \"ENTER\" to finish mixing...");
		   @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		   scanner.nextLine();
		   mixed = true;
		   notifyAll();
	}

	public synchronized void waitToMix() throws InterruptedException {
		while (mixed == false)
			wait();
	}

	public void resetProperties() {
		mixed = false;
	}
	
}
