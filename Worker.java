/**
 * 
 * @author Mardaloescu Serban 334CA
 *
 */

import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class Worker extends Thread {
	WorkPool wp;
	int docNumber;	
	Map map;
	Reduce reduce;
	boolean flag;	//Variable that notifies whether we reached the last document to analyze or not
	int id;			//Identifier for the document
	float minimumScore;	//The minimum percentage of plagiarism

	public Worker(WorkPool wp, int count, Map map, Reduce reduce, int id, float minimumScore2) {
		this.wp = wp;
		this.docNumber = count;
		this.map = map;
		this.reduce = reduce;
		this.flag = false;
		this.id = id;
		this.minimumScore = minimumScore2;
	}

//Function that processes a solution from the WorkPool	
	void processPartialSolution(PartialSolution ps, int count) {
		StringTokenizer st = new StringTokenizer(ps.stringToBeAnalyzed, " \n,\t.-()*; ");	//Parsing the string
		synchronized (this) {
			while(st.hasMoreTokens()) {
				String token = st.nextToken().toLowerCase();	//Prepare the token
				Pair pair = new Pair(count, token);	//More preparing to add it to the HashTable
				//Checking to see whether the token already has an entry in the HashTable or
				//it has to be added
				if(map.containsKey(pair)) {
					map.putIncrementedValue(pair);
				} else {
					map.putOne(pair);
				}
			}
		}
	}

//The function that makes threads go crazy	
	public void run() {
		while (true) {
			if(wp.getSize(docNumber) == 0) {//If there are no more tasks for a given document
				if(docNumber == 0) {//If it's the last document
					flag = true;	//it means that we've compute all the tasks, so we have to compute the plagiarism percentage
				} else {
					docNumber--;	//else move to the next document
				}
				if(flag) {//If there are no more documents
					float sum = reduce.iterateOverSource(this.id);
					if(sum >= minimumScore) {
						DecimalFormat df = new DecimalFormat("0.000");
						System.out.println("Plagiarism percentage for doc" + id + ".txt is: " + df.format(sum) + " [%]");
					}
					break;
				}
			} else {//Else, compute the task for the current document
				PartialSolution ps = wp.getWork(docNumber);
				if(ps == null) {//If there was no solution, break
					System.out.println("I'm out !");
					break;
				}
				processPartialSolution(ps, docNumber);//Else, compute it :) 
			}
		}
	}
}