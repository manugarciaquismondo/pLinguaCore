package org.gcn.plinguacore.parser.output.simplekernel;

import org.gcn.plinguacore.parser.output.simplekernel.KernelHeaderWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.parser.output.simplekernel.KernelNumberWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelObjectWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelOutputParser;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class KernelMembraneWriter {

	
	protected SimpleKernelLikePsystem  psystem;

	protected KernelHeaderWriter headerWriter;

	protected StringBuffer psystemDescription;

	protected KernelNumberWriter numberWriter;
	
	protected KernelMapper mapper;

	protected KernelObjectWriter objectWriter;
	
	
	
	public KernelMembraneWriter(SimpleKernelLikePsystem psystem, KernelOutputParser parser) {
		
		super();
		this.headerWriter = parser.headerWriter;
		this.numberWriter = new KernelNumberWriter();
		this.objectWriter = parser.objectWriter;
		this.mapper = parser.getMapper();
		numberWriter.setMapper(mapper);
		this.psystem = psystem;
		
	}




	
	protected void writeMembraneHeader(Membrane membrane) {
		String label = membrane.getLabel();
		numberWriter.writeNumber(mapper.getMembraneID(label), psystemDescription);
		numberWriter.writeNumber(headerWriter.getNumberOfRules(membrane), psystemDescription);
	}
	
	protected void addMembraneContent(Membrane membrane) {
		writeMembraneHeader(membrane);
		objectWriter.writeObjectSequence(psystem.getInitialMultiSets().get(membrane.getLabel()), psystemDescription);
		psystemDescription.append(";\n");
		
	}
	
	public void addInitialConfiguration(StringBuffer psystemDescription) {
		this.psystemDescription = psystemDescription;
		for (Membrane membrane : psystem.getMembraneStructure().getAllMembranes()) {
			addMembraneContent(membrane);
		}
		this.psystemDescription.append(";\n");
	}
}
