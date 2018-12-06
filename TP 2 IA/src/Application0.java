import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import structure.Atom;
import structure.FactBase;
import structure.KnowledgeBase;
import structure.Rule;
import structure.RuleBase;


public class Application0 {
	public static void main(String[] args) throws IOException
	{
		// creation de la base de faits de 2 facons differentes
		/*FactBase bf = new FactBase("P");
		bf.addAtomWithoutCheck(new Atom("Q"));
		System.out.println("Base de faits initiale :\n"+bf);
	
		// creation de la base de regles
		RuleBase br = new RuleBase();
		br.addRule(new Rule("P;S"));
		br.addRule(new Rule("Q;S;R"));
		System.out.println("Base de regles :\n"+br);*/
		
		//Création de la base de connaissances à partir du fichier texte		
		String fileName = "TD5exo4.txt" ; // nom du fichier
		System.out.println("Chargement du fichier : "+
		new java.io.File( "." ).getCanonicalPath()+ "/" + fileName);
		BufferedReader readFile = new BufferedReader(new FileReader (fileName));
		KnowledgeBase kn = new KnowledgeBase(readFile);
		
		System.out.println("*********************");
		
		//Affichage de la base de connaissances
		System.out.println(kn.toString());
		
/*		System.out.println("*********************");
		
		//calcul de la base de fait saturée
		kn.forwardChaining();
		System.out.println("Base de fait saturée algorithme naif:\n" + kn.toString());
		
		System.out.println("*********************");
		
		kn.forwardChainingOpt();
		System.out.println("Base de fait saturée algorithme optimisé:\n" + kn.toString());
		
		System.out.println("*********************");
		
		//Boucle sur saisie d'un atome à prouver et preuve
		boolean bon = false, preuve = false;
		String symbol = "";
		
		while(!bon){
			symbol = "D";
			if(symbol.length()==1){
				bon = true;
			}
		}
		Atom b = new Atom(symbol);
		for(Atom a : kn.getBf2().getAtoms()){
			if(a.equals(b)){
				System.out.println(b.toString() + " prouvé");
				preuve = true;
			}
		}
		if(preuve == false){
			System.out.println(b.toString() + " non prouvé");
		}
		
		System.out.println("*********************");
		
		//prouver le symbol avec le bc3
		System.out.println("Algorithme non optimisé :");
		
		ArrayList<Atom> l = new ArrayList<Atom>();
		
		preuve = kn.bc3(b, l);
		if(preuve){
			System.out.println(b.toString() + " prouvé");
		}else System.out.println(b.toString() + " non prouvé");
		
		System.out.println("*********************");
		
		System.out.println("Algorithme optimisé :");
		
		l = new ArrayList<Atom>();
		ArrayList<Atom> p = new ArrayList<Atom>(); 
		ArrayList<Atom> e = new ArrayList<Atom>();
		preuve = kn.bc3Opt(b, l, p, e);
		if(preuve){
			System.out.println(b.toString() + " prouvé");
		}else System.out.println(b.toString() + " non prouvé");
*/		
		System.out.println("*********************");
	
		kn.forwardChainingOptEtendu();
		System.out.println("Base de fait saturée algorithme étendu:\n" + kn.toString());
		
	}
}
