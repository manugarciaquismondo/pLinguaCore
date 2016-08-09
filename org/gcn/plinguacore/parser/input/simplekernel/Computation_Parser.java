// $ANTLR 3.4 C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g 2016-06-22 16:35:47


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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class Computation_Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALPHABET_SIZE", "COLON", "COMMA", "CONFIGURATION", "DOT", "EQUAL", "FLAGS", "FLAGS_SIZE", "FLOAT", "INTEGER", "LEFT_BRACKET", "LEFT_KEY_BRACKET", "LEFT_SQUARED_BRACKET", "MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE", "MAXIMUM_SIZE_OF_HAND_SIDE", "MEMBRANES", "NUMBER_OF_BLOCKS", "OBJECTS", "RIGHT_BRACKET", "RIGHT_KEY_BRACKET", "RIGHT_SQUARED_BRACKET", "RULES", "SEMICOLON", "SHARP", "SIGN", "STEPS", "STRING", "WS"
    };

    public static final int EOF=-1;
    public static final int ALPHABET_SIZE=4;
    public static final int COLON=5;
    public static final int COMMA=6;
    public static final int CONFIGURATION=7;
    public static final int DOT=8;
    public static final int EQUAL=9;
    public static final int FLAGS=10;
    public static final int FLAGS_SIZE=11;
    public static final int FLOAT=12;
    public static final int INTEGER=13;
    public static final int LEFT_BRACKET=14;
    public static final int LEFT_KEY_BRACKET=15;
    public static final int LEFT_SQUARED_BRACKET=16;
    public static final int MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE=17;
    public static final int MAXIMUM_SIZE_OF_HAND_SIDE=18;
    public static final int MEMBRANES=19;
    public static final int NUMBER_OF_BLOCKS=20;
    public static final int OBJECTS=21;
    public static final int RIGHT_BRACKET=22;
    public static final int RIGHT_KEY_BRACKET=23;
    public static final int RIGHT_SQUARED_BRACKET=24;
    public static final int RULES=25;
    public static final int SEMICOLON=26;
    public static final int SHARP=27;
    public static final int SIGN=28;
    public static final int STEPS=29;
    public static final int STRING=30;
    public static final int WS=31;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public Computation_Parser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public Computation_Parser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return Computation_Parser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g"; }


    public static class computation_return extends ParserRuleReturnScope {
        public List<SimpleKernelLikeMembraneStructure> computation;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "computation"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:27:1: computation returns [List<SimpleKernelLikeMembraneStructure> computation] : header configurations_result= configurations ;
    public final Computation_Parser.computation_return computation() throws RecognitionException {
        Computation_Parser.computation_return retval = new Computation_Parser.computation_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Computation_Parser.configurations_return configurations_result =null;

        Computation_Parser.header_return header1 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:27:74: ( header configurations_result= configurations )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:28:1: header configurations_result= configurations
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_header_in_computation55);
            header1=header();

            state._fsp--;

            adaptor.addChild(root_0, header1.getTree());

            pushFollow(FOLLOW_configurations_in_computation60);
            configurations_result=configurations();

            state._fsp--;

            adaptor.addChild(root_0, configurations_result.getTree());

            retval.computation=configurations_result.computation;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "computation"


    public static class header_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "header"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:32:1: header : steps_line membranes_line rules_line maximum_number_of_rules_per_membrane_line alphabet_size_line ;
    public final Computation_Parser.header_return header() throws RecognitionException {
        Computation_Parser.header_return retval = new Computation_Parser.header_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Computation_Parser.steps_line_return steps_line2 =null;

        Computation_Parser.membranes_line_return membranes_line3 =null;

        Computation_Parser.rules_line_return rules_line4 =null;

        Computation_Parser.maximum_number_of_rules_per_membrane_line_return maximum_number_of_rules_per_membrane_line5 =null;

        Computation_Parser.alphabet_size_line_return alphabet_size_line6 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:32:7: ( steps_line membranes_line rules_line maximum_number_of_rules_per_membrane_line alphabet_size_line )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:33:1: steps_line membranes_line rules_line maximum_number_of_rules_per_membrane_line alphabet_size_line
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_steps_line_in_header70);
            steps_line2=steps_line();

            state._fsp--;

            adaptor.addChild(root_0, steps_line2.getTree());

            pushFollow(FOLLOW_membranes_line_in_header72);
            membranes_line3=membranes_line();

            state._fsp--;

            adaptor.addChild(root_0, membranes_line3.getTree());

            pushFollow(FOLLOW_rules_line_in_header75);
            rules_line4=rules_line();

            state._fsp--;

            adaptor.addChild(root_0, rules_line4.getTree());

            pushFollow(FOLLOW_maximum_number_of_rules_per_membrane_line_in_header77);
            maximum_number_of_rules_per_membrane_line5=maximum_number_of_rules_per_membrane_line();

            state._fsp--;

            adaptor.addChild(root_0, maximum_number_of_rules_per_membrane_line5.getTree());

            pushFollow(FOLLOW_alphabet_size_line_in_header80);
            alphabet_size_line6=alphabet_size_line();

            state._fsp--;

            adaptor.addChild(root_0, alphabet_size_line6.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "header"


    public static class steps_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "steps_line"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:40:1: steps_line : STEPS COLON INTEGER SEMICOLON ;
    public final Computation_Parser.steps_line_return steps_line() throws RecognitionException {
        Computation_Parser.steps_line_return retval = new Computation_Parser.steps_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token STEPS7=null;
        Token COLON8=null;
        Token INTEGER9=null;
        Token SEMICOLON10=null;

        Object STEPS7_tree=null;
        Object COLON8_tree=null;
        Object INTEGER9_tree=null;
        Object SEMICOLON10_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:40:11: ( STEPS COLON INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:41:1: STEPS COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            STEPS7=(Token)match(input,STEPS,FOLLOW_STEPS_in_steps_line88); 
            STEPS7_tree = 
            (Object)adaptor.create(STEPS7)
            ;
            adaptor.addChild(root_0, STEPS7_tree);


            COLON8=(Token)match(input,COLON,FOLLOW_COLON_in_steps_line90); 
            COLON8_tree = 
            (Object)adaptor.create(COLON8)
            ;
            adaptor.addChild(root_0, COLON8_tree);


            INTEGER9=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_steps_line92); 
            INTEGER9_tree = 
            (Object)adaptor.create(INTEGER9)
            ;
            adaptor.addChild(root_0, INTEGER9_tree);


            SEMICOLON10=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_steps_line94); 
            SEMICOLON10_tree = 
            (Object)adaptor.create(SEMICOLON10)
            ;
            adaptor.addChild(root_0, SEMICOLON10_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "steps_line"


    public static class membranes_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membranes_line"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:43:1: membranes_line : MEMBRANES COLON INTEGER SEMICOLON ;
    public final Computation_Parser.membranes_line_return membranes_line() throws RecognitionException {
        Computation_Parser.membranes_line_return retval = new Computation_Parser.membranes_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token MEMBRANES11=null;
        Token COLON12=null;
        Token INTEGER13=null;
        Token SEMICOLON14=null;

        Object MEMBRANES11_tree=null;
        Object COLON12_tree=null;
        Object INTEGER13_tree=null;
        Object SEMICOLON14_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:43:15: ( MEMBRANES COLON INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:44:1: MEMBRANES COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            MEMBRANES11=(Token)match(input,MEMBRANES,FOLLOW_MEMBRANES_in_membranes_line102); 
            MEMBRANES11_tree = 
            (Object)adaptor.create(MEMBRANES11)
            ;
            adaptor.addChild(root_0, MEMBRANES11_tree);


            COLON12=(Token)match(input,COLON,FOLLOW_COLON_in_membranes_line104); 
            COLON12_tree = 
            (Object)adaptor.create(COLON12)
            ;
            adaptor.addChild(root_0, COLON12_tree);


            INTEGER13=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_membranes_line106); 
            INTEGER13_tree = 
            (Object)adaptor.create(INTEGER13)
            ;
            adaptor.addChild(root_0, INTEGER13_tree);


            SEMICOLON14=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_membranes_line108); 
            SEMICOLON14_tree = 
            (Object)adaptor.create(SEMICOLON14)
            ;
            adaptor.addChild(root_0, SEMICOLON14_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "membranes_line"


    public static class rules_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rules_line"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:47:1: rules_line : RULES COLON INTEGER SEMICOLON ;
    public final Computation_Parser.rules_line_return rules_line() throws RecognitionException {
        Computation_Parser.rules_line_return retval = new Computation_Parser.rules_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token RULES15=null;
        Token COLON16=null;
        Token INTEGER17=null;
        Token SEMICOLON18=null;

        Object RULES15_tree=null;
        Object COLON16_tree=null;
        Object INTEGER17_tree=null;
        Object SEMICOLON18_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:47:11: ( RULES COLON INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:48:1: RULES COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            RULES15=(Token)match(input,RULES,FOLLOW_RULES_in_rules_line116); 
            RULES15_tree = 
            (Object)adaptor.create(RULES15)
            ;
            adaptor.addChild(root_0, RULES15_tree);


            COLON16=(Token)match(input,COLON,FOLLOW_COLON_in_rules_line118); 
            COLON16_tree = 
            (Object)adaptor.create(COLON16)
            ;
            adaptor.addChild(root_0, COLON16_tree);


            INTEGER17=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_rules_line120); 
            INTEGER17_tree = 
            (Object)adaptor.create(INTEGER17)
            ;
            adaptor.addChild(root_0, INTEGER17_tree);


            SEMICOLON18=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_rules_line122); 
            SEMICOLON18_tree = 
            (Object)adaptor.create(SEMICOLON18)
            ;
            adaptor.addChild(root_0, SEMICOLON18_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "rules_line"


    public static class maximum_number_of_rules_per_membrane_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "maximum_number_of_rules_per_membrane_line"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:51:1: maximum_number_of_rules_per_membrane_line : MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON ;
    public final Computation_Parser.maximum_number_of_rules_per_membrane_line_return maximum_number_of_rules_per_membrane_line() throws RecognitionException {
        Computation_Parser.maximum_number_of_rules_per_membrane_line_return retval = new Computation_Parser.maximum_number_of_rules_per_membrane_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE19=null;
        Token COLON20=null;
        Token INTEGER21=null;
        Token SEMICOLON22=null;

        Object MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE19_tree=null;
        Object COLON20_tree=null;
        Object INTEGER21_tree=null;
        Object SEMICOLON22_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:51:42: ( MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:52:3: MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE19=(Token)match(input,MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE,FOLLOW_MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE_in_maximum_number_of_rules_per_membrane_line133); 
            MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE19_tree = 
            (Object)adaptor.create(MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE19)
            ;
            adaptor.addChild(root_0, MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE19_tree);


            COLON20=(Token)match(input,COLON,FOLLOW_COLON_in_maximum_number_of_rules_per_membrane_line135); 
            COLON20_tree = 
            (Object)adaptor.create(COLON20)
            ;
            adaptor.addChild(root_0, COLON20_tree);


            INTEGER21=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_maximum_number_of_rules_per_membrane_line137); 
            INTEGER21_tree = 
            (Object)adaptor.create(INTEGER21)
            ;
            adaptor.addChild(root_0, INTEGER21_tree);


            SEMICOLON22=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_maximum_number_of_rules_per_membrane_line139); 
            SEMICOLON22_tree = 
            (Object)adaptor.create(SEMICOLON22)
            ;
            adaptor.addChild(root_0, SEMICOLON22_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "maximum_number_of_rules_per_membrane_line"


    public static class alphabet_size_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "alphabet_size_line"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:54:1: alphabet_size_line : ALPHABET_SIZE COLON INTEGER SEMICOLON ;
    public final Computation_Parser.alphabet_size_line_return alphabet_size_line() throws RecognitionException {
        Computation_Parser.alphabet_size_line_return retval = new Computation_Parser.alphabet_size_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ALPHABET_SIZE23=null;
        Token COLON24=null;
        Token INTEGER25=null;
        Token SEMICOLON26=null;

        Object ALPHABET_SIZE23_tree=null;
        Object COLON24_tree=null;
        Object INTEGER25_tree=null;
        Object SEMICOLON26_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:54:19: ( ALPHABET_SIZE COLON INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:55:3: ALPHABET_SIZE COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            ALPHABET_SIZE23=(Token)match(input,ALPHABET_SIZE,FOLLOW_ALPHABET_SIZE_in_alphabet_size_line149); 
            ALPHABET_SIZE23_tree = 
            (Object)adaptor.create(ALPHABET_SIZE23)
            ;
            adaptor.addChild(root_0, ALPHABET_SIZE23_tree);


            COLON24=(Token)match(input,COLON,FOLLOW_COLON_in_alphabet_size_line151); 
            COLON24_tree = 
            (Object)adaptor.create(COLON24)
            ;
            adaptor.addChild(root_0, COLON24_tree);


            INTEGER25=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_alphabet_size_line153); 
            INTEGER25_tree = 
            (Object)adaptor.create(INTEGER25)
            ;
            adaptor.addChild(root_0, INTEGER25_tree);


            SEMICOLON26=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_alphabet_size_line155); 
            SEMICOLON26_tree = 
            (Object)adaptor.create(SEMICOLON26)
            ;
            adaptor.addChild(root_0, SEMICOLON26_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "alphabet_size_line"


    public static class configurations_return extends ParserRuleReturnScope {
        public List<SimpleKernelLikeMembraneStructure> computation;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "configurations"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:57:1: configurations returns [List<SimpleKernelLikeMembraneStructure> computation] : (configuration_result= configuration )* ;
    public final Computation_Parser.configurations_return configurations() throws RecognitionException {
        Computation_Parser.configurations_return retval = new Computation_Parser.configurations_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Computation_Parser.configuration_return configuration_result =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:57:77: ( (configuration_result= configuration )* )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:58:1: (configuration_result= configuration )*
            {
            root_0 = (Object)adaptor.nil();


            retval.computation = new LinkedList<SimpleKernelLikeMembraneStructure>();

            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:59:2: (configuration_result= configuration )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==CONFIGURATION) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:59:3: configuration_result= configuration
            	    {
            	    pushFollow(FOLLOW_configuration_in_configurations173);
            	    configuration_result=configuration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, configuration_result.getTree());

            	    retval.computation.add(configuration_result.configuration);

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "configurations"


    public static class configuration_return extends ParserRuleReturnScope {
        public SimpleKernelLikeMembraneStructure configuration;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "configuration"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:62:1: configuration returns [SimpleKernelLikeMembraneStructure configuration] : configuration_line initial_configuration_result= initial_configuration ;
    public final Computation_Parser.configuration_return configuration() throws RecognitionException {
        Computation_Parser.configuration_return retval = new Computation_Parser.configuration_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Computation_Parser.initial_configuration_return initial_configuration_result =null;

        Computation_Parser.configuration_line_return configuration_line27 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:62:72: ( configuration_line initial_configuration_result= initial_configuration )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:63:1: configuration_line initial_configuration_result= initial_configuration
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_configuration_line_in_configuration191);
            configuration_line27=configuration_line();

            state._fsp--;

            adaptor.addChild(root_0, configuration_line27.getTree());

            pushFollow(FOLLOW_initial_configuration_in_configuration196);
            initial_configuration_result=initial_configuration();

            state._fsp--;

            adaptor.addChild(root_0, initial_configuration_result.getTree());

            retval.configuration=initial_configuration_result.configuration;

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "configuration"


    public static class configuration_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "configuration_line"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:68:1: configuration_line : CONFIGURATION COLON INTEGER SEMICOLON ;
    public final Computation_Parser.configuration_line_return configuration_line() throws RecognitionException {
        Computation_Parser.configuration_line_return retval = new Computation_Parser.configuration_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token CONFIGURATION28=null;
        Token COLON29=null;
        Token INTEGER30=null;
        Token SEMICOLON31=null;

        Object CONFIGURATION28_tree=null;
        Object COLON29_tree=null;
        Object INTEGER30_tree=null;
        Object SEMICOLON31_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:68:19: ( CONFIGURATION COLON INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:68:21: CONFIGURATION COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            CONFIGURATION28=(Token)match(input,CONFIGURATION,FOLLOW_CONFIGURATION_in_configuration_line206); 
            CONFIGURATION28_tree = 
            (Object)adaptor.create(CONFIGURATION28)
            ;
            adaptor.addChild(root_0, CONFIGURATION28_tree);


            COLON29=(Token)match(input,COLON,FOLLOW_COLON_in_configuration_line208); 
            COLON29_tree = 
            (Object)adaptor.create(COLON29)
            ;
            adaptor.addChild(root_0, COLON29_tree);


            INTEGER30=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_configuration_line210); 
            INTEGER30_tree = 
            (Object)adaptor.create(INTEGER30)
            ;
            adaptor.addChild(root_0, INTEGER30_tree);


            SEMICOLON31=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_configuration_line212); 
            SEMICOLON31_tree = 
            (Object)adaptor.create(SEMICOLON31)
            ;
            adaptor.addChild(root_0, SEMICOLON31_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "configuration_line"


    public static class initial_configuration_return extends ParserRuleReturnScope {
        public SimpleKernelLikeMembraneStructure configuration;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "initial_configuration"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:70:1: initial_configuration returns [SimpleKernelLikeMembraneStructure configuration] : membrane_structure_result= membrane_structure_content SEMICOLON ;
    public final Computation_Parser.initial_configuration_return initial_configuration() throws RecognitionException {
        Computation_Parser.initial_configuration_return retval = new Computation_Parser.initial_configuration_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON32=null;
        Computation_Parser.membrane_structure_content_return membrane_structure_result =null;


        Object SEMICOLON32_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:70:80: (membrane_structure_result= membrane_structure_content SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:71:3: membrane_structure_result= membrane_structure_content SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_membrane_structure_content_in_initial_configuration229);
            membrane_structure_result=membrane_structure_content();

            state._fsp--;

            adaptor.addChild(root_0, membrane_structure_result.getTree());

            retval.configuration= membrane_structure_result.configuration;

            SEMICOLON32=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_initial_configuration238); 
            SEMICOLON32_tree = 
            (Object)adaptor.create(SEMICOLON32)
            ;
            adaptor.addChild(root_0, SEMICOLON32_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "initial_configuration"


    public static class membrane_structure_content_return extends ParserRuleReturnScope {
        public SimpleKernelLikeMembraneStructure configuration;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membrane_structure_content"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:75:1: membrane_structure_content returns [SimpleKernelLikeMembraneStructure configuration] : ( membrane_content[retval.configuration] )* ;
    public final Computation_Parser.membrane_structure_content_return membrane_structure_content() throws RecognitionException {
        Computation_Parser.membrane_structure_content_return retval = new Computation_Parser.membrane_structure_content_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Computation_Parser.membrane_content_return membrane_content33 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:75:85: ( ( membrane_content[retval.configuration] )* )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:76:1: ( membrane_content[retval.configuration] )*
            {
            root_0 = (Object)adaptor.nil();


            retval.configuration = new SimpleKernelLikeMembraneStructure((CellLikeSkinMembrane)CellLikeMembraneFactory.getCellLikeMembrane(new Label("0")));

            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:77:3: ( membrane_content[retval.configuration] )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==INTEGER) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:77:4: membrane_content[retval.configuration]
            	    {
            	    pushFollow(FOLLOW_membrane_content_in_membrane_structure_content255);
            	    membrane_content33=membrane_content(retval.configuration);

            	    state._fsp--;

            	    adaptor.addChild(root_0, membrane_content33.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "membrane_structure_content"


    public static class membrane_content_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membrane_content"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:79:1: membrane_content[SimpleKernelLikeMembraneStructure membraneStructure] : membrane_index= numerical_ident numerical_ident objects_list[multiSet] SEMICOLON ;
    public final Computation_Parser.membrane_content_return membrane_content(SimpleKernelLikeMembraneStructure membraneStructure) throws RecognitionException {
        Computation_Parser.membrane_content_return retval = new Computation_Parser.membrane_content_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON36=null;
        Computation_Parser.numerical_ident_return membrane_index =null;

        Computation_Parser.numerical_ident_return numerical_ident34 =null;

        Computation_Parser.objects_list_return objects_list35 =null;


        Object SEMICOLON36_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:79:71: (membrane_index= numerical_ident numerical_ident objects_list[multiSet] SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:81:3: membrane_index= numerical_ident numerical_ident objects_list[multiSet] SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_membrane_content274);
            membrane_index=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, membrane_index.getTree());

            pushFollow(FOLLOW_numerical_ident_in_membrane_content278);
            numerical_ident34=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident34.getTree());


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
              

            pushFollow(FOLLOW_objects_list_in_membrane_content287);
            objects_list35=objects_list(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, objects_list35.getTree());

            SEMICOLON36=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_membrane_content290); 
            SEMICOLON36_tree = 
            (Object)adaptor.create(SEMICOLON36)
            ;
            adaptor.addChild(root_0, SEMICOLON36_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "membrane_content"


    public static class cardinalities_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cardinalities"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:99:1: cardinalities[MultiSet<String> multiset] : ( cardinality[multiset] )* ;
    public final Computation_Parser.cardinalities_return cardinalities(MultiSet<String> multiset) throws RecognitionException {
        Computation_Parser.cardinalities_return retval = new Computation_Parser.cardinalities_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Computation_Parser.cardinality_return cardinality37 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:99:41: ( ( cardinality[multiset] )* )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:100:2: ( cardinality[multiset] )*
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:100:2: ( cardinality[multiset] )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==LEFT_BRACKET) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:100:2: cardinality[multiset]
            	    {
            	    pushFollow(FOLLOW_cardinality_in_cardinalities300);
            	    cardinality37=cardinality(multiset);

            	    state._fsp--;

            	    adaptor.addChild(root_0, cardinality37.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "cardinalities"


    public static class cardinality_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cardinality"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:102:1: cardinality[MultiSet<String> multiset] : LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET ;
    public final Computation_Parser.cardinality_return cardinality(MultiSet<String> multiset) throws RecognitionException {
        Computation_Parser.cardinality_return retval = new Computation_Parser.cardinality_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token object_token=null;
        Token cardinality_token=null;
        Token LEFT_BRACKET38=null;
        Token RIGHT_BRACKET39=null;

        Object object_token_tree=null;
        Object cardinality_token_tree=null;
        Object LEFT_BRACKET38_tree=null;
        Object RIGHT_BRACKET39_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:102:39: ( LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:103:1: LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET
            {
            root_0 = (Object)adaptor.nil();


            LEFT_BRACKET38=(Token)match(input,LEFT_BRACKET,FOLLOW_LEFT_BRACKET_in_cardinality312); 
            LEFT_BRACKET38_tree = 
            (Object)adaptor.create(LEFT_BRACKET38)
            ;
            adaptor.addChild(root_0, LEFT_BRACKET38_tree);


            object_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_cardinality317); 
            object_token_tree = 
            (Object)adaptor.create(object_token)
            ;
            adaptor.addChild(root_0, object_token_tree);


            cardinality_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_cardinality322); 
            cardinality_token_tree = 
            (Object)adaptor.create(cardinality_token)
            ;
            adaptor.addChild(root_0, cardinality_token_tree);


            RIGHT_BRACKET39=(Token)match(input,RIGHT_BRACKET,FOLLOW_RIGHT_BRACKET_in_cardinality325); 
            RIGHT_BRACKET39_tree = 
            (Object)adaptor.create(RIGHT_BRACKET39)
            ;
            adaptor.addChild(root_0, RIGHT_BRACKET39_tree);


            multiset.add((object_token!=null?object_token.getText():null), Integer.parseInt((cardinality_token!=null?cardinality_token.getText():null)));

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "cardinality"


    public static class numerical_ident_return extends ParserRuleReturnScope {
        public String identifier;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "numerical_ident"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:110:1: numerical_ident returns [String identifier] : membrane_index_token= INTEGER COLON ;
    public final Computation_Parser.numerical_ident_return numerical_ident() throws RecognitionException {
        Computation_Parser.numerical_ident_return retval = new Computation_Parser.numerical_ident_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token membrane_index_token=null;
        Token COLON40=null;

        Object membrane_index_token_tree=null;
        Object COLON40_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:110:44: (membrane_index_token= INTEGER COLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:111:21: membrane_index_token= INTEGER COLON
            {
            root_0 = (Object)adaptor.nil();


            membrane_index_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_numerical_ident343); 
            membrane_index_token_tree = 
            (Object)adaptor.create(membrane_index_token)
            ;
            adaptor.addChild(root_0, membrane_index_token_tree);


            retval.identifier=(membrane_index_token!=null?membrane_index_token.getText():null);

            COLON40=(Token)match(input,COLON,FOLLOW_COLON_in_numerical_ident348); 
            COLON40_tree = 
            (Object)adaptor.create(COLON40)
            ;
            adaptor.addChild(root_0, COLON40_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "numerical_ident"


    public static class objects_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "objects_list"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:117:1: objects_list[MultiSet<String> multiset] : LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET ;
    public final Computation_Parser.objects_list_return objects_list(MultiSet<String> multiset) throws RecognitionException {
        Computation_Parser.objects_list_return retval = new Computation_Parser.objects_list_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token LEFT_SQUARED_BRACKET41=null;
        Token RIGHT_SQUARED_BRACKET43=null;
        Computation_Parser.cardinalities_return cardinalities42 =null;


        Object LEFT_SQUARED_BRACKET41_tree=null;
        Object RIGHT_SQUARED_BRACKET43_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:117:41: ( LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Computation_Parser.g:118:1: LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET
            {
            root_0 = (Object)adaptor.nil();


            LEFT_SQUARED_BRACKET41=(Token)match(input,LEFT_SQUARED_BRACKET,FOLLOW_LEFT_SQUARED_BRACKET_in_objects_list361); 
            LEFT_SQUARED_BRACKET41_tree = 
            (Object)adaptor.create(LEFT_SQUARED_BRACKET41)
            ;
            adaptor.addChild(root_0, LEFT_SQUARED_BRACKET41_tree);


            pushFollow(FOLLOW_cardinalities_in_objects_list363);
            cardinalities42=cardinalities(multiset);

            state._fsp--;

            adaptor.addChild(root_0, cardinalities42.getTree());

            RIGHT_SQUARED_BRACKET43=(Token)match(input,RIGHT_SQUARED_BRACKET,FOLLOW_RIGHT_SQUARED_BRACKET_in_objects_list366); 
            RIGHT_SQUARED_BRACKET43_tree = 
            (Object)adaptor.create(RIGHT_SQUARED_BRACKET43)
            ;
            adaptor.addChild(root_0, RIGHT_SQUARED_BRACKET43_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "objects_list"

    // Delegated rules


 

    public static final BitSet FOLLOW_header_in_computation55 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_configurations_in_computation60 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_steps_line_in_header70 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_membranes_line_in_header72 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_rules_line_in_header75 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_maximum_number_of_rules_per_membrane_line_in_header77 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_alphabet_size_line_in_header80 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STEPS_in_steps_line88 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_steps_line90 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_steps_line92 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_steps_line94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MEMBRANES_in_membranes_line102 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_membranes_line104 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_membranes_line106 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_membranes_line108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULES_in_rules_line116 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_rules_line118 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_rules_line120 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_rules_line122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE_in_maximum_number_of_rules_per_membrane_line133 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_maximum_number_of_rules_per_membrane_line135 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_maximum_number_of_rules_per_membrane_line137 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_maximum_number_of_rules_per_membrane_line139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ALPHABET_SIZE_in_alphabet_size_line149 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_alphabet_size_line151 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_alphabet_size_line153 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_alphabet_size_line155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_configuration_in_configurations173 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_configuration_line_in_configuration191 = new BitSet(new long[]{0x0000000004002000L});
    public static final BitSet FOLLOW_initial_configuration_in_configuration196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONFIGURATION_in_configuration_line206 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_configuration_line208 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_configuration_line210 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_configuration_line212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membrane_structure_content_in_initial_configuration229 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_initial_configuration238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membrane_content_in_membrane_structure_content255 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_numerical_ident_in_membrane_content274 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_numerical_ident_in_membrane_content278 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_objects_list_in_membrane_content287 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_membrane_content290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinality_in_cardinalities300 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_LEFT_BRACKET_in_cardinality312 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_cardinality317 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_cardinality322 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_RIGHT_BRACKET_in_cardinality325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_numerical_ident343 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_numerical_ident348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_SQUARED_BRACKET_in_objects_list361 = new BitSet(new long[]{0x0000000001004000L});
    public static final BitSet FOLLOW_cardinalities_in_objects_list363 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_RIGHT_SQUARED_BRACKET_in_objects_list366 = new BitSet(new long[]{0x0000000000000002L});

}