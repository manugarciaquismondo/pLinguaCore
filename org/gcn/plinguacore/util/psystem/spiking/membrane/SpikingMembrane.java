package org.gcn.plinguacore.util.psystem.spiking.membrane;


import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembrane;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.rule.spiking.SpikingRule;
import org.gcn.plinguacore.util.psystem.spiking.SpikingConstants;



public class SpikingMembrane extends ChangeableMembrane  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -991479572199736842L;
	private long stepsToOpen = 0L;
	private SpikingRule selectedRule = null;
	private SpikingMembraneStructure structure = null;
	private String object = SpikingConstants.spikeSymbol;
	
	// The next attributes are considered only for efficiency reasons
	private boolean efficiencyAttributesProcessed = false;
	private String membraneSpikingString = null;
	private long membraneSpikingStringSize = 0;

	// New attributes in order to implement the unbounded spiking neural p systems
	
	private long boundTimer = 0L;		// initializes to b-1 (b >= 2) and decrements one unit per step (as long as the selected rule remains unchanged). When zero, the neuron can fire.
	
	private boolean skipped = false;
		
	// Improvement: Add clone() method so one can use the simulation algorithms safely;
	
	
	public static SpikingMembrane buildMembrane(String label, long elements, SpikingMembraneStructure structure, boolean computeEfficiencyAttributes)
	{
		String object = SpikingConstants.spikeSymbol;
		
		SpikingMembrane result = new SpikingMembrane(label, object, elements, structure, computeEfficiencyAttributes);
		
		structure.add(result);
		
		return result;
	}
	
	protected SpikingMembrane(String label, String object, long elements, SpikingMembraneStructure structure, boolean computeEfficiencyAttributes)
	{
		super(new Label(label), (byte)0, new HashMultiSet<String>());
		
		
		if(elements < 0)
			throw new IllegalArgumentException("Spiking Neurons must contain a non-negative amount of spikes or antispikes");
	
		if((object.equals(SpikingConstants.spikeSymbol) || object.equals(SpikingConstants.antiSpikeSymbol)) == false)
			throw new IllegalArgumentException("Spiking Neurons must contain only spikes or antispikes");
		
		String newObj = new String(object);
		
		getMultiSet().add(newObj, elements);
		this.object = newObj;
		
		if(computeEfficiencyAttributes)
		{
			membraneSpikingStringSize = elements;
			membraneSpikingString = this.getMembraneSpikingString();
			efficiencyAttributesProcessed = true;
		}
	
		initMembraneStructure(structure);
	}
	
	
	protected SpikingMembrane(String label,MultiSet<String> multiSet,SpikingMembraneStructure structure, boolean computeEfficiencyAttributes) {
		
		super(new Label(label), (byte)0, multiSet);
		
		updateObject();
		
		if(computeEfficiencyAttributes)
		{

			membraneSpikingStringSize = this.getObjectCount();
			membraneSpikingString = this.getMembraneSpikingString();
			efficiencyAttributesProcessed = true;
		}

		
		initMembraneStructure(structure);
		
	}

	
	// has to do clone()
	
	protected SpikingMembrane(Membrane membrane,SpikingMembraneStructure structure)
	{

		this(membrane.getLabel(), getObject(membrane), getObjectCount(membrane),structure, false);
		
		this.setCharge(new Byte(membrane.getCharge()));
	
		if (membrane instanceof CellLikeMembrane)
		{
			CellLikeMembrane clm=(CellLikeMembrane)membrane;
			if (!clm.getChildMembranes().isEmpty())
				throw new IllegalArgumentException("Spiking neurons must be elemental membranes");
		}
	
		if (membrane instanceof SpikingMembrane)
		{
		
			this.selectedRule = ((SpikingMembrane)membrane).selectedRule;
			this.stepsToOpen = new Long(((SpikingMembrane)membrane).stepsToOpen);
			this.boundTimer = new Long(((SpikingMembrane)membrane).boundTimer);
			this.skipped = new Boolean(((SpikingMembrane)membrane).skipped);

			
			efficiencyAttributesProcessed = ((SpikingMembrane)membrane).efficiencyAttributesProcessed;
			
			if(efficiencyAttributesProcessed)
			{
				membraneSpikingString = new String(((SpikingMembrane)membrane).membraneSpikingString);
				membraneSpikingStringSize = new Long(((SpikingMembrane)membrane).membraneSpikingStringSize);
			}
		
		}
		
	}
	
	
public boolean isOpen()
{
	
	return !isClosed();
	
}

