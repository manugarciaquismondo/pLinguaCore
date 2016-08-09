package org.gcn.plinguacore.parser.output.XML.enps;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Vector;

import org.gcn.plinguacore.parser.output.OutputParser;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.enps.ENPSHolder;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import edu.psys.core.Membrane;
import edu.psys.core.enps.Constant;
import edu.psys.core.enps.ENPSMembraneSystem;
import edu.psys.core.enps.ENPSMemory;
import edu.psys.core.enps.ENPSRule;
import edu.psys.core.enps.ENPSVariable;
import edu.psys.core.enps.Operation;
import edu.psys.core.enps.RepartitionVariable;
import edu.psys.core.enps.TreeElement;
import edu.psys.core.enps.Variable;

public class XMLNumericalOutputParser extends OutputParser {

	protected Document d;
	protected Element e;
	ENPSMembraneSystem membraneSystem;
	@Override
	public boolean parse(Psystem psystem, OutputStream stream) {
		// TODO Auto-generated method stub
		membraneSystem = ((ENPSHolder)psystem).getENPSMembraneSystem();
		boolean parsed = parseOutput(stream);
		try{
			stream.close();
		}
		catch (Exception e) {
			System.err.println("Errors found while parsing the input");
		}
		return parsed;
	}

	@Override
	public boolean parse(Psystem psystem, Writer stream) {
		// TODO Auto-generated method stub
		membraneSystem = ((ENPSHolder)psystem).getENPSMembraneSystem();
		boolean parsed = parseOutput(stream);
		try{
			stream.close();
		}
		catch (Exception e) {
			System.err.println("Errors found while parsing the input");
		}
		return parsed;
	}
	
