package org.gcn.plinguacore.simulator.scripts;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

public class PsystemScriptReader extends PsystemScriptParameterReader {
	protected Map<String,String> scriptAssociations;
	
	
	protected boolean parseParameters(InputStream stream) throws JDOMException, IOException {
		/* We can write P-systems on Write or OutputStream instances */

		Document document=builder.build(stream);
		readAssociations(document);
		return true;
	
	}
	
	public String getObject(String scriptID){
		if(!scriptAssociations.containsKey(scriptID))
			return "";
		return scriptAssociations.get(scriptID);
	}
	
	public Set<String> getObjects(){
		return scriptAssociations.keySet();
	}


	
	protected void readAssociations(Document document) {
		for(Object object:document.getRootElement().getChildren("script")){
			Element element=(Element) object;
			scriptAssociations.put(element.getAttribute("id").getValue(), element.getAttribute("object").getValue());
		}
	}

	@Override
	protected String classPurpose() {
		// TODO Auto-generated method stub
		return "script associations";
	}

	public PsystemScriptReader() {
		super();
		scriptAssociations = new HashMap<String, String>();
		// TODO Auto-generated constructor stub
	}

}
