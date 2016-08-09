package org.gcn.plinguacore.simulator.fuzzy;


import java.util.Iterator;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.FuzzyMembrane;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.FuzzyMembraneStructure;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.PropositionNeuron;
import org.gcn.plinguacore.util.psystem.fuzzy.membrane.RuleNeuron;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

public class RealFuzzyMatrixAlgorithm extends AbstractFuzzyMatrixAlgorithm {

	
	// U(n x k): has a 1 if there exists an arc from prop_i to rule_j
	private 	float[] U; // = {{1, 0, 0, 0}, {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 1},{0, 1, 1, 1},{0, 1, 0, 0},{0, 0, 1, 0},{0, 0, 1, 0},{0, 0, 1, 0},{0, 0, 0, 1},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0}}; 
	
	// V(n x k): has a 1 if there exists an arc from rule_j to prop_i
	private 	float[] V; // = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0},{1, 0, 0, 0},{0, 1, 0, 0},{0, 0, 1, 0},{0, 0, 0, 1}}; 
	
	// A(k x k): diagonal matrix containing the confidence factor of each rule
	private 	float[] A; // = {{0.8, 0, 0, 0}, {0, 0.8, 0, 0}, {0, 0, 0.8, 0}, {0, 0, 0, 0.8}};
	
	// H1(k x k): diagonal matrix such that h_i = 1 <-> rule_i is AND-type (ruleType = 1)
	private 	float[] H1; // = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}}; 
	
	// H2(k x k): diagonal matrix such that h_i = 1 <-> rule_i is OR-type (ruleType = 3)
	private 	float[] H2; // = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}; 
	
	// alpha_p(n): vector containing initially the truth value for each input neuron and zero for the rest
	private 	float[] alpha_p; // = {0.8, 0.2, 0.8, 0.8, 0.9, 0.8, 0.2, 0.9, 0.1, 0.2, 0, 0, 0, 0};
	
	// Set to zero at step 1
	private 	float[] alpha_r;
	
	// a_p(n): vector containing initially a 1 for each input neuron and zero for the rest 
	private 	float[] a_p; // = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0};
	
	// Set to zero at step 1
	private 	float[] a_r;
	
	// lambda_p(n): vector containing the number of spikes required to fire each prop neuron (it seem that it will be always made of 1) 
	private 	float[] lambda_p; // = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	
	// lambda_r(k): vector containing the number of spikes required to fire each rule neuron -> equal to the number of neurons in the antecedent part
	private 	float[] lambda_r; // = {2, 4, 4, 3};

	// these vectors have to be initialized
	
	private 	float[] beta_p;
	private 	float[] beta_r;
	private 	float[] b_p;
	private 	float[] b_r;
	
	private int n;
	private int k;
	private int t;

	//private boolean stop = false;

	// this object is used in the algorithm
	
	private float[] B_p;
	private float[] B_r;
	
	float[] auxUnitVectorN;
	float[] auxUnitVectorK;

	float[] kVector1;
	float[] kVector2;
	float[] kVector3;

	float[] nVector1;

	float[] temp;
	float[] nkMatrix;
	
	float[] out_p;
	float[] out_r;
		
