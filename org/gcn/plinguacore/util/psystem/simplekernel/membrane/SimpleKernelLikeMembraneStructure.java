package org.gcn.plinguacore.util.psystem.simplekernel.membrane;

import java.util.LinkedList;
import java.util.List;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class SimpleKernelLikeMembraneStructure extends TissueLikeForSimpleKernelMembraneStructure {

	public SimpleKernelLikeMembraneStructure(MembraneStructure membrane) {
		super(membrane);
	}
	
	public SimpleKernelLikeMembraneStructure(MembraneStructure membrane,
			Psystem psystem) {
		super(membrane, psystem);
	}

	public void updateMembranes(String parentLabel){
		List<TissueLikeMembrane> affectedMembranes = cells.get(parentLabel);
		List<TissueLikeMembrane> newMembranes = new LinkedList<TissueLikeMembrane>();
		for(TissueLikeMembrane membrane : affectedMembranes){
			if(!membrane.getLabel().equals(parentLabel))
				newMembranes.add(membrane);
		}
		affectedMembranes.removeAll(newMembranes);
		for(TissueLikeMembrane membrane : newMembranes){
			if(!cells.containsKey(membrane.getLabel())){
				List<TissueLikeMembrane> createdMembranes = new LinkedList<TissueLikeMembrane>();
				createdMembranes.add(membrane);
				cells.put(membrane.getLabel(), createdMembranes);
			}
			else
				cells.get(membrane.getLabel()).add(membrane);
		}
		
	}


	@Override
	public boolean remove(TissueLikeMembrane arg0) {
		return super.remove(arg0);
	}

	@Override
	public boolean add(TissueLikeMembrane arg0) {
		return super.add(arg0);
	}

	@Override
	public Object clone()
	{
		return new SimpleKernelLikeMembraneStructure(this);
	}
}
