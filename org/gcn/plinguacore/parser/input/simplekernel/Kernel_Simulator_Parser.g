parser grammar Kernel_Simulator_Parser;

options {
  language = Java;
  output = AST;
  tokenVocab = Kernel_Simulator_Lexer;
}

@header{
package org.gcn.plinguacore.parser.input.simplekernel;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembrane;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneFactory;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.KernelRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.guard.UnitGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.ComparationMasks;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.KernelRuleTypes;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembraneFactory;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.Label;

}



@members{

  
  SimpleKernelLikePsystem psystem;
  TissueLikeMembraneStructure membraneStructure;
  UnitGuard guard; 
 
}

psystem returns [SimpleKernelLikePsystem  psystem] : 
{psystem = new SimpleKernelLikePsystem();
membraneStructure = new SimpleKernelLikeMembraneStructure((CellLikeSkinMembrane)CellLikeMembraneFactory.getCellLikeMembrane(new Label("environment")));
psystem.setMembraneStructure(membraneStructure);
}
//{retval.psystem_struct = (Kernel_Instance_psystem_struct*)malloc(sizeof(Kernel_Instance_psystem_struct));}
header

initial_configuration
rules;

header: 
membranes_line maximum_number_of_rules_per_membrane_line alphabet_size_line
;

membranes_line: 
MEMBRANES COLON INTEGER SEMICOLON;
/*maximum_size_of_hand_side_line: MAXIMUM_SIZE_OF_HAND_SIDE COLON max_size_of_hand_side=INT{parser_max_size_of_hand_side=atoi($max_size_of_hand_side);} SEMICOLON;*/

maximum_number_of_rules_per_membrane_line: 
  MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON;

alphabet_size_line: 
  ALPHABET_SIZE COLON INTEGER SEMICOLON ;

initial_configuration:  
  membrane_structure_content SEMICOLON;

membrane_structure_content: 
  (membrane_content)*;

membrane_content:  

	membrane_index=numerical_ident
	numerical_ident 
	{
	SimpleKernelLikeMembrane membrane = SimpleKernelLikeMembraneFactory.getKernelLikeMembrane(membrane_index.identifier, membraneStructure);}
	objects_list[membrane.getMultiSet()] SEMICOLON;

cardinalities[MultiSet<String> multiset]: 
(cardinality[multiset])*;

cardinality[MultiSet<String> multiset]: 
LEFT_BRACKET 
object_token=INTEGER 
cardinality_token=INTEGER 
RIGHT_BRACKET
 {multiset.add($object_token.text, Integer.parseInt($cardinality_token.text));}
;

rules: 
(rule)* SEMICOLON;

rule returns [IRule rule]:  
leftHandSide=left_hand_side COLON rightHandSide=right_hand_side 
{IRule rule = (new KernelRuleFactory()).createKernelRule(false, leftHandSide.leftHandSide, rightHandSide.rightHandSide, guard, KernelRuleTypes.EVOLUTION);}
;

left_hand_side returns [LeftHandRule leftHandSide]:

membrane_index=numerical_ident 
{MultiSet<String> multiSet = new HashMultiSet<String>();
retval.leftHandSide = new LeftHandRule(new OuterRuleMembrane(new Label(membrane_index.identifier), (byte)0, multiSet), new HashMultiSet<String>());
}
numerical_ident
rule_guard=guard{guard=rule_guard.guard;}
objects_list[multiSet]
;  

right_hand_side returns [RightHandRule rightHandSide]:
membrane_destination=numerical_ident 
{MultiSet<String> multiSet = new HashMultiSet<String>();
retval.rightHandSide = new RightHandRule(new OuterRuleMembrane(new Label(membrane_destination.identifier), (byte)0, multiSet), new HashMultiSet<String>());
}
objects_list[multiSet]
SEMICOLON;

numerical_ident returns [String identifier]: 
membrane_index_token=INTEGER{retval.identifier=$membrane_index_token.text;}  
COLON;




objects_list [MultiSet<String> multiset]: 
LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET;

guard returns [UnitGuard guard]:  
{guard=null; short sign;}
(guard_sign=INTEGER{sign=(Integer.parseInt($guard_sign.text)==1)? ComparationMasks.MINUS :ComparationMasks.PLUS;} guard_token=INTEGER{retval.guard= new UnitGuard(ComparationMasks.GREATER_OR_EQUAL_THAN, sign , $guard_token.text, 1);} DOT)?;


