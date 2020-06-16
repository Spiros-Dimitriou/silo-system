
public class Silo4SoftwareRepresentative {
	boolean full = false;
	boolean empty = true;
	boolean resistor = false;
	float temperature = 0;
	boolean mixed = false;

	public synchronized void waitToFill() throws InterruptedException {	
		while (full == false)
				wait();
	}
	
	public synchronized void waitToEmpty() throws InterruptedException {
		while (empty == false)
			wait();
	}
	
	public synchronized void waitToHeat(float reqTemp) throws InterruptedException {
		while (temperature < reqTemp)
			wait();
	}

	public synchronized void fullCapacityReached() {
		full = true;
		empty = false;
		notifyAll();
	}
	
	public synchronized void temperatureReached() {
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

	public synchronized void waitToMix() throws InterruptedException {
		while (mixed == false)
			wait();
	}

	public void resetProperties() {
		temperature = 0;
		mixed = false;
	}
	
}