public boolean isClosed()
{
	
	return stepsToOpen > 0L || stepsToOpen == -1L;

}


public boolean isFiring()
{
	return stepsToOpen > 0L;
}

public boolean isSkipped()
{
	return skipped;
}

public void setSkipped(boolean value)
{
	this.skipped = value;
}


public long getStepsToOpen()
{

	return stepsToOpen;

}


public SpikingRule getSelectedRule()
{

	return selectedRule ;

}

public boolean decreaseStepsToOpen()
{

	boolean result = isFiring();
	
	if(result)
		stepsToOpen --;
	else
		; // do nothing
	
	return result; // return true iif the decreasing is performed
	
}


public boolean decreaseBoundTimer()
{

	boolean result = (boundTimer > 0);
	
	if(result)
		boundTimer --;
	else
		; // do nothing
	
	return result; // return true iif the decreasing is performed
	
}

public void restartBoundTimer()
{
	if(structure.getSpecificRuleBoundActive())
	{
		if(this.selectedRule != null)
			this.boundTimer = selectedRule.getBound();
	}
	else
		this.boundTimer = structure.getBound();
			
}

public void setBoundTimer(long bound)
{
	if(bound < 2)
		;
	else 
		this.boundTimer = bound;
}

public void setBoundTimerToZero()
{
	this.boundTimer = 0L;
}

public boolean isBoundTimerZero()
{
	return (this.boundTimer == 0L);
}

public long getBoundTimer()
{
	return boundTimer;
}

public void doCheckApplicability()
{
	this.boundTimer = 0L;
}

public void doNotCheckApplicability()
{
	this.boundTimer = -1L;
}

public boolean hasToCheckApplicability()
{
	return (this.boundTimer != -1L);
}

private void setMembraneClosedToSpike(long stepsToOpen, SpikingRule selectedRule)
{

	if(stepsToOpen < 0)
		throw new IllegalArgumentException("Steps must be non-negative");
	
	if(selectedRule == null && stepsToOpen > 0)
		throw new IllegalArgumentException("A Closed Spiking Membrane can't have no Selected Rule");
	
	if(selectedRule != null && selectedRule.getDelay() < stepsToOpen)
		throw new IllegalArgumentException("A closed Spiking Membrane can't have a Delayed Rule with less delay time than the number of steps to open");

	if(selectedRule != null && selectedRule.isFiringRule() && !selectedRule.getLeftHandRule().getOuterRuleMembrane().getLabel().equals(this.getLabel()))
		throw new IllegalArgumentException("A Firing Selected Rule must be contained in the firing rules set");
	else if(selectedRule != null && selectedRule.isForgettingRule() && !selectedRule.getLeftHandRule().getOuterRuleMembrane().getLabel().equals(this.getLabel()))
		throw new IllegalArgumentException("A Forgetting Selected Rule must be contained in the firing rules set");

	
	this.selectedRule = selectedRule;
	this.stepsToOpen = stepsToOpen;
	

}

private void setMembraneClosedToBuddingOrDivision(SpikingRule selectedRule)
{

	if(selectedRule != null && selectedRule.isBuddingRule() && !selectedRule.getLeftHandRule().getOuterRuleMembrane().getLabel().equals(this.getLabel()))
		throw new IllegalArgumentException("A Budding Selected Rule must be contained in the firing rules set");
	else if(selectedRule != null && selectedRule.isDivisionRule() && !selectedRule.getLeftHandRule().getOuterRuleMembrane().getLabel().equals(this.getLabel()))
		throw new IllegalArgumentException("A Division Selected Rule must be contained in the firing rules set");

	
	this.selectedRule = selectedRule;
	
	this.stepsToOpen = -1;

}

// this is used to close the child neurons

public void setMembraneClosedToBuddingOrDivision()
{
	
	this.stepsToOpen = -1;

}

public void setSelectedRule(SpikingRule selectedRule)
{
	
	if(selectedRule != null && selectedRule.isBuddingRule() || selectedRule.isDivisionRule())
		setMembraneClosedToBuddingOrDivision(selectedRule);
	else
		setMembraneClosedToSpike(selectedRule.getDelay(), selectedRule);
}


public void setMembraneOpen()
{
	this.stepsToOpen = 0L;
	this.selectedRule = null;
}


public SpikingMembraneStructure getStructure()
{

	return structure;

}

public String getMembraneSpikingString()
{
	
		if(efficiencyAttributesProcessed)
			return membraneSpikingString;
			
	
		String ss = new String("");

		long max = getObjectCount();
		
			
		for(long i = 0; i < max; i++)
		{
			ss = ss + getObject();
		}
		
		return ss;
}

