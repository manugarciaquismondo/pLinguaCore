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
package org.gcn.plinguacore.parser.output.binary;



import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.simulator.cellLike.probabilistic.dcba.DCBAProbabilisticSimulator;
import org.gcn.plinguacore.simulator.cellLike.probabilistic.dcba.EnvironmentRightHandRule;
import org.gcn.plinguacore.simulator.cellLike.probabilistic.dcba.EnvironmentRulesBlock;
import org.gcn.plinguacore.simulator.cellLike.probabilistic.dcba.MatrixColumn;
import org.gcn.plinguacore.simulator.cellLike.probabilistic.dcba.SkeletonRightHandRule;
import org.gcn.plinguacore.simulator.cellLike.probabilistic.dcba.SkeletonRulesBlock;
import org.gcn.plinguacore.simulator.cellLike.probabilistic.dcba.StaticMethods;
import org.gcn.plinguacore.util.ByteOrderDataOutputStream;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.AlphabetObject;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;



/**
 * This class writes a probabilistic P system in a binary file (under development)
 * 
 *  @author Research Group on Natural Computing (http://www.gcn.us.es)
 * 
 */
class ProbabilisticBinaryOutputParser extends AbstractBinaryOutputParser {

	
	private DCBAProbabilisticSimulator simulator;
	private Map<String,Integer>environments;
	private Map<String,Integer>membranes;
	private Map<String,Integer>alphabet;
	private Map<String,String>parentMembranes;
	private Map<EnvironmentRulesBlock,Integer>environmentRulesBlocks;
	private Map<SkeletonRulesBlock,Integer>skeletonRulesBlocks;
	private int alphabetAccuracy;
	private int environmentsAccuracy;
	private int membranesAccuracy;
	private int skeletonRulesBlocksAccuracy;
	private int environmentRulesBlocksAccuracy;
	
	private int objMultInRulesAccuracy;
	private int initialNumObjMembranesAccuracy;
	private int objMultInitialMultisetsAccuracy;
	
	private Map<EnvironmentRulesBlock,List<Integer>> environmentRulesBlocksAccuracyMap;
	private Map<SkeletonRulesBlock,List<Integer>> skeletonRulesBlocksAccuracyMap;
	
	
	public ProbabilisticBinaryOutputParser() {
		super();
		environments = new LinkedHashMap<String,Integer>();
		membranes = new LinkedHashMap<String,Integer>();
		parentMembranes = new LinkedHashMap<String,String>();
		alphabet = new LinkedHashMap<String,Integer>();
		environmentRulesBlocks=new LinkedHashMap<EnvironmentRulesBlock,Integer>();
		skeletonRulesBlocks=new LinkedHashMap<SkeletonRulesBlock,Integer>();
		// TODO Auto-generated constructor stub
	}
	
	private void setAlphabet() throws IOException
	{
		alphabet.clear();
		int i=0;
		
		// code to consider the empty string
		alphabet.put("#", i);
		i = i + 1;
		// end code to consider the empty string
		
		for (AlphabetObject object:getPsystem().getAlphabet())
		{
			alphabet.put(object.toString(), i);
			i++;
		}
		alphabetAccuracy = ByteOrderDataOutputStream.getAccuracy(alphabet.size());
	}
	
	private void setMembranes() throws IOException
	{
		membranes.clear();
		parentMembranes.clear();
		CellLikeSkinMembrane skin=(CellLikeSkinMembrane) getPsystem().getMembraneStructure();
		if (skin.getChildMembranes().isEmpty())
			return;
		CellLikeMembrane firstEnvironment = skin.getChildMembranes().iterator().next();
		StaticMethods.getParents(firstEnvironment,parentMembranes);
		int i=1;
		for (String m:parentMembranes.keySet())
		{
			membranes.put(m,i);
			i++;
		}
		membranesAccuracy = ByteOrderDataOutputStream.getAccuracy(membranes.size()+1);
	}
	
