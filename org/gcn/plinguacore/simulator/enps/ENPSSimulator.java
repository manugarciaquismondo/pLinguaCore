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

package org.gcn.plinguacore.simulator.enps;





import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.gcn.plinguacore.simulator.AbstractSimulator;
import org.gcn.plinguacore.simulator.AbstractSelectionExecutionSimulator;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.RandomNumbersGenerator;

import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.enps.ENPSConfiguration;
import org.gcn.plinguacore.util.psystem.enps.ENPSHolder;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.spiking.SpikingRule;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingEnvironment;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingMembrane;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingMembraneStructure;

import edu.psys.core.enps.ENPSMembraneSystem;

import java.util.ArrayList;

public class ENPSSimulator extends AbstractSimulator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7318292361416757718L;
	ENPSMembraneSystem membraneSystem;
	
	public ENPSSimulator(Psystem psystem) {
		super(psystem);
	
		if (!(psystem instanceof ENPSHolder))
			throw new IllegalArgumentException("The argument to create an ENPSSimulator should be an ENPSHolder");
		membraneSystem = ((ENPSHolder)psystem).getENPSMembraneSystem();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean step() throws PlinguaCoreException {
		// TODO Auto-generated method stub
		membraneSystem.simulate(1);
		currentConfig= (Configuration) currentConfig.clone();
		return true;
	}
	


	public Configuration getFirstConfiguration() {
		// TODO Auto-generated method stub
		return new ENPSConfiguration(this.getPsystem());
	}


	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean specificStep() throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	


	

}
