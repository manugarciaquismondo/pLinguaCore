package org.gcn.plinguacore.parser.output.simplekernel;
import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedAuxiliaryWriter;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;


public class KernelHeaderWriter {
	
	
	
	private final static String MEMBRANES ="membranes";
	private final static String MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE = "maximum_number_of_rules_per_membrane";
	private final static String ALPHABET_SIZE = "alphabet_size";
	private final static String RULES = "rules";
	
	protected SimpleKernelLikePsystem psystem;

	public KernelHeaderWriter(SimpleKernelLikePsystem psystem) {
		super();
		this.psystem = psystem;
	}
	
	private void addMembranes(StringBuffer psystemDescription) {
		writeField(psystemDescription, MEMBRANES, getMembranes());
		
	}
	
	
	public void addHeader(StringBuffer psystemDescription) {
		// TODO Auto-generated method stub
		addMembranes(psystemDescription);
		addRules(psystemDescription);
		addMaximumNumberOfRulesPerMembrane(psystemDescription);
		addAlphabetSize(psystemDescription);
		
	}
	
	protected void writeField(StringBuffer psystemDescription, String field, int value){
		psystemDescription.append(field);
		psystemDescription.append(":");
		psystemDescription.append(""+value+";\n");
	}

	private void addRules(StringBuffer psystemDescription) {
		writeField(psystemDescription, RULES, getNumberOfRules());
		
	}

	private int getNumberOfRules() {
		// TODO Auto-generated method stub
		return psystem.getRules().size();
	}

	private void addAlphabetSize(StringBuffer psystemDescription) {
		writeField(psystemDescription, ALPHABET_SIZE, getAlphabetSize());
		
	}

	private int getAlphabetSize() {
		// TODO Auto-generated method stub
		return psystem.getAlphabet().size();
	}

	private void addMaximumNumberOfRulesPerMembrane(
			StringBuffer psystemDescription) {
		writeField(psystemDescription, MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE, getMaximumNumberOfRulesPerMembrane());
		
	}

	private int getMaximumNumberOfRulesPerMembrane() {
		// TODO Auto-generated method stub
		return ProbabilisticGuardedAuxiliaryWriter.getMaximumNumberOfRulesPerMembrane(psystem);
	}



	private int getMembranes() {
		// TODO Auto-generated method stub
		return psystem.getMembraneStructure().getAllMembranes().size();
	}
	
	int getNumberOfRules(Membrane membrane){
		return ProbabilisticGuardedAuxiliaryWriter.getNumberOfRules(membrane, psystem);
	}
}
