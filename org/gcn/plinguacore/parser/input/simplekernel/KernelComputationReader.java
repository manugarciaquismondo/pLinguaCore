package org.gcn.plinguacore.parser.input.simplekernel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;

public class KernelComputationReader {
	
	public List<SimpleKernelLikeMembraneStructure> getComputation(String file) throws PlinguaCoreException {

	    try{ 
	    	   CharStream antlrStream = new ANTLRFileStream(file);
	    	   Kernel_Simulator_Lexer lexer = new Kernel_Simulator_Lexer(antlrStream); 
	    	   CommonTokenStream tokens = new CommonTokenStream(lexer);
	    	   Computation_Parser parser = new Computation_Parser(tokens); 
	    	   return parser.computation().computation;
	    }catch (FileNotFoundException fnfe){ 
	    	throw new PlinguaCoreException("The computation file "+file+" was not found"); 
	    	
	    } catch (RecognitionException e){
	    	throw new PlinguaCoreException("The file does not match the format for Kernel Computations");
	    	
	    } catch (IOException e) {
	    		// TODO Auto-generated catch block
	    	throw new PlinguaCoreException("A generic IO error ocurred");
	    		
	    } 
	}

}
