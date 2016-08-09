package org.gcn.plinguacore.parser.input.XML.enps;

import java.io.InputStream;
import java.io.StringReader;

import org.gcn.plinguacore.parser.input.InputParser;
import org.gcn.plinguacore.parser.input.VerbosityConstants;
import org.gcn.plinguacore.parser.input.messages.InputParserMsgFactory;
import org.gcn.plinguacore.simulator.enps.ENPSFactory;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.enps.ENPSHolder;

import edu.psys.core.MembraneXMLParser;
import edu.psys.core.enps.ENPSMembrane;
import edu.psys.core.enps.ENPSMembraneSystem;
import edu.psys.core.enps.ENPSMemory;
import edu.psys.core.enps.ENPSRule;
import edu.psys.core.enps.ENPSXMLParser;

public class XMLNumericalInputParser extends InputParser {

	@Override
	protected Psystem specificParse(InputStream stream, String[] fileRoutes)
			throws PlinguaCoreException {
		return parseENPS(fileRoutes);
	}

	@Override
	protected Psystem specificParse(StringReader reader, String[] fileRoutes)
			throws PlinguaCoreException {

		// TODO Auto-generated method stub
		return parseENPS(fileRoutes);
	}
	
	protected Psystem parseENPS(String[] fileRoutes) throws PlinguaCoreException{
		checkFileRoute(fileRoutes);
		MembraneXMLParser<ENPSMemory,
        ENPSRule,
        ENPSMembrane,
        ENPSMembraneSystem> ENPSparser = new ENPSXMLParser(fileRoutes[0]);
		writeMsg(InputParserMsgFactory.createInfoMessage("Reading Numerical P-system",
				VerbosityConstants.GENERAL_INFO));
		ENPSMembraneSystem membraneSystem = ENPSparser.parseMembraneSystem();
		writeMsg(InputParserMsgFactory.createInfoMessage("Numerical P-system read",
				VerbosityConstants.GENERAL_INFO));
		Psystem psystemHolder = new ENPSHolder(membraneSystem);
		
		return psystemHolder;
	}

	private void checkFileRoute(String[] fileRoute) throws PlinguaCoreException {
		if (fileRoute.length != 1)
			throw new PlinguaCoreException(
					"in XML input parse files, fileRoute array should have one and only one file route");

	}
}
