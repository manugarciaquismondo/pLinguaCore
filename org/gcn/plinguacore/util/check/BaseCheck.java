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

package org.gcn.plinguacore.util.check;

import java.util.ArrayList;
import java.util.List;



public class BaseCheck<T> implements InnerCheck<T> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3685134922361847876L;
	private List<String> causes;
	private boolean initializedCauses = false;

	/**
	 * Creates a new BaseCheckRule instance
	 */
	public BaseCheck() {
		super();
		causes = new ArrayList<String>();
	}

	/**
	 * @see org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule#checkRule(org.gcn.plinguacore.util.psystem.rule.Rule)
	 */
	@Override
	public boolean check(T obj) {
		// TODO Auto-generated method stub
		// ;
		initializedCauses = false;
		return true;
	}

	/**
	 * @see org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule#getCauses()
	 */
	@Override
	public List<String> getCauses() {
		return causes;
	}

	/**
	 * @see org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule#getCausesString()
	 */
	@Override
	public String getCausesString() {
		String str = "";
		for (int i = 0; i < causes.size(); i++) {
			str += "    " + causes.get(i);
			if (i < causes.size() - 1)
				str += "\n";
		}
		return str;
	}

	/* (non-Javadoc)
	 * @see util.psystem.rule.checkRule.InnerCheckRule#initializedCauses()
	 */
	@Override
	public boolean initializedCauses() {
		// TODO Auto-generated method stub
		return initializedCauses;
	}


	/* (non-Javadoc)
	 * @see util.psystem.rule.checkRule.InnerCheckRule#initCauses()
	 */
	@Override
	public void initCauses() {
		initializedCauses = true;

	}

}