	private RealFuzzyMatrix fuzzy;

	
	public RealFuzzyMatrixAlgorithm(Psystem ps) {
		super(ps);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		
		// TODO Auto-generated method stub
		
		FuzzyMembraneStructure structure = (FuzzyMembraneStructure) this.getPsystem().getMembraneStructure();
		
		if(structure.getExecuteInParallel())
			fuzzy = new RealFuzzyMatrixCUDA();
		else
			fuzzy = new RealFuzzyMatrixLineal();
			
		if(structure.getNProps() == 0 || structure.getNRules() == 0)
			throw new IllegalArgumentException("This algorithm requires for each of number of propositions and rules to be greater than 0");
		
		n = structure.getNProps();
		k = structure.getNRules();
		this.endCondition = false;
		
		// Here we create the matrices
		
		U = new float[n*k];
		V = new float[n*k];
		
		A = new float[k*k];
		
		H1 = new float[k*k];
		H2 = new float[k*k];
		
		alpha_p = new float[n];
		a_p = new float[n];
		
		lambda_p = new float[n];
		
		fuzzy.makeUnitVector(lambda_p, n);	// proposition neurons have only one ingoing synapse
		
		lambda_r = new float[k];
		
		out_p = new float[n];
		
		out_r = new float[k];
		
		// Stop matrices creation code
		
		for(Membrane m : structure.getAllMembranes())
		{
			FuzzyMembrane f = (FuzzyMembrane) m;
			
			if(f instanceof PropositionNeuron)
			{
				Integer id = f.getInternalId();
				float v = f.getValue().get(0);
				
				if(structure.isInput(f))
				{	
					if(v > 0.0f)
						a_p[id] = 1;
					
					alpha_p[id] = v;
				}
					
				if(structure.isOutput(f))
				{
					out_p[id] = 1; // mark it as output proposition neuron
				}
					
			}
				
			else if(f instanceof RuleNeuron)
			{
				RuleNeuron r = (RuleNeuron) f;
				
				Integer id = r.getInternalId();
				float v = r.getValue().get(0);
				Integer rtype = r.getRuleType();
				Integer nleft = r.getLeftSide().size();
								
				A[id*k+id] = v;
				
				if(rtype == 1)
					H1[id*k+id] = 1;
				else if(rtype == 0 || rtype == 2 || rtype == 3)
					H2[id*k+id] = 1;
				
				lambda_r[id] = nleft;
				
				Iterator<Integer> left = r.getLeftSide().iterator();
				
				while(left.hasNext())
				{
					Integer internalId = structure.getCellById(left.next()).getInternalId();
					
					U[internalId*k+id] = 1;
					
				}
					

				Iterator<Integer> right = r.getRightSide().iterator();
				
				while(right.hasNext())
				{
					Integer internalId = structure.getCellById(right.next()).getInternalId();
					
					V[internalId*k+id] = 1;
					
				}
				
			}
		}
		
		 alpha_r 	= new float[k];
		 a_r 		= new float[k];

		 beta_p	= new float[n];
		 beta_r	= new float[k];

		 b_p		= new float[n];
		 b_r		= new float[k];

		 B_p		= new float[n*n];
		 B_r		= new float[k*k];
		
		auxUnitVectorN	= new float[n];
		auxUnitVectorK	= new float[k];

		kVector1		= new float[k];
		kVector2		= new float[k];
		kVector3		= new float[k];

		nVector1		= new float[n];

		temp			= new float[n*k];
		nkMatrix		= new float[n*k];


	}

	@Override
	public void runInitialSteps() {
		// TODO Auto-generated method stub
		
		step1();
		step2();
		
		System.out.print("Initial Configuration ");
		System.out.println("t: " + t);
		fuzzy.printMatrix("alpha_p",alpha_p,n,1);
		fuzzy.printMatrix("alpha_r",alpha_r,k,1);
		fuzzy.printMatrix("a_p",a_p,n,1);
		fuzzy.printMatrix("a_r",a_r,k,1);
		
	}

	@Override
	public void runOtherSteps() {
		
		step3();
		step4();
		
		System.out.print("Configuration ");
		System.out.println("t: " + (t+1));
		fuzzy.printMatrix("alpha_p",alpha_p,n,1);
		fuzzy.printMatrix("alpha_r",alpha_r,k,1);
		fuzzy.printMatrix("a_p",a_p,n,1);
		fuzzy.printMatrix("a_r",a_r,k,1);
		
		
		if(!this.checkEndCondition())
			t = t + 1;

	}

	@Override
	public void printResults() {

		System.out.println("Halting condition reached! - Results follow!");
		fuzzy.printMatrix("alpha_p",alpha_p,n,1);
		System.out.println("Halting condition reached! - No more steps can be taken!");
		
	}

	private void step1()
	{

		// this is not necessary as the matrix elements are set to zero
		
		/*
		
		for(int i = 0; i < k; i++)
			alpha_r[i] = 0;
		
		*/
		
		
		// this is not necessary as the matrix elements are set to zero
		
		/*
		
		for(int i = 0; i < k; i++)
			a_r[i] = 0;
			
		*/
		
		fuzzy.makeUnitVector(auxUnitVectorN, n);
		fuzzy.makeUnitVector(auxUnitVectorK, k);
				
	}
	
	private void step2()
	{
		t = 0;
	}
	
