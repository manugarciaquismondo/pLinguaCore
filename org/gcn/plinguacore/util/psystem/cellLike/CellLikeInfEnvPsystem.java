package org.gcn.plinguacore.util.psystem.cellLike;


import org.gcn.plinguacore.util.HashInfiniteMultiSet;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.InfiniteMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeNoSkinMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;

public class CellLikeInfEnvPsystem extends CellLikePsystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8977659883243860341L;
	
	private MultiSet<String> envInfMultiSet = new HashMultiSet<String>();
	
	public MultiSet<String> getEnvInfMultiSet() {
		return envInfMultiSet;
	}

	public void setEnvInfMultiSet(MultiSet<String> envInfMultiSet) {
		this.envInfMultiSet = envInfMultiSet;
	}
	
	@Override
	public Configuration getFirstConfiguration() {
		
		Configuration config = super.getFirstConfiguration();
		
		CellLikeSkinMembrane structure = (CellLikeSkinMembrane) config.getMembraneStructure();
		
		if(!structure.getLabel().equals("environment"))
			throw new IllegalArgumentException("The environment 'membrane' must be labelled as 'environment'");
		
		if(structure.getChildMembranes().size() != 1)
			throw new IllegalArgumentException("The environment membrane must contain exactly one membrane (corresponding to the skin)");
		
		structure.makeInfiniteMultiSet();
		
		HashInfiniteMultiSet<String> inf = (HashInfiniteMultiSet<String>) structure.getMultiSet();
		
		inf.addAll(envInfMultiSet);
		
		inf.setAllObjectsWithInfiniteMultiplicity();
		
		return config;
	}

}
