package org.gcn.plinguacore.util.psystem.simpleregenerative;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.simpleregenerative.membrane.SimpleRegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;


public class SimpleRegenerativePsystem extends RegenerativePsystem {


	@Override
	protected void testMembraneListByLinkObject(String linkObject,
			List<RegenerativeMembrane> membraneList)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
	}

	@Override
	protected List<RegenerativeMembrane> findMembraneByLinkObject(
			String linkObject, Map<String, MultiSet<String>> initialMultiSets) {
		// TODO Auto-generated method stub
		List<RegenerativeMembrane> membraneList=new LinkedList<RegenerativeMembrane>();
		Iterator<TissueLikeMembrane> membraneIterator =((TissueLikeMembraneStructure)getMembraneStructure()).iterator(linkObject);
		while(membraneIterator.hasNext()){
			membraneList.add((RegenerativeMembrane)membraneIterator.next());
		}
		return membraneList;
	}

	@Override
	public boolean isLinkObject(String linkObject) {
		// TODO Auto-generated method stub
		return ((TissueLikeMembraneStructure)getMembraneStructure()).iterator(linkObject).hasNext();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return coreString()+printInitialLinks();
	}


	@Override
	protected RegenerativeMembraneStructure createMembraneStructure(
			MembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		return new SimpleRegenerativeMembraneStructure(membraneStructure);
	}
}
