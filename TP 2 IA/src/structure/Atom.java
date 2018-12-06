package structure;

import java.util.Collection;
import java.util.HashMap;

/* Un atome est un symbole propositionnel. 
 * Remarque : le seul attribut d'un atome est la string correspondant au symbole 
 * Dans un but d'optimisation, on pourrait aussi associer à chaque symbole un entier distinct, 
 * et utiliser cet entier comme index dans diverses structures
 */

public class Atom
{
	private HashMap<String, Boolean> symbol; // le symbole propositionnel
		
	/**
	 * @param s l'atome (
	 */
	public Atom (String s)
	{
		symbol = new HashMap<String, Boolean>();
		if(s.charAt(0)=='!') symbol.put(s, false);
		else symbol.put(s, true); 
	}
	
	/** overrrides equals from Object **/
	
	public boolean equals(Object b)
	{
		if (!(b instanceof Atom)) return false;
		return this.symbol.equals(((Atom)b).symbol);
	}
	
		
	/**
	 * @return le symbole propositionnel
	 */
	public String toString()
	{
		String s ="";
		if(this.symbol.toString().charAt(1)=='!'){
			s += this.symbol.toString().substring(1, this.symbol.toString().length() - 7);
		}else s += this.symbol.toString().substring(1, this.symbol.toString().length() - 6);
		return s;
	}
	
	public Boolean getValue(String key){
		return symbol.get(key);
	}
	
	public Collection<Boolean> getValues(){
		return symbol.values();
	}
}

