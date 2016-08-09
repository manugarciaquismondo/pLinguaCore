package org.gcn.plinguacore.simulator.regenerative.scripts;

import java.io.PrintStream;

import org.gcn.plinguacore.simulator.scripts.PsystemScriptProvider;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;

public class RegenerativePsystemScriptProvider extends PsystemScriptProvider {

	protected static CheckAndCompareMembraneStructureScript membraneStructureComparatorScript;
	protected static boolean isEquivalentStructure;
	protected static RegenerativeMembraneStructure previousMembraneStructure;
	private static String scriptNames[]={
		"checkAndCompareStructure"
	};
	
	protected RegenerativePsystemScriptProvider(PrintStream infoChannel) throws PlinguaCoreException {
		super(infoChannel);
		isEquivalentStructure=false;
	}

	@Override
	protected String[] getScriptNames() {
		// TODO Auto-generated method stub
		return scriptNames;
	}

	@Override
	protected void associateScriptsAndNames() throws PlinguaCoreException {
		membraneStructureComparatorScript=new CheckAndCompareMembraneStructureScript();
		scripts.put(reader.getObject(scriptNames[0]), membraneStructureComparatorScript);
	}
	
	public static PsystemScriptProvider getInstance(PrintStream infoChannel) throws PlinguaCoreException{
		if(provider==null||! (provider instanceof RegenerativePsystemScriptProvider))
			provider= new RegenerativePsystemScriptProvider(infoChannel);
		return provider;
	}

	@Override
	protected String getScriptsFileName() {
		// TODO Auto-generated method stub
		return "regenerative.xml";
	}
	
	public static boolean isEquivalentStructure(){
		return isEquivalentStructure;
	}
	
	protected static void setIfIsEquivalentStructure(boolean isEquivalentStructure){
		RegenerativePsystemScriptProvider.isEquivalentStructure=isEquivalentStructure;
	}

	public static void clearPreviousMembraneStructure() {
		previousMembraneStructure=null;
		isEquivalentStructure=false;
		
	}
	
	public static void updatePreviousMembraneStructure(RegenerativeMembraneStructure previousMembraneStructure){
		if (previousMembraneStructure == null) {
			throw new IllegalArgumentException("Argument of type "
					+ RegenerativeMembraneStructure.class
					+ " cannot be null when creating an object of type "
					+ RegenerativeMembraneStructure.class);
		}
		RegenerativePsystemScriptProvider.previousMembraneStructure = previousMembraneStructure;
	}
	
	public static boolean isPreviousMembraneStructureNull(){
		return previousMembraneStructure==null;
	}
	public static boolean isEquivalentToPreviousMembraneStructure(RegenerativeMembraneStructure membraneStructure){
		if(previousMembraneStructure==null)
			return false;
		return membraneStructure.isEquivalent(RegenerativePsystemScriptProvider.previousMembraneStructure);
	}
}
