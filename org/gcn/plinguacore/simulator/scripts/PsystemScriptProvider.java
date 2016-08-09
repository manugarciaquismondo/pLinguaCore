package org.gcn.plinguacore.simulator.scripts;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.gcn.plinguacore.util.PlinguaCoreException;

public abstract class PsystemScriptProvider {
	
	protected static PsystemScriptProvider provider;
	protected Map<String, PsystemScript> scripts;
	private Map<String, String> scriptNameMap;
	protected PsystemScriptReader reader;
	protected final static String scriptsRoute = "org/gcn/plinguacore/resources/scripts/";
	
	
	
	protected abstract String[] getScriptNames();
		
	protected PsystemScriptProvider(PrintStream infoChannel) throws PlinguaCoreException{
		reader=new PsystemScriptReader();
		InputStream localRoute = this.getClass().getResourceAsStream("/" + scriptsRoute+getScriptsFileName());
		reader.readParameters(localRoute, scriptsRoute);
		initializeScripts(infoChannel);

	}

	
	protected abstract String getScriptsFileName();

	protected void initializeScripts(PrintStream infoChannel) {
		scripts = new HashMap<String, PsystemScript>();		
		setScriptNames();
		try {
			associateScriptsAndNames();
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			infoChannel.append(e.getMessage());
		}
		
	}


	protected abstract void associateScriptsAndNames() throws PlinguaCoreException ;

	protected void setScriptNames() {
		String scriptNames[]= getScriptNames();
		scriptNameMap = new HashMap<String, String>();
		for (int i = 0; i < scriptNames.length; i++) {
			scriptNameMap.put(reader.getObject(scriptNames[i]), scriptNames[i]);			
		}
		// TODO Auto-generated method stub
		
	}
	
	public String getScriptName(String object){
		if(!scriptNameMap.containsKey(object))
			return "";
		return scriptNameMap.get(object);
	}


	
	
	public boolean triggersScript(String id){
		return scripts.containsKey(id.toLowerCase());
	}
	
	public PsystemScript getScript(String id) throws PlinguaCoreException{
		if(this.triggersScript(id))
			return scripts.get(id);
		else
			throw new PlinguaCoreException("The script ID ["+id+"] is not associated with any script");
	}
}
