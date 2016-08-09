package org.gcn.plinguacore.parser.output.simplekernel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.gcn.plinguacore.parser.output.simplekernel.KernelDictionary;

class KernelDictionaryWriter {
	
	protected KernelDictionary dictionary;
	protected String dictionaryRoute;

	public KernelDictionaryWriter(KernelDictionary dictionary) {
		super();
		this.dictionary = dictionary;
	}

	public void setRoute(String dictionaryRoute) {
		this.dictionaryRoute = dictionaryRoute;
		
	}

	public boolean writeDictionary() {
		// TODO Auto-generated method stub
		try {
			FileOutputStream stream = new FileOutputStream(dictionaryRoute);
			OutputStreamWriter writer = new OutputStreamWriter(stream);
			writeDictionary(writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Errors occurred while writing dictionary on "+dictionaryRoute+"\n");
		}
		return true;
	}

	protected void writeDictionary(OutputStreamWriter writer) throws IOException {
		writeMembranes(writer);
		writer.append("\n\n");
		writeObjects(writer);
		
		
	}

	protected void writeObjects(OutputStreamWriter writer) throws IOException {
		writer.append("objects: "+dictionary.numberOfObjects()+";\n\n");
		for(String object : dictionary.objects())
			writer.append(object+" = "+dictionary.getObjectID(object)+";\n");
	}

	protected void writeMembranes(OutputStreamWriter writer) throws IOException {
		writer.append("membranes: "+dictionary.numberOfMembranes()+";\n\n");
		for(String label : dictionary.membranes())
			writer.append(label+" = "+dictionary.getMembraneID(label)+";\n");
	}
	
	
	
	

}
