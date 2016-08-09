package org.gcn.plinguacore.util.psystem.enps;

import org.gcn.plinguacore.simulator.enps.ENPSFactory;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;

import edu.psys.core.enps.ENPSMembraneSystem;

public class ENPSHolder extends Psystem {

	protected ENPSMembraneSystem ENPS;
	
	@Override
	protected Configuration newConfigurationObject() {
		// TODO Auto-generated method stub
		return new ENPSConfiguration(this);
	}
	
	public Configuration getFirstConfiguration(){
		return new ENPSConfiguration(this);
	}

	public ENPSHolder(ENPSMembraneSystem ENPS) {
		if(ENPS==null)
			throw new IllegalArgumentException("The Enzymatic Numerical Membrane System given as argument cannot be null");
		try {
			setAbstractPsystemFactory(ENPSFactory.getInstance());
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			System.err.println("Error while creating the ENPS abstract P System factory");
		}
		this.setMembraneStructure(new ENPSMembraneStructure());
		this.ENPS = ENPS;
	}
	
	public ENPSMembraneSystem getENPSMembraneSystem(){
		return ENPS;
	}
	
	public String toString(){
		return ENPS.toString();
	}
	
	
	

}
