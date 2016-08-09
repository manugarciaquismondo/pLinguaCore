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

import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;

import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;

/**
 * This class tests if the rule doesn't hold a guard representing
 * a condition over the rule to execute or not
 * 
 *  @author Research Group on Natural Computing (http://www.gcn.us.es)
 * 
 */
public class NoGuard extends DecoratorCheckRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8215412423729798727L;

	
	/**
	 * Creates a new NoGuard instance, which checks only the restrictions defined on itself.
	 */
	public NoGuard(){
		super();
	}
	/**
	 * Creates a new NoGuard instance, which wraps cr as stated
	 * by Decorator pattern. Thus, it will be capable to test both the instance restrictions and cr restrictions 
	 * 
	 * @param cr
	 *            the CheckRule instance to be wrapped
	 */
	public NoGuard(CheckRule cr) {
		super(cr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * util.psystem.rule.checkrule.DecoratorCheckRule#checkSpecificPart(util
	 * .psystem.rule.IRule)
	 */
	@Override
	protected boolean checkSpecificPart(IRule r) {

		return !(r instanceof IKernelRule)||(((IKernelRule)r).getGuard()==null);

	}

	@Override
	protected String getSpecificCause() {
		return "Rules with guards are not allowed";
	}

}
