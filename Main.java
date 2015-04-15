/**
 * 
 * @author Mardaloescu Serban 334CA
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	private static Scanner sc;
	private static WorkPool wp;	//Workpool
	
//Function that processes a given document
	static void processDocument(Scanner document, int noBytes, int count) {
		int counter = 0;			//Bytes counter
		String ss = new String();	//Helper String Object - task string
		String ch = new String();	//Helper String Object - letter string
		document.useDelimiter("");	//Split the document in bytes
		while(document.hasNext()) {
			ch = document.next();	//One letter
			ss += ch;				//Concatenate to the string which will soon become a task in the WorkPool
			counter += 2;			//Increment the byte-counter
			if(counter % noBytes == 0) {	//The prerequisite for the size of a task
				while(true) {
					//Check if a task `stops` in the middle of a word
					if(Character.isLetter(ch.charAt(0)) || ch.compareTo("'") == 0) {
						ch = new String();
						ch = document.next();
						ss += ch;
						counter += 2;
					} else {
						break;
					}
				}
				wp.putWork(new PartialSolution(ss), count);	//Add the task into the WorkPool
				ss = new String();	//Reinitialize the task string
			}
			ch = new String();	//Reinitialize the letter string
		}
	}
	
	public static void main(String args[]) throws FileNotFoundException, InterruptedException {

//Initialize the variable that we are going to use
		sc = new Scanner(new File(args[1]));
		int noThreads = Integer.parseInt(args[0]);
		Scanner source = new Scanner(new File(sc.nextLine()));
		int noBytes = Integer.parseInt(sc.nextLine());
		float minimumScore = Float.parseFloat(sc.nextLine());
		int documentsToBeTested = Integer.parseInt(sc.nextLine());
		
		wp = new WorkPool(noThreads, documentsToBeTested + 1);
		processDocument(source, noBytes, 0);
		for(int i = 1; i <= documentsToBeTested; i++) {
			processDocument(new Scanner(new File(sc.nextLine())), noBytes, i);
		}

//Instances of the objects who are going to compute the Map and Reduce operations		
		Map map = new Map();
		Reduce reduce = new Reduce(map);
		
//Initialize the threads		
		Worker worker1 = new Worker(wp, documentsToBeTested, map, reduce, 1, minimumScore);
		Worker worker2 = new Worker(wp, documentsToBeTested, map, reduce, 2, minimumScore);
		Worker worker3 = new Worker(wp, documentsToBeTested, map, reduce, 3, minimumScore);
		Worker worker4 = new Worker(wp, documentsToBeTested, map, reduce, 4, minimumScore);
		
		System.out.println("Results for: (in.txt)");System.out.println();

//Start the threads		
		worker1.start(); worker2.start();
		worker3.start(); worker4.start();
		
//Join the threads	
		worker1.join(); worker2.join();
		worker3.join();	worker4.join();
	}
	
}


