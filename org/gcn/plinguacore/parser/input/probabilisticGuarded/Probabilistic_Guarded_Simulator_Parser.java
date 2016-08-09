// $ANTLR 3.4 C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g 2013-12-26 13:19:10

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



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class Probabilistic_Guarded_Simulator_Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALPHABET_SIZE", "COLON", "FLAGS", "FLAGS_SIZE", "FLOAT", "INTEGER", "LEFT_BRACKET", "LEFT_SQUARED_BRACKET", "MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE", "MEMBRANES", "NUMBER_OF_BLOCKS", "RIGHT_BRACKET", "RIGHT_SQUARED_BRACKET", "RULES", "SEMICOLON", "SHARP"
    };

    public static final int EOF=-1;
    public static final int ALPHABET_SIZE=4;
    public static final int COLON=5;
    public static final int FLAGS=6;
    public static final int FLAGS_SIZE=7;
    public static final int FLOAT=8;
    public static final int INTEGER=9;
    public static final int LEFT_BRACKET=10;
    public static final int LEFT_SQUARED_BRACKET=11;
    public static final int MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE=12;
    public static final int MEMBRANES=13;
    public static final int NUMBER_OF_BLOCKS=14;
    public static final int RIGHT_BRACKET=15;
    public static final int RIGHT_SQUARED_BRACKET=16;
    public static final int RULES=17;
    public static final int SEMICOLON=18;
    public static final int SHARP=19;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public Probabilistic_Guarded_Simulator_Parser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public Probabilistic_Guarded_Simulator_Parser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return Probabilistic_Guarded_Simulator_Parser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g"; }



      
      SimpleKernelLikeMembraneStructure membraneStructure;
      RestrictiveGuard guard; 
     


    public static class psystem_return extends ParserRuleReturnScope {
        public ProbabilisticGuardedPsystem  psystem;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "psystem"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:50:1: psystem returns [ProbabilisticGuardedPsystem psystem] : header initial_configuration flags[retval.psystem] rules[retval.psystem] blocks ;
    public final Probabilistic_Guarded_Simulator_Parser.psystem_return psystem() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.psystem_return retval = new Probabilistic_Guarded_Simulator_Parser.psystem_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Probabilistic_Guarded_Simulator_Parser.header_return header1 =null;

        Probabilistic_Guarded_Simulator_Parser.initial_configuration_return initial_configuration2 =null;

        Probabilistic_Guarded_Simulator_Parser.flags_return flags3 =null;

        Probabilistic_Guarded_Simulator_Parser.rules_return rules4 =null;

        Probabilistic_Guarded_Simulator_Parser.blocks_return blocks5 =null;



        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:50:56: ( header initial_configuration flags[retval.psystem] rules[retval.psystem] blocks )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:51:1: header initial_configuration flags[retval.psystem] rules[retval.psystem] blocks
            {
            root_0 = (Object)adaptor.nil();


            retval.psystem = new ProbabilisticGuardedPsystem();

            pushFollow(FOLLOW_header_in_psystem64);
            header1=header();

            state._fsp--;

            adaptor.addChild(root_0, header1.getTree());

            pushFollow(FOLLOW_initial_configuration_in_psystem68);
            initial_configuration2=initial_configuration();

            state._fsp--;

            adaptor.addChild(root_0, initial_configuration2.getTree());

            retval.psystem.setMembraneStructure(membraneStructure);

            pushFollow(FOLLOW_flags_in_psystem72);
            flags3=flags(retval.psystem);

            state._fsp--;

            adaptor.addChild(root_0, flags3.getTree());

            pushFollow(FOLLOW_rules_in_psystem75);
            rules4=rules(retval.psystem);

            state._fsp--;

            adaptor.addChild(root_0, rules4.getTree());

            pushFollow(FOLLOW_blocks_in_psystem78);
            blocks5=blocks();

            state._fsp--;

            adaptor.addChild(root_0, blocks5.getTree());

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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:63:1: header : membranes_line rules_line maximum_number_of_rules_per_membrane_line alphabet_size_line flags_line blocks_line ;
    public final Probabilistic_Guarded_Simulator_Parser.header_return header() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.header_return retval = new Probabilistic_Guarded_Simulator_Parser.header_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Probabilistic_Guarded_Simulator_Parser.membranes_line_return membranes_line6 =null;

        Probabilistic_Guarded_Simulator_Parser.rules_line_return rules_line7 =null;

        Probabilistic_Guarded_Simulator_Parser.maximum_number_of_rules_per_membrane_line_return maximum_number_of_rules_per_membrane_line8 =null;

        Probabilistic_Guarded_Simulator_Parser.alphabet_size_line_return alphabet_size_line9 =null;

        Probabilistic_Guarded_Simulator_Parser.flags_line_return flags_line10 =null;

        Probabilistic_Guarded_Simulator_Parser.blocks_line_return blocks_line11 =null;



        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:63:7: ( membranes_line rules_line maximum_number_of_rules_per_membrane_line alphabet_size_line flags_line blocks_line )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:64:1: membranes_line rules_line maximum_number_of_rules_per_membrane_line alphabet_size_line flags_line blocks_line
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_membranes_line_in_header87);
            membranes_line6=membranes_line();

            state._fsp--;

            adaptor.addChild(root_0, membranes_line6.getTree());

            pushFollow(FOLLOW_rules_line_in_header90);
            rules_line7=rules_line();

            state._fsp--;

            adaptor.addChild(root_0, rules_line7.getTree());

            pushFollow(FOLLOW_maximum_number_of_rules_per_membrane_line_in_header92);
            maximum_number_of_rules_per_membrane_line8=maximum_number_of_rules_per_membrane_line();

            state._fsp--;

            adaptor.addChild(root_0, maximum_number_of_rules_per_membrane_line8.getTree());

            pushFollow(FOLLOW_alphabet_size_line_in_header95);
            alphabet_size_line9=alphabet_size_line();

            state._fsp--;

            adaptor.addChild(root_0, alphabet_size_line9.getTree());

            pushFollow(FOLLOW_flags_line_in_header97);
            flags_line10=flags_line();

            state._fsp--;

            adaptor.addChild(root_0, flags_line10.getTree());

            pushFollow(FOLLOW_blocks_line_in_header99);
            blocks_line11=blocks_line();

            state._fsp--;

            adaptor.addChild(root_0, blocks_line11.getTree());

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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:72:1: membranes_line : MEMBRANES COLON INTEGER SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.membranes_line_return membranes_line() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.membranes_line_return retval = new Probabilistic_Guarded_Simulator_Parser.membranes_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token MEMBRANES12=null;
        Token COLON13=null;
        Token INTEGER14=null;
        Token SEMICOLON15=null;

        Object MEMBRANES12_tree=null;
        Object COLON13_tree=null;
        Object INTEGER14_tree=null;
        Object SEMICOLON15_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:72:15: ( MEMBRANES COLON INTEGER SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:73:1: MEMBRANES COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            MEMBRANES12=(Token)match(input,MEMBRANES,FOLLOW_MEMBRANES_in_membranes_line108); 
            MEMBRANES12_tree = 
            (Object)adaptor.create(MEMBRANES12)
            ;
            adaptor.addChild(root_0, MEMBRANES12_tree);


            COLON13=(Token)match(input,COLON,FOLLOW_COLON_in_membranes_line110); 
            COLON13_tree = 
            (Object)adaptor.create(COLON13)
            ;
            adaptor.addChild(root_0, COLON13_tree);


            INTEGER14=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_membranes_line112); 
            INTEGER14_tree = 
            (Object)adaptor.create(INTEGER14)
            ;
            adaptor.addChild(root_0, INTEGER14_tree);


            SEMICOLON15=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_membranes_line114); 
            SEMICOLON15_tree = 
            (Object)adaptor.create(SEMICOLON15)
            ;
            adaptor.addChild(root_0, SEMICOLON15_tree);


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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:76:1: rules_line : RULES COLON INTEGER SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.rules_line_return rules_line() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.rules_line_return retval = new Probabilistic_Guarded_Simulator_Parser.rules_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token RULES16=null;
        Token COLON17=null;
        Token INTEGER18=null;
        Token SEMICOLON19=null;

        Object RULES16_tree=null;
        Object COLON17_tree=null;
        Object INTEGER18_tree=null;
        Object SEMICOLON19_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:76:11: ( RULES COLON INTEGER SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:77:1: RULES COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            RULES16=(Token)match(input,RULES,FOLLOW_RULES_in_rules_line122); 
            RULES16_tree = 
            (Object)adaptor.create(RULES16)
            ;
            adaptor.addChild(root_0, RULES16_tree);


            COLON17=(Token)match(input,COLON,FOLLOW_COLON_in_rules_line124); 
            COLON17_tree = 
            (Object)adaptor.create(COLON17)
            ;
            adaptor.addChild(root_0, COLON17_tree);


            INTEGER18=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_rules_line126); 
            INTEGER18_tree = 
            (Object)adaptor.create(INTEGER18)
            ;
            adaptor.addChild(root_0, INTEGER18_tree);


            SEMICOLON19=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_rules_line128); 
            SEMICOLON19_tree = 
            (Object)adaptor.create(SEMICOLON19)
            ;
            adaptor.addChild(root_0, SEMICOLON19_tree);


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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:80:1: maximum_number_of_rules_per_membrane_line : MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.maximum_number_of_rules_per_membrane_line_return maximum_number_of_rules_per_membrane_line() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.maximum_number_of_rules_per_membrane_line_return retval = new Probabilistic_Guarded_Simulator_Parser.maximum_number_of_rules_per_membrane_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE20=null;
        Token COLON21=null;
        Token INTEGER22=null;
        Token SEMICOLON23=null;

        Object MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE20_tree=null;
        Object COLON21_tree=null;
        Object INTEGER22_tree=null;
        Object SEMICOLON23_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:80:42: ( MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:81:3: MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE20=(Token)match(input,MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE,FOLLOW_MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE_in_maximum_number_of_rules_per_membrane_line139); 
            MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE20_tree = 
            (Object)adaptor.create(MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE20)
            ;
            adaptor.addChild(root_0, MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE20_tree);


            COLON21=(Token)match(input,COLON,FOLLOW_COLON_in_maximum_number_of_rules_per_membrane_line141); 
            COLON21_tree = 
            (Object)adaptor.create(COLON21)
            ;
            adaptor.addChild(root_0, COLON21_tree);


            INTEGER22=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_maximum_number_of_rules_per_membrane_line143); 
            INTEGER22_tree = 
            (Object)adaptor.create(INTEGER22)
            ;
            adaptor.addChild(root_0, INTEGER22_tree);


            SEMICOLON23=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_maximum_number_of_rules_per_membrane_line145); 
            SEMICOLON23_tree = 
            (Object)adaptor.create(SEMICOLON23)
            ;
            adaptor.addChild(root_0, SEMICOLON23_tree);


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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:83:1: alphabet_size_line : ALPHABET_SIZE COLON INTEGER SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.alphabet_size_line_return alphabet_size_line() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.alphabet_size_line_return retval = new Probabilistic_Guarded_Simulator_Parser.alphabet_size_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token ALPHABET_SIZE24=null;
        Token COLON25=null;
        Token INTEGER26=null;
        Token SEMICOLON27=null;

        Object ALPHABET_SIZE24_tree=null;
        Object COLON25_tree=null;
        Object INTEGER26_tree=null;
        Object SEMICOLON27_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:83:19: ( ALPHABET_SIZE COLON INTEGER SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:84:3: ALPHABET_SIZE COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            ALPHABET_SIZE24=(Token)match(input,ALPHABET_SIZE,FOLLOW_ALPHABET_SIZE_in_alphabet_size_line155); 
            ALPHABET_SIZE24_tree = 
            (Object)adaptor.create(ALPHABET_SIZE24)
            ;
            adaptor.addChild(root_0, ALPHABET_SIZE24_tree);


            COLON25=(Token)match(input,COLON,FOLLOW_COLON_in_alphabet_size_line157); 
            COLON25_tree = 
            (Object)adaptor.create(COLON25)
            ;
            adaptor.addChild(root_0, COLON25_tree);


            INTEGER26=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_alphabet_size_line159); 
            INTEGER26_tree = 
            (Object)adaptor.create(INTEGER26)
            ;
            adaptor.addChild(root_0, INTEGER26_tree);


            SEMICOLON27=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_alphabet_size_line161); 
            SEMICOLON27_tree = 
            (Object)adaptor.create(SEMICOLON27)
            ;
            adaptor.addChild(root_0, SEMICOLON27_tree);


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


    public static class flags_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "flags_line"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:86:1: flags_line : FLAGS_SIZE COLON INTEGER SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.flags_line_return flags_line() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.flags_line_return retval = new Probabilistic_Guarded_Simulator_Parser.flags_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token FLAGS_SIZE28=null;
        Token COLON29=null;
        Token INTEGER30=null;
        Token SEMICOLON31=null;

        Object FLAGS_SIZE28_tree=null;
        Object COLON29_tree=null;
        Object INTEGER30_tree=null;
        Object SEMICOLON31_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:86:11: ( FLAGS_SIZE COLON INTEGER SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:87:2: FLAGS_SIZE COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            FLAGS_SIZE28=(Token)match(input,FLAGS_SIZE,FOLLOW_FLAGS_SIZE_in_flags_line170); 
            FLAGS_SIZE28_tree = 
            (Object)adaptor.create(FLAGS_SIZE28)
            ;
            adaptor.addChild(root_0, FLAGS_SIZE28_tree);


            COLON29=(Token)match(input,COLON,FOLLOW_COLON_in_flags_line172); 
            COLON29_tree = 
            (Object)adaptor.create(COLON29)
            ;
            adaptor.addChild(root_0, COLON29_tree);


            INTEGER30=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_flags_line174); 
            INTEGER30_tree = 
            (Object)adaptor.create(INTEGER30)
            ;
            adaptor.addChild(root_0, INTEGER30_tree);


            SEMICOLON31=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_flags_line176); 
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
    // $ANTLR end "flags_line"


    public static class blocks_line_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "blocks_line"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:89:2: blocks_line : NUMBER_OF_BLOCKS COLON INTEGER SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.blocks_line_return blocks_line() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.blocks_line_return retval = new Probabilistic_Guarded_Simulator_Parser.blocks_line_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token NUMBER_OF_BLOCKS32=null;
        Token COLON33=null;
        Token INTEGER34=null;
        Token SEMICOLON35=null;

        Object NUMBER_OF_BLOCKS32_tree=null;
        Object COLON33_tree=null;
        Object INTEGER34_tree=null;
        Object SEMICOLON35_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:89:13: ( NUMBER_OF_BLOCKS COLON INTEGER SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:90:2: NUMBER_OF_BLOCKS COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            NUMBER_OF_BLOCKS32=(Token)match(input,NUMBER_OF_BLOCKS,FOLLOW_NUMBER_OF_BLOCKS_in_blocks_line186); 
            NUMBER_OF_BLOCKS32_tree = 
            (Object)adaptor.create(NUMBER_OF_BLOCKS32)
            ;
            adaptor.addChild(root_0, NUMBER_OF_BLOCKS32_tree);


            COLON33=(Token)match(input,COLON,FOLLOW_COLON_in_blocks_line188); 
            COLON33_tree = 
            (Object)adaptor.create(COLON33)
            ;
            adaptor.addChild(root_0, COLON33_tree);


            INTEGER34=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_blocks_line190); 
            INTEGER34_tree = 
            (Object)adaptor.create(INTEGER34)
            ;
            adaptor.addChild(root_0, INTEGER34_tree);


            SEMICOLON35=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_blocks_line192); 
            SEMICOLON35_tree = 
            (Object)adaptor.create(SEMICOLON35)
            ;
            adaptor.addChild(root_0, SEMICOLON35_tree);


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
    // $ANTLR end "blocks_line"


    public static class initial_configuration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "initial_configuration"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:92:1: initial_configuration : membrane_structure_content SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.initial_configuration_return initial_configuration() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.initial_configuration_return retval = new Probabilistic_Guarded_Simulator_Parser.initial_configuration_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON37=null;
        Probabilistic_Guarded_Simulator_Parser.membrane_structure_content_return membrane_structure_content36 =null;


        Object SEMICOLON37_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:92:22: ( membrane_structure_content SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:93:3: membrane_structure_content SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_membrane_structure_content_in_initial_configuration203);
            membrane_structure_content36=membrane_structure_content();

            state._fsp--;

            adaptor.addChild(root_0, membrane_structure_content36.getTree());

            SEMICOLON37=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_initial_configuration205); 
            SEMICOLON37_tree = 
            (Object)adaptor.create(SEMICOLON37)
            ;
            adaptor.addChild(root_0, SEMICOLON37_tree);


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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:95:1: membrane_structure_content : ( membrane_content )* ;
    public final Probabilistic_Guarded_Simulator_Parser.membrane_structure_content_return membrane_structure_content() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.membrane_structure_content_return retval = new Probabilistic_Guarded_Simulator_Parser.membrane_structure_content_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Probabilistic_Guarded_Simulator_Parser.membrane_content_return membrane_content38 =null;



        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:95:27: ( ( membrane_content )* )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:96:3: ( membrane_content )*
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:96:3: ( membrane_content )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==INTEGER) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:96:4: membrane_content
            	    {
            	    pushFollow(FOLLOW_membrane_content_in_membrane_structure_content216);
            	    membrane_content38=membrane_content();

            	    state._fsp--;

            	    adaptor.addChild(root_0, membrane_content38.getTree());

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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:98:1: membrane_content : membrane_index= numerical_ident numerical_ident objects_list[multiSet] flag[multiSet] SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.membrane_content_return membrane_content() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.membrane_content_return retval = new Probabilistic_Guarded_Simulator_Parser.membrane_content_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON42=null;
        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return membrane_index =null;

        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return numerical_ident39 =null;

        Probabilistic_Guarded_Simulator_Parser.objects_list_return objects_list40 =null;

        Probabilistic_Guarded_Simulator_Parser.flag_return flag41 =null;


        Object SEMICOLON42_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:98:17: (membrane_index= numerical_ident numerical_ident objects_list[multiSet] flag[multiSet] SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:100:2: membrane_index= numerical_ident numerical_ident objects_list[multiSet] flag[multiSet] SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_membrane_content231);
            membrane_index=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, membrane_index.getTree());

            pushFollow(FOLLOW_numerical_ident_in_membrane_content234);
            numerical_ident39=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident39.getTree());


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
            	

            pushFollow(FOLLOW_objects_list_in_membrane_content241);
            objects_list40=objects_list(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, objects_list40.getTree());

            pushFollow(FOLLOW_flag_in_membrane_content245);
            flag41=flag(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, flag41.getTree());

            SEMICOLON42=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_membrane_content248); 
            SEMICOLON42_tree = 
            (Object)adaptor.create(SEMICOLON42)
            ;
            adaptor.addChild(root_0, SEMICOLON42_tree);


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


    public static class flag_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "flag"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:120:1: flag[MultiSet<String> multiset] : ( (object_token= INTEGER ) | SHARP ) ;
    public final Probabilistic_Guarded_Simulator_Parser.flag_return flag(MultiSet<String> multiset) throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.flag_return retval = new Probabilistic_Guarded_Simulator_Parser.flag_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token object_token=null;
        Token SHARP43=null;

        Object object_token_tree=null;
        Object SHARP43_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:120:32: ( ( (object_token= INTEGER ) | SHARP ) )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:121:2: ( (object_token= INTEGER ) | SHARP )
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:121:2: ( (object_token= INTEGER ) | SHARP )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==INTEGER) ) {
                alt2=1;
            }
            else if ( (LA2_0==SHARP) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:121:3: (object_token= INTEGER )
                    {
                    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:121:3: (object_token= INTEGER )
                    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:121:4: object_token= INTEGER
                    {
                    object_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_flag262); 
                    object_token_tree = 
                    (Object)adaptor.create(object_token)
                    ;
                    adaptor.addChild(root_0, object_token_tree);


                    if(!multiset.contains((object_token!=null?object_token.getText():null)))
                        multiset.add((object_token!=null?object_token.getText():null));

                    }


                    }
                    break;
                case 2 :
                    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:123:41: SHARP
                    {
                    SHARP43=(Token)match(input,SHARP,FOLLOW_SHARP_in_flag269); 
                    SHARP43_tree = 
                    (Object)adaptor.create(SHARP43)
                    ;
                    adaptor.addChild(root_0, SHARP43_tree);


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
    // $ANTLR end "flag"


    public static class cardinalities_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "cardinalities"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:126:1: cardinalities[MultiSet<String> multiset] : ( cardinality[multiset] )* ;
    public final Probabilistic_Guarded_Simulator_Parser.cardinalities_return cardinalities(MultiSet<String> multiset) throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.cardinalities_return retval = new Probabilistic_Guarded_Simulator_Parser.cardinalities_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Probabilistic_Guarded_Simulator_Parser.cardinality_return cardinality44 =null;



        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:126:41: ( ( cardinality[multiset] )* )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:127:2: ( cardinality[multiset] )*
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:127:2: ( cardinality[multiset] )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==LEFT_BRACKET) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:127:2: cardinality[multiset]
            	    {
            	    pushFollow(FOLLOW_cardinality_in_cardinalities281);
            	    cardinality44=cardinality(multiset);

            	    state._fsp--;

            	    adaptor.addChild(root_0, cardinality44.getTree());

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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:129:1: cardinality[MultiSet<String> multiset] : LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET ;
    public final Probabilistic_Guarded_Simulator_Parser.cardinality_return cardinality(MultiSet<String> multiset) throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.cardinality_return retval = new Probabilistic_Guarded_Simulator_Parser.cardinality_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token object_token=null;
        Token cardinality_token=null;
        Token LEFT_BRACKET45=null;
        Token RIGHT_BRACKET46=null;

        Object object_token_tree=null;
        Object cardinality_token_tree=null;
        Object LEFT_BRACKET45_tree=null;
        Object RIGHT_BRACKET46_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:129:39: ( LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:130:1: LEFT_BRACKET object_token= INTEGER cardinality_token= INTEGER RIGHT_BRACKET
            {
            root_0 = (Object)adaptor.nil();


            LEFT_BRACKET45=(Token)match(input,LEFT_BRACKET,FOLLOW_LEFT_BRACKET_in_cardinality293); 
            LEFT_BRACKET45_tree = 
            (Object)adaptor.create(LEFT_BRACKET45)
            ;
            adaptor.addChild(root_0, LEFT_BRACKET45_tree);


            object_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_cardinality298); 
            object_token_tree = 
            (Object)adaptor.create(object_token)
            ;
            adaptor.addChild(root_0, object_token_tree);


            cardinality_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_cardinality303); 
            cardinality_token_tree = 
            (Object)adaptor.create(cardinality_token)
            ;
            adaptor.addChild(root_0, cardinality_token_tree);


            RIGHT_BRACKET46=(Token)match(input,RIGHT_BRACKET,FOLLOW_RIGHT_BRACKET_in_cardinality306); 
            RIGHT_BRACKET46_tree = 
            (Object)adaptor.create(RIGHT_BRACKET46)
            ;
            adaptor.addChild(root_0, RIGHT_BRACKET46_tree);


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


    public static class flags_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "flags"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:137:1: flags[Psystem psystem] : FLAGS COLON (flag_token= INTEGER )* COLON numerical_ident dummy_mode= numerical_ident SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.flags_return flags(Psystem psystem) throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.flags_return retval = new Probabilistic_Guarded_Simulator_Parser.flags_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token flag_token=null;
        Token FLAGS47=null;
        Token COLON48=null;
        Token COLON49=null;
        Token SEMICOLON51=null;
        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return dummy_mode =null;

        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return numerical_ident50 =null;


        Object flag_token_tree=null;
        Object FLAGS47_tree=null;
        Object COLON48_tree=null;
        Object COLON49_tree=null;
        Object SEMICOLON51_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:137:24: ( FLAGS COLON (flag_token= INTEGER )* COLON numerical_ident dummy_mode= numerical_ident SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:138:1: FLAGS COLON (flag_token= INTEGER )* COLON numerical_ident dummy_mode= numerical_ident SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            FLAGS47=(Token)match(input,FLAGS,FOLLOW_FLAGS_in_flags319); 
            FLAGS47_tree = 
            (Object)adaptor.create(FLAGS47)
            ;
            adaptor.addChild(root_0, FLAGS47_tree);


            COLON48=(Token)match(input,COLON,FOLLOW_COLON_in_flags321); 
            COLON48_tree = 
            (Object)adaptor.create(COLON48)
            ;
            adaptor.addChild(root_0, COLON48_tree);


            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:139:2: (flag_token= INTEGER )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==INTEGER) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:139:2: flag_token= INTEGER
            	    {
            	    flag_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_flags326); 
            	    flag_token_tree = 
            	    (Object)adaptor.create(flag_token)
            	    ;
            	    adaptor.addChild(root_0, flag_token_tree);


            	    ((ProbabilisticGuardedPsystem)psystem).addFlag((flag_token!=null?flag_token.getText():null));

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            COLON49=(Token)match(input,COLON,FOLLOW_COLON_in_flags331); 
            COLON49_tree = 
            (Object)adaptor.create(COLON49)
            ;
            adaptor.addChild(root_0, COLON49_tree);


            pushFollow(FOLLOW_numerical_ident_in_flags333);
            numerical_ident50=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident50.getTree());

            pushFollow(FOLLOW_numerical_ident_in_flags339);
            dummy_mode=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, dummy_mode.getTree());

            ((ProbabilisticGuardedPsystem)psystem).setDummyMode(Byte.parseByte(dummy_mode.identifier));

            SEMICOLON51=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_flags342); 
            SEMICOLON51_tree = 
            (Object)adaptor.create(SEMICOLON51)
            ;
            adaptor.addChild(root_0, SEMICOLON51_tree);


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
    // $ANTLR end "flags"


    public static class rules_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rules"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:146:1: rules[Psystem psystem] : ( rule[psystem] )* SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.rules_return rules(Psystem psystem) throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.rules_return retval = new Probabilistic_Guarded_Simulator_Parser.rules_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON53=null;
        Probabilistic_Guarded_Simulator_Parser.rule_return rule52 =null;


        Object SEMICOLON53_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:146:24: ( ( rule[psystem] )* SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:147:2: ( rule[psystem] )* SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:147:2: ( rule[psystem] )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==INTEGER) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:147:2: rule[psystem]
            	    {
            	    pushFollow(FOLLOW_rule_in_rules353);
            	    rule52=rule(psystem);

            	    state._fsp--;

            	    adaptor.addChild(root_0, rule52.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            SEMICOLON53=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_rules358); 
            SEMICOLON53_tree = 
            (Object)adaptor.create(SEMICOLON53)
            ;
            adaptor.addChild(root_0, SEMICOLON53_tree);


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
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "rule"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:149:1: rule[Psystem psystem] : numerical_ident probability_string= float_ident leftHandSide= left_hand_side COLON rightHandSide= right_hand_side ;
    public final Probabilistic_Guarded_Simulator_Parser.rule_return rule(Psystem psystem) throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.rule_return retval = new Probabilistic_Guarded_Simulator_Parser.rule_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token COLON55=null;
        Probabilistic_Guarded_Simulator_Parser.float_ident_return probability_string =null;

        Probabilistic_Guarded_Simulator_Parser.left_hand_side_return leftHandSide =null;

        Probabilistic_Guarded_Simulator_Parser.right_hand_side_return rightHandSide =null;

        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return numerical_ident54 =null;


        Object COLON55_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:149:23: ( numerical_ident probability_string= float_ident leftHandSide= left_hand_side COLON rightHandSide= right_hand_side )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:151:1: numerical_ident probability_string= float_ident leftHandSide= left_hand_side COLON rightHandSide= right_hand_side
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_rule370);
            numerical_ident54=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident54.getTree());

            pushFollow(FOLLOW_float_ident_in_rule377);
            probability_string=float_ident();

            state._fsp--;

            adaptor.addChild(root_0, probability_string.getTree());

            pushFollow(FOLLOW_left_hand_side_in_rule381);
            leftHandSide=left_hand_side();

            state._fsp--;

            adaptor.addChild(root_0, leftHandSide.getTree());

            COLON55=(Token)match(input,COLON,FOLLOW_COLON_in_rule383); 
            COLON55_tree = 
            (Object)adaptor.create(COLON55)
            ;
            adaptor.addChild(root_0, COLON55_tree);


            pushFollow(FOLLOW_right_hand_side_in_rule387);
            rightHandSide=right_hand_side();

            state._fsp--;

            adaptor.addChild(root_0, rightHandSide.getTree());


            float probability = Float.parseFloat(probability_string.identifier);
            IRule rule = (new ProbabilisticGuardedRuleFactory()).createProbabilisticGuardedRule(false, leftHandSide.leftHandSide, rightHandSide.rightHandSide, guard, KernelRuleTypes.EVOLUTION, probability);
            psystem.addRule(rule);

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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:160:1: left_hand_side returns [LeftHandRule leftHandSide] : membrane_index= numerical_ident rule_guard= guard flag[multiSet] objects_list[multiSet] ;
    public final Probabilistic_Guarded_Simulator_Parser.left_hand_side_return left_hand_side() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.left_hand_side_return retval = new Probabilistic_Guarded_Simulator_Parser.left_hand_side_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return membrane_index =null;

        Probabilistic_Guarded_Simulator_Parser.guard_return rule_guard =null;

        Probabilistic_Guarded_Simulator_Parser.flag_return flag56 =null;

        Probabilistic_Guarded_Simulator_Parser.objects_list_return objects_list57 =null;



        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:160:51: (membrane_index= numerical_ident rule_guard= guard flag[multiSet] objects_list[multiSet] )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:162:15: membrane_index= numerical_ident rule_guard= guard flag[multiSet] objects_list[multiSet]
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_left_hand_side405);
            membrane_index=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, membrane_index.getTree());

            MultiSet<String> multiSet = new HashMultiSet<String>();
            retval.leftHandSide = new LeftHandRule(new OuterRuleMembrane(new Label(membrane_index.identifier), (byte)0, multiSet), new HashMultiSet<String>());


            pushFollow(FOLLOW_guard_in_left_hand_side411);
            rule_guard=guard();

            state._fsp--;

            adaptor.addChild(root_0, rule_guard.getTree());

            guard=rule_guard.guard;

            pushFollow(FOLLOW_flag_in_left_hand_side414);
            flag56=flag(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, flag56.getTree());

            pushFollow(FOLLOW_objects_list_in_left_hand_side417);
            objects_list57=objects_list(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, objects_list57.getTree());

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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:171:1: right_hand_side returns [RightHandRule rightHandSide] : membrane_destination= numerical_ident flag[multiSet] objects_list[multiSet] numerical_ident SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.right_hand_side_return right_hand_side() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.right_hand_side_return retval = new Probabilistic_Guarded_Simulator_Parser.right_hand_side_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON61=null;
        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return membrane_destination =null;

        Probabilistic_Guarded_Simulator_Parser.flag_return flag58 =null;

        Probabilistic_Guarded_Simulator_Parser.objects_list_return objects_list59 =null;

        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return numerical_ident60 =null;


        Object SEMICOLON61_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:171:54: (membrane_destination= numerical_ident flag[multiSet] objects_list[multiSet] numerical_ident SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:172:21: membrane_destination= numerical_ident flag[multiSet] objects_list[multiSet] numerical_ident SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_right_hand_side434);
            membrane_destination=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, membrane_destination.getTree());

            MultiSet<String> multiSet = new HashMultiSet<String>();
            retval.rightHandSide = new RightHandRule(new OuterRuleMembrane(new Label(membrane_destination.identifier), (byte)0, multiSet), new HashMultiSet<String>());


            pushFollow(FOLLOW_flag_in_right_hand_side439);
            flag58=flag(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, flag58.getTree());

            pushFollow(FOLLOW_objects_list_in_right_hand_side442);
            objects_list59=objects_list(multiSet);

            state._fsp--;

            adaptor.addChild(root_0, objects_list59.getTree());

            pushFollow(FOLLOW_numerical_ident_in_right_hand_side445);
            numerical_ident60=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident60.getTree());

            SEMICOLON61=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_right_hand_side447); 
            SEMICOLON61_tree = 
            (Object)adaptor.create(SEMICOLON61)
            ;
            adaptor.addChild(root_0, SEMICOLON61_tree);


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
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:181:1: numerical_ident returns [String identifier] : membrane_index_token= INTEGER COLON ;
    public final Probabilistic_Guarded_Simulator_Parser.numerical_ident_return numerical_ident() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return retval = new Probabilistic_Guarded_Simulator_Parser.numerical_ident_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token membrane_index_token=null;
        Token COLON62=null;

        Object membrane_index_token_tree=null;
        Object COLON62_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:181:44: (membrane_index_token= INTEGER COLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:182:21: membrane_index_token= INTEGER COLON
            {
            root_0 = (Object)adaptor.nil();


            membrane_index_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_numerical_ident461); 
            membrane_index_token_tree = 
            (Object)adaptor.create(membrane_index_token)
            ;
            adaptor.addChild(root_0, membrane_index_token_tree);


            retval.identifier=(membrane_index_token!=null?membrane_index_token.getText():null);

            COLON62=(Token)match(input,COLON,FOLLOW_COLON_in_numerical_ident466); 
            COLON62_tree = 
            (Object)adaptor.create(COLON62)
            ;
            adaptor.addChild(root_0, COLON62_tree);


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


    public static class float_ident_return extends ParserRuleReturnScope {
        public String identifier;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "float_ident"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:185:1: float_ident returns [String identifier] : float_index_token= FLOAT COLON ;
    public final Probabilistic_Guarded_Simulator_Parser.float_ident_return float_ident() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.float_ident_return retval = new Probabilistic_Guarded_Simulator_Parser.float_ident_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token float_index_token=null;
        Token COLON63=null;

        Object float_index_token_tree=null;
        Object COLON63_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:185:40: (float_index_token= FLOAT COLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:186:18: float_index_token= FLOAT COLON
            {
            root_0 = (Object)adaptor.nil();


            float_index_token=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_float_ident480); 
            float_index_token_tree = 
            (Object)adaptor.create(float_index_token)
            ;
            adaptor.addChild(root_0, float_index_token_tree);


            retval.identifier=(float_index_token!=null?float_index_token.getText():null);

            COLON63=(Token)match(input,COLON,FOLLOW_COLON_in_float_ident485); 
            COLON63_tree = 
            (Object)adaptor.create(COLON63)
            ;
            adaptor.addChild(root_0, COLON63_tree);


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
    // $ANTLR end "float_ident"


    public static class objects_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "objects_list"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:189:1: objects_list[MultiSet<String> multiset] : LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET ;
    public final Probabilistic_Guarded_Simulator_Parser.objects_list_return objects_list(MultiSet<String> multiset) throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.objects_list_return retval = new Probabilistic_Guarded_Simulator_Parser.objects_list_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token LEFT_SQUARED_BRACKET64=null;
        Token RIGHT_SQUARED_BRACKET66=null;
        Probabilistic_Guarded_Simulator_Parser.cardinalities_return cardinalities65 =null;


        Object LEFT_SQUARED_BRACKET64_tree=null;
        Object RIGHT_SQUARED_BRACKET66_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:189:41: ( LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:190:1: LEFT_SQUARED_BRACKET cardinalities[multiset] RIGHT_SQUARED_BRACKET
            {
            root_0 = (Object)adaptor.nil();


            LEFT_SQUARED_BRACKET64=(Token)match(input,LEFT_SQUARED_BRACKET,FOLLOW_LEFT_SQUARED_BRACKET_in_objects_list495); 
            LEFT_SQUARED_BRACKET64_tree = 
            (Object)adaptor.create(LEFT_SQUARED_BRACKET64)
            ;
            adaptor.addChild(root_0, LEFT_SQUARED_BRACKET64_tree);


            pushFollow(FOLLOW_cardinalities_in_objects_list497);
            cardinalities65=cardinalities(multiset);

            state._fsp--;

            adaptor.addChild(root_0, cardinalities65.getTree());

            RIGHT_SQUARED_BRACKET66=(Token)match(input,RIGHT_SQUARED_BRACKET,FOLLOW_RIGHT_SQUARED_BRACKET_in_objects_list500); 
            RIGHT_SQUARED_BRACKET66_tree = 
            (Object)adaptor.create(RIGHT_SQUARED_BRACKET66)
            ;
            adaptor.addChild(root_0, RIGHT_SQUARED_BRACKET66_tree);


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
        public RestrictiveGuard guard;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "guard"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:192:1: guard returns [RestrictiveGuard guard] : (guard_sign= INTEGER COLON guard_token= INTEGER COLON )? ;
    public final Probabilistic_Guarded_Simulator_Parser.guard_return guard() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.guard_return retval = new Probabilistic_Guarded_Simulator_Parser.guard_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token guard_sign=null;
        Token guard_token=null;
        Token COLON67=null;
        Token COLON68=null;

        Object guard_sign_tree=null;
        Object guard_token_tree=null;
        Object COLON67_tree=null;
        Object COLON68_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:192:39: ( (guard_sign= INTEGER COLON guard_token= INTEGER COLON )? )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:193:1: (guard_sign= INTEGER COLON guard_token= INTEGER COLON )?
            {
            root_0 = (Object)adaptor.nil();


            guard=null; short sign;

            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:194:2: (guard_sign= INTEGER COLON guard_token= INTEGER COLON )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==INTEGER) ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==COLON) ) {
                    alt6=1;
                }
            }
            switch (alt6) {
                case 1 :
                    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:194:2: guard_sign= INTEGER COLON guard_token= INTEGER COLON
                    {
                    guard_sign=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_guard518); 
                    guard_sign_tree = 
                    (Object)adaptor.create(guard_sign)
                    ;
                    adaptor.addChild(root_0, guard_sign_tree);


                    sign=(Integer.parseInt((guard_sign!=null?guard_sign.getText():null))==1)? ComparationMasks.MINUS :ComparationMasks.PLUS;

                    COLON67=(Token)match(input,COLON,FOLLOW_COLON_in_guard523); 
                    COLON67_tree = 
                    (Object)adaptor.create(COLON67)
                    ;
                    adaptor.addChild(root_0, COLON67_tree);


                    guard_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_guard527); 
                    guard_token_tree = 
                    (Object)adaptor.create(guard_token)
                    ;
                    adaptor.addChild(root_0, guard_token_tree);


                    retval.guard= RestrictiveGuardFactory.createRestrictiveGuard((guard_token!=null?guard_token.getText():null));

                    COLON68=(Token)match(input,COLON,FOLLOW_COLON_in_guard534); 
                    COLON68_tree = 
                    (Object)adaptor.create(COLON68)
                    ;
                    adaptor.addChild(root_0, COLON68_tree);


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


    public static class blocks_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "blocks"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:200:1: blocks : ( block )* SEMICOLON ;
    public final Probabilistic_Guarded_Simulator_Parser.blocks_return blocks() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.blocks_return retval = new Probabilistic_Guarded_Simulator_Parser.blocks_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token SEMICOLON70=null;
        Probabilistic_Guarded_Simulator_Parser.block_return block69 =null;


        Object SEMICOLON70_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:200:7: ( ( block )* SEMICOLON )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:200:9: ( block )* SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:200:9: ( block )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==INTEGER) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:200:10: block
            	    {
            	    pushFollow(FOLLOW_block_in_blocks546);
            	    block69=block();

            	    state._fsp--;

            	    adaptor.addChild(root_0, block69.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            SEMICOLON70=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_blocks550); 
            SEMICOLON70_tree = 
            (Object)adaptor.create(SEMICOLON70)
            ;
            adaptor.addChild(root_0, SEMICOLON70_tree);


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
    // $ANTLR end "blocks"


    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "block"
    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:202:1: block : numerical_ident numerical_ident flag[new HashMultiSet<String>()] COLON flag[new HashMultiSet<String>()] COLON flag[new HashMultiSet<String>()] objects_list[new HashMultiSet<String>()] ( INTEGER )* ;
    public final Probabilistic_Guarded_Simulator_Parser.block_return block() throws RecognitionException {
        Probabilistic_Guarded_Simulator_Parser.block_return retval = new Probabilistic_Guarded_Simulator_Parser.block_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token COLON74=null;
        Token COLON76=null;
        Token INTEGER79=null;
        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return numerical_ident71 =null;

        Probabilistic_Guarded_Simulator_Parser.numerical_ident_return numerical_ident72 =null;

        Probabilistic_Guarded_Simulator_Parser.flag_return flag73 =null;

        Probabilistic_Guarded_Simulator_Parser.flag_return flag75 =null;

        Probabilistic_Guarded_Simulator_Parser.flag_return flag77 =null;

        Probabilistic_Guarded_Simulator_Parser.objects_list_return objects_list78 =null;


        Object COLON74_tree=null;
        Object COLON76_tree=null;
        Object INTEGER79_tree=null;

        try {
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:202:6: ( numerical_ident numerical_ident flag[new HashMultiSet<String>()] COLON flag[new HashMultiSet<String>()] COLON flag[new HashMultiSet<String>()] objects_list[new HashMultiSet<String>()] ( INTEGER )* )
            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:202:8: numerical_ident numerical_ident flag[new HashMultiSet<String>()] COLON flag[new HashMultiSet<String>()] COLON flag[new HashMultiSet<String>()] objects_list[new HashMultiSet<String>()] ( INTEGER )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_numerical_ident_in_block557);
            numerical_ident71=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident71.getTree());

            pushFollow(FOLLOW_numerical_ident_in_block559);
            numerical_ident72=numerical_ident();

            state._fsp--;

            adaptor.addChild(root_0, numerical_ident72.getTree());

            pushFollow(FOLLOW_flag_in_block561);
            flag73=flag(new HashMultiSet<String>());

            state._fsp--;

            adaptor.addChild(root_0, flag73.getTree());

            COLON74=(Token)match(input,COLON,FOLLOW_COLON_in_block564); 
            COLON74_tree = 
            (Object)adaptor.create(COLON74)
            ;
            adaptor.addChild(root_0, COLON74_tree);


            pushFollow(FOLLOW_flag_in_block566);
            flag75=flag(new HashMultiSet<String>());

            state._fsp--;

            adaptor.addChild(root_0, flag75.getTree());

            COLON76=(Token)match(input,COLON,FOLLOW_COLON_in_block570); 
            COLON76_tree = 
            (Object)adaptor.create(COLON76)
            ;
            adaptor.addChild(root_0, COLON76_tree);


            pushFollow(FOLLOW_flag_in_block572);
            flag77=flag(new HashMultiSet<String>());

            state._fsp--;

            adaptor.addChild(root_0, flag77.getTree());

            pushFollow(FOLLOW_objects_list_in_block576);
            objects_list78=objects_list(new HashMultiSet<String>());

            state._fsp--;

            adaptor.addChild(root_0, objects_list78.getTree());

            // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:202:194: ( INTEGER )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==INTEGER) ) {
                    int LA8_2 = input.LA(2);

                    if ( (LA8_2==INTEGER||LA8_2==SEMICOLON) ) {
                        alt8=1;
                    }


                }


                switch (alt8) {
            	case 1 :
            	    // C:\\Users\\manu\\workspaces\\eclipse\\workspace\\pLinguaCoreGuardedProbabilisticIntegrated\\org\\gcn\\plinguacore\\parser\\input\\probabilisticGuarded\\Probabilistic_Guarded_Simulator_Parser.g:202:195: INTEGER
            	    {
            	    INTEGER79=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_block580); 
            	    INTEGER79_tree = 
            	    (Object)adaptor.create(INTEGER79)
            	    ;
            	    adaptor.addChild(root_0, INTEGER79_tree);


            	    }
            	    break;

            	default :
            	    break loop8;
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
    // $ANTLR end "block"

    // Delegated rules


 

    public static final BitSet FOLLOW_header_in_psystem64 = new BitSet(new long[]{0x0000000000040200L});
    public static final BitSet FOLLOW_initial_configuration_in_psystem68 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_flags_in_psystem72 = new BitSet(new long[]{0x0000000000040200L});
    public static final BitSet FOLLOW_rules_in_psystem75 = new BitSet(new long[]{0x0000000000040200L});
    public static final BitSet FOLLOW_blocks_in_psystem78 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membranes_line_in_header87 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_rules_line_in_header90 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_maximum_number_of_rules_per_membrane_line_in_header92 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_alphabet_size_line_in_header95 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_flags_line_in_header97 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_blocks_line_in_header99 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MEMBRANES_in_membranes_line108 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_membranes_line110 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_membranes_line112 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_membranes_line114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULES_in_rules_line122 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_rules_line124 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_rules_line126 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_rules_line128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MAXIMUM_NUMBER_OF_RULES_PER_MEMBRANE_in_maximum_number_of_rules_per_membrane_line139 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_maximum_number_of_rules_per_membrane_line141 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_maximum_number_of_rules_per_membrane_line143 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_maximum_number_of_rules_per_membrane_line145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ALPHABET_SIZE_in_alphabet_size_line155 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_alphabet_size_line157 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_alphabet_size_line159 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_alphabet_size_line161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLAGS_SIZE_in_flags_line170 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_flags_line172 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_flags_line174 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_flags_line176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_OF_BLOCKS_in_blocks_line186 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_blocks_line188 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_blocks_line190 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_blocks_line192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membrane_structure_content_in_initial_configuration203 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_initial_configuration205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membrane_content_in_membrane_structure_content216 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_numerical_ident_in_membrane_content231 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_numerical_ident_in_membrane_content234 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_objects_list_in_membrane_content241 = new BitSet(new long[]{0x0000000000080200L});
    public static final BitSet FOLLOW_flag_in_membrane_content245 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_membrane_content248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_flag262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SHARP_in_flag269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cardinality_in_cardinalities281 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_LEFT_BRACKET_in_cardinality293 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_cardinality298 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_cardinality303 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_RIGHT_BRACKET_in_cardinality306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLAGS_in_flags319 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_flags321 = new BitSet(new long[]{0x0000000000000220L});
    public static final BitSet FOLLOW_INTEGER_in_flags326 = new BitSet(new long[]{0x0000000000000220L});
    public static final BitSet FOLLOW_COLON_in_flags331 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_numerical_ident_in_flags333 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_numerical_ident_in_flags339 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_flags342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_rules353 = new BitSet(new long[]{0x0000000000040200L});
    public static final BitSet FOLLOW_SEMICOLON_in_rules358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numerical_ident_in_rule370 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_float_ident_in_rule377 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_left_hand_side_in_rule381 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_rule383 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_right_hand_side_in_rule387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numerical_ident_in_left_hand_side405 = new BitSet(new long[]{0x0000000000080200L});
    public static final BitSet FOLLOW_guard_in_left_hand_side411 = new BitSet(new long[]{0x0000000000080200L});
    public static final BitSet FOLLOW_flag_in_left_hand_side414 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_objects_list_in_left_hand_side417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numerical_ident_in_right_hand_side434 = new BitSet(new long[]{0x0000000000080200L});
    public static final BitSet FOLLOW_flag_in_right_hand_side439 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_objects_list_in_right_hand_side442 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_numerical_ident_in_right_hand_side445 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SEMICOLON_in_right_hand_side447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_numerical_ident461 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_numerical_ident466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_float_ident480 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_float_ident485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_SQUARED_BRACKET_in_objects_list495 = new BitSet(new long[]{0x0000000000010400L});
    public static final BitSet FOLLOW_cardinalities_in_objects_list497 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RIGHT_SQUARED_BRACKET_in_objects_list500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_guard518 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_guard523 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INTEGER_in_guard527 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_guard534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_blocks546 = new BitSet(new long[]{0x0000000000040200L});
    public static final BitSet FOLLOW_SEMICOLON_in_blocks550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numerical_ident_in_block557 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_numerical_ident_in_block559 = new BitSet(new long[]{0x0000000000080200L});
    public static final BitSet FOLLOW_flag_in_block561 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_block564 = new BitSet(new long[]{0x0000000000080200L});
    public static final BitSet FOLLOW_flag_in_block566 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_block570 = new BitSet(new long[]{0x0000000000080200L});
    public static final BitSet FOLLOW_flag_in_block572 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_objects_list_in_block576 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_INTEGER_in_block580 = new BitSet(new long[]{0x0000000000000202L});

}