	private void setEnvironments() throws IOException
	{
		environments.clear();
		int environmentCounter=0;
		CellLikeSkinMembrane skin=(CellLikeSkinMembrane) getPsystem().getMembraneStructure();
		for (CellLikeMembrane environment:skin.getChildMembranes())
		{
			environments.put(environment.getLabel(), environmentCounter);
			environmentCounter++;
		}
		environmentsAccuracy = ByteOrderDataOutputStream.getAccuracy(environments.size());
	}
	private void setBlocks() throws IOException
	{
		environmentRulesBlocks.clear();
		skeletonRulesBlocks.clear();
		int environmentCounter=0;
		int skeletonCounter=0;
		for (MatrixColumn c:simulator.getStaticMatrix().getColumns())
		{
			if (c.isEnvironmentColumn())
			{
				environmentRulesBlocks.put((EnvironmentRulesBlock)c,environmentCounter);
				environmentCounter++;
			}
			else
			{
				skeletonRulesBlocks.put((SkeletonRulesBlock)c,skeletonCounter);
				skeletonCounter++;
			}
		}
		environmentRulesBlocksAccuracy=ByteOrderDataOutputStream.getAccuracy(environmentRulesBlocks.size());
		skeletonRulesBlocksAccuracy=ByteOrderDataOutputStream.getAccuracy(skeletonRulesBlocks.size());
	}
	
	private void writeSubHeader() throws IOException
	{
		// sub header: 3 bytes
		
		// b1:
		// 00 00	:	num objects	  (2) + num environments	(2)
		// 00 00	:	num membranes (2) + num skeleton rules	(2)
		
		// b2:
		// 00 00	:	num env rules (2) + obj mult rules		(2)
		// 00 00	:	init obj memb (2) + obj mult init mult	(2)
		
		// b3
		// 00 00	:	0000 (reserved)
		// 00 00	:	0 (reserved) + listing_alphabet(1) + listing_environments(1) + listing_membranes(1)
		// default	:	b3 = 0000 0100
		
		int b1,b2,b3;
		
		// calculating b1
		
		b1 = alphabetAccuracy;
		
		b1 = b1<<2;
		b1 = b1 | environmentsAccuracy;
		
		b1 = b1<<2;
		b1 = b1 | membranesAccuracy;
		
		b1 = b1<<2;
		b1 = b1 | skeletonRulesBlocksAccuracy;
		
		// calculating b2
		
		calculateMaxAccuracy();
		
		b2 = environmentRulesBlocksAccuracy;
		
		b2 = b2<<2;
		b2 = b2 | objMultInRulesAccuracy;
		
		b2 = b2<<2;
		b2 = b2 | initialNumObjMembranesAccuracy;
		
		b2 = b2<<2;
		b2 = b2 | objMultInitialMultisetsAccuracy;		
			
		// calculating b3
		// b3 is fixed to 0000 0100
		
		b3 = 0x04;
		
		// also we need to actually write b1 b2 b3
		
		getStream().writeByte(b1);
		getStream().writeByte(b2);
		getStream().writeByte(b3);
		
	}	
	
