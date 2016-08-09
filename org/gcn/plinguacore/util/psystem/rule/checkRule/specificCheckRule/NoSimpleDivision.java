package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule;

import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;

public class NoSimpleDivision extends DecoratorCheckRule {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected boolean checkSpecificPart(IRule r) {
		return r.getRightHandRule().getSecondOuterRuleMembrane() == null;
	}

	public NoSimpleDivision() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoSimpleDivision(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return "Simple Division rules are not allowed";
	}

}
