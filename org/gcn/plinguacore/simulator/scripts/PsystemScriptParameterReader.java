package org.gcn.plinguacore.simulator.scripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.gcn.plinguacore.simulator.probabilisticGuarded.scripts.ButterflyEmigrationPsystemScriptBase;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class PsystemScriptParameterReader {
	
	protected SAXBuilder builder;
	public PsystemScriptParameterReader() {
		super();
		builder= new SAXBuilder();
		// TODO Auto-generated constructor stub
	}
	public boolean readParameters(String route) throws PlinguaCoreException{
		
		InputStream stream;
		try {
			stream = new FileInputStream(route);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("Errors found while reading "+classPurpose()+" from file ["+route+"]");
		}
		return readParameters(stream, route);

		
		
	}
	
	public boolean readParameters(InputStream stream, String route) throws PlinguaCoreException{
		
		boolean parsed = false;
		try{
			parsed=parseParameters(stream);
			stream.close();
		}		
		catch (IOException e) {
			throw new PlinguaCoreException("Errors found while reading "+classPurpose()+" from file ["+route+"]");
		}
		catch(JDOMException e){
			throw new PlinguaCoreException("Errors found while building the XML file of "+classPurpose()+" from file ["+route+"]");
		}
		return parsed;
		
		
	}



	protected String classPurpose() {
		// TODO Auto-generated method stub
		return "script parameters";
	}
	protected boolean parseParameters(InputStream stream) throws JDOMException, IOException {
		/* We can write P-systems on Write or OutputStream instances */

		Document document=builder.build(stream);
		PsystemScriptParameters parameters= readParameters(document);
		ButterflyEmigrationPsystemScriptBase.setParameters(parameters);
		return true;
	
	}
	protected PsystemScriptParameters readParameters(Document document) {
		PsystemScriptParameters parameters = new PsystemScriptParameters();
		for(Object object:document.getRootElement().getChildren("parameter")){
			Element element=(Element) object;
			readElement(element.getAttribute("name").getValue(), element.getAttribute("value").getValue(), parameters);
		}
		return parameters;
	}
protected void readElement(String name, String value, PsystemScriptParameters parameters) {
	parameters.setParameter(name, value);
		
	// TODO Auto-generated method stub
	
}
}
