package org.gcn.plinguacore.parser.input.plingua;

import java.util.LinkedList;
import java.util.List;

import org.gcn.plinguacore.parser.input.messages.InputParserMsg;
import org.gcn.plinguacore.parser.input.messages.InputParserMsgFactory;
import org.gcn.plinguacore.parser.input.messages.MsgInterval;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembraneFactory;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;

public class EnvironmentAdder {

	
	protected static ChangeableMembrane lookForMembrane(MembraneStructure structure, Label label) throws PlinguaSemanticsException{
		for(Membrane membrane : structure.getAllMembranes()){
			//CellLikeMembrane cellLikeMembrane = (CellLikeMembrane)membrane;
			if(membrane.getLabelObj().equals(label))
				return (ChangeableMembrane)membrane;
		}
		
		InputParserMsg error = InputParserMsgFactory.createSemanticsErrorMessage("Membrane "+label.toString()+" not found");
		throw new PlinguaSemanticsException(error);
		
		
	}
}