	private void step3()
	{
		System.out.println("Step: " + t + " --> " + (t+1));
		
		// p <--> n
		// r <--> k

		// step 3

		// step 3.1a

		fuzzy.fire(alpha_p, a_p, lambda_p, out_p, beta_p, n, n, n, n);
		
		fuzzy.fire(auxUnitVectorN, a_p, lambda_p, out_p, b_p, n, n, n, n);
	
		fuzzy.update(alpha_p, a_p, lambda_p, out_p, alpha_p, n, n, n, n);
		
		fuzzy.update(a_p, a_p, lambda_p, out_p, a_p, n, n, n, n);

		fuzzy.diagonal(b_p, B_p, n);

		/*
		fuzzy.printMatrix("betha_p", beta_p, n, 1);
		fuzzy.printMatrix("b_p", b_p, n, 1);
		fuzzy.printMatrix("alpha_p", alpha_p, n, 1);
		fuzzy.printMatrix("B_p", B_p, n, n);
		*/
		
		// step 3.2a

		fuzzy.multiplicationWithMatrix(A, alpha_r, kVector1, k, k, k, 1);
		fuzzy.fire(kVector1, a_r, lambda_r, out_r, beta_r, k, k, k, k);

		fuzzy.fire(auxUnitVectorK, a_r, lambda_r, out_r, b_r, k, k, k, k);

		fuzzy.update(alpha_r, a_r, lambda_r, out_r, alpha_r, k, k, k, k);
		
		fuzzy.update(a_r, a_r, lambda_r, out_r, a_r, k, k, k, k);
					
		fuzzy.diagonal(b_r, B_r, k);
		
		/*
		fuzzy.printMatrix("betha_r", beta_r, k, 1);
		fuzzy.printMatrix("b_r", b_r, k, 1);
		fuzzy.printMatrix("alpha_r", alpha_r, k, 1);
		fuzzy.printMatrix("B_r", B_r, k, k);
		*/

		
		// step 3.3a

		// calculating temp

		fuzzy.multiplicationWithMatrix(V, B_r, temp, n, k, k, k); // dim(temp) = (n x k)

		
		// first operation
		
		fuzzy.timesMaxMinWithMatrix(temp, beta_r, nVector1, n, k, k, 1, true);
		fuzzy.maxMinWithMatrix(alpha_p, nVector1, alpha_p, n, 1, n, 1, true);
		
		// second operation
		
		fuzzy.multiplicationWithMatrix(temp, b_r, nVector1, n, k, k, 1);
		fuzzy.additionWithMatrix(a_p, nVector1, a_p, n, 1, n, 1);
		
		/*
		fuzzy.printMatrix("alpha_p", alpha_p, n, 1);
		fuzzy.printMatrix("a_p", a_p, n, 1);
		*/
		
		// step 3.4a

		// calculating temp

		fuzzy.multiplicationWithMatrix(B_p, U, nkMatrix, n, n, n, k);
		fuzzy.transpose(nkMatrix, temp, n, k, k, n);	// dim(temp) = (k x n)

		// first operation
				
		// AND
		
		fuzzy.timesMaxMinWithMatrix(temp, beta_p, kVector1, k, n, n, 1, false);
		fuzzy.maxMinWithMatrix(alpha_r, kVector1, kVector1, k, 1, k, 1, false);
		fuzzy.multiplicationWithMatrix(H1, kVector1, kVector2, k, k, k, 1);
		
		// OR
		
		fuzzy.timesMaxMinWithMatrix(temp, beta_p, kVector1, k, n, n, 1, true);
		fuzzy.maxMinWithMatrix(alpha_r, kVector1, kVector1, k, 1, k, 1, true);
		fuzzy.multiplicationWithMatrix(H2, kVector1, kVector3, k, k, k, 1);
			
		//  AND + OR
		
		fuzzy.additionWithMatrix(kVector2, kVector3, alpha_r, k, 1, k, 1);
		
		// second operation
		
		fuzzy.multiplicationWithMatrix(temp, b_p, kVector1, k, n, n, 1);
		fuzzy.additionWithMatrix(a_r, kVector1, a_r, k, 1, k, 1);
				
		/*
		fuzzy.printMatrix("alpha_r", alpha_r, k, 1);
		fuzzy.printMatrix("a_r", a_r, k, 1);
		*/
		
	}
	
	private void step4()
	{
		boolean breakLoop = false;
		
		int i;
		int n;
		
		i = 0;
		n = a_r.length;
		
		while(i < n && !breakLoop)
		{
			breakLoop = !(a_r[i] == 0);
			i++;
		}

		if(breakLoop)
			return;
		
		i = 0;
		n = a_p.length;
		
		while(i < n && !breakLoop)
		{
			breakLoop = !(a_p[i] == 0 || (a_p[i] == 1 && out_p[i] == 1));
			i++;
		}
		

		this.endCondition = !breakLoop;
	}
}
