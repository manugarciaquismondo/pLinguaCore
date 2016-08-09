// $ANTLR 3.4 C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g 2016-06-22 16:35:48

package org.gcn.plinguacore.parser.input.simplekernel;

import org.gcn.plinguacore.parser.output.simplekernel.KernelDictionary;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class Dictionary_Reader extends Parser {
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


    public Dictionary_Reader(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public Dictionary_Reader(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return Dictionary_Reader.tokenNames; }
    public String getGrammarFileName() { return "C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g"; }


    public static class dictionary_return extends ParserRuleReturnScope {
        public KernelDictionary dictionary;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "dictionary"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:18:1: dictionary returns [KernelDictionary dictionary] : membraneMap= membranes objectsMap= objects ;
    public final Dictionary_Reader.dictionary_return dictionary() throws RecognitionException {
        Dictionary_Reader.dictionary_return retval = new Dictionary_Reader.dictionary_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Dictionary_Reader.membranes_return membraneMap =null;

        Dictionary_Reader.objects_return objectsMap =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:18:49: (membraneMap= membranes objectsMap= objects )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:18:51: membraneMap= membranes objectsMap= objects
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_membranes_in_dictionary51);
            membraneMap=membranes();

            state._fsp--;

            adaptor.addChild(root_0, membraneMap.getTree());

            pushFollow(FOLLOW_objects_in_dictionary55);
            objectsMap=objects();

            state._fsp--;

            adaptor.addChild(root_0, objectsMap.getTree());

            KernelDictionary dictionary = new KernelDictionary();
            for(String label: membraneMap.membraneMap.keySet())
              dictionary.putMembrane(label, membraneMap.membraneMap.get(label));

            for(String label: objectsMap.objectsMap.keySet())
              dictionary.putObject(label, objectsMap.objectsMap.get(label));
              
              retval.dictionary=dictionary;


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
    // $ANTLR end "dictionary"


    public static class membranes_return extends ParserRuleReturnScope {
        public Map<String, Integer> membraneMap;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membranes"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:32:1: membranes returns [Map<String, Integer> membraneMap] : membranes_header membraneListMap= membrane_list ;
    public final Dictionary_Reader.membranes_return membranes() throws RecognitionException {
        Dictionary_Reader.membranes_return retval = new Dictionary_Reader.membranes_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Dictionary_Reader.membrane_list_return membraneListMap =null;

        Dictionary_Reader.membranes_header_return membranes_header1 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:32:53: ( membranes_header membraneListMap= membrane_list )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:32:55: membranes_header membraneListMap= membrane_list
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_membranes_header_in_membranes71);
            membranes_header1=membranes_header();

            state._fsp--;

            adaptor.addChild(root_0, membranes_header1.getTree());

            pushFollow(FOLLOW_membrane_list_in_membranes75);
            membraneListMap=membrane_list();

            state._fsp--;

            adaptor.addChild(root_0, membraneListMap.getTree());

            retval.membraneMap = membraneListMap.membraneMap;

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
    // $ANTLR end "membranes"


    public static class membranes_header_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membranes_header"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:35:1: membranes_header : MEMBRANES COLON INTEGER SEMICOLON ;
    public final Dictionary_Reader.membranes_header_return membranes_header() throws RecognitionException {
        Dictionary_Reader.membranes_header_return retval = new Dictionary_Reader.membranes_header_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token MEMBRANES2=null;
        Token COLON3=null;
        Token INTEGER4=null;
        Token SEMICOLON5=null;

        Object MEMBRANES2_tree=null;
        Object COLON3_tree=null;
        Object INTEGER4_tree=null;
        Object SEMICOLON5_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:35:17: ( MEMBRANES COLON INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:35:19: MEMBRANES COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            MEMBRANES2=(Token)match(input,MEMBRANES,FOLLOW_MEMBRANES_in_membranes_header84); 
            MEMBRANES2_tree = 
            (Object)adaptor.create(MEMBRANES2)
            ;
            adaptor.addChild(root_0, MEMBRANES2_tree);


            COLON3=(Token)match(input,COLON,FOLLOW_COLON_in_membranes_header86); 
            COLON3_tree = 
            (Object)adaptor.create(COLON3)
            ;
            adaptor.addChild(root_0, COLON3_tree);


            INTEGER4=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_membranes_header88); 
            INTEGER4_tree = 
            (Object)adaptor.create(INTEGER4)
            ;
            adaptor.addChild(root_0, INTEGER4_tree);


            SEMICOLON5=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_membranes_header90); 
            SEMICOLON5_tree = 
            (Object)adaptor.create(SEMICOLON5)
            ;
            adaptor.addChild(root_0, SEMICOLON5_tree);


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
    // $ANTLR end "membranes_header"


    public static class membrane_list_return extends ParserRuleReturnScope {
        public Map<String, Integer> membraneMap;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membrane_list"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:37:1: membrane_list returns [Map<String, Integer> membraneMap] : ( membrane[retval.membraneMap] )* ;
    public final Dictionary_Reader.membrane_list_return membrane_list() throws RecognitionException {
        Dictionary_Reader.membrane_list_return retval = new Dictionary_Reader.membrane_list_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Dictionary_Reader.membrane_return membrane6 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:37:57: ( ( membrane[retval.membraneMap] )* )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:38:1: ( membrane[retval.membraneMap] )*
            {
            root_0 = (Object)adaptor.nil();


            retval.membraneMap= new HashMap<String, Integer>();

            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:39:2: ( membrane[retval.membraneMap] )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==INTEGER) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:39:3: membrane[retval.membraneMap]
            	    {
            	    pushFollow(FOLLOW_membrane_in_membrane_list105);
            	    membrane6=membrane(retval.membraneMap);

            	    state._fsp--;

            	    adaptor.addChild(root_0, membrane6.getTree());

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
    // $ANTLR end "membrane_list"


    public static class membrane_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "membrane"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:41:1: membrane[Map<String, Integer> membraneMap] : membrane_label= integer_list EQUAL id= INTEGER SEMICOLON ;
    public final Dictionary_Reader.membrane_return membrane(Map<String, Integer> membraneMap) throws RecognitionException {
        Dictionary_Reader.membrane_return retval = new Dictionary_Reader.membrane_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token id=null;
        Token EQUAL7=null;
        Token SEMICOLON8=null;
        Dictionary_Reader.integer_list_return membrane_label =null;


        Object id_tree=null;
        Object EQUAL7_tree=null;
        Object SEMICOLON8_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:41:44: (membrane_label= integer_list EQUAL id= INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:42:15: membrane_label= integer_list EQUAL id= INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_integer_list_in_membrane120);
            membrane_label=integer_list();

            state._fsp--;

            adaptor.addChild(root_0, membrane_label.getTree());

            EQUAL7=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_membrane122); 
            EQUAL7_tree = 
            (Object)adaptor.create(EQUAL7)
            ;
            adaptor.addChild(root_0, EQUAL7_tree);


            id=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_membrane126); 
            id_tree = 
            (Object)adaptor.create(id)
            ;
            adaptor.addChild(root_0, id_tree);


            SEMICOLON8=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_membrane128); 
            SEMICOLON8_tree = 
            (Object)adaptor.create(SEMICOLON8)
            ;
            adaptor.addChild(root_0, SEMICOLON8_tree);


            membraneMap.put(membrane_label.integer_string, Integer.parseInt((id!=null?id.getText():null)));

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
    // $ANTLR end "membrane"


    public static class integer_list_return extends ParserRuleReturnScope {
        public String integer_string;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "integer_list"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:46:1: integer_list returns [String integer_string] : id_token= INTEGER ( COMMA id_token= INTEGER )* ;
    public final Dictionary_Reader.integer_list_return integer_list() throws RecognitionException {
        Dictionary_Reader.integer_list_return retval = new Dictionary_Reader.integer_list_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token id_token=null;
        Token COMMA9=null;

        Object id_token_tree=null;
        Object COMMA9_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:46:45: (id_token= INTEGER ( COMMA id_token= INTEGER )* )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:47:9: id_token= INTEGER ( COMMA id_token= INTEGER )*
            {
            root_0 = (Object)adaptor.nil();


            id_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_integer_list144); 
            id_token_tree = 
            (Object)adaptor.create(id_token)
            ;
            adaptor.addChild(root_0, id_token_tree);


            retval.integer_string=(id_token!=null?id_token.getText():null);

            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:48:2: ( COMMA id_token= INTEGER )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==COMMA) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:48:2: COMMA id_token= INTEGER
            	    {
            	    COMMA9=(Token)match(input,COMMA,FOLLOW_COMMA_in_integer_list148); 
            	    COMMA9_tree = 
            	    (Object)adaptor.create(COMMA9)
            	    ;
            	    adaptor.addChild(root_0, COMMA9_tree);


            	    id_token=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_integer_list152); 
            	    id_token_tree = 
            	    (Object)adaptor.create(id_token)
            	    ;
            	    adaptor.addChild(root_0, id_token_tree);


            	    retval.integer_string+=","+(id_token!=null?id_token.getText():null);

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
    // $ANTLR end "integer_list"


    public static class objects_return extends ParserRuleReturnScope {
        public Map<String, Integer> objectsMap;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "objects"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:51:1: objects returns [ Map<String, Integer> objectsMap] : objects_header objectListMap= object_list ;
    public final Dictionary_Reader.objects_return objects() throws RecognitionException {
        Dictionary_Reader.objects_return retval = new Dictionary_Reader.objects_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Dictionary_Reader.object_list_return objectListMap =null;

        Dictionary_Reader.objects_header_return objects_header10 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:51:51: ( objects_header objectListMap= object_list )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:51:53: objects_header objectListMap= object_list
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_objects_header_in_objects167);
            objects_header10=objects_header();

            state._fsp--;

            adaptor.addChild(root_0, objects_header10.getTree());

            pushFollow(FOLLOW_object_list_in_objects171);
            objectListMap=object_list();

            state._fsp--;

            adaptor.addChild(root_0, objectListMap.getTree());

            retval.objectsMap = objectListMap.objectsMap;

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
    // $ANTLR end "objects"


    public static class objects_header_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "objects_header"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:56:1: objects_header : OBJECTS COLON INTEGER SEMICOLON ;
    public final Dictionary_Reader.objects_header_return objects_header() throws RecognitionException {
        Dictionary_Reader.objects_header_return retval = new Dictionary_Reader.objects_header_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token OBJECTS11=null;
        Token COLON12=null;
        Token INTEGER13=null;
        Token SEMICOLON14=null;

        Object OBJECTS11_tree=null;
        Object COLON12_tree=null;
        Object INTEGER13_tree=null;
        Object SEMICOLON14_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:56:15: ( OBJECTS COLON INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:56:17: OBJECTS COLON INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            OBJECTS11=(Token)match(input,OBJECTS,FOLLOW_OBJECTS_in_objects_header182); 
            OBJECTS11_tree = 
            (Object)adaptor.create(OBJECTS11)
            ;
            adaptor.addChild(root_0, OBJECTS11_tree);


            COLON12=(Token)match(input,COLON,FOLLOW_COLON_in_objects_header184); 
            COLON12_tree = 
            (Object)adaptor.create(COLON12)
            ;
            adaptor.addChild(root_0, COLON12_tree);


            INTEGER13=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_objects_header186); 
            INTEGER13_tree = 
            (Object)adaptor.create(INTEGER13)
            ;
            adaptor.addChild(root_0, INTEGER13_tree);


            SEMICOLON14=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_objects_header188); 
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
    // $ANTLR end "objects_header"


    public static class object_list_return extends ParserRuleReturnScope {
        public Map<String, Integer> objectsMap;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "object_list"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:58:1: object_list returns [Map<String, Integer> objectsMap] : ( object_element[map] )* ;
    public final Dictionary_Reader.object_list_return object_list() throws RecognitionException {
        Dictionary_Reader.object_list_return retval = new Dictionary_Reader.object_list_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Dictionary_Reader.object_element_return object_element15 =null;



        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:58:54: ( ( object_element[map] )* )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:59:1: ( object_element[map] )*
            {
            root_0 = (Object)adaptor.nil();


            Map<String, Integer> map =  new HashMap<String, Integer>();

            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:60:2: ( object_element[map] )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==STRING) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:60:2: object_element[map]
            	    {
            	    pushFollow(FOLLOW_object_element_in_object_list202);
            	    object_element15=object_element(map);

            	    state._fsp--;

            	    adaptor.addChild(root_0, object_element15.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            retval.objectsMap =map;

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
    // $ANTLR end "object_list"


    public static class object_element_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "object_element"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:64:1: object_element[Map<String, Integer> objectsMap] : object_label= object_id EQUAL id= INTEGER SEMICOLON ;
    public final Dictionary_Reader.object_element_return object_element(Map<String, Integer> objectsMap) throws RecognitionException {
        Dictionary_Reader.object_element_return retval = new Dictionary_Reader.object_element_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token id=null;
        Token EQUAL16=null;
        Token SEMICOLON17=null;
        Dictionary_Reader.object_id_return object_label =null;


        Object id_tree=null;
        Object EQUAL16_tree=null;
        Object SEMICOLON17_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:64:49: (object_label= object_id EQUAL id= INTEGER SEMICOLON )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:65:13: object_label= object_id EQUAL id= INTEGER SEMICOLON
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_object_id_in_object_element219);
            object_label=object_id();

            state._fsp--;

            adaptor.addChild(root_0, object_label.getTree());

            EQUAL16=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_object_element221); 
            EQUAL16_tree = 
            (Object)adaptor.create(EQUAL16)
            ;
            adaptor.addChild(root_0, EQUAL16_tree);


            id=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_object_element225); 
            id_tree = 
            (Object)adaptor.create(id)
            ;
            adaptor.addChild(root_0, id_tree);


            SEMICOLON17=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_object_element227); 
            SEMICOLON17_tree = 
            (Object)adaptor.create(SEMICOLON17)
            ;
            adaptor.addChild(root_0, SEMICOLON17_tree);


            objectsMap.put(object_label.object_id, Integer.parseInt((id!=null?id.getText():null)));

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
    // $ANTLR end "object_element"


    public static class object_id_return extends ParserRuleReturnScope {
        public String object_id;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "object_id"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:69:1: object_id returns [String object_id] : id_token= STRING indexes_token= indexes ;
    public final Dictionary_Reader.object_id_return object_id() throws RecognitionException {
        Dictionary_Reader.object_id_return retval = new Dictionary_Reader.object_id_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token id_token=null;
        Dictionary_Reader.indexes_return indexes_token =null;


        Object id_token_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:69:37: (id_token= STRING indexes_token= indexes )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:70:9: id_token= STRING indexes_token= indexes
            {
            root_0 = (Object)adaptor.nil();


            id_token=(Token)match(input,STRING,FOLLOW_STRING_in_object_id243); 
            id_token_tree = 
            (Object)adaptor.create(id_token)
            ;
            adaptor.addChild(root_0, id_token_tree);


            retval.object_id=(id_token!=null?id_token.getText():null);

            pushFollow(FOLLOW_indexes_in_object_id250);
            indexes_token=indexes();

            state._fsp--;

            adaptor.addChild(root_0, indexes_token.getTree());

            retval.object_id+=indexes_token.indexes_string;

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
    // $ANTLR end "object_id"


    public static class indexes_return extends ParserRuleReturnScope {
        public String indexes_string;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "indexes"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:76:1: indexes returns [String indexes_string] : ( LEFT_KEY_BRACKET indexes_token= indexes_list RIGHT_KEY_BRACKET )? ;
    public final Dictionary_Reader.indexes_return indexes() throws RecognitionException {
        Dictionary_Reader.indexes_return retval = new Dictionary_Reader.indexes_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token LEFT_KEY_BRACKET18=null;
        Token RIGHT_KEY_BRACKET19=null;
        Dictionary_Reader.indexes_list_return indexes_token =null;


        Object LEFT_KEY_BRACKET18_tree=null;
        Object RIGHT_KEY_BRACKET19_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:76:40: ( ( LEFT_KEY_BRACKET indexes_token= indexes_list RIGHT_KEY_BRACKET )? )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:77:1: ( LEFT_KEY_BRACKET indexes_token= indexes_list RIGHT_KEY_BRACKET )?
            {
            root_0 = (Object)adaptor.nil();


            String returnedValue="";

            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:78:2: ( LEFT_KEY_BRACKET indexes_token= indexes_list RIGHT_KEY_BRACKET )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LEFT_KEY_BRACKET) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:78:2: LEFT_KEY_BRACKET indexes_token= indexes_list RIGHT_KEY_BRACKET
                    {
                    LEFT_KEY_BRACKET18=(Token)match(input,LEFT_KEY_BRACKET,FOLLOW_LEFT_KEY_BRACKET_in_indexes267); 
                    LEFT_KEY_BRACKET18_tree = 
                    (Object)adaptor.create(LEFT_KEY_BRACKET18)
                    ;
                    adaptor.addChild(root_0, LEFT_KEY_BRACKET18_tree);


                    pushFollow(FOLLOW_indexes_list_in_indexes271);
                    indexes_token=indexes_list();

                    state._fsp--;

                    adaptor.addChild(root_0, indexes_token.getTree());

                    RIGHT_KEY_BRACKET19=(Token)match(input,RIGHT_KEY_BRACKET,FOLLOW_RIGHT_KEY_BRACKET_in_indexes273); 
                    RIGHT_KEY_BRACKET19_tree = 
                    (Object)adaptor.create(RIGHT_KEY_BRACKET19)
                    ;
                    adaptor.addChild(root_0, RIGHT_KEY_BRACKET19_tree);



                    returnedValue+="{";
                    for(String index: indexes_token.indexes)
                      returnedValue+=index+",";
                    returnedValue=returnedValue.substring(0, returnedValue.length()-1)+"}";


                    }
                    break;

            }


            retval.indexes_string=returnedValue;

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
    // $ANTLR end "indexes"


    public static class indexes_list_return extends ParserRuleReturnScope {
        public List<String> indexes;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "indexes_list"
    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:88:1: indexes_list returns [List<String> indexes] :index= INTEGER ( COMMA index= INTEGER )* ;
    public final Dictionary_Reader.indexes_list_return indexes_list() throws RecognitionException {
        Dictionary_Reader.indexes_list_return retval = new Dictionary_Reader.indexes_list_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token index=null;
        Token COMMA20=null;

        Object index_tree=null;
        Object COMMA20_tree=null;

        try {
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:88:44: (index= INTEGER ( COMMA index= INTEGER )* )
            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:89:1: index= INTEGER ( COMMA index= INTEGER )*
            {
            root_0 = (Object)adaptor.nil();


            retval.indexes= new LinkedList<String>();

            index=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_indexes_list295); 
            index_tree = 
            (Object)adaptor.create(index)
            ;
            adaptor.addChild(root_0, index_tree);


            retval.indexes.add((index!=null?index.getText():null));

            // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:91:2: ( COMMA index= INTEGER )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Users\\manu_\\localdata\\workspaces\\eclipse\\workspace\\pLinguaCore5.0\\org\\gcn\\plinguacore\\parser\\input\\simplekernel\\Dictionary_Reader.g:91:3: COMMA index= INTEGER
            	    {
            	    COMMA20=(Token)match(input,COMMA,FOLLOW_COMMA_in_indexes_list301); 
            	    COMMA20_tree = 
            	    (Object)adaptor.create(COMMA20)
            	    ;
            	    adaptor.addChild(root_0, COMMA20_tree);


            	    index=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_indexes_list305); 
            	    index_tree = 
            	    (Object)adaptor.create(index)
            	    ;
            	    adaptor.addChild(root_0, index_tree);


            	    retval.indexes.add((index!=null?index.getText():null));

            	    }
            	    break;

            	default :
            	    break loop5;
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
    // $ANTLR end "indexes_list"

    // Delegated rules


 

    public static final BitSet FOLLOW_membranes_in_dictionary51 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_objects_in_dictionary55 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membranes_header_in_membranes71 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_membrane_list_in_membranes75 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MEMBRANES_in_membranes_header84 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_membranes_header86 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_membranes_header88 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_membranes_header90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_membrane_in_membrane_list105 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_integer_list_in_membrane120 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUAL_in_membrane122 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_membrane126 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_membrane128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_integer_list144 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_integer_list148 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_integer_list152 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_objects_header_in_objects167 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_object_list_in_objects171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECTS_in_objects_header182 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_objects_header184 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_objects_header186 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_objects_header188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_object_element_in_object_list202 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_object_id_in_object_element219 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_EQUAL_in_object_element221 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_object_element225 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_object_element227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_object_id243 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_indexes_in_object_id250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_KEY_BRACKET_in_indexes267 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_indexes_list_in_indexes271 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RIGHT_KEY_BRACKET_in_indexes273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_indexes_list295 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_indexes_list301 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INTEGER_in_indexes_list305 = new BitSet(new long[]{0x0000000000000042L});

}