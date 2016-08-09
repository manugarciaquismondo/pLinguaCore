package org.gcn.plinguacore.parser.input.simplekernel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.gcn.plinguacore.parser.input.InputParser;
import org.gcn.plinguacore.parser.input.VerbosityConstants;
import org.gcn.plinguacore.parser.input.messages.InputParserMsgFactory;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;

public class KernelInputParser extends InputParser {

	
	@Override
	protected Psystem specificParse(InputStream stream, String[] fileRoutes)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return checkFileRouteAndGetPsystem(fileRoutes);
	}

	@Override
	protected Psystem specificParse(StringReader reader, String[] fileRoutes)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return checkFileRouteAndGetPsystem(fileRoutes);
	}

	private Psystem checkFileRouteAndGetPsystem(String[] fileRoutes) throws PlinguaCoreException {
		checkFileRoute(fileRoutes);
		writeMsg(InputParserMsgFactory.createInfoMessage("Reading P-system",
				VerbosityConstants.GENERAL_INFO));
		
		Psystem psystem = getPsystem(fileRoutes[0]); 
		writeMsg(InputParserMsgFactory.createInfoMessage("P-system read",
				VerbosityConstants.GENERAL_INFO));
		return psystem;
		
	}

	private Psystem getPsystem(String file){

	    try{ 
	    	   CharStream antlrStream = new ANTLRFileStream(file);
	    	   return readPsystem(antlrStream);
	    }catch (FileNotFoundException fnfe){ 
	    	writeMsg(InputParserMsgFactory.createInternalErrorMsg("File for simple kernel p systems "+file+" not found"));
	    	fnfe.printStackTrace();
	    } catch (RecognitionException e){
	    	writeMsg(InputParserMsgFactory.createInternalErrorMsg("File "+file+" does not match simple kernel binary format"));
	    	e.printStackTrace();
	    } catch(IOException e) {
	    
	    	writeMsg(InputParserMsgFactory.createInternalErrorMsg("A generic IO error occurred when reading simple kernel file "+file)); 
	    		e.printStackTrace();
	    }
	    return null;	    
	    
	}

	protected Psystem readPsystem(CharStream antlrStream)
			throws RecognitionException {
		Kernel_Simulator_Lexer lexer = new Kernel_Simulator_Lexer(antlrStream); 
		   CommonTokenStream tokens = new CommonTokenStream(lexer);
		   return parsePsystemWithANTLR(tokens);
	}

	protected Psystem parsePsystemWithANTLR(CommonTokenStream tokens)
			throws RecognitionException {
		Kernel_Simulator_Parser parser = new Kernel_Simulator_Parser(tokens); 
		   return parser.psystem().psystem;
	}
	
	private void checkFileRoute(String[] fileRoutes) throws PlinguaCoreException {
		if (fileRoutes.length != 1)
			throw new PlinguaCoreException(
					"In Kernel input parse files, fileRoutes array should have one and only one file route");

	}
	

}
