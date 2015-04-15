/**
 * 
 * @author Mardaloescu Serban 334CA
 *
 */

import java.util.Enumeration;

public class Reduce {
	private Map map;	//Instance of a Map Object
	private float sum;	//Calculates the plagiarism percentage
	
	
	public Reduce(Map map) {
		this.map = map;
		this.sum = 0;
	}
	
//Function that returns the plagiarism percentage	
	public synchronized float iterateOverSource(int number) {
		Enumeration<Pair> en = map.getKeys();
		Enumeration<Pair> spareEn = map.getKeys();
		Pair pair, sparePair;
		while(en.hasMoreElements()) {
			pair = en.nextElement();
			if(pair.docNumber == 0) {
				while(spareEn.hasMoreElements()) {
					sparePair = spareEn.nextElement();
					if(sparePair.docNumber != 0 && sparePair.tokenName.compareTo(pair.tokenName) == 0) {
						sum += (map.getValue(pair) * 1000 /map.getSize(0)) *
								(map.getValue(sparePair) * 1000 /map.getSize(number));
						sum /= 10;
					}
				}
			}
		}
		return sum;
	}
}
