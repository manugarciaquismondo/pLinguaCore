// $ANTLR 3.4 C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g 2013-03-07 10:23:29

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



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class Kernel_Simulator_Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALPHABET_SIZE", "COLON", "COMMA", "DOT", "EQUAL", "INTEGER", "LEFT_BRACKET", "LEFT_KEY_BRACKET", "LEFT_SQUARED_BRACKET", "MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE", "MAXIMUM_SIZE_OF_HAND_SIDE", "MEMBRANES", "NUMBER_OF_RULES", "OBJECTS", "RIGHT_BRACKET", "RIGHT_KEY_BRACKET", "RIGHT_SQUARED_BRACKET", "SEMICOLON", "SIGN", "STRING", "WS"
    };

    public static final int EOF=-1;
    public static final int ALPHABET_SIZE=4;
    public static final int COLON=5;
    public static final int COMMA=6;
    public static final int DOT=7;
    public static final int EQUAL=8;
    public static final int INTEGER=9;
    public static final int LEFT_BRACKET=10;
    public static final int LEFT_KEY_BRACKET=11;
    public static final int LEFT_SQUARED_BRACKET=12;
    public static final int MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE=13;
    public static final int MAXIMUM_SIZE_OF_HAND_SIDE=14;
    public static final int MEMBRANES=15;
    public static final int NUMBER_OF_RULES=16;
    public static final int OBJECTS=17;
    public static final int RIGHT_BRACKET=18;
    public static final int RIGHT_KEY_BRACKET=19;
    public static final int RIGHT_SQUARED_BRACKET=20;
    public static final int SEMICOLON=21;
    public static final int SIGN=22;
    public static final int STRING=23;
    public static final int WS=24;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public Kernel_Simulator_Parser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public Kernel_Simulator_Parser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return Kernel_Simulator_Parser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g"; }



      
      SimpleKernelLikePsystem psystem;
      TissueLikeMembraneStructure membraneStructure;
      UnitGuard guard; 
     


    public static class psystem_return extends ParserRuleReturnScope {
        public SimpleKernelLikePsystem  psystem;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "psystem"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:46:1: psystem returns [SimpleKernelLikePsystem psystem] : header initial_configuration rules ;
    public final Kernel_Simulator_Parser.psystem_return psystem() throws RecognitionException {
        Kernel_Simulator_Parser.psystem_return retval = new Kernel_Simulator_Parser.psystem_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Kernel_Simulator_Parser.header_return header1 =null;

        Kernel_Simulator_Parser.initial_configuration_return initial_configuration2 =null;

        Kernel_Simulator_Parser.rules_return rules3 =null;



        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:46:52: ( header initial_configuration rules )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:47:1: header initial_configuration rules
            {
            root_0 = (Object)adaptor.nil();


            psystem = new SimpleKernelLikePsystem();
            membraneStructure = new SimpleKernelLikeMembraneStructure((CellLikeSkinMembrane)CellLikeMembraneFactory.getCellLikeMembrane(new Label("environment")));
            psystem.setMembraneStructure(membraneStructure);


            pushFollow(FOLLOW_header_in_psystem64);
            header1=header();

            state._fsp--;

            adaptor.addChild(root_0, header1.getTree());

            pushFollow(FOLLOW_initial_configuration_in_psystem67);
            initial_configuration2=initial_configuration();

            state._fsp--;

            adaptor.addChild(root_0, initial_configuration2.getTree());

            pushFollow(FOLLOW_rules_in_psystem69);
            rules3=rules();

            state._fsp--;

            adaptor.addChild(root_0, rules3.getTree());

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
    // $ANTLR end "psystem"


    public static class header_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "header"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:57:1: header : membranes_line maximum_number_of_rules_per_membrane_line alphabet_size_line ;
    public final Kernel_Simulator_Parser.header_return header() throws RecognitionException {
        Kernel_Simulator_Parser.header_return retval = new Kernel_Simulator_Parser.header_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Kernel_Simulator_Parser.membranes_line_return membranes_line4 =null;

        Kernel_Simulator_Parser.maximum_number_of_rules_per_membrane_line_return maximum_number_of_rules_per_membrane_line5 =null;

        Kernel_Simulator_Parser.alphabet_size_line_return alphabet_size_line6 =null;



        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:57:7: ( membranes_line maximum_number_of_rules_per_membrane_line alphabet_size_line )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:58:1: membranes_line maximum_number_of_rules_per_membrane_line alphabet_size_line
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_membranes_line_in_header77);
            membranes_line4=membranes_line();

            state._fsp--;

            adaptor.addChild(root_0, membranes_line4.getTree());

            pushFollow(FOLLOW_maximum_number_of_rules_per_membrane_line_in_header79);
            maximum_number_of_rules_per_membrane_line5=maximum_number_of_rules_per_membrane_line();

            state._fsp--;

            adaptor.addChild(root_0, maximum_number_of_rules_per_membrane_line5.getTree());

            pushFollow(FOLLOW_alphabet_size_line_in_header81);
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


    public static class membranes_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membranes_line"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:61:1: membranes_line : MEMBRANES COLON INTEGER SEMICOLON ;
    public final Kernel_Simulator_Parser.membranes_line_return membranes_line() throws RecognitionException {
        Kernel_Simulator_Parser.membranes_line_return retval = new Kernel_Simulator_Parser.membranes_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token MEMBRANES7=null;
        Token COLON8=null;
        Token INTEGER9=null;
        Token SEMICOLON10=null;

        Object MEMBRANES7_tree=null;
        Object COLON8_tree=null;
        Object INTEGER9_tree=null;
        Object SEMICOLON10_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:61:15: ( MEMBRANES COLON INTEGER SEMICOLON )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:62:1: MEMBRANES COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            MEMBRANES7=(Token)match(input,MEMBRANES,FOLLOW_MEMBRANES_in_membranes_line90); 
            MEMBRANES7_tree = 
            (Object)adaptor.create(MEMBRANES7)
            ;
            adaptor.addChild(root_0, MEMBRANES7_tree);


            COLON8=(Token)match(input,COLON,FOLLOW_COLON_in_membranes_line92); 
            COLON8_tree = 
            (Object)adaptor.create(COLON8)
            ;
            adaptor.addChild(root_0, COLON8_tree);


            INTEGER9=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_membranes_line94); 
            INTEGER9_tree = 
            (Object)adaptor.create(INTEGER9)
            ;
            adaptor.addChild(root_0, INTEGER9_tree);


            SEMICOLON10=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_membranes_line96); 
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
    // $ANTLR end "membranes_line"


    public static class maximum_number_of_rules_per_membrane_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "maximum_number_of_rules_per_membrane_line"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:65:1: maximum_number_of_rules_per_membrane_line : MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON ;
    public final Kernel_Simulator_Parser.maximum_number_of_rules_per_membrane_line_return maximum_number_of_rules_per_membrane_line() throws RecognitionException {
        Kernel_Simulator_Parser.maximum_number_of_rules_per_membrane_line_return retval = new Kernel_Simulator_Parser.maximum_number_of_rules_per_membrane_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE11=null;
        Token COLON12=null;
        Token INTEGER13=null;
        Token SEMICOLON14=null;

        Object MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE11_tree=null;
        Object COLON12_tree=null;
        Object INTEGER13_tree=null;
        Object SEMICOLON14_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:65:42: ( MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:66:3: MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE11=(Token)match(input,MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE,FOLLOW_MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE_in_maximum_number_of_rules_per_membrane_line108); 
            MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE11_tree = 
            (Object)adaptor.create(MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE11)
            ;
            adaptor.addChild(root_0, MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE11_tree);


            COLON12=(Token)match(input,COLON,FOLLOW_COLON_in_maximum_number_of_rules_per_membrane_line110); 
            COLON12_tree = 
            (Object)adaptor.create(COLON12)
            ;
            adaptor.addChild(root_0, COLON12_tree);


            INTEGER13=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_maximum_number_of_rules_per_membrane_line112); 
            INTEGER13_tree = 
            (Object)adaptor.create(INTEGER13)
            ;
            adaptor.addChild(root_0, INTEGER13_tree);


            SEMICOLON14=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_maximum_number_of_rules_per_membrane_line114); 
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
    // $ANTLR end "maximum_number_of_rules_per_membrane_line"


    public static class alphabet_size_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "alphabet_size_line"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:68:1: alphabet_size_line : ALPHABET_SIZE COLON INTEGER SEMICOLON ;
    public final Kernel_Simulator_Parser.alphabet_size_line_return alphabet_size_line() throws RecognitionException {
        Kernel_Simulator_Parser.alphabet_size_line_return retval = new Kernel_Simulator_Parser.alphabet_size_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ALPHABET_SIZE15=null;
        Token COLON16=null;
        Token INTEGER17=null;
        Token SEMICOLON18=null;

        Object ALPHABET_SIZE15_tree=null;
        Object COLON16_tree=null;
        Object INTEGER17_tree=null;
        Object SEMICOLON18_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:68:19: ( ALPHABET_SIZE COLON INTEGER SEMICOLON )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:69:3: ALPHABET_SIZE COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            ALPHABET_SIZE15=(Token)match(input,ALPHABET_SIZE,FOLLOW_ALPHABET_SIZE_in_alphabet_size_line124); 
            ALPHABET_SIZE15_tree = 
            (Object)adaptor.create(ALPHABET_SIZE15)
            ;
            adaptor.addChild(root_0, ALPHABET_SIZE15_tree);


            COLON16=(Token)match(input,COLON,FOLLOW_COLON_in_alphabet_size_line126); 
            COLON16_tree = 
            (Object)adaptor.create(COLON16)
            ;
            adaptor.addChild(root_0, COLON16_tree);


            INTEGER17=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_alphabet_size_line128); 
            INTEGER17_tree = 
            (Object)adaptor.create(INTEGER17)
            ;
            adaptor.addChild(root_0, INTEGER17_tree);


            SEMICOLON18=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_alphabet_size_line130); 
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
    // $ANTLR end "alphabet_size_line"


    public static class initial_configuration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "initial_configuration"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:71:1: initial_configuration : membrane_structure_content SEMICOLON ;
    public final Kernel_Simulator_Parser.initial_configuration_return initial_configuration() throws RecognitionException {
        Kernel_Simulator_Parser.initial_configuration_return retval = new Kernel_Simulator_Parser.initial_configuration_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON20=null;
        Kernel_Simulator_Parser.membrane_structure_content_return membrane_structure_content19 =null;


        Object SEMICOLON20_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:71:22: ( membrane_structure_content SEMICOLON )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:72:3: membrane_structure_content SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_membrane_structure_content_in_initial_configuration142);
            membrane_structure_content19=membrane_structure_content();

            state._fsp--;

            adaptor.addChild(root_0, membrane_structure_content19.getTree());

            SEMICOLON20=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_initial_configuration144); 
            SEMICOLON20_tree = 
            (Object)adaptor.create(SEMICOLON20)
            ;
            adaptor.addChild(root_0, SEMICOLON20_tree);


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
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membrane_structure_content"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:74:1: membrane_structure_content : ( membrane_content )* ;
    public final Kernel_Simulator_Parser.membrane_structure_content_return membrane_structure_content() throws RecognitionException {
        Kernel_Simulator_Parser.membrane_structure_content_return retval = new Kernel_Simulator_Parser.membrane_structure_content_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Kernel_Simulator_Parser.membrane_content_return membrane_content21 =null;



        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:74:27: ( ( membrane_content )* )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:75:3: ( membrane_content )*
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:75:3: ( membrane_content )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==INTEGER) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:75:4: membrane_content
            	    {
            	    pushFollow(FOLLOW_membrane_content_in_membrane_structure_content155);
            	    membrane_content21=membrane_content();

            	    state._fsp--;

            	    adaptor.addChild(root_0, membrane_content21.getTree());

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
    // $ANTLR end "membrane_structure_content"


    public static class membrane_content_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membrane_content"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:77:1: membrane_content : membrane_index= numerical_ident numerical_ident objects_list[membrane.getMultiSet()] SEMICOLON ;
    public final Kernel_Simulator_Parser.membrane_content_return membrane_content() throws RecognitionException {
        Kernel_Simulator_Parser.membrane_content_return retval = new Kernel_Simulator_Parser.membrane_content_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON24=null;
        Kernel_Simulator_Parser.numerical_ident_return membrane_index =null;

        Kernel_Simulator_Parser.numerical_ident_return numerical_ident22 =null;

        Kernel_Simulator_Parser.objects_list_return objects_list23 =null;


        Object SEMICOLON24_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:77:17: (membrane_index= numerical_ident numerical_ident objects_list[membrane.getMultiSet()] SEMICOLON )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:79:2: membrane_index= numerical_ident numerical_ident objects_list[membrane.getMultiSet()] SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_membrane_content170);
            membrane_index=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, membrane_index.getTree());

            pushFollow(FOLLOW_numerical_ident_in_membrane_content173);
            numerical_ident22=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident22.getTree());


            	SimpleKernelLikeMembrane membrane = SimpleKernelLikeMembraneFactory.getKernelLikeMembrane(membrane_index.identifier, membraneStructure);

            pushFollow(FOLLOW_objects_list_in_membrane_content180);
            objects_list23=objects_list(membrane.getMultiSet());

            state._fsp--;

            adaptor.addChild(root_0, objects_list23.getTree());

            SEMICOLON24=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_membrane_content183); 
            SEMICOLON24_tree = 
            (Object)adaptor.create(SEMICOLON24)
            ;
            adaptor.addChild(root_0, SEMICOLON24_tree);


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
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:85:1: cardinalities[MultiSet<String> multiset] : ( cardinality[multiset] )* ;
    public final Kernel_Simulator_Parser.cardinalities_return cardinalities(MultiSet<String> multiset) throws RecognitionException {
        Kernel_Simulator_Parser.cardinalities_return retval = new Kernel_Simulator_Parser.cardinalities_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Kernel_Simulator_Parser.cardinality_return cardinality25 =null;



        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:85:41: ( ( cardinality[multiset] )* )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:86:2: ( cardinality[multiset] )*
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:86:2: ( cardinality[multiset] )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==LEFT_BRACKET) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:86:2: cardinality[multiset]
            	    {
            	    pushFollow(FOLLOW_cardinality_in_cardinalities193);
            	    cardinality25=cardinality(multiset);

            	    state._fsp--;

            	    adaptor.addChild(root_0, cardinality25.getTree());

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
    // $ANTLR end "cardinalities"


    public static class cardinality_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cardinality"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:88:1: cardinality[MultiSet<String> multiset] : LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET ;
    public final Kernel_Simulator_Parser.cardinality_return cardinality(MultiSet<String> multiset) throws RecognitionException {
        Kernel_Simulator_Parser.cardinality_return retval = new Kernel_Simulator_Parser.cardinality_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token object_token=null;
        Token cardinality_token=null;
        Token LEFT_BRACKET26=null;
        Token RIGHT_BRACKET27=null;

        Object object_token_tree=null;
        Object cardinality_token_tree=null;
        Object LEFT_BRACKET26_tree=null;
        Object RIGHT_BRACKET27_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:88:39: ( LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:89:1: LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET
            {
            root_0 = (Object)adaptor.nil();


            LEFT_BRACKET26=(Token)match(input,LEFT_BRACKET,FOLLOW_LEFT_BRACKET_in_cardinality205); 
            LEFT_BRACKET26_tree = 
            (Object)adaptor.create(LEFT_BRACKET26)
            ;
            adaptor.addChild(root_0, LEFT_BRACKET26_tree);


            object_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_cardinality210); 
            object_token_tree = 
            (Object)adaptor.create(object_token)
            ;
            adaptor.addChild(root_0, object_token_tree);


            cardinality_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_cardinality215); 
            cardinality_token_tree = 
            (Object)adaptor.create(cardinality_token)
            ;
            adaptor.addChild(root_0, cardinality_token_tree);


            RIGHT_BRACKET27=(Token)match(input,RIGHT_BRACKET,FOLLOW_RIGHT_BRACKET_in_cardinality218); 
            RIGHT_BRACKET27_tree = 
            (Object)adaptor.create(RIGHT_BRACKET27)
            ;
            adaptor.addChild(root_0, RIGHT_BRACKET27_tree);


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


    public static class rules_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rules"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:96:1: rules : ( rule )* SEMICOLON ;
    public final Kernel_Simulator_Parser.rules_return rules() throws RecognitionException {
        Kernel_Simulator_Parser.rules_return retval = new Kernel_Simulator_Parser.rules_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON29=null;
        Kernel_Simulator_Parser.rule_return rule28 =null;


        Object SEMICOLON29_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:96:6: ( ( rule )* SEMICOLON )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:97:2: ( rule )* SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:97:2: ( rule )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==INTEGER) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:97:2: rule
            	    {
            	    pushFollow(FOLLOW_rule_in_rules231);
            	    rule28=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_0, rule28.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            SEMICOLON29=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_rules235); 
            SEMICOLON29_tree = 
            (Object)adaptor.create(SEMICOLON29)
            ;
            adaptor.addChild(root_0, SEMICOLON29_tree);


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
    // $ANTLR end "rules"


    public static class rule_return extends ParserRuleReturnScope {
        public IRule rule;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rule"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:99:1: rule returns [IRule rule] : leftHandSide= left_hand_side COLON rightHandSide= right_hand_side ;
    public final Kernel_Simulator_Parser.rule_return rule() throws RecognitionException {
        Kernel_Simulator_Parser.rule_return retval = new Kernel_Simulator_Parser.rule_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token COLON30=null;
        Kernel_Simulator_Parser.left_hand_side_return leftHandSide =null;

        Kernel_Simulator_Parser.right_hand_side_return rightHandSide =null;


        Object COLON30_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:99:26: (leftHandSide= left_hand_side COLON rightHandSide= right_hand_side )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:100:13: leftHandSide= left_hand_side COLON rightHandSide= right_hand_side
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_left_hand_side_in_rule250);
            leftHandSide=left_hand_side();

            state._fsp--;

            adaptor.addChild(root_0, leftHandSide.getTree());

            COLON30=(Token)match(input,COLON,FOLLOW_COLON_in_rule252); 
            COLON30_tree = 
            (Object)adaptor.create(COLON30)
            ;
            adaptor.addChild(root_0, COLON30_tree);


            pushFollow(FOLLOW_right_hand_side_in_rule256);
            rightHandSide=right_hand_side();

            state._fsp--;

            adaptor.addChild(root_0, rightHandSide.getTree());

            IRule rule = (new KernelRuleFactory()).createKernelRule(false, leftHandSide.leftHandSide, rightHandSide.rightHandSide, guard, KernelRuleTypes.EVOLUTION);

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
    // $ANTLR end "rule"


    public static class left_hand_side_return extends ParserRuleReturnScope {
        public LeftHandRule leftHandSide;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "left_hand_side"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:104:1: left_hand_side returns [LeftHandRule leftHandSide] : membrane_index= numerical_ident numerical_ident rule_guard= guard objects_list[multiSet] ;
    public final Kernel_Simulator_Parser.left_hand_side_return left_hand_side() throws RecognitionException {
        Kernel_Simulator_Parser.left_hand_side_return retval = new Kernel_Simulator_Parser.left_hand_side_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Kernel_Simulator_Parser.numerical_ident_return membrane_index =null;

        Kernel_Simulator_Parser.guard_return rule_guard =null;

        Kernel_Simulator_Parser.numerical_ident_return numerical_ident31 =null;

        Kernel_Simulator_Parser.objects_list_return objects_list32 =null;



        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:104:51: (membrane_index= numerical_ident numerical_ident rule_guard= guard objects_list[multiSet] )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:106:15: membrane_index= numerical_ident numerical_ident rule_guard= guard objects_list[multiSet]
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_left_hand_side274);
            membrane_index=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, membrane_index.getTree());

            MultiSet<String> multiSet = new HashMultiSet<String>();
            retval.leftHandSide = new LeftHandRule(new OuterRuleMembrane(new Label(membrane_index.identifier), (byte)0, multiSet), new HashMultiSet<String>());


            pushFollow(FOLLOW_numerical_ident_in_left_hand_side279);
            numerical_ident31=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident31.getTree());

            pushFollow(FOLLOW_guard_in_left_hand_side283);
            rule_guard=guard();

            state._fsp--;

            adaptor.addChild(root_0, rule_guard.getTree());

            guard=rule_guard.guard;

            pushFollow(FOLLOW_objects_list_in_left_hand_side286);
            objects_list32=objects_list(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, objects_list32.getTree());

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
    // $ANTLR end "left_hand_side"


    public static class right_hand_side_return extends ParserRuleReturnScope {
        public RightHandRule rightHandSide;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "right_hand_side"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:115:1: right_hand_side returns [RightHandRule rightHandSide] : membrane_destination= numerical_ident objects_list[multiSet] SEMICOLON ;
    public final Kernel_Simulator_Parser.right_hand_side_return right_hand_side() throws RecognitionException {
        Kernel_Simulator_Parser.right_hand_side_return retval = new Kernel_Simulator_Parser.right_hand_side_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON34=null;
        Kernel_Simulator_Parser.numerical_ident_return membrane_destination =null;

        Kernel_Simulator_Parser.objects_list_return objects_list33 =null;


        Object SEMICOLON34_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:115:54: (membrane_destination= numerical_ident objects_list[multiSet] SEMICOLON )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:116:21: membrane_destination= numerical_ident objects_list[multiSet] SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_right_hand_side303);
            membrane_destination=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, membrane_destination.getTree());

            MultiSet<String> multiSet = new HashMultiSet<String>();
            retval.rightHandSide = new RightHandRule(new OuterRuleMembrane(new Label(membrane_destination.identifier), (byte)0, multiSet), new HashMultiSet<String>());


            pushFollow(FOLLOW_objects_list_in_right_hand_side308);
            objects_list33=objects_list(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, objects_list33.getTree());

            SEMICOLON34=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_right_hand_side311); 
            SEMICOLON34_tree = 
            (Object)adaptor.create(SEMICOLON34)
            ;
            adaptor.addChild(root_0, SEMICOLON34_tree);


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
    // $ANTLR end "right_hand_side"


    public static class numerical_ident_return extends ParserRuleReturnScope {
        public String identifier;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "numerical_ident"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:123:1: numerical_ident returns [String identifier] : membrane_index_token= INTEGER COLON ;
    public final Kernel_Simulator_Parser.numerical_ident_return numerical_ident() throws RecognitionException {
        Kernel_Simulator_Parser.numerical_ident_return retval = new Kernel_Simulator_Parser.numerical_ident_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token membrane_index_token=null;
        Token COLON35=null;

        Object membrane_index_token_tree=null;
        Object COLON35_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:123:44: (membrane_index_token= INTEGER COLON )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:124:21: membrane_index_token= INTEGER COLON
            {
            root_0 = (Object)adaptor.nil();


            membrane_index_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_numerical_ident325); 
            membrane_index_token_tree = 
            (Object)adaptor.create(membrane_index_token)
            ;
            adaptor.addChild(root_0, membrane_index_token_tree);


            retval.identifier=(membrane_index_token!=null?membrane_index_token.getText():null);

            COLON35=(Token)match(input,COLON,FOLLOW_COLON_in_numerical_ident330); 
            COLON35_tree = 
            (Object)adaptor.create(COLON35)
            ;
            adaptor.addChild(root_0, COLON35_tree);


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
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:130:1: objects_list[MultiSet<String> multiset] : LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET ;
    public final Kernel_Simulator_Parser.objects_list_return objects_list(MultiSet<String> multiset) throws RecognitionException {
        Kernel_Simulator_Parser.objects_list_return retval = new Kernel_Simulator_Parser.objects_list_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token LEFT_SQUARED_BRACKET36=null;
        Token RIGHT_SQUARED_BRACKET38=null;
        Kernel_Simulator_Parser.cardinalities_return cardinalities37 =null;


        Object LEFT_SQUARED_BRACKET36_tree=null;
        Object RIGHT_SQUARED_BRACKET38_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:130:41: ( LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:131:1: LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET
            {
            root_0 = (Object)adaptor.nil();


            LEFT_SQUARED_BRACKET36=(Token)match(input,LEFT_SQUARED_BRACKET,FOLLOW_LEFT_SQUARED_BRACKET_in_objects_list343); 
            LEFT_SQUARED_BRACKET36_tree = 
            (Object)adaptor.create(LEFT_SQUARED_BRACKET36)
            ;
            adaptor.addChild(root_0, LEFT_SQUARED_BRACKET36_tree);


            pushFollow(FOLLOW_cardinalities_in_objects_list345);
            cardinalities37=cardinalities(multiset);

            state._fsp--;

            adaptor.addChild(root_0, cardinalities37.getTree());

            RIGHT_SQUARED_BRACKET38=(Token)match(input,RIGHT_SQUARED_BRACKET,FOLLOW_RIGHT_SQUARED_BRACKET_in_objects_list348); 
            RIGHT_SQUARED_BRACKET38_tree = 
            (Object)adaptor.create(RIGHT_SQUARED_BRACKET38)
            ;
            adaptor.addChild(root_0, RIGHT_SQUARED_BRACKET38_tree);


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


    public static class guard_return extends ParserRuleReturnScope {
        public UnitGuard guard;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "guard"
    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:133:1: guard returns [UnitGuard guard] : (guard_sign= INTEGER guard_token= INTEGER DOT )? ;
    public final Kernel_Simulator_Parser.guard_return guard() throws RecognitionException {
        Kernel_Simulator_Parser.guard_return retval = new Kernel_Simulator_Parser.guard_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token guard_sign=null;
        Token guard_token=null;
        Token DOT39=null;

        Object guard_sign_tree=null;
        Object guard_token_tree=null;
        Object DOT39_tree=null;

        try {
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:133:32: ( (guard_sign= INTEGER guard_token= INTEGER DOT )? )
            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:134:1: (guard_sign= INTEGER guard_token= INTEGER DOT )?
            {
            root_0 = (Object)adaptor.nil();


            guard=null; short sign;

            // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:135:2: (guard_sign= INTEGER guard_token= INTEGER DOT )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==INTEGER) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\miguel\\workspace\\pLinguaCore4_all\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Kernel_Simulator_Parser.g:135:2: guard_sign= INTEGER guard_token= INTEGER DOT
                    {
                    guard_sign=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_guard366); 
                    guard_sign_tree = 
                    (Object)adaptor.create(guard_sign)
                    ;
                    adaptor.addChild(root_0, guard_sign_tree);


                    sign=(Integer.parseInt((guard_sign!=null?guard_sign.getText():null))==1)? ComparationMasks.MINUS :ComparationMasks.PLUS;

                    guard_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_guard371); 
                    guard_token_tree = 
                    (Object)adaptor.create(guard_token)
                    ;
                    adaptor.addChild(root_0, guard_token_tree);


                    retval.guard= new UnitGuard(ComparationMasks.GREATER_OR_EQUAL_THAN, sign , (guard_token!=null?guard_token.getText():null), 1);

                    DOT39=(Token)match(input,DOT,FOLLOW_DOT_in_guard374); 
                    DOT39_tree = 
                    (Object)adaptor.create(DOT39)
                    ;
                    adaptor.addChild(root_0, DOT39_tree);


                    }
                    break;

            }


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
    // $ANTLR end "guard"

    // Delegated rules


 

    public static final BitSet FOLLOW_header_in_psystem64 = new BitSet(new long[]{0x0000000000200200L});
    public static final BitSet FOLLOW_initial_configuration_in_psystem67 = new BitSet(new long[]{0x0000000000200200L});
    public static final BitSet FOLLOW_rules_in_psystem69 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membranes_line_in_header77 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_maximum_number_of_rules_per_membrane_line_in_header79 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_alphabet_size_line_in_header81 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MEMBRANES_in_membranes_line90 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_membranes_line92 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_membranes_line94 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_SEMICOLON_in_membranes_line96 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE_in_maximum_number_of_rules_per_membrane_line108 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_maximum_number_of_rules_per_membrane_line110 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_maximum_number_of_rules_per_membrane_line112 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_SEMICOLON_in_maximum_number_of_rules_per_membrane_line114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ALPHABET_SIZE_in_alphabet_size_line124 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_alphabet_size_line126 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_alphabet_size_line128 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_SEMICOLON_in_alphabet_size_line130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membrane_structure_content_in_initial_configuration142 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_SEMICOLON_in_initial_configuration144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membrane_content_in_membrane_structure_content155 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_numerical_ident_in_membrane_content170 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_numerical_ident_in_membrane_content173 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_objects_list_in_membrane_content180 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_SEMICOLON_in_membrane_content183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinality_in_cardinalities193 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_LEFT_BRACKET_in_cardinality205 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_cardinality210 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_cardinality215 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_RIGHT_BRACKET_in_cardinality218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_rules231 = new BitSet(new long[]{0x0000000000200200L});
    public static final BitSet FOLLOW_SEMICOLON_in_rules235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_left_hand_side_in_rule250 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_rule252 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_right_hand_side_in_rule256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numerical_ident_in_left_hand_side274 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_numerical_ident_in_left_hand_side279 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_guard_in_left_hand_side283 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_objects_list_in_left_hand_side286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numerical_ident_in_right_hand_side303 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_objects_list_in_right_hand_side308 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_SEMICOLON_in_right_hand_side311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_numerical_ident325 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_numerical_ident330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_SQUARED_BRACKET_in_objects_list343 = new BitSet(new long[]{0x0000000000100400L});
    public static final BitSet FOLLOW_cardinalities_in_objects_list345 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RIGHT_SQUARED_BRACKET_in_objects_list348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_guard366 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_guard371 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_DOT_in_guard374 = new BitSet(new long[]{0x0000000000000002L});

}