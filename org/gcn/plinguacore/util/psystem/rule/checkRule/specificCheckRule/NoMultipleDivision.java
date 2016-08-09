package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule;

import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;

public class NoMultipleDivision extends DecoratorCheckRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkSpecificPart(IRule r) {
		return (r.getRightHandRule().getAffectedMembranes()==null||
				r.getRightHandRule().getAffectedMembranes().isEmpty());
	}

	public NoMultipleDivision() {
		super();
	}

	public NoMultipleDivision(CheckRule cr) {
		super(cr);
	}

	@Override
	protected String getSpecificCause() {
		return "No multiple division is allowed";
	}

}
