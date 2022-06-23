
import java.io.*;
import java.net.*;
import java.util.Random;
/**
 * Worker is a server. It computes PI by Monte Carlo method and sends 
 * the result to Master.
 */
public class WorkerSocket {
    static int port = 25545; //default port
    private static boolean isRunning = true;
    
    /**
     * compute PI locally by MC and sends the number of points 
     * inside the disk to Master. 
     */
    public static void main(String[] args) throws Exception {

	// if a argument is given, use it as port
	if (args.length > 0) {
	  port = Integer.parseInt(args[0]);
	} else { // else use default port
	  System.out.println("No port given. Using default port " + port);
	}
	System.out.println(port);
		// ETAPE 1
        ServerSocket s = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        Socket soc = s.accept();
	
        // BufferedReader bRead for reading message from Master
        BufferedReader bRead = new BufferedReader(new InputStreamReader(soc.getInputStream()));

        // PrintWriter pWrite for writing message to Master
        PrintWriter pWrite = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true);
	String str;
        while (isRunning) {
	    str = bRead.readLine();          // read message from Master
	    if (!(str.equals("END"))){
		System.out.println("Server receives totalCount = " +  str);
		
		// compute
		System.out.println("TODO : compute Monte Carlo and send total");
		int numIterations = Integer.parseInt(str);
		long circleCount = 0;
		  Random prng = new Random ();
		  for (int j = 0; j < numIterations; j++) 
		      {
			  double x = prng.nextDouble();
			  double y = prng.nextDouble();
			  if ((x * x + y * y) < 1)  ++circleCount;
		      }		  
		  	
	        pWrite.println(circleCount);         // send number of points in quarter of disk // ça passe par le réseau youuuu jusqu'à ligne 78 de Master 
	    }else{
		isRunning=false;
	    }	    
        }
        bRead.close();
        pWrite.close();
        soc.close();
   }
}