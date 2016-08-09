package org.gcn.plinguacore.simulator.scripts;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;

public interface PsystemScript {
	
	public Configuration getNextConfiguration(Configuration inputConfiguration, int membraneID) throws PlinguaCoreException;

}
