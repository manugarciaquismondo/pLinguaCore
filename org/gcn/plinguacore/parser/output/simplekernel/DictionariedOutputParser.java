package org.gcn.plinguacore.parser.output.simplekernel;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Writer;

import org.gcn.plinguacore.parser.input.InputParser;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;
import org.gcn.plinguacore.parser.output.OutputParser;


public abstract class DictionariedOutputParser extends OutputParser {


	
	protected String dictionaryRoute;
	protected KernelMapper mapper;
	
	public DictionariedOutputParser() {
		super();
		
		setDictionaryRoute("dictionary.txt");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean parse(Psystem psystem, OutputStream stream) {
		mapper = new KernelMapper((SimpleKernelLikePsystem)psystem);
		return false;
	}

	@Override
	public boolean parse(Psystem psystem, Writer stream) {
		mapper = new KernelMapper((SimpleKernelLikePsystem)psystem);
		return false;
	}

	public void setDictionaryRoute(String dictionaryRoute){
		this.dictionaryRoute=dictionaryRoute;
	}
	
	protected boolean writeDictionary() {
		// TODO Auto-generated method stub
		KernelDictionaryWriter dictionaryWriter = new KernelDictionaryWriter(getMapper().getDictionary());
		dictionaryWriter.setRoute(dictionaryRoute);
		if(!dictionaryWriter.writeDictionary()) return false;
		String csvDictionaryRoute=dictionaryRoute.split("\\.", -1)[0]+".csv";
		KernelDictionaryWriter csvDictionaryWriter= new CSVKernelDictionaryWriter(getMapper().getDictionary());
		csvDictionaryWriter.setRoute(csvDictionaryRoute);
		return csvDictionaryWriter.writeDictionary();
		
	}
	
	public KernelMapper getMapper() {
		// TODO Auto-generated method stub
		return mapper;
	}

}
