// Estimate the value of Pi using Monte-Carlo Method, using parallel program
package tp4;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
class PiMonteCarlo {
	AtomicInteger nAtomSuccess;
	int nThrows;
	double value;
	class MonteCarlo implements Runnable { // 1 throw = 1 run = 1 thread
		@Override
		public void run() { // T1 + T2 => elle fait une itération de Monte Carlo
			double x = Math.random();
			double y = Math.random();
			if (x * x + y * y <= 1)
				nAtomSuccess.incrementAndGet();
		}
	}
	public PiMonteCarlo(int i) {
		this.nAtomSuccess = new AtomicInteger(0);
		this.nThrows = i;
		this.value = 0;
	}
	public double getPi() {
		int nProcessors = Runtime.getRuntime().availableProcessors(); // nombre de processus, on peut mettre notre propre valeur aussi 4,5,...
		ExecutorService executor = Executors.newWorkStealingPool(nProcessors);
		for (int i = 1; i <= nThrows; i++) {  //  Tâche T0
			Runnable worker = new MonteCarlo(); 
			executor.execute(worker);
		}
		executor.shutdown(); // si ils sont morts, je les tue, garantie un nettoyage propre
		while (!executor.isTerminated()) {	// bloquant - on ne continue pas tant que l'incrementation sur nAtomSuccess n'est pas terminée
		}
		value = 4.0 * nAtomSuccess.get() / nThrows;
		return value;
	}
}
public class Assignment102 {
	public static void main(String[] args) {
		PiMonteCarlo PiVal = new PiMonteCarlo(100000);
		long startTime = System.currentTimeMillis();
		double value = PiVal.getPi();
		long stopTime = System.currentTimeMillis();
		System.out.println("Approx value:" + value);
		System.out.println("Difference to exact value of pi: " + (value - Math.PI));
		System.out.println("Error: " + (value - Math.PI) / Math.PI * 100 + " %");
		System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
		System.out.println("Time Duration: " + (stopTime - startTime) + "ms");
	}
}