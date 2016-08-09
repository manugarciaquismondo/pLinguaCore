package org.gcn.plinguacore.util.psystem.fuzzy.membrane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingMembrane;




public class FuzzyMembraneStructure implements MembraneStructure {

	private Map<Integer,FuzzyMembrane>cellsById;
	private Map<String,List<FuzzyMembrane>>cellsByLabel;

	private Set<Integer> input = null;
	private Set<Integer> output = null;

	private Map<Integer,Integer> cloneMap = null;
	
	private int nProps = 0;
	private int nRules = 0;
	
	private boolean executeInParallel = false;
	
	private Map<Pair<Integer,Integer>,List<Float>> weights = null;
	
	public FuzzyMembraneStructure()
	{
		super();
		
		cellsById = new LinkedHashMap<Integer,FuzzyMembrane>();
		cellsByLabel = new LinkedHashMap<String,List<FuzzyMembrane>>();
		
		cloneMap = new LinkedHashMap<Integer,Integer>();	// this is used to clone the structure mapping  to new id's
		
		input = new HashSet<Integer>();
		output = new HashSet<Integer>();
		
		nProps = 0;
		nRules = 0;
		
		executeInParallel = false;
		
		weights = new LinkedHashMap<Pair<Integer,Integer>,List<Float>>();
	}
	
	public FuzzyMembraneStructure(MembraneStructure membrane)
	{
		super();
				
		cellsById = new LinkedHashMap<Integer,FuzzyMembrane>();
		cellsByLabel = new LinkedHashMap<String,List<FuzzyMembrane>>();
		
		input = new HashSet<Integer>();
		output = new HashSet<Integer>();
		
		nProps = 0;
		nRules = 0;
		
		executeInParallel = false;
		
		weights = new LinkedHashMap<Pair<Integer,Integer>,List<Float>>();
		
		cloneMap = new LinkedHashMap<Integer,Integer>();	// this is used to clone the structure mapping  to new id's
		
		Iterator<? extends Membrane>it;

		if (membrane instanceof FuzzyMembraneStructure)
		{
			FuzzyMembraneStructure sps = (FuzzyMembraneStructure)membrane;
			
			executeInParallel = sps.executeInParallel;
			
			it = membrane.getAllMembranes().iterator();

			while(it.hasNext())
			{
				Membrane m = it.next();
				
				FuzzyMembrane mem = null;
				
				if(m instanceof PropositionNeuron)
				{
					PropositionNeuron rprop = (PropositionNeuron) m;
					mem = new PropositionNeuron(rprop.getLabel(), rprop.getValue(),this);
				}
				else
				{
					RuleNeuron rrule = (RuleNeuron) m;
					mem = new RuleNeuron(rrule.getLabel(), rrule.getValue(),rrule.getLeftSide(), rrule.getRightSide(), rrule.getRuleType(), this);
				}
									
				add(mem);
				
				if (m instanceof ChangeableMembrane)
					cloneMap.put(((ChangeableMembrane)m).getId(), mem.getId());
				else
					throw new IllegalArgumentException("Changeable Membranes are needed in otder to Map Ids");	
				
			}
			
			Iterator<Integer> itInputs = sps.input.iterator();
			
			while(itInputs.hasNext())
			{
				Integer in = itInputs.next();
				this.input.add(cloneMap.get(in));
			}
			
			Iterator<Integer> itOutputs = sps.output.iterator();
			
			while(itOutputs.hasNext())
			{
				Integer out = itOutputs.next();
				this.output.add(cloneMap.get(out));
			}
			
			Iterator<Pair<Integer,Integer>> itWeights = sps.weights.keySet().iterator();
			
			while(itWeights.hasNext())
			{
				Pair<Integer,Integer> wKey = itWeights.next();
				List<Float> values = sps.weights.get(wKey);
				
				Integer leftKey = cloneMap.get(wKey.getFirst());
				Integer rightKey = cloneMap.get(wKey.getSecond());
				List<Float> cloneList = new ArrayList<Float>();
				
				Iterator<Float> itValues = values.iterator();
				
				while(itValues.hasNext())
				{
					Float value = itValues.next();
					cloneList.add(value);
				}
				
				this.weights.put(new Pair<Integer,Integer>(leftKey,rightKey), values);
			}
			
		}
		else
			throw new IllegalArgumentException("The membrane structure must be kinda RealFuzzy one");
		
		cloneMap = null;	// this line is not strictly necessary as memory will be cleaned up.
				
	}

	
	@Override
	public Object clone()
	{
		FuzzyMembraneStructure clone = new FuzzyMembraneStructure(this);
		return clone;
	}
	

	public void setInputMembrane(String inputMembraneLabel,List<Float> value, boolean check)
	{
		List<FuzzyMembrane>l = this.getCellsByLabel(inputMembraneLabel);
		
		if (l==null || l.isEmpty())
			throw new IllegalArgumentException("The input membrane doesn't exist");
		
		List<List<Float>> vl = new ArrayList<List<Float>>();
		vl.add(value);
		
		setInputMembranes(l,vl,check);
		
	}
	
