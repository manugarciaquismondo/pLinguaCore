parser grammar Dictionary_Reader;

options {
  language = Java;
  output= AST;
  tokenVocab = Kernel_Simulator_Lexer;
}
@header{
package org.gcn.plinguacore.parser.input.simplekernel;

import org.gcn.plinguacore.parser.output.simplekernel.KernelDictionary;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
}
dictionary returns [KernelDictionary dictionary]: membraneMap=membranes objectsMap=objects
{KernelDictionary dictionary = new KernelDictionary();
for(String label: membraneMap.membraneMap.keySet())
  dictionary.putMembrane(label, membraneMap.membraneMap.get(label));

for(String label: objectsMap.objectsMap.keySet())
  dictionary.putObject(label, objectsMap.objectsMap.get(label));
  
  retval.dictionary=dictionary;
}


;

membranes returns [Map<String, Integer> membraneMap]: membranes_header membraneListMap=membrane_list
{retval.membraneMap = membraneListMap.membraneMap;};

membranes_header: MEMBRANES COLON INTEGER SEMICOLON;

membrane_list returns [Map<String, Integer> membraneMap]:
{retval.membraneMap= new HashMap<String, Integer>();}
 (membrane[retval.membraneMap])*;

membrane [Map<String, Integer> membraneMap]: 
membrane_label=integer_list EQUAL id=INTEGER SEMICOLON
{membraneMap.put(membrane_label.integer_string, Integer.parseInt($id.text));}
;

integer_list returns [String integer_string]:
id_token=INTEGER{retval.integer_string=$id_token.text;}
(COMMA id_token=INTEGER{retval.integer_string+=","+$id_token.text;})*
;

objects returns [ Map<String, Integer> objectsMap]: objects_header objectListMap=object_list
{retval.objectsMap = objectListMap.objectsMap;}
;


objects_header: OBJECTS COLON INTEGER SEMICOLON;

object_list returns [Map<String, Integer> objectsMap]:
{Map<String, Integer> map =  new HashMap<String, Integer>();}
(object_element[map])*
{retval.objectsMap =map;}
;

object_element [Map<String, Integer> objectsMap]:
object_label=object_id EQUAL id=INTEGER SEMICOLON
{objectsMap.put(object_label.object_id, Integer.parseInt($id.text));}
;

object_id returns [String object_id]:
id_token=STRING
{retval.object_id=$id_token.text;}
 indexes_token=indexes
{retval.object_id+=indexes_token.indexes_string;}
;

indexes returns [String indexes_string]:
{String returnedValue="";}
(LEFT_KEY_BRACKET indexes_token=indexes_list RIGHT_KEY_BRACKET
{
returnedValue+="{";
for(String index: indexes_token.indexes)
  returnedValue+=index+",";
returnedValue=returnedValue.substring(0, returnedValue.length()-1)+"}";
})?
{retval.indexes_string=returnedValue;}
;

indexes_list returns [List<String> indexes]:
{retval.indexes= new LinkedList<String>();}
index=INTEGER {retval.indexes.add($index.text);}
 (COMMA index=INTEGER {retval.indexes.add($index.text);})*
 ;