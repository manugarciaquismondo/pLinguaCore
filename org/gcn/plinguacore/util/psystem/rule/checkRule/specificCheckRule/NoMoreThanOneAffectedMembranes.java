package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule;

import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;

public class NoMoreThanOneAffectedMembranes extends NoMultipleDivision {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean checkSpecificPart(IRule r) {
		// TODO Auto-generated method stub
		return (super.checkSpecificPart(r)||
				r.getRightHandRule().getAffectedMembranes().size()<=1);
	}

	public NoMoreThanOneAffectedMembranes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoMoreThanOneAffectedMembranes(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return "No more than one affected membranes are allowed";
	}

}
