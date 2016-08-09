package org.gcn.plinguacore.parser.output.simplekernel;
import java.util.Set;

import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;


public class KernelObjectWriter {
	
	SimpleKernelLikePsystem psystem;
	KernelMapper mapper;

	public KernelObjectWriter(SimpleKernelLikePsystem psystem, KernelMapper mapper) {
		super();
		this.psystem = psystem;
		this.mapper = mapper;
	}

	public void writeSet(Set<String> set, StringBuffer psystemDescription){
		writeSetContent(set, psystemDescription);
	}
	
	private void writeSetContent(Set<String> set, StringBuffer psystemDescription) {
		psystemDescription.append(" ");
		for(String element: set)
			psystemDescription.append(mapper.getObjectID(element)+" ");
		
	}

	public void writeObjectSequence(MultiSet<String> multiSet, StringBuffer psystemDescription) {		
		psystemDescription.append(" [ ");
		writeObjects(multiSet, psystemDescription);
		psystemDescription.append(" ] ");
		
	}

	private void writeObjects(MultiSet<String> multiSet, StringBuffer psystemDescription) {
		if(multiSet==null) return;
		for (String object : multiSet.entrySet()) {
			psystemDescription.append(" ");
			writeObject(multiSet, object, psystemDescription);
			psystemDescription.append(" ");
		}		
	}
	
	public void writeObject( String object, StringBuffer psystemDescription){
		psystemDescription.append(""+mapper.getObjectID(object));
	}

	private void writeObject(MultiSet<String> multiSet, String object, StringBuffer psystemDescription) {
		psystemDescription.append("(");
		psystemDescription.append(""+mapper.getObjectID(object));
		psystemDescription.append(" ");
		psystemDescription.append(""+multiSet.count(object));
		psystemDescription.append(")");
		
	}
	

}
