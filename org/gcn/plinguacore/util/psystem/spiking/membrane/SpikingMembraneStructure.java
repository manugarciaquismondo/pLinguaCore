/* 
 * pLinguaCore: A JAVA library for Membrane Computing
 *              http://www.p-lingua.org
 *
 * Copyright (C) 2009  Research Group on Natural Computing
 *                     http://www.gcn.us.es
 *                      
 * This file is part of pLinguaCore.
 *
 * pLinguaCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pLinguaCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pLinguaCore.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gcn.plinguacore.util.psystem.spiking.membrane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.util.InfiniteMultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.spiking.SpikingConstants;

public class SpikingMembraneStructure implements MembraneStructure {

	private static boolean DEBUG = false;
	
	private Map<Integer,SpikingMembrane>cellsById;
	private Map<String,List<SpikingMembrane>>cellsByLabel;
	private SpikingEnvironment environment = null;
	private Integer input = null;
	private Map<String,Set<String>> dictionary = null;
	private Map<Integer,Integer> cloneMap = null;
	private Map<Integer,Set<Integer>> graph = null;
	private Map<Integer,Set<Integer>> rgraph = null;
	
	// attributes to store / recover data associated to arcs

	private Map<String,Astrocyte> astrocytes = null;
	private Map<Pair<Integer,Integer>,ArcInfo> arcsInfo = null;
	private Map<String,List<Pair<Integer,Integer>>> astroToArcs = null;
	private Map<String,List<Pair<Integer,Integer>>> astroToCtrlArcs = null;
	private Map<String,EvaluableFunction> astroFunctions = null;	
	
	// the output neurons are in two ways calculated:
	// - as the predecessors to the environment (for spiking reasons)
	// - as members of the following set (for efficiency reasons)
	
	private Set<Integer> output = null;
	
	// attributes to store the Loc set for the local synchronization
	
	private List<List<Integer>> loc = null;
	private Map<Integer,List<Integer>> memToLoc = null;
	
	// the next attributes are needed only in terms of simulation parameters
	
	private boolean showBinarySequence = false;
	private List<Object> showNaturalSequence = null;
	private boolean showSummatories = false;
	private int sequentialMode = 0;
	private int asynchMode = 0;
	private Map<String, Long> validConfiguration = null; 
	
	private long bound = 0L;
	private boolean specificRuleBoundActive = false;
		
	public SpikingMembraneStructure(String envLabel)
	{
		super();
		
		cellsById = new LinkedHashMap<Integer,SpikingMembrane>();
		cellsByLabel = new LinkedHashMap<String,List<SpikingMembrane>>();
		environment = new SpikingEnvironment(envLabel,this);
		secureAdd(environment);
		environment.setStepsTaken(0L);
		
		input = null;
		dictionary = new LinkedHashMap<String,Set<String>>();
		cloneMap = null;
				
		graph = new LinkedHashMap<Integer, Set<Integer>>();
		rgraph = new LinkedHashMap<Integer, Set<Integer>>(); 
		
		astrocytes = new LinkedHashMap<String,Astrocyte>();
		arcsInfo = new LinkedHashMap<Pair<Integer,Integer>,ArcInfo>();
		astroToArcs = new LinkedHashMap<String,List<Pair<Integer,Integer>>>();
		astroToCtrlArcs = new LinkedHashMap<String,List<Pair<Integer,Integer>>>();
		
		astroFunctions = new LinkedHashMap<String,EvaluableFunction>(); 		
		
		EvaluableFunction afIdentity = new AstrocyteFunction();
		try {
			afIdentity.storeFunction("identity(x1)=x1", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		astroFunctions.put("identity(x1)", afIdentity);
		
		EvaluableFunction afZero = new AstrocyteFunction();
		try {
			afZero.storeFunction("zero(x1)=0", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		astroFunctions.put("zero(x1)", afZero);
		
		EvaluableFunction afPol = new AstrocyteFunction();
		try {
			afPol.storeFunction("pol()=0", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		astroFunctions.put("pol()", afPol);
		
		EvaluableFunction afSub = new AstrocyteFunction();
		try {
			afSub.storeFunction("sub()=0", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		astroFunctions.put("sub()", afSub);
		
		output = new HashSet<Integer>();
		
		loc = new ArrayList<List<Integer>>();
		memToLoc = new HashMap<Integer,List<Integer>>();
		
		showBinarySequence = false;
		showNaturalSequence = null;
		showSummatories = false;
		sequentialMode = 0;
		asynchMode = 0;
		validConfiguration = new HashMap<String, Long>();
		bound = 0L;
		specificRuleBoundActive = false;


	}

	public SpikingMembraneStructure(MembraneStructure membrane)
	{
		super();
				
		cellsById = new LinkedHashMap<Integer,SpikingMembrane>();
		cellsByLabel = new LinkedHashMap<String,List<SpikingMembrane>>();
		input = null;
		dictionary = null;
		cloneMap = new LinkedHashMap<Integer,Integer>();	// this is used to clone the structure mapping  to new id's
		
		graph = new LinkedHashMap<Integer, Set<Integer>>();
		rgraph = new LinkedHashMap<Integer, Set<Integer>>(); 
		
		astrocytes = new LinkedHashMap<String,Astrocyte>();
		arcsInfo = new LinkedHashMap<Pair<Integer,Integer>,ArcInfo>();
		astroToArcs = new LinkedHashMap<String,List<Pair<Integer,Integer>>>();
		astroToCtrlArcs = new LinkedHashMap<String,List<Pair<Integer,Integer>>>();
		astroFunctions = new LinkedHashMap<String,EvaluableFunction>(); 
		
		output = new HashSet<Integer>();
		
		loc = new ArrayList<List<Integer>>();
		memToLoc = new HashMap<Integer,List<Integer>>();
		
		showBinarySequence = false;
		showNaturalSequence = null;
		showSummatories = false;
		sequentialMode = 0;
		asynchMode = 0;
		validConfiguration = new HashMap<String, Long>();
		bound = 0L;
		specificRuleBoundActive = false;

		
		Iterator<? extends Membrane>it;

		if (membrane instanceof SpikingMembraneStructure)
		{
			SpikingMembraneStructure sps = (SpikingMembraneStructure)membrane;
			environment = new SpikingEnvironment(sps.getEnvironmentLabel(),this);
			environment.getMultiSet().addAll(sps.getEnvironment());
			secureAdd(environment);							// this will add the environment in any case
			environment.setStepsTaken(sps.environment.getStepsTaken());
			
			this.showBinarySequence = sps.showBinarySequence;
			this.showNaturalSequence = sps.showNaturalSequence;
			this.showSummatories = sps.showSummatories;
			this.sequentialMode = sps.sequentialMode;
			this.asynchMode = sps.asynchMode;
			this.validConfiguration = sps.validConfiguration;
			this.bound = sps.bound;
			this.specificRuleBoundActive = sps.specificRuleBoundActive;
			
			it = membrane.getAllMembranes().iterator();

			while(it.hasNext())
			{
				Membrane m = it.next();
				
				if (m.getLabel().equals(environment.getLabel()))
				{
					if (membrane instanceof CellLikeSkinMembrane)
						throw new IllegalArgumentException("The environment label cannot be used as neuron label");
				
					if (m instanceof ChangeableMembrane)
						cloneMap.put(((ChangeableMembrane)m).getId(), environment.getId());
					else
						throw new IllegalArgumentException("Changeable Membranes are needed in otder to Map Ids");
				}	
				else
				{
					SpikingMembrane mem = new SpikingMembrane(m,this);
					add(mem);
					
					if (m instanceof ChangeableMembrane)
						cloneMap.put(((ChangeableMembrane)m).getId(), mem.getId());
					else
						throw new IllegalArgumentException("Changeable Membranes are needed in otder to Map Ids");
				
				}
				
			}
			
			input = ((sps.input == null) ? null: cloneMap.get(sps.input));	// with this, we get the right id
			
			if(input != null)	
				environment.setInputSequence(sps.environment.getInputSequence());
			
			// Now we clone the dictionary
		
			dictionary = sps.dictionary;			// As the dictionary itself is static we can assign it safely.
			
			
			// Astrocyte Functions are static
			this.astroFunctions = sps.astroFunctions;
			
			// Now we clone the astrocyte: note: also direct assign is supposed to work at the current state
			
			Iterator<Astrocyte> itAstro = sps.astrocytes.values().iterator();
			
			while(itAstro.hasNext())
			{
				Astrocyte spsAstro = (Astrocyte) itAstro.next();
				
				if(spsAstro instanceof HybridAstrocyte)
				{
					HybridAstrocyte wSpsAstro = (HybridAstrocyte) spsAstro;					
					Astrocyte thisAstro = new HybridAstrocyte(wSpsAstro.getLabel(),this,wSpsAstro);
					addAstrocyte(thisAstro);
				}
				else if(spsAstro instanceof FunctionalAstrocyte)
				{
					FunctionalAstrocyte bSpsAstro = (FunctionalAstrocyte) spsAstro;					
					Astrocyte thisAstro = new FunctionalAstrocyte(bSpsAstro.getLabel(),this,bSpsAstro);
					addAstrocyte(thisAstro);					
				}
				
			}
			
			// the output neurons are calculated as the predecessors to the environment
			// the output list can be automatically created as the edges are built
			// but for efficiency reasons we build also an output set
			// additionally for the output neurons we must clone their spike trains also
			// so we define the necessary variables first
			
			Map<Integer, List<Short>> spsBinarySpikeTrain = sps.environment.getBinarySpikeTrain();
			Map<Integer, List<Short>> thisBinarySpikeTrain = this.environment.getBinarySpikeTrain();
			Map<Integer, List<Long>> spsNaturalSpikeTrain = sps.environment.getNaturalSpikeTrain();
			Map<Integer, List<Long>> thisNaturalSpikeTrain = this.environment.getNaturalSpikeTrain();
			
			Iterator<Integer> keys = sps.graph.keySet().iterator();
			
			while(keys.hasNext())
			{
				Integer key = keys.next();
				
				Iterator<Integer> values = sps.graph.get(key).iterator();
				
				while(values.hasNext())
				{
					Integer value = values.next();
					
					int spsSourceId, spsTargetId, thisSourceId, thisTargetId;
					
					spsSourceId 		= key;
					spsTargetId 		= value;
				
					thisSourceId 		= cloneMap.get(spsSourceId);
					thisTargetId 		= cloneMap.get(spsTargetId);
					
					// now we extract the ArcInfo object and we pass it through to the connect method
					
					ArcInfo spsArcInfo = sps.getArcInfo(spsSourceId, spsTargetId);
					
					connect(thisSourceId, thisTargetId, spsArcInfo, false, false);
					// we don't need to build the dictionary as it can be assigned safely
					// we don't need to initialize the spike train as it's going to be cloned
					
					SpikingMembrane thisTarget = this.getCellById(thisTargetId);
					
					// if target is the environment then source is an output neuron and we have to clone its spike trains
					if(thisTarget.getLabel().equals(environment.getLabel()))
					{
						List<Short> binarySpikeTrainArray = (List<Short>) ((ArrayList<Short>) spsBinarySpikeTrain.get(spsSourceId)).clone();
						thisBinarySpikeTrain.put(thisSourceId, binarySpikeTrainArray);
						List<Long> naturalSpikeTrainArray = (List<Long>) ((ArrayList<Long>) spsNaturalSpikeTrain.get(spsSourceId)).clone();
						thisNaturalSpikeTrain.put(thisSourceId, naturalSpikeTrainArray);
						
						// we add the neuron to the output set
						output.add(thisSourceId);
					
					}
					
					
				}
				
				
			}
			
			// lets clone the loc sets
			
			Iterator<List<Integer>> itLoc = sps.loc.iterator();
			
			while(itLoc.hasNext())
			{
				ArrayList<Integer> thisLocElem = new ArrayList<Integer>();
				
				List<Integer> spsLocElem = (List<Integer>) itLoc.next();
								
				Iterator<Integer> itLocElem = spsLocElem.iterator();
				
				while(itLocElem.hasNext())
				{
					Integer memId = (Integer) itLocElem.next();
					Integer thisMemId = cloneMap.get(memId);
					thisLocElem.add(thisMemId);
				}
				
				this.loc.add(thisLocElem);
				
			}
			
			Iterator<Integer> itMemToLoc = sps.memToLoc.keySet().iterator();
			
			while(itMemToLoc.hasNext())
			{
				ArrayList<Integer> thisMemToLocElem = new ArrayList<Integer>();
				
				Integer spsMemToLocElemKey = (Integer) itMemToLoc.next();
				
				List<Integer> spsMemToLocElem = sps.memToLoc.get(spsMemToLocElemKey);
				
				Iterator<Integer> itSpsMemToLocElem = spsMemToLocElem.iterator();
				
				while(itSpsMemToLocElem.hasNext())
				{
					Integer locSubSet = (Integer) itSpsMemToLocElem.next();
					thisMemToLocElem.add(new Integer(locSubSet));
					
				}
				
				Integer thisMemToLocElemKey = cloneMap.get(spsMemToLocElemKey);
				
				this.memToLoc.put(thisMemToLocElemKey, thisMemToLocElem);
			}
						
		}
		else
			throw new IllegalArgumentException("The membrane structure must be kinda Spiking one");
		
		cloneMap = null;	// this line is not strictly necessary as memory will be cleaned up.
				
	}
	
	
	public ArcInfo getArcInfo(Integer sourceId, Integer targetId)
	{
		ArcInfo arcInfo = null;
		
		Pair<Integer,Integer> arc = new Pair<Integer,Integer>(sourceId,targetId);
		arcInfo = arcsInfo.get(arc);
		
		return arcInfo;
	}
	
	public Astrocyte getAstrocyte(String label)
	{
		Astrocyte ast = null;
		
		ast = astrocytes.get(label);
		
		return ast;
	}

	protected int getNextId()
	{
		
		return cellsById.size();
	}
	

	public String getEnvironmentLabel() {
		return environment.getLabel();
	}
	
	public SpikingEnvironment getEnvironmentMembrane()
	{
		return environment;
	}
	
	@Override
	public Object clone()
	{
		SpikingMembraneStructure clone = new SpikingMembraneStructure(this);
		return clone;
	}
	
	@Override
	public Collection<? extends Membrane> getAllMembranes() {
		// TODO Auto-generated method stub
		return new SpikingMembraneCollection();
	}
	

	public SpikingMembrane getCellById(int id)
	{
		return cellsById.get(id);
	}
	
	public List<SpikingMembrane> getCellsByLabel(String label)
	{
		return cellsByLabel.get(label);
	}
	
	protected boolean renewLabel(SpikingMembrane m, String Label, String newLabel)
	{
		
		List<SpikingMembrane> l = cellsByLabel.get(Label);
		
		int i = 0;
		boolean stop = false;
		while(i < l.size() && !stop)
		{
			SpikingMembrane mem = (SpikingMembrane) l.get(i);
			
			if(mem.getId() == m.getId())
				stop = true;
			else
				i++;
			
		}
		
		if(stop)
		{
			l.remove(i);
			
			if(l.isEmpty())
				cellsByLabel.remove(Label);
		
			if (!cellsByLabel.containsKey(newLabel))
			{
				l = new ArrayList<SpikingMembrane>();
				cellsByLabel.put(newLabel, l);
			}
			else
				l = cellsByLabel.get(newLabel);
			
			l.add(m);	// The neuron is always added to the list
			
			return true;
			
		}
		else
			return false;
	}
	
	
	private boolean secureAdd(SpikingMembrane arg0)
	{
		if (!cellsById.containsKey(arg0.getId()))
		{
			cellsById.put(arg0.getId(), arg0);
								
			String label = arg0.getLabel();
			
			List<SpikingMembrane>l;
			
			if (!cellsByLabel.containsKey(label))
			{
				l = new ArrayList<SpikingMembrane>();
				cellsByLabel.put(label, l);
			}
			else
				l = cellsByLabel.get(label);
			
			l.add(arg0);	// The neuron is always added to the list
			
			return true;
		}
		
		return false;
	}

	protected boolean add(SpikingMembrane arg0)
	{
		// TODO Auto-generated method stub
		
		if(astrocytes.containsKey(arg0.getLabel()))
		{
			throw new IllegalArgumentException("Can't add a neuron with a label corresponding to an existing astrocyte");
		}
		
		if (arg0.getLabel().equals(getEnvironmentLabel()))
			throw new IllegalArgumentException("The label 'environment' is not allowed");
		
		return secureAdd(arg0);
		
	}
	
	// This method is called from the clone method
	private boolean addAstrocyte(Astrocyte arg0)
	{
		if(cellsByLabel.containsKey(arg0.getLabel()))
		{
			throw new IllegalArgumentException("Can't add an astrocyte with a label corresponding to an existing neuron");
		}
		

		if (astrocytes.containsKey(arg0.getLabel()))
		{
			throw new IllegalArgumentException("An astrocyte with the same label already exists");			
		}
		
		astrocytes.put(arg0.getLabel(), arg0);		
		
		return true;
		
	}
	

	// This method is called from the astrocyte class which is called from the parser
	protected boolean addAstrocyte(Astrocyte arg0, List<Pair<String,String>> arcs, boolean ctrlArcs)
	{	
		if(cellsByLabel.containsKey(arg0.getLabel()))
		{
			throw new IllegalArgumentException("Can't astrocyte with a label corresponding to an existing neuron");
		}
	
		
		Iterator<Pair<String,String>> itArcs = arcs.iterator();
		
		while(itArcs.hasNext())
		{
			Pair<String,String> arc = (Pair<String,String>) itArcs.next();
			addAstrocyte(arg0,arc,ctrlArcs);
		}
		
				
		return true;
		
	}
	
	
	private boolean addAstrocyte(Astrocyte arg0, Pair<String,String> plabel, boolean ctrlArc)
	{

		String labelSource = plabel.getFirst();
		String labelTarget = plabel.getSecond();
		
		List<SpikingMembrane> sources = this.getCellsByLabel(labelSource);
		List<SpikingMembrane> targets = this.getCellsByLabel(labelTarget);
		
		if (sources==null || sources.isEmpty())
			throw new IllegalArgumentException("There's no source neurons with the specified label");
		if (targets==null || targets.isEmpty())
			throw new IllegalArgumentException("There's no target neurons with the specified label");
		
		if (!astrocytes.containsKey(arg0.getLabel()))
		{
			astrocytes.put(arg0.getLabel(), arg0);
		}
		
		boolean result = true;
		
		Iterator<SpikingMembrane> its = sources.iterator();
		
		while(result && its.hasNext())
		{
			SpikingMembrane s = (SpikingMembrane) its.next();
			
			Iterator<SpikingMembrane> itt = targets.iterator();
			
			while(result && itt.hasNext())
			{
				SpikingMembrane t = (SpikingMembrane) itt.next();
		
				Pair<Integer,Integer> p = new Pair<Integer,Integer>(s.getId(),t.getId());
				
				if(!existsArc(p))
				{
					throw new IllegalArgumentException("Can't associate an astrocyte to an arc that doesn't exists");
				}
				else
				{
										
					ArcInfo arcInfo = arcsInfo.get(p);
					
					boolean canBeAssociated = ctrlArc || ((arcInfo != null) && ( (arcInfo.getAstrocyteList().isEmpty()) || (arcInfo.getAstType().equals(arg0.getType()))));
					
					if(!canBeAssociated)
					{
						throw new IllegalArgumentException("Impossible to assign different kind of astrocytes to the arc");						
					}
					else
					{
					
						if(!ctrlArc && arcInfo.getAstrocyteList().isEmpty())
						{
							arcInfo.setAstType(arg0.getType());
							
						}
						
						String astLabel = arg0.getLabel();

						
						if(ctrlArc)
						{

							// We update both structures

							// First: arcsInfo, to inform that the arc is now controlling astrocyte arg0 behavior
						
							List<String> astCtrlList = arcInfo.getAstrocyteCtrlList();
							
							if(!astCtrlList.contains(astLabel))
								astCtrlList.add(astLabel);
							
							arcInfo.setAstrocyteCtrlList(astCtrlList);

							arcsInfo.put(p, arcInfo);
							
							// Second: astroToCtrlArcs, to inform that the astrocyte is controlled by the arc
							
							ArrayList<Pair<Integer,Integer>> arcsCtrlList = null;

							if(astroToCtrlArcs.containsKey(astLabel))
								arcsCtrlList = (ArrayList<Pair<Integer,Integer>>) astroToCtrlArcs.get(astLabel);
							else
								arcsCtrlList = new ArrayList<Pair<Integer,Integer>>();

							arcsCtrlList.add(p);
							astroToCtrlArcs.put(astLabel, arcsCtrlList);


						}
						else
						{

							// We update both structures

							// First: arcsInfo, to inform that the arc is now under astrocyte arg0 surveillance

							List<String> astList = arcInfo.getAstrocyteList();
							
							if(!astList.contains(astLabel))
								astList.add(astLabel);
							
							arcInfo.setAstrocyteList(astList);

							arcsInfo.put(p, arcInfo);

							// Second: astroToArcs, to inform that the astrocyte now controls traffic associated to the arc

							ArrayList<Pair<Integer,Integer>> arcsList = null;

							if(astroToArcs.containsKey(astLabel))
								arcsList = (ArrayList<Pair<Integer,Integer>>) astroToArcs.get(astLabel);
							else
								arcsList = new ArrayList<Pair<Integer,Integer>>();

							arcsList.add(p);
							astroToArcs.put(astLabel, arcsList);

							
						}

					}

				}

			}

		} 
		
		return true;
		
	}
	

	private boolean existsArc(Pair<Integer,Integer> p)
	{
		
		Integer sourceId = p.getFirst();
		Integer targetId = p.getSecond();
		
		if(!cellsById.containsKey(sourceId) || !cellsById.containsKey(targetId))
			return false;
		
		Set<Integer> targetSet = (Set<Integer>) graph.get(sourceId);
		
		if(targetSet == null)
			return false;
		else
			return targetSet.contains(targetId);
	}
	
	public boolean addAstrocyteFunction(String name, String body, int numParams)
	{
		boolean result = false;
		
		if (!astroFunctions.containsKey(name))
		{
			
			try
			{
				EvaluableFunction af = (EvaluableFunction) new AstrocyteFunction();
				af.storeFunction(new String(body), numParams);
				astroFunctions.put(name, af);
				result = true;
			
			}
			catch (Exception e)
			{
				result = false;
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	protected AstrocyteFunction getAstrocyteFunction(String name)
	{
		AstrocyteFunction af = null;
		
		af = (AstrocyteFunction) astroFunctions.get(name);
		
		if(af == null)
			af = (AstrocyteFunction) astroFunctions.get("identity(x1)");
		
		return af;
	}
	
	protected List<Pair<Integer,Integer>> getAstrocyteArcs(String astroLabel)
	{
		return astroToArcs.get(astroLabel);
	}
	
	protected List<Pair<Integer,Integer>> getAstrocyteCtrlArcs(String astroLabel)
	{
		return astroToCtrlArcs.get(astroLabel);
	}

	
	public boolean loadAstrocytes(Set<Pair<Integer,Integer>> affectedArcs)
	{
		boolean result = true;
		
		Iterator<Pair<Integer,Integer>> it = affectedArcs.iterator();

		while (it.hasNext()) {

			Pair<Integer,Integer> p = it.next();
			Integer sourceId = p.getFirst();
			Integer targetId = p.getSecond();
			ArcInfo arcInfo = this.getArcInfo(sourceId, targetId);
			
			if(!arcInfo.getAstrocyteCtrlList().isEmpty())
			{
				Iterator<String> itAstro = arcInfo.getAstrocyteCtrlList().iterator();
				
				while(itAstro.hasNext())
				{
					String astroLabel = (String) itAstro.next();
					Astrocyte ast = this.getAstrocyte(astroLabel);
					
					// We load the spikes into the astrocyte
					ast.loadSpikes(arcInfo.getSpikesInput());
				}
				
				printDebug("This arc controls Functional Astrocytes... Spikes loaded!!!");
				printDebug(arcInfo.getAstrocyteCtrlList());
			}

			if(!arcInfo.getAstType().equals("none"))
			{
				
				if(arcInfo.getAstType().equals("hybrid"))
				{
					Iterator<String> itAstro = arcInfo.getAstrocyteList().iterator();
					
					while(itAstro.hasNext())
					{
						String astroLabel = (String) itAstro.next();
						Astrocyte ast = this.getAstrocyte(astroLabel);
						
						// We load the spikes into the astrocyte
						ast.loadSpikes(arcInfo.getSpikesInput());
					}

					printDebug("This arc is under control of Hybrid Astrocytes... Spikes loaded!!!");
					printDebug(arcInfo.getAstrocyteList());					
				}
				else if(arcInfo.getAstType().equals("functional"))
				{
					; printDebug("This arc is under control of Functional Astrocytes... Nothing loaded!!!");
				}

			}

		}
		
		return result;
	}
	
	public boolean flushAstrocytes(Set<Pair<Integer,Integer>> affectedArcs)
	{
		
		boolean result = true;
						
		Iterator<Astrocyte> itAstro = astrocytes.values().iterator();
		
		while(itAstro.hasNext())
		{
			Astrocyte ast = (Astrocyte) itAstro.next();
			ast.flush();
			affectedArcs.addAll(ast.getArcs());
		}

		return result;
		
	}
	

	protected Long evalFunction(EvaluableFunction function, List<Object> params)
	{
		Long result = 0L;
		
		
		try {
			result = (Long) function.evaluateFunction(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public InfiniteMultiSet<String> getEnvironment()
	{
		return (InfiniteMultiSet<String>)environment.getMultiSet();
	}
	
	
	// this method is called from the clone() method.
	
	private boolean connect(Integer sourceId, Integer targetId, ArcInfo arcInfo, boolean updateDictionary, boolean initializeSpikeTrain)
	{
		SpikingMembrane source = this.getCellById(sourceId);
		SpikingMembrane target = this.getCellById(targetId);
		
		if (source==null)
			throw new IllegalArgumentException("The source neuron doesn't exist");
		if (target==null)
			throw new IllegalArgumentException("The target neuron doesn't exist");
		
		
		return connect(source,target, arcInfo, updateDictionary,initializeSpikeTrain);
	}
	
	// this method is called from the parser
	
	public boolean connect(String labelSource,String labelTarget)
	{
		return connect(labelSource,labelTarget,null,true,true);
	}
	

	
	private boolean connect(String labelSource,String labelTarget, ArcInfo arcInfo, boolean updateDictionary, boolean initializeSpikeTrain)
	{
		List<SpikingMembrane> sources = this.getCellsByLabel(labelSource);
		List<SpikingMembrane> targets = this.getCellsByLabel(labelTarget);
		if (sources==null || sources.isEmpty())
			throw new IllegalArgumentException("There's no source neurons with the specified label");
		if (targets==null || targets.isEmpty())
			throw new IllegalArgumentException("There's no target neurons with the specified label");
		
		boolean result = true;
		
		Iterator<SpikingMembrane> its = sources.iterator();
		
		while(result && its.hasNext())
		{
			SpikingMembrane s = (SpikingMembrane) its.next();
			
			Iterator<SpikingMembrane> itt = targets.iterator();
			
			while(result && itt.hasNext())
			{
				SpikingMembrane t = (SpikingMembrane) itt.next();
				
				ArcInfo checkedArcInfo = null;
				
				if(arcInfo == null)
					checkedArcInfo = new ArcInfo(s.getId(),t.getId());
				else
					checkedArcInfo = arcInfo;
				
				result = connect(s,t,checkedArcInfo, updateDictionary,initializeSpikeTrain);
			}
		}
		
		
		return result;
		
	}	
	
	public boolean connect(SpikingMembrane source, SpikingMembrane target, ArcInfo arcInfo, boolean updateDictionary, boolean initializeSpikeTrain)
	{
		boolean result = true;
		
		boolean notExist = !existsArc(new Pair<Integer,Integer>(source.getId(),target.getId()));
		
		if(notExist)
		{
		
				HashSet<Integer> set = null;
				
				if(graph.containsKey(source.getId()))
					set = (HashSet<Integer>) graph.get(source.getId());
				else
					set = new HashSet<Integer>();
				
				set.add(target.getId());
				graph.put(source.getId(), set);
				
				if(rgraph.containsKey(target.getId()))
					set = (HashSet<Integer>) rgraph.get(target.getId());
				else
					set = new HashSet<Integer>();
				
				set.add(source.getId());
				rgraph.put(target.getId(), set);
	
				// now we add the code for the attributes
				
				Integer sourceId = source.getId();
				Integer targetId = target.getId();
				
				Pair<Integer,Integer> newArc = new Pair<Integer,Integer>(sourceId,targetId);
				ArcInfo newArcInfo = new ArcInfo(source.getId(),target.getId(),arcInfo);
				
				arcsInfo.put(newArc, newArcInfo);
				
				// it's possible to have more than one arc associated to an astrocyte
				
				Iterator<String> itAstro = newArcInfo.getAstrocyteList().iterator();
				
				while(itAstro.hasNext())
				{
					String astLabel = (String) itAstro.next();
					
					Astrocyte ast = null;
					
					if(astLabel != null && !astLabel.equals(""))
						ast = astrocytes.get(astLabel); 		
					
					if(ast != null)
					{
											
						List<Pair<Integer,Integer>> arcsList = null;
						
						if(astroToArcs.containsKey(astLabel))
							arcsList = (List<Pair<Integer,Integer>>) astroToArcs.get(astLabel);
						else
							arcsList = new ArrayList<Pair<Integer,Integer>>();
						
						arcsList.add(newArc);
						astroToArcs.put(astLabel, arcsList);
						
					}				

				}
				
				// the same applies for the control arcs
				
				Iterator<String> itCtrlAstro = newArcInfo.getAstrocyteCtrlList().iterator();
				
				while(itCtrlAstro.hasNext())
				{
					String astLabel = (String) itCtrlAstro.next();
					
					Astrocyte ast = null;
					
					if(astLabel != null && !astLabel.equals(""))
						ast = astrocytes.get(astLabel); 		
					
					if(ast != null)
					{
											
						List<Pair<Integer,Integer>> arcsList = null;
						
						if(astroToCtrlArcs.containsKey(astLabel))
							arcsList = (List<Pair<Integer,Integer>>) astroToCtrlArcs.get(astLabel);
						else
							arcsList = new ArrayList<Pair<Integer,Integer>>();
						
						arcsList.add(newArc);
						astroToCtrlArcs.put(astLabel, arcsList);
						
					}				

				}
				
		}
		else
			result = false;
		
				
		// Now we update the dictionary but only when
		// - we are creating post sn p system creation synapses (budding / division) i. e. updateDictionary = true
		// - the connection went ok i. e. result = true
		// - we are not connecting to the environment as the environment has a special label not contained in the dictionary
		
		if(updateDictionary && result && !target.getLabel().equals(this.getEnvironmentLabel()))
		{
			String sourceLabel = source.getLabel();
			String targetLabel = target.getLabel();
			updateDictionary(sourceLabel,targetLabel);

		}
		
		// if we are connecting a neuron to the environment then we are setting an output neuron
		// so we have to mark it as output and initialize its spike trains
		
		if(result && target.getLabel().equals(this.getEnvironmentLabel()))
		{
			output.add(source.getId());
			
			if(initializeSpikeTrain)
				this.environment.initializeSpikeTrain(source);
		}
		
		return result;
	}
	
	public boolean connect(SpikingMembrane source, SpikingMembrane target, boolean updateDictionary, boolean initializeSpikeTrain)
	{
		
		ArcInfo arcInfo = new ArcInfo(source.getId(),target.getId());
		
		return connect(source,target,arcInfo,updateDictionary,initializeSpikeTrain);
		
	}
	
	
	public boolean updateDictionary(String sourceLabel, String targetLabel)
	{
		boolean result = true;
		
		HashSet<String> set = null;
		
		if(dictionary.containsKey(sourceLabel))
			set = (HashSet<String>) dictionary.get(sourceLabel);
		else
			set = new HashSet<String>();
		
		set.add(targetLabel);
		dictionary.put(sourceLabel, set);
			
		return result;
		
	}
	
	public boolean disconnect(SpikingMembrane source, SpikingMembrane target)
	{
		boolean result = true;
		
		if(cellsById.containsKey(source.getId()) && cellsById.containsKey(target.getId()))
		{
		
				HashSet<Integer> set = null;
				
				if(graph.containsKey(source.getId()))
					set = (HashSet<Integer>) graph.get(source.getId());
				else
					set = new HashSet<Integer>();
				
				if(set.contains(target.getId()))
					set.remove(target.getId());
				
				if(set.isEmpty())
					graph.remove(source.getId());
				else
					graph.put(source.getId(), set);
				
				
				if(rgraph.containsKey(target.getId()))
					set = (HashSet<Integer>) rgraph.get(target.getId());
				else
					set = new HashSet<Integer>();
				
				if(set.contains(source.getId()))
					set.remove(source.getId());
				
				if(set.isEmpty())
					rgraph.remove(target.getId());
				else
					rgraph.put(target.getId(), set);
				
				
				// now we add the code for the attributes
				
				Integer sourceId = source.getId();
				Integer targetId = target.getId();
				
				Pair<Integer,Integer> disposableArc = new Pair<Integer,Integer>(sourceId,targetId);	
				ArcInfo disposableArcInfo = arcsInfo.get(disposableArc);
				
				// astroToArcs
				
				Iterator<String> itAstro = disposableArcInfo.getAstrocyteList().iterator();
				
				while(itAstro.hasNext())
				{
					String astLabel = (String) itAstro.next();
					
					Astrocyte ast = null;
					
					if(astLabel != null && !astLabel.equals(""))
						ast = astrocytes.get(astLabel); 		
					
					if(ast != null)
					{
										
						List<Pair<Integer, Integer>> arcsList = null;
						
						if(astroToArcs.containsKey(astLabel))
							arcsList = (List<Pair<Integer,Integer>>) astroToArcs.get(astLabel);
						else
							arcsList = new ArrayList<Pair<Integer,Integer>>();			
						
						if(arcsList.contains(disposableArc))
							arcsList.remove(disposableArc);

						astroToArcs.put(astLabel, arcsList);
						
					}

				}
				
				// astroToCtrlArcs
				
				Iterator<String> itCtrlAstro = disposableArcInfo.getAstrocyteCtrlList().iterator();
				
				while(itCtrlAstro.hasNext())
				{
					String astLabel = (String) itCtrlAstro.next();
					
					Astrocyte ast = null;
					
					if(astLabel != null && !astLabel.equals(""))
						ast = astrocytes.get(astLabel); 		
					
					if(ast != null)
					{
										
						List<Pair<Integer, Integer>> arcsList = null;
						
						if(astroToCtrlArcs.containsKey(astLabel))
							arcsList = (List<Pair<Integer,Integer>>) astroToCtrlArcs.get(astLabel);
						else
							arcsList = new ArrayList<Pair<Integer,Integer>>();			
						
						if(arcsList.contains(disposableArc))
							arcsList.remove(disposableArc);

						astroToCtrlArcs.put(astLabel, arcsList);
						
					}

				}
								
				// and don't forget to delete the arcInfo
				
				arcsInfo.remove(disposableArcInfo);
				
		}
		else
			result = false;
		
		// if we are disconnecting a neuron from the environment we have to mark it as no output and clear its spike trains
		
		if(result && target.getLabel().equals(this.getEnvironmentLabel()))
		{
			output.remove(source.getId());
			this.environment.destroySpikeTrain(source);
		}
		
		return result;
	}
	
	public Map<String,Set<String>> getDictionary()
	{
		return dictionary;
		
	}
	
	public List<SpikingMembrane> getPredecessors(SpikingMembrane m)
	{

		List<SpikingMembrane> predecessors = new ArrayList<SpikingMembrane>();
		
		if(rgraph.containsKey(m.getId()))
		{
			Iterator<Integer>itedges = rgraph.get(m.getId()).iterator();
			
			while(itedges.hasNext())
			{
				Integer e = (Integer) itedges.next();
				
				SpikingMembrane s = this.getCellById(e);
		
				predecessors.add(s);			
			}
		}
		
				
		return predecessors;
	}
		
	
	public List<SpikingMembrane> getSuccessors(SpikingMembrane m)
	{

		
		List<SpikingMembrane> successors = new ArrayList<SpikingMembrane>();
		
		if(graph.containsKey(m.getId()))
		{
		
			Iterator<Integer>itedges = graph.get(m.getId()).iterator();
								
			while(itedges.hasNext())
			{
				Integer e = (Integer) itedges.next();
				
				SpikingMembrane s = this.getCellById(e);
				
				successors.add(s);			
			}
			
		}
		
		return successors;
	}
	
	public void setInputMembrane(String inputMembraneLabel,boolean check)
	{
		List<SpikingMembrane>l = this.getCellsByLabel(inputMembraneLabel);
		
		if (l==null || l.isEmpty())
			throw new IllegalArgumentException("The input neuron doesn't exist");
		setInputMembrane(l.get(0),check);
	}
		
	private void setInputMembrane(SpikingMembrane m, boolean check)
	{
		if(check)
			if(m == null || cellsById.containsKey(m.getId()) == false)
				throw new IllegalArgumentException("The neuron is not contained in the structure");
				
		this.input = m.getId();
		
		HashMap<Long,Long> inputSequence = new HashMap<Long,Long>();
		environment.setInputSequence(inputSequence);
				
	}
	
	
	public SpikingMembrane getInputMembrane()
	{
		return ((input == null) ? null : cellsById.get(input));
	}
		
	public boolean setOutputMembrane(String outputMembraneLabel,boolean check)
	{
		List<SpikingMembrane>l = this.getCellsByLabel(outputMembraneLabel);
		
		if (l==null || l.isEmpty())
			throw new IllegalArgumentException("The output neuron doesn't exist");
		
		
		return setOutputMembranes(l,check);
		
	}
	
	private boolean setOutputMembranes(List<SpikingMembrane> o, boolean check)
	{
		if(check)
			if (o == null || cellsById.values().containsAll(o) == false)
				throw new IllegalArgumentException("The neurons are not contained in the structure");
			
		Iterator<SpikingMembrane> it = o.iterator();
		
		boolean result = true;
		
		while(it.hasNext() && result)
		{
			SpikingMembrane mem = (SpikingMembrane) it.next();
			result = connect(mem, environment,false, true);
			
			// we don't need to update the dictionary as we are setting output neurons
			// we have to initialize the spike train as we are setting output neurons
			
		}
		
		return result;

	}
	
	private List<SpikingMembrane> getOutputMembranes()
	{
		return this.getPredecessors(environment);
	}
	
	public boolean isOutput(SpikingMembrane in)
	{
		boolean result = false;
		
		result = output.contains(in.getId());
		
		return result;
	}
	
	public boolean getShowBinarySequence()
	{
		return this.showBinarySequence;
	}
	
	public void setShowBinarySequence(boolean s)
	{
		this.showBinarySequence = s;
	}
	
	public List<Object> getShowNaturalSequence()
	{
		return this.showNaturalSequence;
	}
	
	public void setShowNaturalSequence(List<Object> s)
	{
		this.showNaturalSequence = s;
	}
	
	public boolean getShowSummatories()
	{
		return this.showSummatories;
	}
	
	public void setShowSummatories(boolean s)
	{
		this.showSummatories = s;
	}
	
	public int getSequentialMode()
	{
		return this.sequentialMode;
	}
	
	public void setSequentialMode(int i)
	{
		this.sequentialMode = i;
	}
	
	public int getAsynchMode()
	{
		return this.asynchMode;
	}
	
	public void setAsynchMode(int i)
	{
		this.asynchMode = i;
	}
	
	public Map<String, Long> getValidConfiguration()
	{
		return this.validConfiguration;
	}
	
	public void updateValidConfiguration(String label, long spikes)
	{
		this.validConfiguration.put(label, spikes);
	}
	
	public long getBound()
	{
		return bound;
	}
	
	public void setBound(long bound)
	{
		
		if(bound < 2)
			;
		else
			this.bound = bound;
	}
	
	public boolean getSpecificRuleBoundActive()
	{
		return this.specificRuleBoundActive;
	}
	
	public void setSpecificRuleBoundActive(boolean active)
	{
		this.specificRuleBoundActive = active;
	}
		
	public List<List<Integer>> getLoc()
	{
		return this.loc;
	}
	
	public void setLoc(List<List<Integer>> l)
	{
		this.loc = l;
	}
	
	public Map<Integer,List<Integer>> getMemToLoc()
	{
		return this.memToLoc;
	}
	
	public void setMemToLoc(Map<Integer,List<Integer>> m)
	{
		this.memToLoc = m;
	}
	
	public void setLocAttributesFromSet(Set<Set<String>> set)
	{
		this.loc.clear();
		this.memToLoc.clear();
		
		int pos = 0;
		
		for(Set<String> subset : set)
		{
			List<Integer> subLoc = new ArrayList<Integer>();
			
			for(String s : subset)
			{
				List<SpikingMembrane> list = this.cellsByLabel.get(s);
				
				if(list == null || list.isEmpty())
					throw new IllegalArgumentException("The neuron does not exists");
				
				for(SpikingMembrane m:list)
				{
					Integer i = m.getId();
					subLoc.add(i);
					this.safeMemToLocAdd(pos, i);
				}
				
			}
				
			this.loc.add(subLoc);
			pos++;
		}
	}

	private boolean safeMemToLocAdd(int pos, Integer mem)
	{
		boolean result = true;
		
		List<Integer> list = this.memToLoc.get(mem);
		
		if(list == null)
			list = new ArrayList<Integer>();
		
		if(list.contains(pos))
			return false;
		else
			list.add(pos);
		
		this.memToLoc.put(mem, list);
		
		return result;
	}
	
	public boolean safeLocDivision(Integer original, Integer childOne, Integer childTwo)
	{
		return safeLocDivision(original, childOne, childTwo, false);
	}
	
	public boolean safeLocDivision(Integer original, Integer childOne, Integer childTwo, boolean reuseId)
	{
		boolean result = true;
		
		List<Integer> l = this.memToLoc.get(original);
		
		if(l == null || l.isEmpty())
			return false;
		
		for (Integer i: l)
		{
			List<Integer> list = this.loc.get(i);
			
			// We update first Loc list
			
			list.remove(original);	// this is because we are performing a division operation
			list.add(childOne);
			list.add(childTwo);
			
			// We update now memToLoc list
			
			this.safeMemToLocAdd(i, childOne);
			this.safeMemToLocAdd(i, childTwo);
		}
		
		// Finally we remove the deprecate entry for original neuron from memToLoc (only if we do not reuse the id)
		
		if(reuseId == false)
			this.memToLoc.remove(original);
		
		return result;
	}

	public boolean safeLocBudding(Integer original, Integer childOne)
	{
		boolean result = true;
		
		List<Integer> l = this.memToLoc.get(original);
		
		if(l == null || l.isEmpty())
			return false;
		
		for (Integer i: l)
		{
			List<Integer> list = this.loc.get(i);
			
			// We update first Loc list
		
			list.add(childOne);
			
			// We update now memToLoc list
			
			this.safeMemToLocAdd(i, childOne);
		}
		
		return result;
	}
	
	public Set<Integer> getLocFlatSet(Integer id)
	{
		Set<Integer> result = new HashSet<Integer>();
			
		if(this.memToLoc.containsKey(id))
		{
			List<Integer> list1 = this.memToLoc.get(id);
			
			for(Integer index:list1)
			{
				List<Integer> list2 = this.loc.get(index);
				result.addAll(list2);
			}
			
			result.remove(id);
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
		
		str+="Arcs: ";
		
		str += '\n' + graph.toString();
				
		str+='\n';

		str+="Arcs Info: ";
		
		str += '\n' + arcsInfo.toString();
				
		str+='\n';
		
		str+="Dictionary: ";
		str+='\n';
		Iterator<? extends String>itdict = dictionary.keySet().iterator();
		while (itdict.hasNext())
		{
			String key = itdict.next().toString(); 
			str+=key + ":" + dictionary.get(key).toString();
			if (itdict.hasNext())
				str+='\n';
		}
		
		str+='\n';
		
		str+="Input Membrane ID: ";
		str+= input == null ? "" : getCellById(input).getId();
		
		str+='\n';
		
		str+="Ouput Membranes IDs: ";
		
		Iterator<? extends SpikingMembrane>it3 = this.getOutputMembranes().iterator();
		while (it3.hasNext())
		{
			str+= ((SpikingMembrane) it3.next()).getId();
			if (it3.hasNext())
				str+=", ";
		}
		
		return str;
	}
	
	private static void printDebug(Object o)
	{
		if(DEBUG)
			System.out.println(o);
	}
	
	private static void printDebug()
	{
		if(DEBUG)
			System.out.println();
	}



	class SpikingMembraneCollection implements Collection<SpikingMembrane>
	{
		
		public SpikingMembraneCollection() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean add(SpikingMembrane arg0) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(Collection<? extends SpikingMembrane> arg0) {
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
			if (!(arg0 instanceof SpikingMembrane))
				return false;
			SpikingMembrane tlm = (SpikingMembrane)arg0;
			
			return cellsById.containsKey(tlm.getId());
		}

		public List<SpikingMembrane> getByLabel(Object arg0) {
			// TODO Auto-generated method stub
			if (!(arg0 instanceof String))
				return null;
			
			String label = (String)arg0;
			
			return cellsByLabel.get(label);
			
		}
		
		public SpikingMembrane getById(Object arg0) {
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
		public Iterator<SpikingMembrane> iterator() {
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
   
	

}