	private void calculateMaxAccuracy()
	{
		objMultInRulesAccuracy = 0;
		initialNumObjMembranesAccuracy = 0;
		objMultInitialMultisetsAccuracy = 0;
		
		long max1 = 0, max2 = 0, max3 = 0;
				
		for (MatrixColumn c:simulator.getStaticMatrix().getColumns())
		{
			
			if (c.isEnvironmentColumn())
			{
				EnvironmentRulesBlock block = (EnvironmentRulesBlock) c;
				
				long size = 0;
				long ms = 0;
				
				// LHS has only one element
				
				size = 1;
				
				if(size > max1)
					max1 = size;
				
				// now we go on the RHS
				
				for (EnvironmentRightHandRule r : block.getEnvironmentRightHandRules())
				{
					for (MultiSet<String> multiSet : r.getNewObjects().values())
					{
						ms=getMaxMultiplicity(multiSet);
						
						if(ms > max1)
							max1 = ms;
					}
				}
				
			}
			else
			{
				SkeletonRulesBlock block = (SkeletonRulesBlock) c;
				
				long size1 = 0;
				long ms1 = 0;
				long ms2 = 0;
				
				ms1=getMaxMultiplicity(block.getSkeletonLeftHandRule().getMainMultiSet());
				ms2=getMaxMultiplicity(block.getSkeletonLeftHandRule().getParentMultiSet());
				size1 = ms1>ms2?ms1:ms2;
				
				if(size1 > max1)
					max1 = size1;
				
				for(SkeletonRightHandRule r : block.getRightHandRules())
				{
					ms1=getMaxMultiplicity(r.getMainMultiSet());
					ms2=getMaxMultiplicity(r.getParentMultiSet());
					size1 = ms1>ms2?ms1:ms2;
					
					if(size1 > max1)
						max1 = size1;
				}
			}
						
		}
		
		objMultInRulesAccuracy = ByteOrderDataOutputStream.getAccuracy(max1);
		
		//for(Membrane m: getPsystem().getMembraneStructure().getAllMembranes())
		for(Membrane m: simulator.getCurrentConfig().getMembraneStructure().getAllMembranes())
		{
			
			long size2 = m.getMultiSet().entrySet().size();
			
			if(size2 > max2)
				max2 = size2;
			
			long size3 = getMaxMultiplicity(m.getMultiSet());
			
			if(size3 > max3)
				max3 = size3;
		}
		
		initialNumObjMembranesAccuracy = ByteOrderDataOutputStream.getAccuracy(max2);
		
		objMultInitialMultisetsAccuracy = ByteOrderDataOutputStream.getAccuracy(max3);
		
	}
	
	private void writeStringCollection(Collection<String>collection,int accuracy, boolean writeCollection) throws IOException
	{
		int n= collection.size();
		getStream().writeNumber(n,accuracy);		
		
		if(writeCollection)
		{
			for (String str:collection)
			{
				getStream().writeBytes(str);
				getStream().writeByte(0);
			}
		}

	}
	
	private void writeStringCollection(Collection<String>collection,int accuracy) throws IOException
	{
		boolean writeCollection = true;
		
		writeStringCollection(collection,accuracy,writeCollection);
	}
	
	private void writeAlphabet(boolean writeCollection) throws IOException
	{
		writeStringCollection(alphabet.keySet(),alphabetAccuracy, writeCollection);
	}

	private void writeEnvironments(boolean writeCollection) throws IOException
	{
		System.out.println("Writing environments" + "(" + environments.keySet().size() + ")");
		
		writeStringCollection(environments.keySet(),environmentsAccuracy, writeCollection);
	}
	
	private void writeMembranes(boolean writeCollection) throws IOException
	{
		System.out.println("Writing membranes" + "(" + (membranes.size() + 1)+ ")");
		
		getStream().writeNumber(membranes.size() + 1,membranesAccuracy);
			
		// first we deal with the environment
		
		// parent membrane of the environment has ID = 0
		
		getStream().writeNumber(0,membranesAccuracy);
		
		// label for the environment is @ (for instance)
		
		if(writeCollection)
		{
			String envLabel = "@";
			
			getStream().writeBytes(envLabel);	// label for the environment is the empty string
			getStream().writeByte(0);	
		}
		
		// now we deal with the membranes in the skeleton
		
		for (String m:membranes.keySet())
		{
			String parent = parentMembranes.get(m);
			
			if (membranes.containsKey(parent))
				getStream().writeNumber(membranes.get(parent), membranesAccuracy);
			else
				getStream().writeNumber(0,membranesAccuracy);
			
			if(writeCollection)
			{
				getStream().writeBytes(m);
				getStream().writeByte(0);				
			}

		}
		
	}
	
