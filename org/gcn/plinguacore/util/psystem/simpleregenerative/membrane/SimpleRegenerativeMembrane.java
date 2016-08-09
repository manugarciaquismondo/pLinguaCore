package org.gcn.plinguacore.util.psystem.simpleregenerative.membrane;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class SimpleRegenerativeMembrane extends RegenerativeMembrane {







	public SimpleRegenerativeMembrane(ChangeableMembrane membrane,
			TissueLikeMembraneStructure structure, boolean checkLink)
			throws PlinguaCoreException {
		super(membrane, structure, checkLink);
		// TODO Auto-generated constructor stub
	}



	public SimpleRegenerativeMembrane(Membrane membrane,
			TissueLikeMembraneStructure structure, String linkObject,
			boolean checkLink)
			throws PlinguaCoreException {
		super(membrane, structure, linkObject, checkLink, new HashSet<String>());
		// TODO Auto-generated constructor stub
	}

	public SimpleRegenerativeMembrane(String label, MultiSet<String> multiSet,
			TissueLikeMembraneStructure structure, boolean checkLink) throws PlinguaCoreException {
		super(label, multiSet, structure, checkLink, new HashSet<String>());
		// TODO Auto-generated constructor stub
	}


	@Override
	protected RegenerativeMembraneStructure createMembraneStructure(
			TissueLikeMembraneStructure bufferStructure) {
		// TODO Auto-generated method stub
		return new SimpleRegenerativeMembraneStructure(bufferStructure);
	}



	public SimpleRegenerativeMembrane(String label, MultiSet<String> multiSet,
			TissueLikeMembraneStructure structure, String linkObject,
			boolean checkLink)
			throws PlinguaCoreException {
		super(label, multiSet, structure, linkObject, checkLink, new HashSet<String>());
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void extractAndRemoveLinkObject(String linkObject)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
	}
	

	@Override
	public void setLinkObject(String linkObject, Set<String> linkObjects) {
		// TODO Auto-generated method stub
		super.setLinkObject("", new HashSet<String>());
		this.linkObject="";
	}



	@Override
	protected boolean testLinkObject(String linkObject) {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean linkObjectMatches(String inputLinkObject) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected RegenerativeMembrane createRegenerativeMembrane(
			RegenerativeMembrane regenerativeMembrane,
			TissueLikeMembraneStructure structure, String linkObject2,
			boolean b, Set<String> linkObjects2) throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new SimpleRegenerativeMembrane(regenerativeMembrane, structure,
				linkObject2, b);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toStringMembraneCore()+super.printAdjacents();
	}



	@Override
	protected String printAdjacentsBody() {
		List<String> adjacentsLabel=new LinkedList<String>();
		for(Integer adjacent: adjacents){
			adjacentsLabel.add(this.getStructure().getCell(adjacent).getLabel());
		}
		// TODO Auto-generated method stub
		return adjacentsLabel.toString();
	}

}
