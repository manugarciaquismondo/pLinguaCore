package org.gcn.plinguacore.parser.output.XML.enps;

import org.gcn.gpusimpadaptor.xmlgenerator.ENPSParameterCalculator;
import org.gcn.gpusimpadaptor.xmlgenerator.ENPSParameters;
import org.jdom.Document;
import org.jdom.Element;

public class ParameterizedXMLNumericalOutputParser extends
		XMLNumericalOutputParser {
	protected final String PARAMETER_NAMES= "parameters";
	protected Element parametersElement;
	protected ENPSParameters parameters;
	
	@Override
	public void addInitialContents() {

		super.addInitialContents();
		addParametersElement();
		
	}

	protected void addParametersElement() {
		
		parametersElement = new Element(PARAMETER_NAMES);	
		ENPSParameterCalculator parameterCalculator = new ENPSParameterCalculator();
		parameters = parameterCalculator.calculateParameters(membraneSystem);
		setParameters();
		e.addContent(parametersElement);
		// TODO Auto-generated method stub
		
	}

	protected void setParameters() {
		setNumberOfPrograms();
		setNumberOfVariables();
		setMaxRepartitionProtocolSize();
		setMaxProductionFunctionSize();


	}
	
	private void setNumberOfVariables() {
		parametersElement.setAttribute("variables", ""+parameters.numberOfVariables);
		
	}

	
	

	private void setNumberOfPrograms() {
		parametersElement.setAttribute("programs", ""+parameters.numberOfPrograms);
		
	}



	private void setMaxRepartitionProtocolSize() {
		parametersElement.setAttribute("maxRPSize", ""+parameters.maxRepartitionProtocolSize);
		// TODO Auto-generated method stub
		
	}

	private void setMaxProductionFunctionSize() {
		parametersElement.setAttribute("maxPFSize", ""+parameters.maxProductionFunctionSize);
		
	}

}