	@Override
	protected ByteOrder getByteOrder() {
		// TODO Auto-generated method stub
		//return ByteOrder.LITTLE_ENDIAN;
		return ByteOrder.BIG_ENDIAN;
	}

	private void writeBlockSizes() throws IOException
	{
		System.out.println("Writing skeleton blocks" + "(" + skeletonRulesBlocks.size() + ")");
		
		getStream().writeNumber(skeletonRulesBlocks.size(), skeletonRulesBlocksAccuracy);
		
		System.out.println("Writing environments blocks" + "(" + environmentRulesBlocks.size() + ")");
		
		getStream().writeNumber(environmentRulesBlocks.size(), environmentRulesBlocksAccuracy);
	}
	
	private static long getMaxMultiplicity(MultiSet<?>ms)
	{
		long max=0;
		for (Object o:ms.entrySet())
		{
			if (ms.count(o)>max)
				max=ms.count(o);
		}
		return max;
	}
	
	private void writeSkeletonBlockInformationPhaseOne() throws IOException
	{
		// first we go with the information byte b = b1 b2 b3 b4 b5 b5 b7 b8
		
		// b1 b2 = precision of multiplicity in LHS/RHS
		// b3    = precision of number of objects in LHS
		// b4    = precision of number of objects in RHS
		// b5 b6 = precision of number of rules in the block
		// b7    = probability for each environment (set to zero)
		// b8    = show parent membrane (set to zero)
		
		skeletonRulesBlocksAccuracyMap = new LinkedHashMap<SkeletonRulesBlock,List<Integer>>();
		
		for (SkeletonRulesBlock block:skeletonRulesBlocks.keySet())
		{
			
			long ms1 = 0, ms2 = 0, maxb1b2 = 0, maxb4 = 0, maxAux = 0;
			
			long nObjLHS = 0;
			int nObjLHSAccuracy = 0;
			
			int infoByte = 0;
			
			ms1=getMaxMultiplicity(block.getSkeletonLeftHandRule().getMainMultiSet());
			ms2=getMaxMultiplicity(block.getSkeletonLeftHandRule().getParentMultiSet());
			maxb1b2 = ms1>ms2?ms1:ms2;
			
			ms1 = block.getSkeletonLeftHandRule().getMainMultiSet().entrySet().size();
			ms2 = block.getSkeletonLeftHandRule().getParentMultiSet().entrySet().size();
			
			// nObjLHS = ms1>ms2?ms1:ms2;
			
			nObjLHS = ms1 + ms2;  // actually is the sum of the different objects present in (U) plus (V)
			
			nObjLHSAccuracy = ByteOrderDataOutputStream.getAccuracy(nObjLHS);
			
			
			for(SkeletonRightHandRule r : block.getRightHandRules())
			{
				ms1=getMaxMultiplicity(r.getMainMultiSet());
				ms2=getMaxMultiplicity(r.getParentMultiSet());
				maxAux = ms1>ms2?ms1:ms2;
				
				if(maxAux > maxb1b2)
					maxb1b2 = maxAux;
				
				ms1=r.getMainMultiSet().entrySet().size();
				ms2=r.getParentMultiSet().entrySet().size();
				maxb4 = ms1>ms2?ms1:ms2;
			}
			
			int nRules = block.getRightHandRules().size();
			int nRulesAccuracy = ByteOrderDataOutputStream.getAccuracy(nRules);
						
			int multiplicityLHSRHSAccuracy = ByteOrderDataOutputStream.getAccuracy(maxb1b2);
						
			int nObjRHSAccuracy = ByteOrderDataOutputStream.getAccuracy(maxb4);

			infoByte = multiplicityLHSRHSAccuracy;
			
			infoByte = infoByte<<1;
			infoByte = infoByte | nObjLHSAccuracy;
			
			infoByte = infoByte<<1;
			infoByte = infoByte | nObjRHSAccuracy;
			
			infoByte = infoByte<<2;
			infoByte = infoByte | nRulesAccuracy;
			
			// probability bit (assumed to zero)
			infoByte = infoByte<<1;
			infoByte = infoByte | 0x00;

			// showParentMembraneFlag
			infoByte = infoByte<<1;
			// if(showParentMembrane)
				//infoByte = infoByte | 0x01;
			
			// let's write the infoByte
						
			getStream().writeByte(infoByte);
			
			// let's write the number of rules inside the block
			
			getStream().writeNumber(nRules, nRulesAccuracy);
			
			// let's write the number of objects in LHS
			
			getStream().writeNumber(nObjLHS, nObjLHSAccuracy);
			
			// let's write the active membrane
			
			String activeMembraneLabel = block.getSkeletonLeftHandRule().getMainMembraneLabel();
			
			int activeMembrane = membranes.get(activeMembraneLabel); 
			
			getStream().writeNumber(activeMembrane, membranesAccuracy);
			
			/*	
			// let's write the parent membrane
			
			if(showParentMembrane)
			{
				String parentMembraneLabel = parentMembranes.get(activeMembraneLabel);
				
				int parentMembrane = membranes.get(parentMembraneLabel);
				
				getStream().writeNumber(parentMembrane, membranesAccuracy);
			}
			*/
			
			// let's write the charges
						
			byte lCharge = block.getSkeletonLeftHandRule().getMainMembraneCharge();
			
			if(lCharge == -1)
				lCharge = 2;
			
			byte rCharge = block.getrCharge();
			
			if(rCharge == -1)
				rCharge = 2;
			
			int chargeByte = 0;
			
			chargeByte = chargeByte | lCharge;
			
			chargeByte = chargeByte<<2;
			
			chargeByte = chargeByte | rCharge;
			
			// and the last 4 bits are reserved (set to zero)
			
			chargeByte = chargeByte<<4;
			
			// finally we write the charge byte
			
			getStream().writeByte(chargeByte);
			
			// now we save the info in the map
			
			List<Integer> list = new ArrayList<Integer>();
			
			list.add(multiplicityLHSRHSAccuracy);
			list.add(nObjLHSAccuracy);
			list.add(nObjRHSAccuracy);
			list.add(nRulesAccuracy);
			
			skeletonRulesBlocksAccuracyMap.put(block, list);
			
			
		}

	}
	
	private void writeSkeletonBlockInformationPhaseTwo() throws IOException
	{
		
		int probability;
		int probMultiplier = 10000;
		int probabilityAccuracy = ByteOrderDataOutputStream.getAccuracy(probMultiplier);
		
		for (SkeletonRulesBlock block:skeletonRulesBlocks.keySet())
		{	
			List<Integer> list = skeletonRulesBlocksAccuracyMap.get(block);
			
			int multiplicityLHSRHSAccuracy = list.get(0);
			int nObjLHSAccuracy = list.get(1);
			int nObjRHSAccuracy = list.get(2);
			// int nRulesAccuracy = list.get(3);
			
			int ms1, ms2, max;
			
			for(SkeletonRightHandRule r : block.getRightHandRules())
			{
				ms1 = r.getMainMultiSet().entrySet().size();
				ms2 = r.getParentMultiSet().entrySet().size();
				max = ms1>ms2?ms1:ms2;
				
				getStream().writeNumber(max, nObjRHSAccuracy);
				
				for(String environment : environments.keySet())
				{
					probability = new Double(r.getProbability(environment) * probMultiplier).intValue();
					
					getStream().writeNumber(probability, probabilityAccuracy);
				}
				
			}
			
			MultiSet<String> multiSet = null; 
			
			multiSet = block.getSkeletonLeftHandRule().getParentMultiSet(); // U
						
			getStream().writeNumber(multiSet.entrySet().size(), nObjLHSAccuracy);
			
			for(String obj : multiSet.entrySet())
			{
				int objId = alphabet.get(obj);
				
				getStream().writeNumber(objId, alphabetAccuracy);
				
				long multiplicity = multiSet.count(obj);
				
				getStream().writeNumber(multiplicity, multiplicityLHSRHSAccuracy);
			}
				
			
			multiSet = block.getSkeletonLeftHandRule().getMainMultiSet(); // V
			
			getStream().writeNumber(multiSet.entrySet().size(), nObjLHSAccuracy);
			
			for(String obj : multiSet.entrySet())
			{
				int objId = alphabet.get(obj);
				
				getStream().writeNumber(objId, alphabetAccuracy);
				
				long multiplicity = multiSet.count(obj);
				
				getStream().writeNumber(multiplicity, multiplicityLHSRHSAccuracy);
			}
				
		}
		
	}
	
