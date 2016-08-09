package org.gcn.plinguacore.util.psystem.fuzzy.membrane;

import java.util.ArrayList;
import java.util.List;

import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;

public abstract class FuzzyMembrane extends ChangeableMembrane {

	private static final long serialVersionUID = 1313627009990665682L;
	
	private List<Float> value;
	private FuzzyMembraneStructure structure = null;
	private int internalId;
	
	
	public FuzzyMembrane(String label, List<Float> value) {
		
		super(new Label(label));
		
		this.value = new ArrayList<Float>();
		
		for(float val:value)
			if(val < 0.0 || val > 1.0)
				throw new IllegalArgumentException("value parameter must be a real number in [0,1]");
		
		for(float val:value)
				this.value.add(val);
	}

	FuzzyMembrane(String label, List<Float> value, FuzzyMembraneStructure structure)
	{
		this(label,value);
		
		initMembraneStructure(structure);
	}
	
	private void initMembraneStructure(FuzzyMembraneStructure structure)
	{
		if (structure==null)
			throw new NullPointerException("Null membrane structure");
		setId(structure.getNextId());
		this.structure=structure;
	}

	/**
	 * @return the value
	 */
	public List<Float> getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(List<Float> value) {
		
		this.value.clear();
		
		for(float val:value)
			if(val < 0.0 || val > 1.0)
				throw new IllegalArgumentException("value parameter must be a real number in [0,1]");
			else
				this.value.add(val);
		
	}

	/**
	 * @return the structure
	 */
	public FuzzyMembraneStructure getStructure() {
		return structure;
	}

	
	@Override
	public void dissolve() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChangeableMembrane divide() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the internalId
	 */
	public int getInternalId() {
		return internalId;
	}

	/**
	 * @param internalId the internalId to set
	 */
	public void setInternalId(int internalId) {
		this.internalId = internalId;
	}

	@Override
	public String toString()
	{
		String result = super.toString();
		
		result += " val: " + value.toString() + " id: " + this.getId() + " internalId: " + this.internalId;
		
		return result;
	}

}
