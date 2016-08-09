package org.gcn.plinguacore.parser.input.probabilisticGuarded;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.Triple;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class ConfigurationsCSVAdaptor {

	private final int SIMULATION_POSITION=0;
	private final int CONFIGURATION_POSITION=1;
	private final int MEMBRANE_POSITION=2;
	private final int OBJECT_POSITION=3;
	private final int CARDINALITY_POSITION=4;
	private final int STARTING_ROW=1;
	
	Map<Pair<Integer, Integer>, List<Triple<Integer, Integer, Integer>>> simulations;
	
	public Map<Pair<Integer, Integer>, List<Triple<Integer, Integer, Integer>>> readParametersAndReportExceptions(String route) throws PlinguaCoreException{
		try {
			readSimulations(route);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("File ["+route+"] not found\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("Errors occurred while parsing parameters from file ["+route+"]\n");
		}
		return simulations;
	}
	
	protected void readSimulations(String route) throws FileNotFoundException, IOException {
		simulations = new HashMap<Pair<Integer, Integer>, List<Triple<Integer, Integer, Integer>>>();
		CSVReader csvReader = new CSVReader(new FileReader(route), ',', '\"', STARTING_ROW);
		String[] row = null;
		while((row = csvReader.readNext()) != null) {
		    readSimulationElement(row);
		}
		csvReader.close();
	}

	private void readSimulationElement(String[] row) {
		int simulation=Integer.parseInt(row[SIMULATION_POSITION]);
		int configuration =Integer.parseInt(row[CONFIGURATION_POSITION]);
		int membrane = Integer.parseInt(row[MEMBRANE_POSITION]);
		int objectName=Integer.parseInt(row[OBJECT_POSITION]);
		int cardinality=Integer.parseInt(row[CARDINALITY_POSITION]);
		Pair<Integer,Integer> integerKeyPair=new Pair<Integer, Integer>(simulation, configuration);
		List<Triple<Integer, Integer, Integer>> configurationInfo=new LinkedList<Triple<Integer, Integer, Integer>>();
		if(simulations.containsKey(integerKeyPair))
			configurationInfo=simulations.get(integerKeyPair);
		Triple<Integer, Integer, Integer> objectInfo=new Triple<Integer, Integer, Integer>(membrane, objectName, cardinality);
		configurationInfo.add(objectInfo);
		simulations.put(integerKeyPair, configurationInfo);
		
	}

	
	public void writeResults(String destinationRoute, Map<Pair<Integer, Integer>, List<Triple<String, String, Integer>>> writtenSimulation) throws PlinguaCoreException {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(destinationRoute));
			writeHeader(writer);
			writeSimulations(writer, writtenSimulation);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("Errors ocurred while saving computation into file ["+destinationRoute+"];");
		}

	}
	
	private void writeSimulations(CSVWriter writer, Map<Pair<Integer, Integer>, List<Triple<String, String, Integer>>> writtenSimulation) {
		for(Pair<Integer, Integer> simulationConfigurationPair: simulations.keySet()){
			List<Triple<String, String, Integer>> simulationInformation= writtenSimulation.get(simulationConfigurationPair);
			int simulation=simulationConfigurationPair.getFirst();
			int configuration = simulationConfigurationPair.getSecond();
			writeSimulation(simulation, configuration, simulationInformation, writer);
			
	
			
		}
		
	}
	
	private void writeSimulation(int simulation, int configuration,
			List<Triple<String, String, Integer>> simulationInformation, CSVWriter writer) {
		for(Triple<String, String, Integer> objectInformation: simulationInformation){
			String membraneLabel=objectInformation.getFirst();
			String objectName=objectInformation.getSecond();
			int cardinality=objectInformation.getThird();
			writeTuple(writer, simulation, configuration, membraneLabel, objectName, cardinality);
		}
		
	}

	private void writeTuple(CSVWriter writer, int simulation, int configuration, String membraneLabel, String objectName, int cardinality){
		String simulationString=simulation+"";
		String configurationString=configuration+"";
		String cardinalityString=cardinality+"";
		String[] objectTuple=new String[]{
				simulationString, configurationString, 
				membraneLabel, objectName, cardinalityString};
		writer.writeNext(objectTuple);
	}

	private void writeHeader(CSVWriter writer) {
		writer.writeNext(new String[]{"Simulation", "Configuration", "Membrane", "Object", "Multiplicity"});
		
	}
	
	
}
