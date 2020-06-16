
public class Silo2SoftwareRepresentative {
	boolean full = false;
	boolean empty = true;
	boolean resistor = false;
	float temperature = 0;

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
	
	public synchronized void temperatureReached() {
		notifyAll();
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
	
	public void resetProperties() {
		temperature = 0;
	}

}
