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

package org.gcn.plinguacore.parser.input.plingua;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

abstract class PlinguaEnvironment {

	protected static int unknownValueIndex;
	private Map<String, Object> variables;
	protected static Map<String, Integer> unknownValAssociations;
	private boolean safeMode = true;

	private int depthLevel = 0;

	protected PlinguaEnvironment() {
		variables = new HashMap<String, Object>();
		unknownValueIndex = Integer.MIN_VALUE;
		unknownValAssociations= new HashMap<String, Integer>();
	}

	protected void reset() {
		variables.clear();
		safeMode = true;
		depthLevel = 0;
	}

	private Object getVariable(String var, Token tokenInit, Token tokenEnd, Set<String> nonCheckedVariables)
			throws ParseException {


		if (!variables.containsKey(var)){
			lookForNonCheckedIndexes(var, tokenInit, tokenEnd, nonCheckedVariables);
			PlinguaProgram.throwSemanticsException(var + " cannot be resolved",
					tokenInit, tokenEnd);
		}
		return variables.get(var);
	}

	private void lookForNonCheckedIndexes(String var, Token tokenInit,
			Token tokenEnd, Set<String> nonCheckedVariables)
			throws PlinguaSemanticsException {
		String[] indexes = var.split("[{},]");
		for(int i=0; i<indexes.length; i++){
			try{
			if(unknownValAssociations.containsValue(Integer.parseInt(indexes[i]))||indexes[i].equals("null")||nonCheckedVariables.contains(indexes[i]))
				PlinguaProgram.throwSemanticsException(indexes[i] + " used in " +var+" has already been declared for object templates ",
						tokenInit, tokenEnd);
			}
			catch(NumberFormatException nfe){}
		}
	}

	protected String getStringVariable(String var, Token tokenInit,
			Token tokenEnd, Set<String> nonCheckedVariables) throws ParseException {
		if (safeMode)
			return null;
		if(nonCheckedVariables.contains(var))
			return var;
		Object obj = getVariable(var, tokenInit, tokenEnd, nonCheckedVariables);
		if (!(obj instanceof String))
			PlinguaProgram.throwSemanticsException(var
					+ " cannot be resolved as string", tokenInit, tokenEnd);
		return (String) obj;
	}

	
	protected boolean containsNonCheckedIndexes(String var, Token tokenInit,
			Token tokenEnd, Set<String> nonCheckedVariables){
		if(nonCheckedVariables.contains(var))
			return true;
		try {
			lookForNonCheckedIndexes(var, tokenInit,
						tokenEnd, nonCheckedVariables);
		} catch (PlinguaSemanticsException e) {
				// TODO Auto-generated catch block
			return true;
		}
		return false;
			

		
	}
	protected Number getNumericVariable(String var, Token tokenInit,
			Token tokenEnd, Set<String> nonCheckedVariables) throws ParseException {
		Number devolver = null;
		if (safeMode)
			return null;
		if(containsNonCheckedIndexes(var, tokenInit,
				tokenEnd, nonCheckedVariables)){
			if(unknownValAssociations.containsKey(var)){
				devolver = unknownValAssociations.get(var);
			} else {
				unknownValAssociations.put(var, unknownValueIndex);
				devolver = unknownValueIndex + 0;
			}
			unknownValueIndex++;
			return devolver;
		}
		Object obj = getVariable(var, tokenInit, tokenEnd, nonCheckedVariables);
		if (!(obj instanceof Number))
			PlinguaProgram.throwSemanticsException(var
					+ " cannot be resolved as number", tokenInit, tokenEnd);
		return (Number) obj;
	}

	protected void setVariable(String name, Number value) {
		if (!safeMode)
			variables.put(name, value);
	}

	protected void setVariable(String name, String value) {
		if (!safeMode)
			variables.put(name, value);
	}

	protected void removeVariable(String name) {
		if (!safeMode)
			variables.remove(name);

	}

	protected boolean isSafeMode() {
		return safeMode;
	}

	protected void enableSafeMode() {
		safeMode = true;
	}

	protected void disableSafeMode() {
		safeMode = false;
	}

	protected void incDepthLevel() {
		depthLevel++;
	}

	protected void decDepthLevel() {
		depthLevel--;
	}

	protected void resetDepthLevel() {
		depthLevel = 0;
	}

	protected int getDepthLevel() {
		return depthLevel;
	}

}
