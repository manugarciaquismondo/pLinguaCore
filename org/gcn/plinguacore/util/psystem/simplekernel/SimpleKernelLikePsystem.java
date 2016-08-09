package org.gcn.plinguacore.util.psystem.simplekernel;

import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.TissueLikePsystem;

public class SimpleKernelLikePsystem extends TissueLikePsystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5788593338051827062L;

	@Override
	public void setMembraneStructure(MembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		if (membraneStructure instanceof CellLikeSkinMembrane)
		{
			super.setMembraneStructure(new SimpleKernelLikeMembraneStructure(membraneStructure));
			return;
		}
		super.setMembraneStructure(membraneStructure);
	}
	
	@Override
	public boolean addRule(IRule r) {
		Guard guard = ((IKernelRule)r).getGuard();
		if(guard != null) {
			addAlphabetObjects(guard.getMultiSet());
		}
		
		return super.addRule(r);
	}
	
	@Override
	public boolean definesGuards(){
		return true;
	}
}
