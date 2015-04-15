/**
 * 
 * @author Mardaloescu Serban 334CA
 *
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class WorkPool {
	int nThreads; //Number of threads
	int nWaiting = 0; //Number of blocked threads
	public boolean ready = false; //If the problem is completely solved
	volatile ArrayList<LinkedList<PartialSolution> > tasks;	//The array of linkedlists of tasks
	
	public WorkPool(int nThreads, int count) {
		this.nThreads = nThreads;
		tasks = new ArrayList<LinkedList<PartialSolution> >();
		initTasks(count);
	}

//Function that returns the size of a list of tasks specific for a certain document	
	public int getSize(int count) {
		return tasks.get(count).size();
	}

//Function that initialize the array of linkedlists	
	private synchronized void initTasks(int count) {
		for(int i = 0; i < count; i++) {
			tasks.add(i, new LinkedList<PartialSolution>());
		}
	}

//Function that returns a task related to a specific document from the WorkPool	
	public synchronized PartialSolution getWork(int count) {
		if (tasks.get(count).size() == 0) {//Empty WorkPool
			nWaiting++;
			if (nWaiting == nThreads) {
				ready = true;
				notifyAll();
				return null;
			} else {
				if (ready) {
				    return null;
				}
				nWaiting--;
			}
			return null;
		} else {
			return tasks.get(count).remove();
		}
	}
	
//Function that puts a task related to a specific document in the WorkPool	
	public synchronized void putWork(PartialSolution ps, int count) {
		tasks.get(count).add(ps);
		this.notify();
	}
}