public long getMembraneSpikingStringSize()
{
	if(efficiencyAttributesProcessed)
		return membraneSpikingStringSize; 
	
	return this.getObjectCount();
}

private void initMembraneStructure(SpikingMembraneStructure structure)
{
	if (structure==null)
		throw new NullPointerException("Null membrane structure");
	setId(structure.getNextId());
	this.structure=structure;
}

public void addSpikes(String object, long spikes) 
{
	
	if(spikes <= 0L)
		return;
	
	this.doCheckApplicability();
	
	double valueThis = getObjectCount();
	double signThis = getObject().equals(SpikingConstants.spikeSymbol) ? 1 : -1;
	
	double valueOther = spikes;
	double signOther = object.equals(SpikingConstants.spikeSymbol) ? 1 : -1;
	
	double result = signThis * valueThis + signOther * valueOther;
	
	long signThisNew = result >= 0 ? 1 : -1; 
	long valueThisNew = (long) (signThisNew * result);	// this result is alwats >= 0, since signThisNew and result have the same sign (+/-).
	
	String elementThisNew = signThisNew > 0 ? SpikingConstants.spikeSymbol : SpikingConstants.antiSpikeSymbol;
	
	this.getMultiSet().clear();
	this.getMultiSet().add(elementThisNew, valueThisNew);
	this.object = elementThisNew;
	
	if(efficiencyAttributesProcessed)
	{
		
		membraneSpikingStringSize = valueThisNew;
		
		membraneSpikingString = "";
		
		for(int i = 0; i < valueThisNew;i++)
			membraneSpikingString = membraneSpikingString + elementThisNew;
	}
	
}


public void removeSpikes(long spikes) 
{

	if(spikes <= 0L || getObjectCount() == 0L)
		return;
	
	this.doCheckApplicability();
	
	this.getMultiSet().remove(getObject(), spikes);
	
	if(this.getMultiSet().size() == 0)
		object = SpikingConstants.spikeSymbol;
	
	if(efficiencyAttributesProcessed)
	{
		membraneSpikingStringSize -= spikes;
		membraneSpikingString = membraneSpikingString.substring(0, (int) (membraneSpikingString.length() - spikes)*getObject().length());	
	}


}

public void clearSpikes() 
{
	this.doCheckApplicability();
	
	long objectCount = getObjectCount();
		
	removeSpikes(objectCount);
}


public void renewLabel(String newLabel)
{
	String oldLabel = this.getLabel();
	
	super.label = new Label(newLabel);
	
	if(structure != null)
		structure.renewLabel(this, oldLabel, newLabel);
}

public void updateObject()
{
	
	if(multiSet.count(SpikingConstants.antiSpikeSymbol) > 0)
		object = SpikingConstants.antiSpikeSymbol;
	else
		object = SpikingConstants.spikeSymbol;
	
}

public String getObject()
{
	return object;
}

public long getObjectCount()
{
	return multiSet.count(object);
}

public static String getObject(Membrane m)
{
	String result = SpikingConstants.spikeSymbol;
	
	if(m instanceof SpikingMembrane)
		result = m.getMultiSet().count(SpikingConstants.antiSpikeSymbol) > 0 ? SpikingConstants.antiSpikeSymbol : SpikingConstants.spikeSymbol; 
	
	return result;
}

public static long getObjectCount(Membrane m)
{
	String object = getObject(m);	
	long result = m.getMultiSet().count(object);
		
	return result;
}

@Override
public String toString()  {
	
	return this.getId() + ":" + super.toString(); 
}

@Override
public void dissolve() throws UnsupportedOperationException {
	throw new UnsupportedOperationException();

}
@Override
public ChangeableMembrane divide() throws UnsupportedOperationException {
	// TODO Auto-generated method stub
	if (getLabel().equals(structure.getEnvironmentLabel()))
		throw new UnsupportedOperationException("The environment cannot be divided");
	SpikingMembrane mem= new SpikingMembrane(this,structure);
	structure.add(mem);
	return mem;

}

public ChangeableMembrane doBuddy() throws UnsupportedOperationException {
	// TODO Auto-generated method stub
	if (getLabel().equals(structure.getEnvironmentLabel()))
		throw new UnsupportedOperationException("The environment cannot be budded");
	SpikingMembrane mem= new SpikingMembrane(this,structure);
	structure.add(mem);
	return mem;

}

}
