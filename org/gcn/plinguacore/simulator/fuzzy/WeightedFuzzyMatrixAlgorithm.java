package org.gcn.plinguacore.simulator.fuzzy;

import java.util.Iterator;
import java.util.List;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.FuzzyMembrane;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.FuzzyMembraneStructure;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.PropositionNeuron;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.RuleNeuron;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

public class WeightedFuzzyMatrixAlgorithm extends AbstractFuzzyMatrixAlgorithm {

	// theta(s): vector containing the pulse values of the proposition neurons
	private float[] theta;
	
	// sigma(t): vector containing the pulse values of the rule neurons
	private float[] sigma;
	
	// C(t x t): diagonal matrix containing the confident factor of the rule neurons
	private float[] C;
	
	// W1(s x t): synaptic matrix such that w1(i,j) = w <-> there is an arc from 
	// prop neuron prop_i to general rule neuron r_j
	private float[] W1;
	
	// W2(s x t): synaptic matrix such that w2(i,j) = w <-> there is an arc from 
	// pop neuron prop_i to AND rule neuron r_j
	private float[] W2;
	
	// W3(s x t): synaptic matrix such that w3(i,j) = w <-> there is an arc from 
	// prop neuron prop_i to OR rule neuron r_j
	private float[] W3;
	
	// Wp(t x s): synaptic matrix such that Wp(j,i) = w <-> there is an arc from
	// rule neuron (any type) rule_j to prop neuron prop_i
	private float[] Wp;
	
	// number of proposition neurons
	private int s;
	
	// number of rule nurons
	private int t;
	
	// step counter
	private int g;
	
	public WeightedFuzzyMatrixAlgorithm(Psystem ps) {
		super(ps);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		FuzzyMembraneStructure structure = (FuzzyMembraneStructure) this.getPsystem().getMembraneStructure();
		
		if(structure.getNProps() == 0 || structure.getNRules() == 0)
			throw new IllegalArgumentException("This algorithm requires for each of number of propositions and rules to be greater than 0");
		
		s = structure.getNProps();
		t = structure.getNRules();
		this.endCondition = false;
		
		// initialization code starts here
		
		// theta(s): vector containing the pulse values of the proposition neurons
		theta = new float[s];
		
		// sigma(t): vector containing the pulse values of the rule neurons
		sigma = new float[t];
		
		// C(t x t): diagonal matrix containing the confident factor of the rule neurons
		C = new float[t*t];
		
		// W1(s x t): synaptic matrix such that w1(i,j) = w <-> there is an arc from 
		// prop neuron prop_i to general rule neuron r_j
		W1 = new float[s*t];
		
		// W2(s x t): synaptic matrix such that w2(i,j) = w <-> there is an arc from 
		// pop neuron prop_i to AND rule neuron r_j
		W2 = new float[s*t];
		
		// W3(s x t): synaptic matrix such that w3(i,j) = w <-> there is an arc from 
		// prop neuron prop_i to OR rule neuron r_j
		W3 = new float[s*t];
		
		// Wp(t x s): synaptic matrix such that Wp(j,i) = w <-> there is an arc from
		// rule neuron (any type) rule_j to prop neuron prop_i
		Wp = new float[t*s];
		
		
		for(Membrane m : structure.getAllMembranes())
		{
			FuzzyMembrane f = (FuzzyMembrane) m;
			
			if(f instanceof PropositionNeuron)
			{
				Integer id = f.getInternalId();
				List<Float> v = f.getValue();
				
				if(structure.isInput(f))
				{	
					theta[id] = v.get(0);
				}
					
				if(structure.isOutput(f))
				{
					; // do nothing (at this point)
				}
					
			}
				
			else if(f instanceof RuleNeuron)
			{
				RuleNeuron r = (RuleNeuron) f;
				
				Integer id = r.getInternalId();
				List<Float> v = r.getValue();
				Integer rtype = r.getRuleType();
								
				C[id*t + id] = v.get(0);
								
				Iterator<Integer> left = r.getLeftSide().iterator();
				
				while(left.hasNext())
				{
					Integer internalId = structure.getCellById(left.next()).getInternalId();
					
					if(rtype == 0)
						W1[internalId*t+id] = 1;
					else if(rtype == 1)
						W2[internalId*t+id] = 1;
					else if(rtype == 3)
						W3[internalId*t+id] = 1;

				}
					
				Iterator<Integer> right = r.getRightSide().iterator();
				
				while(right.hasNext())
				{
					Integer internalId = structure.getCellById(right.next()).getInternalId();
					
					Wp[id*s+internalId] = 1;
				}
				
			}
			
		}
				
		// initialization code ends here
		
		
		/*
		System.out.println(s);
		System.out.println(t);
		fuzzy.printMatrix("theta", theta,s,1);
		fuzzy.printMatrix("sigma", sigma,t,1);
		fuzzy.printMatrix("C", C,t,t);
		fuzzy.printMatrix("W1", W1, s, t);
		fuzzy.printMatrix("W2", W2, s, t);
		fuzzy.printMatrix("W3", W3, s, t);
		fuzzy.printMatrix("Wp", Wp, t, s);
		System.out.println();		
		*/
		

		
	}

	@Override
	public void runInitialSteps() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runOtherSteps() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printResults() {
		// TODO Auto-generated method stub
		
	}

}
