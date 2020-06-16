
public class PowerResourceMonitor {
	
private boolean powerAvailable = true;
	
	public synchronized void requestPower() throws InterruptedException {
		while (powerAvailable == false)
			wait();
		powerAvailable = false;
	}
	
	public synchronized void releasePower() {
		powerAvailable = true;
		notifyAll();
	}
	
}