	public void setInputMembranes(List<FuzzyMembrane> o, List<List<Float>> v, boolean check)
	{
		if(check)
			if (o == null || cellsById.values().containsAll(o) == false)
				throw new IllegalArgumentException("The membranes are not contained in the structure");
			
		Iterator<FuzzyMembrane> it = o.iterator();
		Iterator<List<Float>> itv = v.iterator();
		
		while(it.hasNext())
		{
			FuzzyMembrane mem = (FuzzyMembrane) it.next();
			input.add(mem.getId());
			
			List<Float> value = (List<Float>) itv.next();
			mem.setValue(value);
		}
						

	}
	
	
	public boolean isInput(FuzzyMembrane in)
	{
		boolean result = false;
		
		result = input.contains(in.getId());
		
		return result;
	}
	
	public void setOutputMembrane(String outputMembraneLabel,boolean check)
	{
		List<FuzzyMembrane>l = this.getCellsByLabel(outputMembraneLabel);
		
		if (l==null || l.isEmpty())
			throw new IllegalArgumentException("The output membrane doesn't exist");
		
		
		setOutputMembranes(l,check);
		
	}
	
	public void setOutputMembranes(List<FuzzyMembrane> o, boolean check)
	{
		if(check)
			if (o == null || cellsById.values().containsAll(o) == false)
				throw new IllegalArgumentException("The membranes are not contained in the structure");
			
		Iterator<FuzzyMembrane> it = o.iterator();
		
		while(it.hasNext())
		{
			FuzzyMembrane mem = (FuzzyMembrane) it.next();
			output.add(mem.getId());
			
		}
						

	}
	
	
	public boolean isOutput(FuzzyMembrane in)
	{
		boolean result = false;
		
		result = output.contains(in.getId());
		
		return result;
	}
	
	public List<FuzzyMembrane> getInputMembranes()
	{
		List<FuzzyMembrane> result = new ArrayList<FuzzyMembrane>();
		
		Iterator<Integer> it = input.iterator();
		
		while(it.hasNext())
		{
			int id = it.next();
			FuzzyMembrane m = this.getCellById(id);
			result.add(m);
		}
		
		return result;
	}
	
	public List<FuzzyMembrane> getOutputMembranes()
	{
		List<FuzzyMembrane> result = new ArrayList<FuzzyMembrane>();
		
		Iterator<Integer> it = output.iterator();
		
		while(it.hasNext())
		{
			int id = it.next();
			FuzzyMembrane m = this.getCellById(id);
			result.add(m);
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str="Cells: ";
		Iterator<? extends Membrane>it = getAllMembranes().iterator();
		while (it.hasNext())
		{
			str+=it.next().toString();
			if (it.hasNext())
				str+=", ";
		}
		
		str+='\n';
		
		str+="Input Membranes: ";
		
		Iterator<? extends FuzzyMembrane>it2 = this.getInputMembranes().iterator();
		while (it2.hasNext())
		{
			str+= ((FuzzyMembrane) it2.next()).getId();
			if (it2.hasNext())
				str+=", ";
		}
		
		str+='\n';
		
		str+="Ouput Membranes: ";
		
		Iterator<? extends FuzzyMembrane>it3 = this.getOutputMembranes().iterator();
		while (it3.hasNext())
		{
			str+= ((FuzzyMembrane) it3.next()).getId();
			if (it3.hasNext())
				str+=", ";
		}
		
		str+='\n';
		
		str+="Weights: ";
		
		Iterator<? extends Pair<Integer,Integer>>it4 = this.getWeights().keySet().iterator();
		while (it4.hasNext())
		{
			Pair<Integer,Integer> p = it4.next();

			Integer left = p.getFirst();
			Integer right = p.getSecond();

			FuzzyMembrane leftM = this.getCellById(left);
			FuzzyMembrane rightM = this.getCellById(right);
			
			List<Float> values = this.weights.get(p);
			
			str+= "(" + leftM.toString() + "," + rightM.toString() + "," + values + ")"; 

			if (it4.hasNext())
				str+=", ";
		}
		
		return str;
	}
	
	@Override
	public Collection<? extends Membrane> getAllMembranes() {
		// TODO Auto-generated method stub
		return new RealFuzzyMembraneCollection();
	}
	
	protected int getNextId()
	{
		return cellsById.size();
	}
	
	public int getNProps()
	{
		return nProps;
	}
	
	public int getNRules()
	{
		return nRules;
	}

	public FuzzyMembrane getCellById(int id)
	{
		return cellsById.get(id);
	}
	
	public List<FuzzyMembrane> getCellsByLabel(String label)
	{
		return cellsByLabel.get(label);
	}
	
	private boolean secureAdd(FuzzyMembrane arg0)
	{
		if (!cellsById.containsKey(arg0.getId()))
		{
			cellsById.put(arg0.getId(), arg0);
								
			String label = arg0.getLabel();
			
			List<FuzzyMembrane>l;
			
			if (!cellsByLabel.containsKey(label))
			{
				l = new ArrayList<FuzzyMembrane>();
				cellsByLabel.put(label, l);
			}
			else
				l = cellsByLabel.get(label);
			
			l.add(arg0);	// The membrane is always added to the list
			
			if(arg0 instanceof PropositionNeuron)
			{
				arg0.setInternalId(nProps);
				nProps++;
			}
			else
			{
				arg0.setInternalId(nRules);
				nRules++;
			}
			
			return true;
		}
		
		return false;
	}

	public boolean add(FuzzyMembrane arg0)
	{
		// TODO Auto-generated method stub
		return secureAdd(arg0);
		
	}
	
	class RealFuzzyMembraneCollection implements Collection<FuzzyMembrane>
	{
		
		public RealFuzzyMembraneCollection() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean add(FuzzyMembrane arg0) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(Collection<? extends FuzzyMembrane> arg0) {
			throw new UnsupportedOperationException();
		}

		
		@Override
		public void clear() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}


		@Override
		public boolean contains(Object arg0) {
			// TODO Auto-generated method stub
			if (!(arg0 instanceof FuzzyMembrane))
				return false;
			FuzzyMembrane tlm = (FuzzyMembrane)arg0;
			
			return cellsById.containsKey(tlm.getId());
		}

		public List<FuzzyMembrane> getByLabel(Object arg0) {
			// TODO Auto-generated method stub
			if (!(arg0 instanceof String))
				return null;
			
			String label = (String)arg0;
			
			return cellsByLabel.get(label);
			
		}
		
		public FuzzyMembrane getById(Object arg0) {
			// TODO Auto-generated method stub
			if (!(arg0 instanceof Integer))
				return null;
			
			Integer id = ((Integer)arg0).intValue();
			
			return cellsById.get(id);
			
		}

		
		@Override
		public boolean containsAll(Collection<?> arg0) {
			// TODO Auto-generated method stub
			Iterator<?>it = arg0.iterator();
			boolean contains=true;
			while(contains && it.hasNext())
				contains=contains(it.next());
			return contains;
		}


		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return cellsById.isEmpty();
		}



