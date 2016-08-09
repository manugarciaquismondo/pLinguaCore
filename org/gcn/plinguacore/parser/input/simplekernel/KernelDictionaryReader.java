package org.gcn.plinguacore.parser.input.simplekernel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.gcn.plinguacore.parser.AbstractParserFactory;
import org.gcn.plinguacore.parser.input.InputParser;
import org.gcn.plinguacore.parser.input.InputParserFactory;
import org.gcn.plinguacore.parser.output.simplekernel.KernelDictionary;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembraneFactory;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembrane;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneFactory;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.TissueLikeForSimpleKernelMembraneStructure;

public class KernelDictionaryReader {
	
	protected final String KERNEL_BIN="KernelBin";
	
	protected String getFormat(){
		return KERNEL_BIN;
	}
	
	protected KernelDictionary readDictionaryAndTreatExceptions(String dictionaryRoute) throws PlinguaCoreException{
	    try{ 
	    	   return readDictionary(dictionaryRoute);
	    }catch (FileNotFoundException fnfe){ 
	    	throw new PlinguaCoreException("The file containing the dictionary for kernel P systems: "+dictionaryRoute+" is not found"); 
	    } catch (RecognitionException | IOException e) {
	    		// TODO Auto-generated catch block
	    	throw new PlinguaCoreException("There was an error recognizing the file containing the dictionary for kernel P systems: "+dictionaryRoute);
	    	
	    }
	}

	private KernelDictionary readDictionary(String dictionaryRoute)
			throws IOException, RecognitionException {
		CharStream antlrStream = new ANTLRFileStream(dictionaryRoute);
		   Kernel_Simulator_Lexer lexer = new Kernel_Simulator_Lexer(antlrStream); 
		   CommonTokenStream tokens = new CommonTokenStream(lexer);
		   Dictionary_Reader reader = new Dictionary_Reader(tokens); 
		   return reader.dictionary().dictionary;
	}
	
	private Psystem readPsystemAndTreatExceptions(String psystemRoute) throws PlinguaCoreException{
		
		try {
			return readPsystem(psystemRoute);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("The file containing the kernel P system: "+psystemRoute+" is not found");
		} catch(PlinguaCoreException e) {
			throw new PlinguaCoreException("There was a pLinguaCore error parsing the kernel P system: "+psystemRoute);
		}
		/* Create a new InputParser for P-Lingua format */
		
	}

	private Psystem readPsystem(String psystemRoute) throws FileNotFoundException,
			PlinguaCoreException {
		FileInputStream stream = new FileInputStream(psystemRoute);
		AbstractParserFactory inputParserFactory = new InputParserFactory();
		InputParser inputParser = (InputParser) inputParserFactory
				.createParser(KERNEL_BIN);
		inputParser.setVerbosityLevel(5);
		/* Parse the file */
		return inputParser.parse(stream,
				new String[] { psystemRoute });
		
	}
	
	public SimpleKernelLikeMembraneStructure readPsystem(String dictionaryRoute, String psystemRoute) throws Exception{
		KernelDictionary dictionary = readDictionaryAndTreatExceptions(dictionaryRoute);
		Psystem psystem  = readPsystemAndTreatExceptions(psystemRoute);
		return replaceNames(dictionary, psystem.getMembraneStructure());
		
	}

	public SimpleKernelLikeMembraneStructure replaceNames(KernelDictionary dictionary, MembraneStructure baseMembraneStructure) throws Exception {
		String generalLabel = getGeneralLabel(dictionary, baseMembraneStructure);
		SimpleKernelLikeMembraneStructure membraneStructure = new SimpleKernelLikeMembraneStructure((CellLikeSkinMembrane)CellLikeMembraneFactory.getCellLikeMembrane(new Label(generalLabel)));
		for(Membrane membrane: baseMembraneStructure.getAllMembranes()){
			String substitutedLabel = dictionary.getMembrane(Integer.parseInt(membrane.getLabel()));
			SimpleKernelLikeMembrane substitutedMembrane = SimpleKernelLikeMembraneFactory.getKernelLikeMembrane(substitutedLabel, membraneStructure);
			for(String object: membrane.getMultiSet().entrySet()){
				String substitutedObject = dictionary.getObject(Integer.parseInt(object));
				if(substitutedObject!=null)
					substitutedMembrane.getMultiSet().add(substitutedObject, membrane.getMultiSet().count(object));
			}
			if(!membrane.getLabel().equals(generalLabel))
				membraneStructure.add(substitutedMembrane);
		}
		return membraneStructure;
			
		
	}

	protected String getGeneralLabel(KernelDictionary dictionary,
			MembraneStructure baseMembraneStructure) throws Exception {
		String label="";
		try {
			label = ((TissueLikeForSimpleKernelMembraneStructure)baseMembraneStructure).getFirstMembrane().getLabel();
			return dictionary.getMembrane(Integer.parseInt(label));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("The general label of a kernel-like model must be a number, rather than "+label);
		} catch (NullPointerException e){
			throw new NullPointerException("In kernel-like models, there must exists at least one membrane in the membrane structure");
		}
		
	}
	
	public List<SimpleKernelLikeMembraneStructure> readComputation(String dictionaryRoute, String computationRoute) throws Exception{
		KernelDictionary dictionary = readDictionaryAndTreatExceptions(dictionaryRoute);
		KernelComputationReader reader = new KernelComputationReader();
		List<SimpleKernelLikeMembraneStructure> sourceComputation = reader.getComputation(computationRoute);
		List<SimpleKernelLikeMembraneStructure> destinationComputation = new LinkedList<SimpleKernelLikeMembraneStructure>();
		for (SimpleKernelLikeMembraneStructure simpleKernelLikeMembraneStructure : sourceComputation) {
			destinationComputation.add(replaceNames(dictionary, simpleKernelLikeMembraneStructure));
		}
		return destinationComputation;
	}
	

}
