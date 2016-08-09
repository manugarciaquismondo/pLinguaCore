package org.gcn.plinguacore.simulator.fuzzy;

import org.gcn.plinguacore.util.psystem.Psystem;

public abstract class AbstractFuzzyMatrixAlgorithm implements IFuzzyMatrixAlgorithm {

	protected Psystem ps;
	protected boolean endCondition = false;
	
	public AbstractFuzzyMatrixAlgorithm(Psystem ps)
	{
		associatePsystem(ps);
	}
	
	@Override
	public void associatePsystem(Psystem ps) {
		this.ps = ps;
		
	}
	
	@Override
	public void perform() {
		// TODO Auto-generated method stub
		runInitialOperation();
		
		while(!checkEndCondition())
			runStep();
		
		printResults();
	}

	@Override
	public void runInitialOperation() {
		initialize();
		runInitialSteps();
		
	}

	@Override
	public boolean checkEndCondition() {
		// TODO Auto-generated method stub
		return endCondition;
	}

	@Override
	public void runStep() {
		if(!checkEndCondition())
			runOtherSteps();
		
	}

	/**
	 * @return the ps
	 */
	public Psystem getPsystem() {
		return ps;
	}

}
