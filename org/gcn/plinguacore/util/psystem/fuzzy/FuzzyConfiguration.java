package org.gcn.plinguacore.util.psystem.fuzzy;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;

public class FuzzyConfiguration extends Configuration {
	
	private static final long serialVersionUID = -8776601890336921636L;

	protected FuzzyConfiguration(Psystem psystem) {
		super(psystem);
		// TODO Auto-generated constructor stub
	}
	public MultiSet<String> getEnvironment() {
		// TODO Auto-generated method stub
		// SpikingMembraneStructure structure =  (SpikingMembraneStructure)getMembraneStructure();
		// return structure.getEnvironment();
		
		return new HashMultiSet<String>();
	}

	@Override
	protected Configuration getNewConfiguration() {
		// TODO Auto-generated method stub
		return new FuzzyConfiguration(getPsystem());
	}

}
