package org.gcn.plinguacore.parser.output.probabilisticGuarded.xml;

import java.util.Set;

import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedAuxiliaryWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.jdom.Element;

public class ProbabilisticGuardedXMLMembranesWriter {

	ProbabilisticGuardedPsystem psystem;
	private Element membranes;
	private KernelMapper mapper;
	private ProbabilisticGuardedXMLObjectsWriter objectsWriter;
	public ProbabilisticGuardedXMLMembranesWriter(
			ProbabilisticGuardedPsystem psystem, KernelMapper mapper) {
		this.psystem = psystem;
		this.mapper =mapper;
		this.objectsWriter = new ProbabilisticGuardedXMLObjectsWriter();
		// TODO Auto-generated constructor stub
	}
	public void addMembranesElement(Element e) {
		membranes = new Element("Membranes");
		for(Membrane membrane : psystem.getMembraneStructure().getAllMembranes())
			addMembrane(membrane, psystem.getInitialMultiSets().get(membrane.getLabel()), psystem.getFlags());
		e.addContent(membranes);
		// TODO Auto-generated method stub
		
	}
	private void addMembrane(Membrane membrane,
			MultiSet<String> multiSet, Set<String> flags) {
		Element membraneElement = new Element("M");
		addMembraneHeader(membrane, multiSet, flags, membraneElement);
		addMembraneContent(multiSet, membraneElement);
		membranes.addContent(membraneElement);
		// TODO Auto-generated method stub
		
	}
	protected void addMembraneContent(MultiSet<String> multiSet,
			Element membraneElement) {
		if(multiSet!=null){
			MultiSet<String> unflaggedMultiSet=ProbabilisticGuardedAuxiliaryWriter.removeFlag(multiSet, psystem.getFlags());
			writeObjects(unflaggedMultiSet, membraneElement);
		}
	}
	protected void addMembraneHeader(Membrane membrane,
			MultiSet<String> multiSet, Set<String> flags,
			Element membraneElement) {
		membraneElement.setAttribute("L", ""+mapper.getMembraneID(membrane.getLabel()));
		membraneElement.setAttribute("R", ""+ProbabilisticGuardedAuxiliaryWriter.getNumberOfRules(membrane, psystem) );
		membraneElement.setAttribute("F", ""+getFlag(ProbabilisticGuardedAuxiliaryWriter.getFlag(multiSet, flags)));
	}
	
	private void writeObjects(MultiSet<String> unflaggedMultiSet, Element membraneElement) {
		objectsWriter.writeObjects(unflaggedMultiSet, membraneElement, mapper);
		
	}
	String getFlag(String flag){
		if(flag==null||flag.equals("#"))
			return "#";
		else
			return ""+mapper.getObjectID(flag);
	}

}
