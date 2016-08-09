parser grammar Computation_Parser;

options {
  language = Java;
  output = AST;
  tokenVocab = Kernel_Simulator_Lexer;
}


@header{

package org.gcn.plinguacore.parser.input.simplekernel;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembraneFactory;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembrane;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneFactory;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import java.util.LinkedList;
}


computation returns [List<SimpleKernelLikeMembraneStructure> computation]: 
header 
configurations_result=configurations
{retval.computation=configurations_result.computation;};

header: 
steps_line
membranes_line 
rules_line
maximum_number_of_rules_per_membrane_line 
alphabet_size_line
;

steps_line:
STEPS COLON INTEGER SEMICOLON;

membranes_line: 
MEMBRANES COLON INTEGER SEMICOLON;


rules_line:
RULES COLON INTEGER SEMICOLON;


maximum_number_of_rules_per_membrane_line: 
  MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON;

alphabet_size_line: 
  ALPHABET_SIZE COLON INTEGER SEMICOLON ;

configurations returns [List<SimpleKernelLikeMembraneStructure> computation]:
{retval.computation = new LinkedList<SimpleKernelLikeMembraneStructure>();}
 (configuration_result=configuration
 {retval.computation.add(configuration_result.configuration);})*;

configuration returns [SimpleKernelLikeMembraneStructure configuration]:  
configuration_line 
initial_configuration_result=initial_configuration
{retval.configuration=initial_configuration_result.configuration;}
;

configuration_line: CONFIGURATION COLON INTEGER SEMICOLON;

initial_configuration returns [SimpleKernelLikeMembraneStructure configuration]:  
  membrane_structure_result=membrane_structure_content
  {retval.configuration= membrane_structure_result.configuration;}
   SEMICOLON;

membrane_structure_content returns [SimpleKernelLikeMembraneStructure configuration]: 
{retval.configuration = new SimpleKernelLikeMembraneStructure((CellLikeSkinMembrane)CellLikeMembraneFactory.getCellLikeMembrane(new Label("0")));}
  (membrane_content[retval.configuration])*;

membrane_content [SimpleKernelLikeMembraneStructure membraneStructure]:  

  membrane_index=numerical_ident
  numerical_ident 
  {
  String labelString = membrane_index.identifier;
  Label label = new Label(labelString);
  Membrane membrane;
  MultiSet<String> multiSet;
  if(labelString.equals("0")){
    multiSet = new HashMultiSet<String>();
  }
  else{
    membrane = SimpleKernelLikeMembraneFactory.getKernelLikeMembrane(labelString, membraneStructure);
    membraneStructure.add((SimpleKernelLikeMembrane)membrane);
    multiSet = membrane.getMultiSet();
  }
  }
  objects_list[multiSet] SEMICOLON;

cardinalities[MultiSet<String> multiset]: 
(cardinality[multiset])*;

cardinality[MultiSet<String> multiset]: 
LEFT_BRACKET 
object_token=INTEGER 
cardinality_token=INTEGER 
RIGHT_BRACKET
 {multiset.add($object_token.text, Integer.parseInt($cardinality_token.text));}
;

numerical_ident returns [String identifier]: 
membrane_index_token=INTEGER{retval.identifier=$membrane_index_token.text;}  
COLON;




objects_list [MultiSet<String> multiset]: 
LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET;