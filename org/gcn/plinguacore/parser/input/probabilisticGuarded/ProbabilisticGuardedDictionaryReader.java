package org.gcn.plinguacore.parser.input.probabilisticGuarded;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import org.gcn.plinguacore.parser.output.simplekernel.KernelDictionary;
import org.gcn.plinguacore.parser.input.simplekernel.KernelDictionaryReader;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.Triple;

public class ProbabilisticGuardedDictionaryReader extends
		KernelDictionaryReader {
	

	
	
	private ConfigurationsCSVAdaptor adaptor;
	Map<Pair<Integer, Integer>, List<Triple<Integer, Integer, Integer>>> simulations;
	
	protected final String PROBABILISTIC_GUARDED="ProbabilisticGuarded";
	
	@Override
	protected String getFormat(){
		return PROBABILISTIC_GUARDED;
	}
	
	private Map<Pair<Integer, Integer>, List<Triple<String, String, Integer>>> replaceNames(KernelDictionary dictionary) throws Exception {
		Map<Pair<Integer, Integer>, List<Triple<String, String, Integer>>> replacedSimulations = new HashMap<Pair<Integer, Integer>, List<Triple<String, String, Integer>>>();
		for(Pair<Integer, Integer> objectData: simulations.keySet()){
			List<Triple<String, String, Integer>> replacedSimulationList = new LinkedList<Triple<String, String, Integer>>();
			for(Triple<Integer,Integer, Integer> objectTriple: simulations.get(objectData)){
				String membraneLabel=dictionary.getMembrane(objectTriple.getFirst());
				String objectName=dictionary.getObject(objectTriple.getSecond());
				int cardinality=objectTriple.getThird();
				Triple<String, String, Integer> replacedTriple= 
								new Triple<String, String, Integer>(
										membraneLabel, objectName, cardinality);
			
				replacedSimulationList.add(replacedTriple);
			}
			replacedSimulations.put(objectData, replacedSimulationList);
		}
		return replacedSimulations;
			
		
	}
	
	public void readSimulations(String dictionaryRoute, String simulationsRoute, String destinationRoute) throws Exception{
		adaptor = new ConfigurationsCSVAdaptor();
		KernelDictionary dictionary = readDictionaryAndTreatExceptions(dictionaryRoute);
		simulations=adaptor.readParametersAndReportExceptions(simulationsRoute);
		adaptor.writeResults(destinationRoute, replaceNames(dictionary));
	}
	


}