	private boolean parseOutput(Object ob) {
		/* We can write P-systems on Write or OutputStream instances */
		if (membraneSystem == null)
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

			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Document generateDocument() {

		configureRootElement();
		d.setRootElement(e);
		return d;
	}

	protected void configureRootElement() {
		addInitialContents();
		/* Then, we add the initial configuration */
		addSkinMembraneElement();
	}

	protected void addSkinMembraneElement() {
		e.addContent(generateMembraneElement(membraneSystem.getSkinMembrane()));
	}

	protected void addInitialContents() {
		d = new Document();
		e = new Element("membraneSystem");
		e.setNamespace(Namespace.getNamespace("http://www.example.org"));
		e.setAttribute(new Attribute("type", "ENPS"));
		Comment c = new Comment(
				"File generated with P-Lingua Compiler 2.1 beta\n"
						+ "See http://www.gcn.us.es for more information.");
		/* First, we add the comments */
		d.addContent(c);
	}


	private Element generateMembraneElement(Membrane<ENPSMemory, ENPSRule> membrane) {
		// TODO Auto-generated method stub
		Element membraneElement = new Element("membrane");
		membraneElement.setAttribute("name", membrane.getName());
		membraneElement.addContent(generateRegionElement(membrane));
		membraneElement.addContent(generateChildrenElement(membrane.getChildren()));
		return membraneElement;
	}

	private Element generateChildrenElement(
			Vector<Membrane<ENPSMemory, ENPSRule>> children) {
		// TODO Auto-generated method stub
		Element childrenElement = new Element("children");
		for (Membrane<ENPSMemory, ENPSRule> membrane : children) {
			childrenElement.addContent(generateMembraneElement(membrane));
		}
		return childrenElement;
	}

	private Element generateRegionElement(Membrane<ENPSMemory, ENPSRule> membrane) {
		// TODO Auto-generated method stub
		Element regionElement = new Element("region");
		Element memoryElement = generateMemoryElement(membrane.getMemory());
		Element rulesElement = generateRulesElement(membrane.getRuleList());
		regionElement.addContent(memoryElement);
		regionElement.addContent(rulesElement);
		return regionElement;
	}

	private Element generateRulesElement(Vector<ENPSRule> ruleList) {
		Element rulesElement = new Element("rulesList");
		for (ENPSRule enpsRule : ruleList) {
			rulesElement.addContent(generateRuleElement(enpsRule));
			
		}
		// TODO Auto-generated method stub
		return rulesElement;
	}

	private Element generateRuleElement(ENPSRule enpsRule) {
		// TODO Auto-generated method stub
		Element ruleElement = new Element("rule");
		Element repartitionProtocolElement = generateRepartitionProtocolElement(enpsRule.getRepartitionProtocol());
		Element productionFunctionElement = generateProductionFunctionElement(enpsRule.getProductionFunction());
		ruleElement.addContent(repartitionProtocolElement);
		ruleElement.addContent(productionFunctionElement);
		return ruleElement;
	}

	private Element generateProductionFunctionElement(
			TreeElement productionFunction) {
		Element repartitionProtocolElement = new Element("productionFunction");
		Element mathElement = new Element("math");
		mathElement.setNamespace(Namespace.getNamespace("http://www.w3.org/1998/Math/MathML"));
		mathElement.addContent(generateProductionFunctionOperationsContent(productionFunction));
		repartitionProtocolElement.addContent(mathElement);
		// TODO Auto-generated method stub
		return repartitionProtocolElement;
	}

	private Element generateProductionFunctionOperationsContent(
			TreeElement productionFunction) {
		// TODO Auto-generated method stub
		if(productionFunction instanceof Constant)
			return generateConstantElement((Constant) productionFunction);
		if(productionFunction instanceof Variable)
			return generateVariableElement((Variable) productionFunction);
		if(productionFunction instanceof Operation)
			return generateOperationElement((Operation) productionFunction);
		throw new IllegalArgumentException("The production function input is not recognized");
	}

	private Element generateOperationElement(Operation productionFunction) {
		// TODO Auto-generated method stub
		Element variableElement = new Element("apply");
		Element operationElement = new Element(getOperationName(productionFunction.getOperation()));
		variableElement.addContent(operationElement);
		Vector<TreeElement> children = productionFunction.getChildren();
		for (TreeElement treeElement : children) {
			variableElement.addContent(generateProductionFunctionOperationsContent(treeElement));
		}
		
		return variableElement;
	}

	private String getOperationName(int operation) {
		// TODO Auto-generated method stub
		switch(operation){
		case(Operation.ADD):
			return "add";
		case(Operation.SUB):
			return "minus";
		case(Operation.MULT):
			return "times";
		case(Operation.DIV):
			return "div";
		case(Operation.POW):
			return "pow";
		default:
			return "";
		}
	}

	private Element generateVariableElement(Variable productionFunction) {
		// TODO Auto-generated method stub
		Element variableElement = new Element("ci");
		variableElement.addContent(productionFunction.getVariableName());
		return variableElement;
	}

	private Element generateConstantElement(Constant productionFunction) {
		// TODO Auto-generated method stub
		Element constantElement = new Element("cn");
		constantElement.addContent(productionFunction.evaluate()+"");
		return constantElement;
	}

	private Element generateRepartitionProtocolElement(
			Vector<RepartitionVariable> repartitionProtocol) {
		Element repartitionProtocolElement = new Element("repartitionProtocol");
		for (RepartitionVariable repartitionVariable : repartitionProtocol) {
			repartitionProtocolElement.addContent(generateRepartitionVariableElement(repartitionVariable));
		}
		return repartitionProtocolElement;
	}

	private Element generateRepartitionVariableElement(
			RepartitionVariable repartitionVariable) {
		Element repartitionVariableElement = new Element("repartitionVariable");
		repartitionVariableElement.setAttribute("contribution", repartitionVariable.getContribution()+"");
		repartitionVariableElement.addContent(repartitionVariable.getRepartitionVariable().getName());
		return repartitionVariableElement;
	}

	private Element generateMemoryElement(ENPSMemory memory) {
		Element memoryElement = new Element("memory");
		addVariables(memoryElement, memory);
		return memoryElement;
	}

	private void addVariables(Element memoryElement, ENPSMemory memory) {
		Iterator<String> variableNamesIterator = memory.getVariableNames();		
		while(variableNamesIterator.hasNext()){
			String variableName = variableNamesIterator.next();
			memoryElement.addContent(generateVariableContent(memory.getVariable(variableName), variableName));
		}
	}

	private Element generateVariableContent(ENPSVariable variable, String name) {
		Element variableElement = new Element("variable");
		variableElement.setAttribute("initialValue", variable.getValue()+"");
		setElementAttributes(name, variableElement);
		variableElement.addContent(name);
		// TODO Auto-generated method stub
		return variableElement;
	}

	private void setElementAttributes(String name, Element variableElement) {
		if(membraneSystem.getInputs().existVariable(name))
			variableElement.setAttribute("input", "true");
		if(membraneSystem.getOutputs().existVariable(name))
			variableElement.setAttribute("output", "true");
		if(membraneSystem.hasStopEnzyme()&&membraneSystem.getStopEnzymeName().equals(name))
			variableElement.setAttribute("stop", "true");
	}


}
