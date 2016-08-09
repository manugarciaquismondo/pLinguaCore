package org.gcn.plinguacore.simulator.scripts;

import java.util.HashMap;
import java.util.Map;

public class PsystemScriptParameters {
	
	Map<String, String> parametersMap;
	public PsystemScriptParameters() {
		super();
		this.parametersMap =new HashMap<String, String>();
		// TODO Auto-generated constructor stub
	}
	public int getIntParameter(String string) {
		// TODO Auto-generated method stub
		return Integer.parseInt(getStringParameter(string));
	}
	
	public float getFloatParameter(String string) {
		// TODO Auto-generated method stub
		return Float.parseFloat(getStringParameter(string));
	}
	
	public String getStringParameter(String string){
		return parametersMap.get(string);
	}
	public void setParameter(String name, String value) {
		this.parametersMap.put(name, value);
		
	}

}
