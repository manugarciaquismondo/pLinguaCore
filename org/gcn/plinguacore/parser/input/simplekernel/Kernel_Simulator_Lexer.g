lexer grammar Kernel_Simulator_Lexer;

options {
  language = Java;
}

@lexer::header{
package org.gcn.plinguacore.parser.input.simplekernel;

}

STEPS: 'steps';
MEMBRANES: 'membranes';
MAXIMUM_SIZE_OF_HAND_SIDE: 'maximum_size_of_hand_size';
MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE: 'maximum_number_of_rules_per_membrane';
ALPHABET_SIZE: 'alphabet_size';
RULES: 'rules';
OBJECTS: 'objects';
CONFIGURATION: 'configuration';
FLAGS: 'flags';
FLAGS_SIZE: 'flags_size';
NUMBER_OF_BLOCKS: 'number_of_blocks';

//FIGURE: ('0'..'9');
STRING: (('A'..'Z')|('a'..'z')|'_')+;
INTEGER: (SIGN)?('0'..'9')+;
FLOAT: INTEGER DOT INTEGER;

//INT: (FIGURE)+;
SHARP: '#';
COMMA: ',';
DOT: '.';
SEMICOLON: ';';
LEFT_KEY_BRACKET: '{';
RIGHT_KEY_BRACKET: '}';
COLON: ':';
LEFT_SQUARED_BRACKET: '[';
RIGHT_SQUARED_BRACKET: ']';
LEFT_BRACKET: '(';
RIGHT_BRACKET: ')';
EQUAL: '=';
SIGN: '+'|'-';







WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;
