/* 
 * pLinguaCore: A JAVA library for Membrane Computing
 *              http://www.p-lingua.org
 *
 * Copyright (C) 2009  Research Group on Natural Computing
 *                     http://www.gcn.us.es
 *                      
 * This file is part of pLinguaCore.
 *
 * pLinguaCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pLinguaCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pLinguaCore.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule;

import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;

/**
 * This class tests if the outer multiset in the left hand of the rule is equal
 * to the multiset into the outer membrane of the right hand of the rule, and
 * vice versa (only if the rule is not a division, creation or dissolution rule)
 * 
 *  @author Research Group on Natural Computing (http://www.gcn.us.es)
 * 
 */
public class InfEnvSymportAntiport extends DecoratorCheckRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8634047200621525016L;

	
	/**
	 * Creates a new SymportAntiport instance, which checks only the restrictions defined on itself.
	 */
	public InfEnvSymportAntiport(){
		super();
	}
	
	/**
	 * Creates a new SymportAntiport instance, which wraps cr as stated
	 * by Decorator pattern. Thus, it will be capable to test both the instance restrictions and cr restrictions 
	 * 
	 * @param cr
	 *            the CheckRule instance to be wrapped
	 */
	public InfEnvSymportAntiport(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkSpecificPart(IRule r) {

		if (!noDivisionCheckRule.checkRule(r) || !noCreationCheckRule.checkRule(r) || !noDissolutionCheckRule.checkRule(r))
			return true;
		
		boolean sameLabels = r.getLeftHandRule().getOuterRuleMembrane().getLabel().equals(r.getRightHandRule().getOuterRuleMembrane().getLabel());
		
		// (u,in) = u[]'h --> [u]'h
		
		boolean case1 = r.getLeftHandRule().getOuterRuleMembrane().getMultiSet().isEmpty() &&
							r.getRightHandRule().getMultiSet().isEmpty() &&
								r.getLeftHandRule().getMultiSet().equals(r.getRightHandRule().getOuterRuleMembrane().getMultiSet());
		
		
		// (u,out) = [u]'h --> u[]'h
		
		boolean case2 = r.getLeftHandRule().getMultiSet().isEmpty() && 
							r.getRightHandRule().getOuterRuleMembrane().getMultiSet().isEmpty() &&
									r.getLeftHandRule().getOuterRuleMembrane().getMultiSet().equals(r.getRightHandRule().getMultiSet());
		

		// (u,in ; v,out) = u[v]'h --> v[u]'h
		
		boolean case3 = !r.getLeftHandRule().getMultiSet().isEmpty() &&
							!r.getLeftHandRule().getOuterRuleMembrane().getMultiSet().isEmpty() &&
									r.getLeftHandRule().getMultiSet().equals(r.getRightHandRule().getOuterRuleMembrane().getMultiSet()) &&
										r.getLeftHandRule().getOuterRuleMembrane().getMultiSet().equals(r.getRightHandRule().getMultiSet());
				
		boolean isRight = sameLabels && (case1 || case2 || case3);
		
		return isRight;

	}

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return "Not a valid symport/antiport rule";
	}

}
