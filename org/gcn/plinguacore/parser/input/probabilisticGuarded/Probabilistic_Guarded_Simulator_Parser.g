parser grammar Probabilistic_Guarded_Simulator_Parser;

options {
  language = Java;
  output = AST;
  tokenVocab = Kernel_Simulator_Lexer;
}

@header{
package org.gcn.plinguacore.parser.input.probabilisticGuarded;

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
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.parser.input.probabilisticGuarded.RestrictiveGuardFactory;

}



@members{

  
  SimpleKernelLikeMembraneStructure membraneStructure;
  RestrictiveGuard guard; 
 
}

psystem returns [ProbabilisticGuardedPsystem  psystem] : 
{retval.psystem = new ProbabilisticGuardedPsystem();}

header


initial_configuration
{retval.psystem.setMembraneStructure(membraneStructure);}
flags[retval.psystem]
rules[retval.psystem]
blocks
;

header: 
membranes_line 
rules_line
maximum_number_of_rules_per_membrane_line 
alphabet_size_line
flags_line
blocks_line
;

membranes_line: 
MEMBRANES COLON INTEGER SEMICOLON;


rules_line:
RULES COLON INTEGER SEMICOLON;


maximum_number_of_rules_per_membrane_line: 
  MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON;

alphabet_size_line: 
  ALPHABET_SIZE COLON INTEGER SEMICOLON ;

flags_line:
 FLAGS_SIZE COLON INTEGER SEMICOLON;
 
 blocks_line:
 NUMBER_OF_BLOCKS COLON INTEGER SEMICOLON;

initial_configuration:  
  membrane_structure_content SEMICOLON;

membrane_structure_content: 
  (membrane_content)*;

membrane_content:  

	membrane_index=numerical_ident
	numerical_ident 
	{
	String labelString = membrane_index.identifier;
	Label label = new Label(labelString);
	Membrane membrane;
	MultiSet<String> multiSet;
	if(labelString.equals("0")){
	  membrane=CellLikeMembraneFactory.getCellLikeMembrane(label);
	  membraneStructure = new SimpleKernelLikeMembraneStructure((CellLikeSkinMembrane)membrane);
	  multiSet = new HashMultiSet<String>();
	}
	else{
	  membrane = SimpleKernelLikeMembraneFactory.getKernelLikeMembrane(labelString, membraneStructure);
	  membraneStructure.add((SimpleKernelLikeMembrane)membrane);
	  multiSet = membrane.getMultiSet();
	}
	}
	objects_list[multiSet]  flag[multiSet] SEMICOLON;

flag[MultiSet<String> multiset]: 
 ((object_token=INTEGER
  {if(!multiset.contains($object_token.text))
    multiset.add($object_token.text);})|SHARP)
;

cardinalities[MultiSet<String> multiset]: 
(cardinality[multiset])*;

cardinality[MultiSet<String> multiset]: 
LEFT_BRACKET 
object_token=INTEGER 
cardinality_token=INTEGER 
RIGHT_BRACKET
 {multiset.add($object_token.text, Integer.parseInt($cardinality_token.text));}
;

flags [Psystem psystem]:
FLAGS COLON
(flag_token=INTEGER{((ProbabilisticGuardedPsystem)psystem).addFlag($flag_token.text);})*
COLON
numerical_ident
dummy_mode = numerical_ident
{((ProbabilisticGuardedPsystem)psystem).setDummyMode(Byte.parseByte(dummy_mode.identifier));}SEMICOLON
;

rules [Psystem psystem]:
(rule[psystem])* SEMICOLON;

rule [Psystem psystem]:  

numerical_ident 
probability_string = float_ident
leftHandSide=left_hand_side COLON rightHandSide=right_hand_side 
{
float probability = Float.parseFloat(probability_string.identifier);
IRule rule = (new ProbabilisticGuardedRuleFactory()).createProbabilisticGuardedRule(false, leftHandSide.leftHandSide, rightHandSide.rightHandSide, guard, KernelRuleTypes.EVOLUTION, probability);
psystem.addRule(rule);}
;

left_hand_side returns [LeftHandRule leftHandSide]:

membrane_index=numerical_ident
{MultiSet<String> multiSet = new HashMultiSet<String>();
retval.leftHandSide = new LeftHandRule(new OuterRuleMembrane(new Label(membrane_index.identifier), (byte)0, multiSet), new HashMultiSet<String>());
}
rule_guard=guard{guard=rule_guard.guard;}
flag[multiSet]
objects_list[multiSet]
;  

right_hand_side returns [RightHandRule rightHandSide]:
membrane_destination=numerical_ident 
{MultiSet<String> multiSet = new HashMultiSet<String>();
retval.rightHandSide = new RightHandRule(new OuterRuleMembrane(new Label(membrane_destination.identifier), (byte)0, multiSet), new HashMultiSet<String>());
}
flag[multiSet]
objects_list[multiSet]
numerical_ident
SEMICOLON;

numerical_ident returns [String identifier]: 
membrane_index_token=INTEGER{retval.identifier=$membrane_index_token.text;}  
COLON;

float_ident returns [String identifier]: 
float_index_token=FLOAT{retval.identifier=$float_index_token.text;}  
COLON;

objects_list [MultiSet<String> multiset]: 
LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET;

guard returns [RestrictiveGuard guard]:  
{guard=null; short sign;}
(guard_sign=INTEGER
{sign=(Integer.parseInt($guard_sign.text)==1)? ComparationMasks.MINUS :ComparationMasks.PLUS;}
 COLON guard_token=INTEGER
 {retval.guard= RestrictiveGuardFactory.createRestrictiveGuard($guard_token.text);}
  COLON)?;
  
blocks: (block)* SEMICOLON;

block: numerical_ident numerical_ident flag[new HashMultiSet<String>()] COLON flag [new HashMultiSet<String>()] COLON flag [new HashMultiSet<String>()] objects_list[new HashMultiSet<String>()] (INTEGER)*;