	private void writeSkeletonBlockInformationPhaseThree() throws IOException
	{
		
		for (SkeletonRulesBlock block:skeletonRulesBlocks.keySet())
		{
			List<Integer> list = skeletonRulesBlocksAccuracyMap.get(block);
			
			int multiplicityLHSRHSAccuracy = list.get(0);
			// int nObjLHSAccuracy = list.get(1);
			int nObjRHSAccuracy = list.get(2);
			// int nRulesAccuracy = list.get(3);
						
			for(SkeletonRightHandRule r : block.getRightHandRules())
			{
				MultiSet<String> multiSet = null; 
				
				multiSet = r.getParentMultiSet(); // U'
							
				getStream().writeNumber(multiSet.entrySet().size(), nObjRHSAccuracy);
				
				for(String obj : multiSet.entrySet())
				{
					int objId = alphabet.get(obj);
					
					getStream().writeNumber(objId, alphabetAccuracy);
					
					long multiplicity = multiSet.count(obj);
					
					getStream().writeNumber(multiplicity, multiplicityLHSRHSAccuracy);
				}
					
				multiSet = r.getMainMultiSet(); // V'
				
				getStream().writeNumber(multiSet.entrySet().size(), nObjRHSAccuracy);
				
				for(String obj : multiSet.entrySet())
				{
					int objId = alphabet.get(obj);
					
					getStream().writeNumber(objId, alphabetAccuracy);
					
					long multiplicity = multiSet.count(obj);
					
					getStream().writeNumber(multiplicity, multiplicityLHSRHSAccuracy);
				}
			}
							
		}
		
	}
	
