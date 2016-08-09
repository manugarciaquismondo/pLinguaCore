package org.gcn.plinguacore.util.psystem.simpleregenerative.membrane;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;

public class SimpleRegenerativeMembraneStructure extends
		RegenerativeMembraneStructure {

	@Override
	protected RegenerativeMembrane createRegenerativeMembrane(
			TissueLikeMembrane arg0,
			RegenerativeMembraneStructure regenerativeMembraneStructure,
			boolean b) throws PlinguaCoreException {
		return new SimpleRegenerativeMembrane(arg0, regenerativeMembraneStructure, b);
	}

	public SimpleRegenerativeMembraneStructure(MembraneStructure membrane) {
		super(membrane);
	}

	@Override
	public Object clone() {
		RegenerativeMembraneStructure structure= new SimpleRegenerativeMembraneStructure(this);
		copyLinksInMembraneStructure(structure);
		return structure;
	}

	protected void copyLinksInMembraneStructure(
			RegenerativeMembraneStructure structure) {
		for(Membrane membrane1: getAllMembranes()){
			for(Membrane membrane2: getAllMembranes()){
				RegenerativeMembrane originalRegenerativeMembrane1=(RegenerativeMembrane)membrane1;
				RegenerativeMembrane originalRegenerativeMembrane2=(RegenerativeMembrane)membrane2;
				RegenerativeMembrane clonedRegenerativeMembrane1=(RegenerativeMembrane)structure.getCell(membrane1.getId());
				RegenerativeMembrane clonedRegenerativeMembrane2=(RegenerativeMembrane)structure.getCell(membrane2.getId());
				if(originalRegenerativeMembrane1.isAdjacent(originalRegenerativeMembrane2)&&!clonedRegenerativeMembrane1.isAdjacent(clonedRegenerativeMembrane2)){
					((RegenerativeMembrane)clonedRegenerativeMembrane1).addLink(clonedRegenerativeMembrane2.getId());
					((RegenerativeMembrane)clonedRegenerativeMembrane2).addLink(clonedRegenerativeMembrane1.getId());
				}
			}
		}
	}

}
