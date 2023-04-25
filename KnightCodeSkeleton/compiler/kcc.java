/** 
 * This class does a basic grammar test and parses
 * and walks through the tree of the inputted
 * program.
 * 
 * @author morganhardin
 * @version 1.0
 * Compiler Project 4
 * CS322 - Compiler Construction
 * Spring 2023
 */

package compiler;

/** 
 * This imports necessary java.io, lexparse directory, 
 * and antlr libraries and packages in order to execute 
 * this main portion.
 */
import java.io.IOException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.gui.Trees;
import lexparse.*;

public class kcc
{
    /** 
     * This main method takes the input, creates the lexer,
     * token stream, and parser and walks through the parse
     * tree of the inputted file.
     */
    public static void main(String[] args)
    {
        CharStream input;
        KnightCodeLexer lexer;
        CommonTokenStream tokens;
        KnightCodeParser parser;

        /** 
         * This section tries to create the lexer, token stream,
         * parser, and parse tree and will throw an error if
         * it cannot be executed.
         */
        try
        {
            input = CharStreams.fromFileName(args[0]);  //get the input
            lexer = new KnightCodeLexer(input); //create the lexer
            tokens = new CommonTokenStream(lexer); //create the token stream
            parser = new KnightCodeParser(tokens); //create the parser
       
            ParseTree tree = parser.file();  //set the start location of the parser
             
            String file = args[1];
            bodyListener listener = new bodyListener(file);
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, tree);
            
            //Trees.inspect(tree, parser);  //displays the parse tree
            
            //System.out.println(tree.toStringTree(parser));
        
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("Done");
    } // end main
} // end class
