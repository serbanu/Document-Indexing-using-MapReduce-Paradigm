/**
 * 
 * @author Mardaloescu Serban 334CA
 *
 */

public class Pair {
	int docNumber;		//Helps keeping tracks of the parsed document 
	String tokenName;	//The word from the WorkPool
	
	public Pair(int docNumber, String tokenName) {
		this.docNumber = docNumber;
		this.tokenName = tokenName;
	}
}
