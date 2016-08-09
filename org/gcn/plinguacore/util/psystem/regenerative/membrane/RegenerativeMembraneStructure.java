package org.gcn.plinguacore.util.psystem.regenerative.membrane;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.gcn.plinguacore.parser.input.plingua.PlinguaSemanticsException;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;

public class RegenerativeMembraneStructure extends SimpleKernelLikeMembraneStructure {
	
	protected boolean linkCheck;
	

	public RegenerativeMembraneStructure(MembraneStructure membrane) {
		super(membrane);
		linkCheck=true;
		// TODO Auto-generated constructor stub
	}
	
	public RegenerativeMembraneStructure(MembraneStructure membrane, Psystem psystem) {
		super(membrane, psystem);
		linkCheck=true;
		// TODO Auto-generated constructor stub
	}


	@Override
	protected boolean secureAdd(TissueLikeMembrane arg0) {
		if(arg0 instanceof RegenerativeMembrane){
			return super.secureAdd(arg0);
		}
		else{
			try{
				return super.secureAdd(createRegenerativeMembrane(arg0, this, false));
			}
			catch(PlinguaCoreException e){return false;}
		}
	}

	protected RegenerativeMembrane createRegenerativeMembrane(
			TissueLikeMembrane arg0,
			RegenerativeMembraneStructure regenerativeMembraneStructure,
			boolean b) throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new RegenerativeMembrane(arg0, this, false);
	}

	@Override
	public boolean add(TissueLikeMembrane arg0) {
		RegenerativeMembrane membrane=null;
		if(arg0 instanceof RegenerativeMembrane){
			membrane=(RegenerativeMembrane)arg0;
		}
		else{
			try{
				membrane=createRegenerativeMembrane(arg0, this, linkCheck);
			}
			catch(PlinguaCoreException e){
				return false;
			}
		}
		return super.add(membrane);
	}
	
	public boolean enableLinkCheck(){
		boolean previousLinkCheck=linkCheck;
		this.linkCheck=true;
		return previousLinkCheck!=linkCheck;
	}
	
	public boolean disableLinkCheck(){
		boolean previousLinkCheck=linkCheck;
		this.linkCheck=false;
		return previousLinkCheck!=linkCheck;
	}
	



	protected TissueLikeMembrane createMembrane(Membrane m) {
		if((m instanceof RegenerativeMembrane)&&(((RegenerativeMembrane) m).getLinkObject()!=null)){
			return (TissueLikeMembrane) ((RegenerativeMembrane) m).clone();
		} else {
			// TODO Auto-generated catch block
			return super.createMembrane(m);
		}
	}
	
	
	public boolean isLinked(RegenerativeMembrane membrane1, RegenerativeMembrane membrane2){
		return membrane1.isAdjacent(membrane2);
	}



	public boolean isEquivalent(RegenerativeMembraneStructure structure){
		return structureIsIncluded(structure)&&structure.structureIsIncluded(this);
		
	}

	protected boolean structureIsIncluded(
			RegenerativeMembraneStructure structure) {
		Map<String, Set<MultiSet<String>>>allLabels=getAllLabels();
		for(Membrane membrane: structure.getAllMembranes()){
			String comparedLabel=membrane.getLabel();
			if(!getEnvironmentLabel().equals(comparedLabel)){
				Iterator<TissueLikeMembrane> membraneIterator=structure.iterator(comparedLabel);
				if(!allLabels.containsKey(comparedLabel)){
					return false;
				}
				Set<MultiSet<String>> localeMultiSets=allLabels.get(comparedLabel);
				while(membraneIterator.hasNext()){
					MultiSet<String> multiSetToCompare=membraneIterator.next().getMultiSet();
					if(!findMultiSet(localeMultiSets, multiSetToCompare)){
						return false;
					}
				}
			}
		}
		return true;
	}

	protected boolean findMultiSet(Set<MultiSet<String>> localeMultiSets,
			MultiSet<String> multiSetToCompare) {
		for(MultiSet<String> localeMultiSet: localeMultiSets){
			if(localeMultiSet.equals(multiSetToCompare))
				return true;
		}
		return false;
	}
	


	protected Map<String, Set<MultiSet<String>>> getAllLabels(){
		Map<String, Set<MultiSet<String>>>allLabels=new HashMap<String,Set<MultiSet<String>>>();
		for(Membrane membrane:this.getAllMembranes()){
			String membraneLabel=membrane.getLabel();
			if(!getEnvironmentLabel().equals(membraneLabel)){
				if(!allLabels.containsKey(membraneLabel)){
					allLabels.put(membraneLabel, new HashSet<MultiSet<String>>());
				}
				allLabels.get(membraneLabel).add(new HashMultiSet<String>(membrane.getMultiSet()));
			}
		}
		return allLabels;
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new RegenerativeMembraneStructure(this);
	}
	
	public boolean isEnvironmentLabel(String membraneLabel) {
		return membraneLabel.equals(getEnvironmentLabel());
		
	}

}
