package org.gcn.plinguacore.simulator.simpleregenerative;

import org.gcn.plinguacore.simulator.CreateSimulator;
import org.gcn.plinguacore.simulator.regenerative.RegenerativePsystemFactory;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.BaseCheckPsystem;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.simpleregenerative.SimpleRegenerativeRuleFactory;
import org.gcn.plinguacore.util.psystem.simpleregenerative.SimpleRegenerativePsystem;

public class SimpleRegenerativePsystemFactory extends
		RegenerativePsystemFactory {
	
	private static SimpleRegenerativePsystemFactory singleton;

	public static SimpleRegenerativePsystemFactory getInstance(){
		if(singleton==null){
			singleton=new SimpleRegenerativePsystemFactory();
		}
		return singleton;
	}
	
	protected SimpleRegenerativePsystemFactory() {
		super();
		checkPsystem=new BaseCheckPsystem();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Psystem newPsystem() {
		// TODO Auto-generated method stub
		return new SimpleRegenerativePsystem();
	}

	@Override
	public CreateSimulator getCreateSimulator() throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new SimpleRegenerativeCreateSimulator(getModelName());
	}

	@Override
	protected AbstractRuleFactory newRuleFactory() {
		// TODO Auto-generated method stub
		return new SimpleRegenerativeRuleFactory();
	}

}
