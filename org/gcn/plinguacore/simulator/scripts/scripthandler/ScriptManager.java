package org.gcn.plinguacore.simulator.scripts.scripthandler;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.gcn.plinguacore.simulator.AbstractSelectionExecutionSimulator;
import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguacore.simulator.probabilisticGuarded.scripts.ProbabilisticGuardedPsystemScriptProvider;
import org.gcn.plinguacore.simulator.scripts.PsystemScript;
import org.gcn.plinguacore.simulator.scripts.PsystemScriptProvider;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;


public abstract class ScriptManager {

	protected final String SCRIPT="script";
	protected Map<Pair<String,Integer>, String> appliedScripts;
	protected AbstractSelectionExecutionSimulator simulator;
	public ScriptManager(Psystem psystem,AbstractSelectionExecutionSimulator simulator) {
		if (simulator == null) {
			throw new IllegalArgumentException("Argument of type "
					+ ISimulator.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		this.simulator = simulator;
		appliedScripts = new HashMap<Pair<String,Integer>, String>();
		// TODO Auto-generated constructor stub
	}
	
	protected void microStepExecuteRules(){
		simulator.microStepExecuteRules();
		executeScripts();

	}

	public void executeScripts(){
		PsystemScriptProvider provider=createScriptProvider();
		if(provider!=null){
			registerScripts(provider);
			if(!appliedScripts.isEmpty()){
				printScripts();
				applyScripts(provider);
			}
		}
	}

	private PsystemScriptProvider createScriptProvider()
	{
		try {
			return getScriptProvider();
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			getInfoChannel().append(e.getMessage());
		}
		return null;
	}



	public abstract PsystemScriptProvider getScriptProvider() throws PlinguaCoreException;

	protected void registerScripts(PsystemScriptProvider provider) {
		appliedScripts.clear();
		for(Membrane membrane: getCurrentConfig().getMembraneStructure().getAllMembranes()){
			for(String object: membrane.getMultiSet().entrySet()){
				if(simulator.getPsystem().hasProperty(object, SCRIPT)&&provider.triggersScript(object)){
					appliedScripts.put(new Pair<String,Integer>(membrane.getLabel(), membrane.getId()), object);			
				}
			}
		}
		for(Entry<Pair<String,Integer>,String> entry: appliedScripts.entrySet()){
			MultiSet<String> multiSet=getCurrentConfig().getMembraneStructure().getMembrane(entry.getKey().getSecond()).getMultiSet();
			String object=entry.getValue();
			multiSet.remove(object, multiSet.count(object));
			
		}
		
	}

	protected void applyScripts(PsystemScriptProvider provider) {
		for(Entry<Pair<String,Integer>,String> entry: appliedScripts.entrySet()){
			applyScript(provider, entry.getValue(), entry.getKey().getFirst(), entry.getKey().getSecond());
		}
	}

	protected void applyScript(PsystemScriptProvider provider, String object, String label, int id) {
		try{
			PsystemScript script=provider.getScript(object);
			Configuration nextConfiguration = script.getNextConfiguration(getCurrentConfig(), id);
			if(nextConfiguration!=null)
				setCurrentConfig(nextConfiguration);
			
		}
		catch(PlinguaCoreException e){
			this.getInfoChannel().println("Script triggered by object \""+object+"\" to be applied on membrane \""+label+"\" does not exist");
		}
	}

	private void setCurrentConfig(Configuration nextConfiguration) {
		simulator.setCurrentConfig(nextConfiguration);
		
	}

	protected void printScripts() {
		getInfoChannel().println("-----------------------------------------------");
		getInfoChannel().println("The scripts ["+printScriptObjects()+"] are going to be applied on the following configuration:");
		getInfoChannel().println("-----------------------------------------------");
		getInfoChannel().println("");
		printCurrentMembraneStructureShort();
	}

	private void printCurrentMembraneStructureShort() {
		simulator.printCurrentMembraneStructureShort();
		
	}

	private PrintStream getInfoChannel() {
		// TODO Auto-generated method stub
		return simulator.getInfoChannel();
	}

	private Configuration getCurrentConfig() {
		// TODO Auto-generated method stub
		return simulator.getCurrentConfig();
	}

	private String printScriptObjects() {
		// TODO Auto-generated method stub
		String scriptObjects="";		
		
		for(Entry<Pair<String,Integer>,String> entry: appliedScripts.entrySet()){
			String scriptName="";
			String objectName=entry.getValue();
			if((scriptName=getScript(objectName))!=null&&!scriptName.equals("")){
				scriptObjects+="\n(Script \""+scriptName+"\" triggered by +\""+objectName+"\" in cell with Label \""+entry.getKey().getFirst()+"\" and ID \""+entry.getKey().getSecond()+"\")";
				scriptObjects+=",";
			}
		}
		
		return scriptObjects.substring(0, scriptObjects.length()-1)+"\n";
	}

	private String getScript(String object) {
		// TODO Auto-generated method stub
		PsystemScriptProvider provider = createScriptProvider();
		if(provider==null) return "";
		return provider.getScriptName(object);
	}

}
