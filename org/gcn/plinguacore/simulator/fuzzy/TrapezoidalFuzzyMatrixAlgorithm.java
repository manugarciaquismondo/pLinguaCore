package org.gcn.plinguacore.simulator.fuzzy;

import java.util.Iterator;
import java.util.List;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.FuzzyMembrane;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.FuzzyMembraneStructure;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.PropositionNeuron;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.RuleNeuron;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

import static org.gcn.plinguacore.simulator.fuzzy.Trapezoidal.*;

public class TrapezoidalFuzzyMatrixAlgorithm extends AbstractFuzzyMatrixAlgorithm {

	// theta(s): vector containing the pulse values of the proposition neurons
	private float[] theta;
	
	// sigma(t): vector containing the pulse values of the rule neurons
	private float[] sigma;
	
	// C(t x t): diagonal matrix containing the confident factor of the rule neurons
	private float[] C;
	
	// D1(s x t): synaptic matrix such that d1(i,j) = 1 <-> there is an arc from 
	// prop neuron prop_i to general rule neuron r_j (ruleType = 0)
	private float[] D1;
	
	// D2(s x t): synaptic matrix such that d2(i,j) = 1 <-> there is an arc from 
	// pop neuron prop_i to AND rule neuron r_j (ruleType = 1)
	private float[] D2;
	
	// D3(s x t): synaptic matrix such that d3(i,j) = 1 <-> there is an arc from 
	// prop neuron prop_i to OR rule neuron r_j (ruleType = 3)
	private float[] D3;
	
	// E(t x s): synaptic matrix such that E(j,i) = 1 <-> there is an arc from
	// rule neuron (any type) rule_j to prop neuron prop_i
	private float[] E;
	
	// trasposed matrices
	private float[] D1t;
	private float[] D2t;
	private float[] D3t;
	private float[] Et;
	
	// number of proposition neurons
	private int s;
	
	// number of rule nurons
	private int t;
	
	// step counter
	private int g;
	
	private float[] term1;
	private float[] term2;
	private float[] term3;
		
	private TrapezoidalFuzzyMatrix fuzzy;
	
