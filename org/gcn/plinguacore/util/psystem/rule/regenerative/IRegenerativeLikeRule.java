package org.gcn.plinguacore.util.psystem.rule.regenerative;

import java.util.Set;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IPriorityRule;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;

public interface IRegenerativeLikeRule extends IKernelRule, IPriorityRule{

	public void setMembraneStructure(
			SimpleKernelLikeMembraneStructure membraneStructure);
	public void setLinkObjects(Set<String> linkObjects) throws PlinguaCoreException;
	public int getObjectOffset();
	public void setPriority(int priority);
}
