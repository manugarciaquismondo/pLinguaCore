package org.gcn.plinguacore.simulator.fuzzy;

import org.gcn.plinguacore.simulator.AbstractSelectionExecutionSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.FuzzyMembraneStructure;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IRule;



public class FuzzyBaseSimulator extends AbstractSelectionExecutionSimulator {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3880218880504932888L;

	
	private IFuzzyMatrixAlgorithm alg;
	private int executionStep = 0;
	
	private FuzzyMembraneStructure structure = null;

	public FuzzyBaseSimulator(Psystem psystem) throws PlinguaCoreException{
		super(psystem);
		alg = ProxyFuzzyMatrixAlgorithm.getConcreteMatrixAlgorithm(psystem);
		alg.initialize();
		structure = (FuzzyMembraneStructure) this.getPsystem().getMembraneStructure();
	
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean specificStep() throws PlinguaCoreException {

		microStepInit();
		microStepSelectRules();
		
		if (hasSelectedRules()) {
			microStepExecuteRules();
			//currentConfig = (Configuration)currentConfig.clone();
			currentConfig.setNumber(currentConfig.getNumber()+1);
		}
		return hasSelectedRules();
	}
	
	public void microStepInit() {
		// TODO Auto-generated method stub
		
		super.microStepInit();
		
		executionStep++;
		
		if(executionStep == 1)
		{
			System.out.println(structure.toString());
			System.out.println();
		}
			
	}

	@Override
	protected boolean hasSelectedRules() {
		// TODO Auto-generated method stub
	
		return !alg.checkEndCondition();
	}


	@Override
	protected void microStepSelectRules() throws PlinguaCoreException{
		// TODO Auto-generated method stub
	}


	@Override
	public void microStepExecuteRules() {
		// TODO Auto-generated method stub
		
		if(executionStep == 1)
			alg.runInitialSteps();
		
		alg.runStep();
		
		if(alg.checkEndCondition())
			alg.printResults();
	}

	@Override
	protected void microStepSelectRules(ChangeableMembrane membrane,
			ChangeableMembrane tempMembrane) {
		// TODO Auto-generated method stub

	}


	@Override
	protected String getHead(ChangeableMembrane m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void printInfoMembrane(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void printInfoMembraneShort(MembraneStructure membranes) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void removeLeftHandRuleObjects(ChangeableMembrane membrane,
			IRule r, long count) {
		// TODO Auto-generated method stub
		
	}
		
}