	private void writeEnvironmentBlockInformationPhaseOne() throws IOException
	{
		// first we go with the information byte b = b1 b2 b3 b4 b5 b5 b7 b8
		
		// b1 b2 = precision of multiplicity in LHS
		// b3    = precision of number of objects in LHS
		// b4    = precision of number of objects in RHS
		// b5 b6 = precision of number of rules in the block
		// b7    = probability for each environment (set to zero)
		// b8    = show parent membrane (set to zero)
		
		environmentRulesBlocksAccuracyMap = new LinkedHashMap<EnvironmentRulesBlock,List<Integer>>();
		
		for (EnvironmentRulesBlock block:environmentRulesBlocks.keySet())
		{
			
			long maxb1b2 = 0, maxb4 = 0;
			
			long nObjLHS = 0;
			int nObjLHSAccuracy = 0;
			
			int infoByte = 0;
			
			// LHS has only one element (with multiplicity 1)
						
			maxb1b2 = 1;
			nObjLHS = 1;
			nObjLHSAccuracy = ByteOrderDataOutputStream.getAccuracy(nObjLHS);
			
			// lets go with the RHS
			// In this implementation we assume that RHS = x1(e1) ... xn(en) with x1 ... xn single objects and e1 ... en the environemnts
			
			for (EnvironmentRightHandRule r : block.getEnvironmentRightHandRules())
			{
				
				/*
				int subTotal = 0;
				
				for(MultiSet<String> multiSet : r.getNewObjects().values())
				{
					int different = multiSet.entrySet().size();
					subTotal += different;
				}
				
				if(subTotal > maxb4)
					maxb4 = subTotal;
					
				*/
				
				int different = r.getNewObjects().size();
				
				if(different > maxb4)
					maxb4 = different;
					
			}
			
			int nRules = block.getRightHandRules().size();
			int nRulesAccuracy = ByteOrderDataOutputStream.getAccuracy(nRules);

			int multiplicityLHSAccuracy = ByteOrderDataOutputStream.getAccuracy(maxb1b2);
			int nObjRHSAccuracy = ByteOrderDataOutputStream.getAccuracy(maxb4);
			
			infoByte = multiplicityLHSAccuracy;
			
			infoByte = infoByte<<1;
			infoByte = infoByte | nObjLHSAccuracy;
			
			infoByte = infoByte<<1;
			infoByte = infoByte | nObjRHSAccuracy;
			
			infoByte = infoByte<<2;
			infoByte = infoByte | nRulesAccuracy;
			
			// probability bit (assumed to zero)
			// infoByte = infoByte | 0x00; // we don't need to do this
			infoByte = infoByte<<1;
			infoByte = infoByte | 0x00;
			// we should do something here!
			
			// showParentMembraneFlag
			infoByte = infoByte<<1;
			// if(showParentMembrane)
				//infoByte = infoByte | 0x01;
			
			// let's write the infoByte
			
			getStream().writeByte(infoByte);
			
			// let's write the number of rules inside the block
			
			getStream().writeNumber(nRules, nRulesAccuracy);
			
			// let's write the environment
			
			String environmentLabel = block.getMainLabel();
			
			int environmentId = environments.get(environmentLabel); 
			
			getStream().writeNumber(environmentId, environmentsAccuracy);
			
			/*	
			// let's write the parent membrane
			
			if(showParentMembrane)
			{
				String parentMembraneLabel = parentMembranes.get(activeMembraneLabel);
				
				int parentMembrane = membranes.get(parentMembraneLabel);
				
				getStream().writeNumber(parentMembrane, membranesAccuracy);
			}
			*/
			
			List<Integer> list = new ArrayList<Integer>();
			
			list.add(multiplicityLHSAccuracy);
			list.add(nObjLHSAccuracy);
			list.add(nObjRHSAccuracy);
			list.add(nRulesAccuracy);
			
			environmentRulesBlocksAccuracyMap.put(block, list);
						
		}

	}
	
	private void writeEnvironmentBlockInformationPhaseTwo() throws IOException
	{
		int probability;
		int probMultiplier = 10000;
		int probabilityAccuracy = ByteOrderDataOutputStream.getAccuracy(probMultiplier);
		
		for (EnvironmentRulesBlock block:environmentRulesBlocks.keySet())
		{
			List<Integer> list = environmentRulesBlocksAccuracyMap.get(block);
			
			// int multiplicityLHSAccuracy = list.get(0);
			//int nObjLHSAccuracy = list.get(1);
			int nObjRHSAccuracy = list.get(2);
			// int nRulesAccuracy = list.get(3);
			
			String obj = block.getEnvironmentLeftHandRule().getObject();
			int objId = alphabet.get(obj);
			
			getStream().writeNumber(objId, alphabetAccuracy);
			
			String environment = block.getEnvironmentLeftHandRule().getEnvironment();
			
			for(EnvironmentRightHandRule r : block.getEnvironmentRightHandRules())
			{			
				/*
				
				long size = 0;
				
				for(MultiSet<String> multiSet : r.getNewObjects().values())
					size = size + multiSet.longSize();
					
				*/
				
				/*
				
				Set<String> different = new HashSet<String>();
				
				for(MultiSet<String> multiSet : r.getNewObjects().values())
					different.addAll(multiSet.entrySet());
				
				long size = different.size();		
				getStream().writeNumber(size, nObjRHSAccuracy);
						
				probability = new Float(r.getProbability(environment) * probMultiplier).intValue();
				getStream().writeNumber(probability, probabilityAccuracy);
				
				*/
				
				// In this implementation we assume that RHS = x1(e1) ... xn(en) with x1 ... xn single objects and e1 ... en the environemnts
				
				long size = r.getNewObjects().size();		
				getStream().writeNumber(size, nObjRHSAccuracy);
						
				probability = new Float(r.getProbability(environment) * probMultiplier).intValue();
				getStream().writeNumber(probability, probabilityAccuracy);
				
			}
			
		}
	}
	
