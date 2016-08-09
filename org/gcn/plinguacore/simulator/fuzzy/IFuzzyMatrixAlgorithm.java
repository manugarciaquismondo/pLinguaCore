package org.gcn.plinguacore.simulator.fuzzy;

import org.gcn.plinguacore.util.psystem.Psystem;

public interface IFuzzyMatrixAlgorithm {

	public void associatePsystem(Psystem ps);
	public void initialize();
	public void runInitialOperation();
	public void runInitialSteps();
	public void runOtherSteps();
	public boolean checkEndCondition();
	public void runStep();
	public void printResults();
	public void perform();
	
}
