package org.gcn.plinguacore.util.psystem.fuzzy;

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


import org.gcn.plinguacore.simulator.fuzzy.FuzzyPsystemFactory;
import org.gcn.plinguacore.simulator.spiking.SpikingPsystemFactory;
import org.gcn.plinguacore.util.InfiniteMultiSet;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.FuzzyMembraneStructure;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingMembrane;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingMembraneStructure;
import org.gcn.plinguacore.util.psystem.spiking.SpikingConstants;


/**
 * This class represents P-systems belonging to spike-like group
 * 
 * @author Research Group on Natural Computing (http://www.gcn.us.es)
 * 
 */
public class FuzzyPsystem extends Psystem {


	private static final long serialVersionUID = -5421686831262316407L;
	
	private int systemType = -1;	// 0: trapezoidal, 1: real, 2: weighted-real
	private int fvalueType = -1;	// 0: trapezoidal, 1: real
	
	public static FuzzyPsystem buildPsystem()
	{
		FuzzyMembraneStructure structure = new FuzzyMembraneStructure();
		return buildPsystem(structure);
	}
	
	public static FuzzyPsystem buildPsystem(FuzzyMembraneStructure structure)
	{
		FuzzyPsystem ps = new FuzzyPsystem();
		
		try{
		ps.setAbstractPsystemFactory(FuzzyPsystemFactory.getInstance());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		ps.setMembraneStructure(structure);

		return ps;
	}
	
	@Override
	public void setMembraneStructure(MembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		if (membraneStructure instanceof FuzzyMembraneStructure)
		{
			
			super.setMembraneStructure(membraneStructure);
		}
		else
			throw new IllegalArgumentException("The membrane structure must be fuzzy-like");
	}

	@Override
	protected Configuration newConfigurationObject() {
		// TODO Auto-generated method stub
		return new FuzzyConfiguration(this);
	}

	@Override
	protected void addInitialMultiSets(Membrane m) {
		// TODO Auto-generated method stub
 
		
	}

	/**
	 * @return the systemType
	 */
	public int getSystemType() {
		return systemType;
	}

	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(int systemType) {

			this.systemType = systemType;
			
			if(this.systemType == 0)
				this.fvalueType = 0;
			else if(this.systemType == 1)
				this.fvalueType = 1;
			else if(this.systemType == 2)
				this.fvalueType = 1;
	}

	public int getFvalueType() {
		return fvalueType;
	}


}
