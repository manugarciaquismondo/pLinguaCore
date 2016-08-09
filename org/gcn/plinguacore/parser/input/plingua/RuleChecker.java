package org.gcn.plinguacore.parser.input.plingua;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.HandRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;

public class RuleChecker {
	public static boolean sameLabel(RightHandRule rightHandRule, LeftHandRule leftHandRule){
		return leftHandRule.getOuterRuleMembrane().getLabel().equals(rightHandRule.getOuterRuleMembrane().getLabel());
	}
	
	public static boolean isDivisionOrRelabellingRule(LeftHandRule leftHandRule, RightHandRule rightHandRule){
		MultiSet<String> multiSet = leftHandRule.getOuterRuleMembrane().getMultiSet();
		return multiSet==null||multiSet.isEmpty();
	}
	
	public static boolean matchModelID(Psystem psystem, String modelID){
		return getModelName(psystem).equals(modelID);
	}

	protected static String getModelName(Psystem psystem) {
		return psystem.getAbstractPsystemFactory().getModelName();
	}
	
	protected static boolean checkProbability(IRule r, float probability, Token beginRuleToken, Token endRuleToken, PlinguaProgram program) throws PlinguaSemanticsException{
	  	if (probability < 0.002) {
				program.writeWarning("Rule ignored: " + r + ". Its probability is close to 0", beginRuleToken,
						endRuleToken);
				return false;
		}
	  	return true;
	}
	
	protected static boolean checkExistingSourceAndDestinationMembrane(IRule r, Token beginRuleToken, Token endRuleToken, PlinguaProgram program) throws PlinguaSemanticsException{
		if(!containsLabel(program.getPsystem().getMembraneStructure(), r.getLeftHandRule())){
			program.writeWarning("Rule ignored: " + r + ". The source membrane does not exist", beginRuleToken,
					endRuleToken);
			return false;
		}
		if(!containsLabel(program.getPsystem().getMembraneStructure(), r.getRightHandRule())){
			program.writeWarning("Rule ignored: " + r + ". The destination membrane does not exist", beginRuleToken,
					endRuleToken);
			return false;
		}
		return true;
			
	}

	private static boolean containsLabel(MembraneStructure membraneStructure,
			HandRule handRule) {
		Label label = handRule.getOuterRuleMembrane().getLabelObj();
		for(Membrane membrane : membraneStructure.getAllMembranes()){
			if(membrane.getLabelObj().equals(label))
				return true;
		}
		return false;
	}
}
