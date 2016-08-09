package org.gcn.plinguacore.util.psystem.fuzzy.membrane;

import java.util.List;

public class PropositionNeuron extends FuzzyMembrane {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4379667208915392731L;

	PropositionNeuron(String label, List<Float> value)
	{
		super(label,value);
	}
	
	PropositionNeuron(String label, List<Float> value, FuzzyMembraneStructure structure)
	{
		super(label,value,structure);
		
	}
	
	public static PropositionNeuron buildMembrane(String label, List<Float> value, FuzzyMembraneStructure structure)
	{
			
		PropositionNeuron result = new PropositionNeuron(label, value, structure);
		
		structure.add(result);
		
		return result;
	}
}
