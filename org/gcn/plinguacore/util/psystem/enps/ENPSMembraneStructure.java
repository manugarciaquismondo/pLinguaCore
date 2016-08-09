package org.gcn.plinguacore.util.psystem.enps;

import java.util.Collection;
import java.util.HashSet;

import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;

public class ENPSMembraneStructure implements MembraneStructure {

	@Override
	public Collection<? extends Membrane> getAllMembranes() {
		// TODO Auto-generated method stub
		return new HashSet<Membrane>();
	}

	@Override
	public Membrane getMembrane(int id) {
		// TODO Auto-generated method stub
		return new ENPSMembrane(new Label(""+id));
	}
	
	public Object clone(){
		return new ENPSMembraneStructure();
	}

}
