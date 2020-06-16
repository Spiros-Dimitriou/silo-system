
public class Silo1SoftwareRepresentative {

	boolean full = false;
	boolean empty = true;

	public synchronized void waitToFill() {	
		try {
			while (full == false)
				wait();
		} catch (InterruptedException e) {}
	}
	
	public synchronized void waitToEmpty() {
		try {
			while (empty == false)
				wait();
		} catch (InterruptedException e) {}
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

}
