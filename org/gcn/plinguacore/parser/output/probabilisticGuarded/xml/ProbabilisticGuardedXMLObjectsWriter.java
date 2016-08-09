package org.gcn.plinguacore.parser.output.probabilisticGuarded.xml;

import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.util.MultiSet;
import org.jdom.Element;

public class ProbabilisticGuardedXMLObjectsWriter {
	
	public void writeObjects(MultiSet<String> multiSet, Element membraneElement, KernelMapper mapper) {
		for(String object: multiSet.entrySet()){
			Element objectElement = new Element("C");
			objectElement.setAttribute("O", ""+mapper.getObjectID(object));
			objectElement.setAttribute("N", ""+multiSet.count(object));
			membraneElement.addContent(objectElement);
			
		}
		
	}

}
