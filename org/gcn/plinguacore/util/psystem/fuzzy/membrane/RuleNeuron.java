package org.gcn.plinguacore.util.psystem.fuzzy.membrane;

import java.util.List;




public class RuleNeuron extends FuzzyMembrane {

	private static final long serialVersionUID = 3076843486525833461L;
	
	
	private List<Integer> leftSide;
	private List<Integer> rightSide;
	private int ruleType; // 0: simple, 1: comp-1, 2: comp-2, 3: comp-3
	private int ruleOp; // 0: none, 1: and, 2: or

	
	
	RuleNeuron(String label, List<Float> value, List<Integer> leftSide, List<Integer> rightSide, int ruleType)
	{
		super(label,value);
		
		configureRule(leftSide, rightSide, ruleType);
	}

	
	RuleNeuron(String label, List<Float> value, List<Integer> leftSide, List<Integer> rightSide, int ruleType, FuzzyMembraneStructure structure)
	{
		super(label,value,structure);
		
		configureRule(leftSide, rightSide, ruleType);
	}
	
	private void configureRule(List<Integer> leftSide, List<Integer> rightSide, int ruleType)
	{	
		if(ruleType < 0 || ruleType > 3)
			throw new IllegalArgumentException("ruleOp parameter must be a int number be [0,1,2,3]");
		
		if(leftSide == null || leftSide.isEmpty())
			throw new IllegalArgumentException("left side cannot be empty");
		
		if(rightSide == null || rightSide.isEmpty())
			throw new IllegalArgumentException("right side cannot be empty");
		
		boolean form0 = (leftSide.size() == 1 && rightSide.size() == 1 && ruleType == 0);
	
		boolean form1 = (leftSide.size() > 1 && rightSide.size() == 1 && ruleType == 1);
	
		boolean form2 = (leftSide.size() == 1 && rightSide.size() > 1 && ruleType == 2);
	
		boolean form3 = (leftSide.size() > 1 && rightSide.size() == 1 && ruleType == 3);
	
		if(form0)
		{
			this.ruleType = 0;
			this.ruleOp = 0;
		}
		
		else if(form1)
		{
			this.ruleType = 1;
			this.ruleOp = 1;

		}
		
		else if(form2)
		{
			this.ruleType = 2;
			this.ruleOp = 0;
		}
		
		else if(form3)
		{
			this.ruleType = 3;
			this.ruleOp = 2;
		}
		else
		throw new IllegalArgumentException("rule Neuron has to be one of the accepted forms");
	
		this.leftSide = leftSide;
		this.rightSide = rightSide;
	}
	
	public static RuleNeuron buildMembrane(String label, List<Float> value, List<Integer> leftSide, List<Integer> rightSide, int ruleOp, FuzzyMembraneStructure structure)
	{
			
		RuleNeuron result = new RuleNeuron(label, value, leftSide, rightSide, ruleOp, structure);
		
		structure.add(result);
		
		return result;
	}

	
	/**
	 * @return the leftSide
	 */
	public List<Integer> getLeftSide() {
		return leftSide;
	}

	/**
	 * @return the rightSide
	 */
	public List<Integer> getRightSide() {
		return rightSide;
	}


	/**
	 * @return the ruleType
	 */
	public int getRuleType() {
		return ruleType;
	}
	

	/**
	 * @return the ruleOp
	 */
	public int getRuleOp() {
		return ruleOp;
	}

	
	@Override
	public String toString()
	{
		String result = super.toString();
		
		result += " ruleType: " + ruleType + " ant: " + leftSide.toString() + " cons: " + rightSide.toString();
		
		return result;
	}


}
