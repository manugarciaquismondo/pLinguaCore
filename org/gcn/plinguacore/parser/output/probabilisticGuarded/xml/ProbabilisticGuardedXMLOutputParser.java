package org.gcn.plinguacore.parser.output.probabilisticGuarded.xml;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Vector;

import org.gcn.gpusimpadaptor.xmlgenerator.ENPSParameterCalculator;
import org.gcn.plinguacore.parser.output.OutputParser;
import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedRuleWriter;
import org.gcn.plinguacore.parser.output.simplekernel.DictionariedOutputParser;
import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.enps.ENPSHolder;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class ProbabilisticGuardedXMLOutputParser extends DictionariedOutputParser {

	protected Document d;
	protected Element e;
	private ProbabilisticGuardedPsystem psystem;
	private ProbabilisticGuardedXMLParametersWriter parametersWriter;
	private ProbabilisticGuardedXMLMembranesWriter membranesWriter;
	private ProbabilisticGuardedXMLRulesWriter rulesWriter;
	private boolean dictionaryWritten;

	@Override
	public boolean parse(Psystem psystem, OutputStream stream) {
		super.parse(psystem, stream);
		if(psystem instanceof ProbabilisticGuardedPsystem){
			this.psystem=(ProbabilisticGuardedPsystem) psystem;
			boolean parsed = parseOutput(stream);
			try{
				stream.close();
			}
			catch (Exception e) {
				System.err.println("Errors found while parsing the input");
			}
			return parsed;
		}
		else return false;
		
	}
	
	

	@Override
	public boolean parse(Psystem psystem, Writer stream) {
		super.parse(psystem, stream);
		if(psystem instanceof ProbabilisticGuardedPsystem){
			this.psystem=(ProbabilisticGuardedPsystem) psystem;
			boolean parsed = parseOutput(stream);
			try{
				stream.close();
			}
			catch (Exception e) {
				System.err.println("Errors found while parsing the input");
			}
			return parsed;
		}
		else return false;
	}
	
	private boolean parseOutput(Object ob) {
		dictionaryWritten=false;
		/* We can write P-systems on Write or OutputStream instances */
		if (psystem == null)
			return false;
		XMLOutputter x = new XMLOutputter(Format.getPrettyFormat());
		try {
			if (ob instanceof Writer)
				x.output(generateDocument(), (Writer) ob);
			else {
				if (ob instanceof OutputStream)
					x.output(generateDocument(), new PrintWriter(
							(OutputStream) ob));
				else
					throw new IllegalArgumentException(
							"Object argument should be an instance of OutputStream or Writer");
			}

			return dictionaryWritten;
		} catch (Exception e) {
			return false;
		}

	}

	public Document generateDocument() {
		mapper = new KernelMapper(this.psystem);
		mapper.createMappings();
		configureRootElement();
		addMembranes();
		addRules();
		dictionaryWritten=writeDictionary();
		d.setRootElement(e);
		return d;
	}

	private void addRules() {
		rulesWriter = new ProbabilisticGuardedXMLRulesWriter(psystem, mapper);
		rulesWriter.addRulesAndBlocksElements(e);
		
	}






	private void addMembranes() {
		membranesWriter=new ProbabilisticGuardedXMLMembranesWriter(psystem, mapper);
		membranesWriter.addMembranesElement(e);
		
	}



	protected void configureRootElement() {
		addInitialContents();
		addParametersElement();
	}

protected void addParametersElement() {
		
	parametersWriter=new ProbabilisticGuardedXMLParametersWriter();
	parametersWriter.addParametersElement(e, psystem);
		// TODO Auto-generated method stub
		
	}

	

	protected void addInitialContents() {
		d = new Document();
		e = new Element("Psystem");
		e.setNamespace(Namespace.getNamespace("http://www.example.org"));
		e.setAttribute(new Attribute("type", "ProbabilisticGuarded"));
		Comment c = new Comment(
				"File generated with P-Lingua Compiler 2.1 beta\n"
						+ "See http://www.gcn.us.es for more information.");
		/* First, we add the comments */
		d.addContent(c);
	}


	

}
