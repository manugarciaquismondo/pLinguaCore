package org.gcn.plinguacore.simulator.fuzzy;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.fuzzy.FuzzyPsystem;

public class ProxyFuzzyMatrixAlgorithm {

	public static IFuzzyMatrixAlgorithm getConcreteMatrixAlgorithm(Psystem ps)
	{
		IFuzzyMatrixAlgorithm result = null;
		
		if(ps instanceof FuzzyPsystem)
		{
			FuzzyPsystem fps = (FuzzyPsystem) ps;
			
			if(fps.getSystemType() == 0)
				result = new TrapezoidalFuzzyMatrixAlgorithm(ps);
			else if(fps.getSystemType() == 1)
				result = new RealFuzzyMatrixAlgorithm(ps);
			else if(fps.getSystemType() == 2)
				result = new RealFuzzyMatrixAlgorithm(ps);
		}
		else
			throw new IllegalArgumentException("Only Fuzzy Psystems are supported");
		
		return result;
	}
}
