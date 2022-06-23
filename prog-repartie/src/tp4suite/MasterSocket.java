
import java.io.*;
import java.net.*;
/** Master is a client. It makes requests to numWorkers.
 *   
 */
public class MasterSocket {
    static int maxServer = 8; // nombre de serveurs maximum que cette classe va adresser.
    static final int[] tab_port = {25545,25546,25547,25548,25549,25550,25551,25552};
    static String[] tab_total_workers = new String[maxServer];
    static final String ip = "127.0.0.1";
    static BufferedReader[] reader = new BufferedReader[maxServer]; // bufferIn, un reader par serveur
    static PrintWriter[] writer = new PrintWriter[maxServer]; // bufferOut
    static Socket[] sockets = new Socket[maxServer];
    
    
    public static void main(String[] args) throws Exception {

	// MC parameters
	int totalCount = 16000000; // total number of throws on a Worker
	int total = 0; // total number of throws inside quarter of disk
	double pi; 

	int numWorkers = maxServer;
	BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in)); // system.in = entrée clavier
	String s; // for bufferRead

	System.out.println("#########################################");
	System.out.println("# Computation of PI by MC method        #");
	System.out.println("#########################################");
	
	System.out.println("\n How many workers for computing PI (< maxServer): ");
	try{
	    s = bufferRead.readLine();
	    numWorkers = Integer.parseInt(s);
	    System.out.println(numWorkers);
	}
	catch(IOException ioE){
	   ioE.printStackTrace();
	}
	
	for (int i=0; i<numWorkers; i++){
	    System.out.println("Enter worker"+ i +" port : ");
	    try{
		s = bufferRead.readLine();
		System.out.println("You select " + s);
	    }
	    catch(IOException ioE){
		ioE.printStackTrace();
	    }
	}	   
       //create worker's socket là on commence l'algo
       for(int i = 0 ; i < numWorkers ; i++) {
		   sockets[i] = new Socket(ip, tab_port[i]);
		   System.out.println("SOCKET = " + sockets[i]);
		   // on crée le bufferIn (reader) on instancie un inputstreamreader (lecteur de flux d'entrée) en tant qu'objet bufferisé on récupère le flux d'entrée du socket à l'adresse et port donné, on le met au bon format et le met à la collection reader
		   reader[i] = new BufferedReader( new InputStreamReader(sockets[i].getInputStream()));
		   writer[i] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[i].getOutputStream())),true); // pareil pour bufferOut
       }

       String message_to_send;
       message_to_send = String.valueOf(totalCount);

       String message_repeat = "y";

       long stopTime, startTime;

       while (message_repeat.equals("y")){
	   total = 0;

	   startTime = System.currentTimeMillis();
	   // initialize workers
	   for(int i = 0 ; i < numWorkers ; i++) {
	       writer[i].println(message_to_send);          // send a message to each worker
	   }
	   
	   //listen to workers's message 
	   for(int i = 0 ; i < numWorkers ; i++) {
	       tab_total_workers[i] = reader[i].readLine();      // read message from server
	       System.out.println("Client sent: " + tab_total_workers[i]);
	   }
	   
	   // compute PI with the result of each workers
	   for(int i = 0 ; i < numWorkers ; i++) {
	       total += Integer.parseInt(tab_total_workers[i]);
	   }
	   pi = 4.0 * total / totalCount / numWorkers;

	   stopTime = System.currentTimeMillis();

	   System.out.println("\nPi : " + pi );
	   System.out.println("Error: " + (Math.abs((pi - Math.PI)) / Math.PI) +"\n");
	   
	   System.out.println("Ntot: " + totalCount*numWorkers);
	   System.out.println("Available processors: " + numWorkers);
	   System.out.println("Time Duration (ms): " + (stopTime - startTime) + "\n");
	   
	   System.out.println( (Math.abs((pi - Math.PI)) / Math.PI) +" "+ totalCount*numWorkers +" "+ numWorkers +" "+ (stopTime - startTime));

	   System.out.println("\n Repeat computation (y/N): ");
	   try{
	       message_repeat = bufferRead.readLine();
	       System.out.println(message_repeat);
	   }
	   catch(IOException ioE){
	       ioE.printStackTrace();
	   }
       }
       
       for(int i = 0 ; i < numWorkers ; i++) {
	   System.out.println("END");     // Send ending message
	   writer[i].println("END") ;
	   reader[i].close();
	   writer[i].close();
	   sockets[i].close();
       }
   }
}