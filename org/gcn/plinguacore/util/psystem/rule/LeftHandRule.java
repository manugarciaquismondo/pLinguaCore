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

package org.gcn.plinguacore.util.psystem.rule;

import org.gcn.plinguacore.util.MultiSet;

/**
 * This class represents left hand rules
 * 
 *  @author Research Group on Natural Computing (http://www.gcn.us.es)
 * 
 */
public final class LeftHandRule extends HandRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 931919701324713064L;

	/**
	 * Creates a LeftHandRule instance
	 * 
	 * @param outerMembrane
	 *            the outer membrane in the hand rule
	 * @param multiset
	 *            the outer multiset in the hand rule
	 */
	public LeftHandRule(OuterRuleMembrane outerMembrane,
			MultiSet<String> multiset) {
		super(outerMembrane, multiset);

	}

}
