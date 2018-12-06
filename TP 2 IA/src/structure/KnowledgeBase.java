package structure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class KnowledgeBase {
	private FactBase bf;
	private RuleBase br;
	private FactBase bf2;
	
	public KnowledgeBase(){
		bf = new FactBase();
		br = new RuleBase();
		bf2 = new FactBase();
	}
	
	public KnowledgeBase(BufferedReader in) throws IOException{
		this();
		
		String s = in.readLine();
		
		
		while (s!= null) // arrêt si fin de fichier ou ligne lue vide
		{
			Rule r = new Rule(s);
			br.addRule(r);
			//bf.addAtoms(r.getHypothesis());
			for(Atom h : r.getHypothesis()){
				if(h.getValue(h.toString()) && !bf.belongsAtom(h)){
					bf.addAtomWithoutCheck(h);
				}
			}
			
			s = in.readLine();
		}
		
		System.out.print(bf.toString());
		
	}
	
	public void forwardChaining(){
		boolean fin = false;
		HashMap<Integer, Boolean> hp = new HashMap<Integer, Boolean>();
		
		for(int i=0; i<br.size();i++){
			//Rule r = br.getRule(i);
			hp.put(i, false);
		}
		
		while(!fin){
			
			bf2 = new FactBase();
			
			for(int i=0; i<br.size();i++){
				Rule r = br.getRule(i);
				
				if(bf.getAtoms().containsAll(r.getHypothesis()) && hp.get(i).equals(false)){
					hp.replace(i, true);
					if(!(bf2.belongsAtom((r.getConclusion()))) && !bf.belongsAtom(r.getConclusion())){
						bf2.addAtomWithoutCheck(r.getConclusion());
					}
				}
				
			}
			if(bf2.isEmpty()){
				fin = true;
			}else{
				bf.addAtoms(bf2.getAtoms());
			}
			
		}
	}
	
	public void forwardChainingOpt(){
		
		bf2.addAtoms(bf.getAtoms());
		int cpt;
		ArrayList<Integer> hp = new ArrayList<Integer>();
		
		for(int i=0; i<br.size(); i++){
			Rule r = br.getRule(i);
			hp.add(r.getHypothesis().size());
		}
		
		
		
		while(!bf2.isEmpty()){
			
			for(int i=0; i<bf2.getAtoms().size();i++){
				Atom f = bf2.getAtoms().get(i);
				bf2.getAtoms().remove(i);
				
				for (int j=0; j<br.size();j++){
					Rule r = br.getRule(i);
					if(r.belongsAtom(f)){
						
						if(hp.get(j)!=0){ 
							cpt = hp.get(j) - 1;
							hp.set(j, cpt);
							
						}
						
						
						if(hp.get(j) == 0){
							
							if(!(bf2.belongsAtom(r.getConclusion())) && !(bf.belongsAtom(r.getConclusion()))){
								bf2.addAtomWithoutCheck(r.getConclusion());
								bf.addAtomWithoutCheck(r.getConclusion());
							}
						}
					}
				}
			}
		}
	}
	
	//chainage avant avec le principe des régles semi-positif
	public void forwardChainingOptEtendu(){
		boolean fin = false, trouve = false;
		HashMap<Integer, Boolean> hp = new HashMap<Integer, Boolean>();
		FactBase conclusion = new FactBase();
		ArrayList<Atom> hyp = new ArrayList<Atom>();
		FactBase nie = new FactBase();
		
		for(int i=0; i<br.size();i++){
			Rule r = br.getRule(i);
			hp.put(i, false);
			conclusion.addAtomWithoutCheck(r.getConclusion());
		}
		
		while(!fin){
			
			bf2 = new FactBase();
			
			for(int i=0; i<br.size();i++){
				Rule r = br.getRule(i);
				
				hyp = new ArrayList<Atom>();
				for(Atom h : r.getHypothesis()){
					trouve = false;
					for(Atom c : conclusion.getAtoms()){
						if((h.toString().charAt(0)=='!')){
							String s = h.toString().substring(1);	
							if(s.equals(c.toString())){
								System.out.println(s.toString() + "|" + c.toString());
								trouve = true;
								nie.addAtomWithoutCheck(new Atom(s));
								break;
							}
						}
					}
					if(!trouve){
						hyp.add(h);
					}
					
				}
				
				if(bf.getAtoms().containsAll(hyp) && hp.get(i).equals(false) && !nie.belongsAtom(r.getConclusion())){
					hp.replace(i, true);
					if(!(bf2.belongsAtom((r.getConclusion()))) && !bf.belongsAtom(r.getConclusion())){
						bf2.addAtomWithoutCheck(r.getConclusion());
					}
				}
				
				
			}
			if(bf2.isEmpty()){
				fin = true;
			}else{
				bf.addAtoms(bf2.getAtoms());
			}
			
		}
	}
	
	public boolean bc3(Atom q, ArrayList<Atom> l){
		if(bf.belongsAtom(q)) return true;
		
		for(int i=0; i<br.size(); i++){
			
			Rule r = br.getRule(i);
			if(r.getConclusion().equals(q)){
				if(!l.containsAll(r.getHypothesis())){
					int j = 0;
					l.add(q);
					while(j<r.getHypothesis().size() && bc3(r.getHypothesis().get(j), l)) j++;
					
					if(j>=r.getHypothesis().size()) return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean bc3Opt(Atom q, ArrayList<Atom> l, ArrayList<Atom> p, ArrayList<Atom> e){
		
		if(bf.belongsAtom(q) && p.contains(q) && !p.isEmpty()) return true;
		
		for(int i=0; i<br.size(); i++){
			
			Rule r = br.getRule(i);
			if(r.getConclusion().equals(q)){
				if(!l.containsAll(r.getHypothesis()) && !e.containsAll(r.getHypothesis())){
					int j = 0;
					l.add(q);
					while(j<r.getHypothesis().size() && bc3Opt(r.getHypothesis().get(j), l, p , e)) j++;
					
					if(j>=r.getHypothesis().size()) {
						p.add(q);
						return true;
					}
				}
			}
		}
		
		e.add(q);
		
		return false;
	}
	
	public FactBase getBf(){
		return(bf);
	}
	
	
	public FactBase getBf2(){
		return(bf2);
	}
	
	public RuleBase getbr(){
		return(br);
	}
	
	public String toString(){
		return (bf.toString() + br.toString());
	}

}
