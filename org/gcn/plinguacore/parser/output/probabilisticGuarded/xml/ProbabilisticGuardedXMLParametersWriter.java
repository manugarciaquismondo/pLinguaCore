package org.gcn.plinguacore.parser.output.probabilisticGuarded.xml;


import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedAuxiliaryWriter;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.jdom.Element;

public class ProbabilisticGuardedXMLParametersWriter {
	
	protected final String PARAMETER_NAMES= "Parameters";
	protected Element parametersElement;
	ProbabilisticGuardedPsystem psystem;
	
	public void addParametersElement(Element e, ProbabilisticGuardedPsystem psystem) {
		this.psystem=psystem;
		parametersElement = new Element(PARAMETER_NAMES);	
		setParameters();
		e.addContent(parametersElement);
		// TODO Auto-generated method stub
		
	}

	protected void setParameters() {
		setNumberOfMembranes();
		setNumberOfRules();
		setMaxRulesPerMembrane();
		setAlphabetSize();
		setFlagsSize();
		setNumberOfBlocks();


	}
	


	
	



	private void setNumberOfMembranes() {
		parametersElement.setAttribute("membranes", ""+psystem.getMembraneStructure().getAllMembranes().size());
		
	}

	private void setNumberOfRules() {
		parametersElement.setAttribute("rules", ""+psystem.getRules().size());
		
	}

	private void setMaxRulesPerMembrane() {
		parametersElement.setAttribute("maximum_number_of_rules_per_membrane", ""+ProbabilisticGuardedAuxiliaryWriter.getMaximumNumberOfRulesPerMembrane(psystem));
		
	}


	private void setAlphabetSize() {
		parametersElement.setAttribute("alphabet_size", ""+psystem.getAlphabet().size());
		// TODO Auto-generated method stub
		
	}

	private void setFlagsSize() {
		parametersElement.setAttribute("flags_size", ""+psystem.getFlags().size());
		
	}
	
	private void setNumberOfBlocks(){
		parametersElement.setAttribute("number_of_blocks", ""+psystem.getBlockTable().size());
	}
	

	
}
