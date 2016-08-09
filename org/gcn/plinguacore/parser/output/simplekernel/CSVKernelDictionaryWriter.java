package org.gcn.plinguacore.parser.output.simplekernel;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import com.opencsv.CSVWriter;

public class CSVKernelDictionaryWriter extends KernelDictionaryWriter {
	CSVWriter csvwriter;
	public CSVKernelDictionaryWriter(KernelDictionary dictionary) {
		super(dictionary);
		
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean writeDictionary() {
		// TODO Auto-generated method stub
		try {
			csvwriter= new CSVWriter(new FileWriter(dictionaryRoute));
			writeDictionary(null);
			csvwriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Errors occurred while writing dictionary on "+dictionaryRoute+"\n");
			return false;
		}
		return true;
	}
	@Override
	protected void writeObjects(OutputStreamWriter writer) throws IOException {
		csvwriter.writeNext(new String[]{"objects", ""+dictionary.numberOfObjects()});
		for(String object : dictionary.objects())
			csvwriter.writeNext(new String[]{object, ""+dictionary.getObjectID(object)});
	}
	
	@Override
	protected void writeMembranes(OutputStreamWriter writer) throws IOException {
		csvwriter.writeNext(new String[]{"membranes", ""+dictionary.numberOfObjects()});
		for(String label : dictionary.membranes())
			csvwriter.writeNext(new String[]{label, ""+dictionary.getMembraneID(label)});
	}
	
	protected void writeDictionary(OutputStreamWriter writer) throws IOException {
		writeMembranes(writer);
		writeObjects(writer);
		
		
	}

}