	public TrapezoidalFuzzyMatrixAlgorithm(Psystem ps) {
		super(ps);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		FuzzyMembraneStructure structure = (FuzzyMembraneStructure) this.getPsystem().getMembraneStructure();
		
		if(structure.getExecuteInParallel())
			fuzzy = new TrapezoidalFuzzyMatrixCUDA();
		else
			fuzzy = new TrapezoidalFuzzyMatrixLineal();
		
		if(structure.getNProps() == 0 || structure.getNRules() == 0)
			throw new IllegalArgumentException("This algorithm requires for each of number of propositions and rules to be greater than 0");
		
		s = structure.getNProps();
		t = structure.getNRules();
		this.endCondition = false;
		
		// initialization code starts here
		
		// theta(s): vector containing the pulse values of the proposition neurons
		theta = new float[s*TRAPL];
		
		// sigma(t): vector containing the pulse values of the rule neurons
		sigma = new float[t*TRAPL];
		
		// C(t x t): diagonal matrix containing the confident factor of the rule neurons
		C = new float[t*t*TRAPL];
		
		// D1(s x t): synaptic matrix such that d1(i,j) = 1 <-> there is an arc from 
		// prop neuron prop_i to general rule neuron r_j (ruleType = 0)
		D1 = new float[s*t];
		
		// D2(s x t): synaptic matrix such that d2(i,j) = 1 <-> there is an arc from 
		// pop neuron prop_i to AND rule neuron r_j (ruleType = 1)
		D2 = new float[s*t];
		
		// D3(s x t): synaptic matrix such that d3(i,j) = 1 <-> there is an arc from 
		// prop neuron prop_i to OR rule neuron r_j (ruleType = 3)
		D3 = new float[s*t];
		
		// E(t x s): synaptic matrix such that E(j,i) = 1 <-> there is an arc from
		// rule neuron (any type) rule_j to prop neuron prop_i
		E = new float[t*s];
		
		D1t = new float[t*s];
		D2t = new float[t*s];
		D3t = new float[t*s];
		Et = new float[s*t];
		
		term1 = new float[t*1*TRAPL];
		term2 = new float[t*1*TRAPL];
		term3 = new float[t*1*TRAPL];
		
		
		for(Membrane m : structure.getAllMembranes())
		{
			FuzzyMembrane f = (FuzzyMembrane) m;
			
			if(f instanceof PropositionNeuron)
			{
				Integer id = f.getInternalId();
				List<Float> v = f.getValue();
				
				if(structure.isInput(f))
				{	
					for(int caux = 0; caux < TRAPL; caux++)
						theta[id*TRAPL + caux] = v.get(caux);
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
								
				for(int caux = 0; caux < TRAPL; caux++)
					C[(id*t + id)*TRAPL + caux] = v.get(caux);
								
				Iterator<Integer> left = r.getLeftSide().iterator();
				
				while(left.hasNext())
				{
					Integer internalId = structure.getCellById(left.next()).getInternalId();
					
					if(rtype == 0 || rtype == 2l)
						D1[internalId*t+id] = 1;
					else if(rtype == 1)
						D2[internalId*t+id] = 1;
					else if(rtype == 3)
						D3[internalId*t+id] = 1;

				}
					
				Iterator<Integer> right = r.getRightSide().iterator();
				
				while(right.hasNext())
				{
					Integer internalId = structure.getCellById(right.next()).getInternalId();
					
					E[id*s+internalId] = 1;
				}
				
			}
		}
		
		
		// initialization code ends here
		
		 fuzzy.transpose(D1,D1t,s,t,t,s);
		 fuzzy.transpose(D2,D2t,s,t,t,s);
		 fuzzy.transpose(D3,D3t,s,t,t,s);
		 fuzzy.transpose(E,Et,t,s,s,t);
		
		
		/*
		System.out.println(s);
		System.out.println(t);
		fuzzy.printMatrixTrap("theta", theta,s,1);
		fuzzy.printMatrixTrap("sigma", sigma,t,1);
		fuzzy.printMatrixTrap("C", C,t,t);
		fuzzy.printMatrixDouble("D1", D1, s, t);
		fuzzy.printMatrixDouble("D2", D2, s, t);
		fuzzy.printMatrixDouble("D3", D3, s, t);
		fuzzy.printMatrixDouble("E", E, t, s);
		System.out.println();		
		fuzzy.printMatrixDouble("D1t", D1t, t, s);
		fuzzy.printMatrixDouble("D2t", D2t, t, s);
		fuzzy.printMatrixDouble("D3t", D3t, t, s);
		fuzzy.printMatrixDouble("Et", Et, s, t);
		System.out.println();
		*/
	}

	@Override
	public void runInitialSteps() {
		// TODO Auto-generated method stub
		
		step1();
		step2();
	}

	@Override
	public void runOtherSteps() {
		// TODO Auto-generated method stub
		
		step3();
		step4();
		step5();
		step6();
				
		if(!checkEndCondition())
		{
			step7();
			step8();
		}
		
		System.out.println("g = " + g);
		fuzzy.printMatrixTrap("sigma",sigma,t,1);
		System.out.println();
		fuzzy.printMatrixTrap("theta",theta,s,1);
		System.out.println();

	}

	@Override
	public void printResults() {

		System.out.println("Halting condition reached! - Results follow!");
		fuzzy.printMatrixTrap("theta",theta,s,1);
		System.out.println("Halting condition reached! - No more steps can be taken!");
		
	}

	
	
	public void step1()
	{
		g = 0;
	}
	
	public void step2()
	{
		// in this step we have to transpose the matrices, but as a matter of efficiency, we do this in the initialization method

	}
		
	public void step3()
	{
		g = g + 1;
	}
	
	public void step4()
	{
		// do nothing
	}
	
	public void step5()
	{
		
		fuzzy.multiplicationWithVector(D1t,theta,term1,t,s,s);
		fuzzy.timesMaxMinWithVector(D2t,theta,term2,t,s,s,false);
		fuzzy.timesMaxMinWithVector(D3t,theta,term3,t,s,s,true);

		// fuzzy.printMatrixTrap("term2",term2,t,1);
		// fuzzy.printMatrixTrap("term3",term3,t,1);
		
		fuzzy.additionWithVector(term1,term2,sigma,t,t);
		fuzzy.additionWithVector(term3,sigma,sigma,t,t);
		
	}
	
	public void step6()
	{
		boolean goOn = true;
		
		int i;
		int n;
		
		i = 0;
		n = t;
		
		while(i < n && goOn)
		{
			goOn = fuzzy.isMin(sigma,i,n);
			i++;
		}
				
		this.endCondition = goOn;
	}
	
	public void step7()
	{
		// do nothing
	}
	
	public void step8()
	{
		fuzzy.diagonalMultiplication(C,sigma,term1,t,t,t);
		fuzzy.timesMaxMinWithVector(Et,term1,theta,s,t,t,true);
		
		// fuzzy.printMatrixTrap("term1", term1, t, 1);
		// fuzzy.printMatrixTrap("theta", theta, s, 1);
		System.out.println();
	}
	
}
