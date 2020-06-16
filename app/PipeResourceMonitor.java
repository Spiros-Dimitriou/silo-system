
public class PipeResourceMonitor {
	
	private boolean pipeAvailable = true;
	
	public synchronized void requestPipe() throws InterruptedException {
		while (pipeAvailable == false)
			wait();
		pipeAvailable = false;
	}
	
	public synchronized void releasePipe() {
		pipeAvailable = true;
		notifyAll();
	}
	
}