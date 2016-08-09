package org.gcn.plinguacore.util.psystem.enps;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;

import edu.psys.core.enps.ENPSMembraneSystem;

public class ENPSConfiguration extends Configuration {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6922981490328088892L;
	ENPSMembraneSystem membraneSystem;
	
	public ENPSConfiguration(Psystem psystem) {
		super(psystem);
		if (! (psystem instanceof ENPSHolder))
			throw new IllegalArgumentException("The P System given as argument should be an ENPSHolder");
		membraneSystem = ((ENPSHolder)psystem).getENPSMembraneSystem();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Configuration getNewConfiguration() {
		// TODO Auto-generated method stub
		return new ENPSConfiguration(this.getPsystem());
	}

	@Override
	public MultiSet<String> getEnvironment() {
		// TODO Auto-generated method stub
		return new HashMultiSet<String>();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return membraneSystem.toString();
	}
	
	public Object clone(){
		return super.clone();
	}

	
	

}