		@Override
		public Iterator<FuzzyMembrane> iterator() {
			// TODO Auto-generated method stub
			return cellsById.values().iterator();
		}

		
		

		@Override
		public boolean remove(Object arg0) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}



		@Override
		public boolean removeAll(Collection<?> arg0) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}



		@Override
		public boolean retainAll(Collection<?> arg0) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}



		@Override
		public int size() {
			// TODO Auto-generated method stub
			return cellsById.size();
		}



		@Override
		public Object[] toArray() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
			
		}



		@Override
		public <T> T[] toArray(T[] arg0) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}


	}

	@Override
	public Membrane getMembrane(int id) {
		// TODO Auto-generated method stub
		return cellsById.get(id);
	}

	public boolean getExecuteInParallel() {
		return executeInParallel;
	}

	public void setExecuteInParallel(boolean executeInParallel) {
		this.executeInParallel = executeInParallel;
	}

	public Map<Pair<Integer,Integer>,List<Float>> getWeights() {
		return weights;
	}
	
	public void addWeight(String left, String right, List<Float> values)
	{
		if(!cellsByLabel.containsKey(left))
			throw new IllegalArgumentException("The left neuron is not contained in the structure");
		
		if(!cellsByLabel.containsKey(right))
			throw new IllegalArgumentException("The right neuron is not contained in the structure");
		
		List<FuzzyMembrane> leftList = this.cellsByLabel.get(left);
		List<FuzzyMembrane> rightList = this.cellsByLabel.get(right);
		
		Iterator<FuzzyMembrane> itLeftList = leftList.iterator();
		
		while(itLeftList.hasNext())
		{
			FuzzyMembrane leftMem = itLeftList.next();
			
			Iterator<FuzzyMembrane> itRightList = rightList.iterator();
			
			while(itRightList.hasNext())
			{
				FuzzyMembrane rightMem = itRightList.next();
				
				boolean check1 = (leftMem instanceof PropositionNeuron) && (rightMem instanceof RuleNeuron);
				boolean check2 = (leftMem instanceof RuleNeuron) && (rightMem instanceof PropositionNeuron);
				
				boolean totalCheck = (check1 && !check2) || (!check1 && check2);
				
				if(!totalCheck)
					throw new IllegalArgumentException("One and only one Proposition Neuron and one and only one Rule Neuron allowed");
				
				if(check1)
				{
					RuleNeuron rule = (RuleNeuron) rightMem;
					
					if(!rule.getLeftSide().contains(leftMem.getId()))
						throw new IllegalArgumentException("Neurons " + leftMem + " and " + rightMem + " are not linked");
				}
				else if(check2)
				{
					RuleNeuron rule = (RuleNeuron) leftMem;
					
					if(!rule.getRightSide().contains(rightMem.getId()))
						throw new IllegalArgumentException("Neurons " + leftMem + " and " + rightMem + " are not linked");
				}
				
				weights.put(new Pair<Integer,Integer>(leftMem.getId(),rightMem.getId()), values);
			}
		}
				
		
	}
   
}