	private void writeEnvironmentBlockInformationPhaseThree() throws IOException
	{
		for (EnvironmentRulesBlock block:environmentRulesBlocks.keySet())
		{	
			for(EnvironmentRightHandRule r : block.getEnvironmentRightHandRules())
			{
				for(String environment : r.getNewObjects().keySet())
				{
					for(String obj : r.getNewObjects().get(environment).entrySet())
					{
						getStream().writeNumber(alphabet.get(obj), alphabetAccuracy);
						getStream().writeNumber(environments.get(environment), environmentsAccuracy);
					}
					
				}
			}
		}
	}
	
	private void writeInitialConfiguration() throws IOException
	{	
		
		Configuration conf = simulator.getCurrentConfig();
		CellLikeSkinMembrane ms = (CellLikeSkinMembrane) conf.getMembraneStructure();
		
		Map<String, Integer> membranesIndexes = StaticMethods.getMembraneIdsByLabelAndEnvironment(ms);
		
		for(String environment : environments.keySet())
		{
			// firstly we process the Environment
			
			writeSingleMembrane(environment,environment,ms,membranesIndexes);
			
			// secondly we process the Membranes
			
			for(String label : membranes.keySet())
			{
				writeSingleMembrane(label,environment,ms,membranesIndexes);
			}
			
		}
	}
	
	private void writeSingleMembrane(String label, String environment, CellLikeSkinMembrane ms, Map<String, Integer> membranesIndexes) throws IOException
	{
		Membrane m = StaticMethods.getMembrane(label, environment, ms, membranesIndexes);
		
		int charge = m.getCharge();
		
		if(charge == -1)
			charge = 2;
		
		// charge goes into the 2 more significant bits, so we have to move it 6 bits
		
		charge = charge<<6;
		
		int different = m.getMultiSet().entrySet().size();
		
		// write charge
		
		getStream().writeByte(charge);
		
		// write different
		
		getStream().writeNumber(different, initialNumObjMembranesAccuracy);
		
		for(String obj : m.getMultiSet().entrySet())
		{
			int objId = alphabet.get(obj);
			long multiplicity = m.getMultiSet().count(obj);
			
			getStream().writeNumber(objId, alphabetAccuracy);
			getStream().writeNumber(multiplicity, objMultInitialMultisetsAccuracy);
			
		}
	}
	
	private void setSimulator() throws IOException
	{
		try {
			simulator = new DCBAProbabilisticSimulator(getPsystem());
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			throw new IOException(e);
		}
	}
	
	@Override
	protected void writeFile() throws IOException {
		// TODO Auto-generated method stub
		setSimulator();
		setAlphabet();
		setEnvironments();
		setMembranes();
		setBlocks();

		writeSubHeader();
		writeAlphabet(true);
		writeEnvironments(false);
		writeMembranes(false);
		writeBlockSizes();
		
		writeSkeletonBlockInformationPhaseOne();
		writeEnvironmentBlockInformationPhaseOne();
		
		writeSkeletonBlockInformationPhaseTwo();
		writeEnvironmentBlockInformationPhaseTwo();
		
		writeSkeletonBlockInformationPhaseThree();
		writeEnvironmentBlockInformationPhaseThree();
		
		writeInitialConfiguration();
		
	}

	@Override
	protected byte getFileId() {
		// TODO Auto-generated method stub
		return 0x21;
	}

}
