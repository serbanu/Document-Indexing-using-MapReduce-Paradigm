/**
 * 
 * @author Mardaloescu Serban 334CA
 *
 */

import java.util.Enumeration;
import java.util.Hashtable;

public class Map {
	private volatile Hashtable<Pair, Integer> table;	//The HashTable of words
	
	public Map() {
		table = new Hashtable<Pair, Integer>();
	}

//Function that returns the number of words processed for a given document
	public int getSize(int count) {
		int counter = 1;
		Enumeration<Pair> en = table.keys();
		Pair pair;
		while(en.hasMoreElements()) {
			pair = en.nextElement();
			if(pair.docNumber == count) {
				counter++;
			}
		}
		return counter;
	}
	
//Function that returns an Enumeration of the HashTable keys	
	public Enumeration<Pair> getKeys() {
		return table.keys();
	}
	
//Function that returns a boolean value, whether the HashTable contains or not a specific key	
	public boolean containsKey(Pair pair) {
		Enumeration<Pair> en = table.keys();
		while(en.hasMoreElements()) {
			Pair sparePair = en.nextElement();
			if(pair.docNumber == sparePair.docNumber &&
					pair.tokenName.compareTo(sparePair.tokenName) == 0) {
				return true;
			}
		}
		return false;
	}
	
//Function that increments an existing value in the HashTable	
	public void putIncrementedValue(Pair pair) {
		Enumeration<Pair> en = table.keys();
		while(en.hasMoreElements()) {
			Pair sparePair = en.nextElement();
			if(sparePair.docNumber == pair.docNumber &&
				sparePair.tokenName.compareTo(pair.tokenName) == 0) {
				int value = table.get(sparePair);
				value++;
				table.put(sparePair, value);
			}
		}
	}
	
//Function that creates a new entry in the HashTable	
	public synchronized void putOne(Pair pair) {
		table.put(pair, 1);
	}
	
//Function that returns the number of occurrences of a given key
	public int getValue(Pair pair) {
		return table.get(pair);
	}
	
//Function that returns a reference of the HashTable	
	public Hashtable<Pair, Integer> getHashTable() {
		return table;
	}
}
