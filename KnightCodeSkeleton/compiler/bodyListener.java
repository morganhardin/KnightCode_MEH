/** 
 * This class implements the BaseListener and creates .class files 
 * and compiles KnightCode. It creates a constructor and 'main' method
 * that can then be used to execute the program. It implements basic
 * operations like printing to the screen, adding, subtracting,
 * multiplying, dividing, and comparing integers using bytecode.
 * 
 * @author morganhardin
 * @version 1.0
 * Compiler Project 4
 * CS322 - Compiler Construction
 * Spring 2023
 */

package compiler;

/** 
 * Imports necessary java, antlr, and asm libraries along with
 * the lexparse directory.
 */
import lexparse.*;
import java.util.*;
import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.objectweb.asm.*;
import org.objectweb.asm.Opcodes;

/**
 * This is the class method that implements the basic functions of
 * this compiler like creating a constructor, adding, subtracting,
 * multiplying, dividing, printing, reading, comparing, and 
 * creating files that implement these operations.
 */
public class bodyListener extends KnightCodeBaseListener
{
    /** 
     * These are private variables that will be used and manipulated
     * in this class to use opcodes and bytecode and store values in
     * the correct index in a hashmap.
     */
    private ClassWriter cw;
    private MethodVisitor mv, mainV;
    private HashMap<SymbolTable, String> symbolTable = new HashMap<SymbolTable, String>();
    private String fileName;
    private int totalVar = 0;
    
    /** 
     * This constructor takes a fileName parameter in order to create a
     * .class file of this file name.
     */
    public bodyListener(String fileName)
    {
        this.fileName = fileName;
    }
    /** 
     * This start class sets up the constructor and main method using
     * bytecode.
     */
    public void start()
    {
        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, this.fileName, null, "java/lang/Object", null);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();
        
        mainV = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mainV.visitCode();
    }
    /** 
     * This close class ends the file and creates a byte file
     * that is labeled as the previously inputted filename as 
     * a .class. 
     */
    public void close()
    {
        mainV.visitInsn(Opcodes.RETURN);
        mainV.visitMaxs(0,0);
        mainV.visitEnd();
        cw.visitEnd();

        byte[] b = cw.toByteArray();
        ByteFile.writeFile(b, this.fileName + ".class");
        
        System.out.println("Done");
    }

    /** 
     * This overrides the enterFile method in BaseListener and calls
     * the previously instantiated start method to create the 
     * constructor and main methods for the file.
     */
    @Override public void enterFile(KnightCodeParser.FileContext ctx)
    {
        System.out.println("Entering File");
        start();
    }
    /** 
     * This overrides the exitFile method in BaseListener and calls
     * the previously instantiated close method to create the 
     * the .class file.
     */
    @Override public void exitFile(KnightCodeParser.FileContext ctx)
    {
        System.out.println("Exiting File");
        close();
    }
    /** 
     * This overrides the enterDeclare method in BaseListener and prints
     * the context of the declare section of the program.
     */
    @Override public void enterDeclare(KnightCodeParser.DeclareContext ctx)
    {
        System.out.println("Entering Declare");

        System.out.println("Declare: " + ctx.getText());
    }
    /** 
     * This overrides the exitDeclare method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitDeclare(KnightCodeParser.DeclareContext ctx)
    {
        System.out.println("Exiting Declare");
    }
    /** 
     * This overrides the enterVariable method in BaseListener and prints
     * the context of the variable section of the program. It also sets the
     * variable type and variable name along with its index in the 
     * symbol table.
     */
    @Override public void enterVariable(KnightCodeParser.VariableContext ctx)
    {
        System.out.println("Entering Variable");

        System.out.println("Variable: " + ctx.getText());
        
        String var = ctx.getText();
        SymbolTable st = new SymbolTable();
        if(var.contains("INTEGER")) // creates and stores integer variable
        {
            String name = var.substring(7);
            symbolTable.put(new SymbolTable(totalVar, name, "INTEGER"), "0");
        }
        else // creates and stores string variable
        {
            String name = var.substring(6);
            symbolTable.put(new SymbolTable(totalVar, name, "STRING"), "0");
        }
        totalVar = totalVar + 1;
    }
    /** 
     * This overrides the exitVariable method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitVariable(KnightCodeParser.VariableContext ctx)
    {
        System.out.println("Exiting Variable");
    }
    /** 
     * This overrides the enterIdentifier method in BaseListener and prints
     * the context of the identifier section of the program.
     */
    @Override public void enterIdentifier(KnightCodeParser.IdentifierContext ctx)
    {
        System.out.println("Entering Identifier");

        System.out.println("Identifier: " + ctx.getText());

    }
    /** 
     * This overrides the exitIdentifier method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitIdentifier(KnightCodeParser.IdentifierContext ctx)
    {
        System.out.println("Exiting Identifier");
    }
    /** 
     * This overrides the enterVartype method in BaseListener and prints
     * the context of the vartype section of the program.
     */
    @Override public void enterVartype(KnightCodeParser.VartypeContext ctx)
    {
        System.out.println("Entering Vartype");

        System.out.println("Variable Type: " + ctx.getText());
    }
    /** 
     * This overrides the exitVartype method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitVartype(KnightCodeParser.VartypeContext ctx)
    {
        System.out.println("Exiting Vartype");
    }
    /** 
     * This overrides the enterBody method in BaseListener and prints
     * the context of the body section of the program.
     */
    @Override public void enterBody(KnightCodeParser.BodyContext ctx)
    {
        System.out.println("Entering Body");

        System.out.println("Body: " + ctx.getText());
    }
    /** 
     * This overrides the exitBody method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitBody(KnightCodeParser.BodyContext ctx)
    {
        System.out.println("Exiting Body");
    }
    /** 
     * This overrides the enterStat method in BaseListener and prints
     * the context of the stat section of the program.
     */
    @Override public void enterStat(KnightCodeParser.StatContext ctx)
    {
        System.out.println("Entering Stat");

        System.out.println("Stat: " + ctx.getText());
    }
    /** 
     * This overrides the exitStat method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitStat(KnightCodeParser.StatContext ctx)
    {
        System.out.println("Exiting Stat");
    }
    /** 
     * This overrides the enterSetvar method in BaseListener and prints
     * the context of the setvar section of the program. This method
     * sets the variable a value based on the type and stores it in 
     * the hashmap.
     */
    @Override public void enterSetvar(KnightCodeParser.SetvarContext ctx)
    {
        System.out.println("Entering Setvar");

        SymbolTable table = new SymbolTable();
        int index = ctx.getText().indexOf("=");
        String key = ctx.getText().substring(3, index - 1);
        String value = ctx.getText().substring(index + 1);
        if(value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/"))
        {
            value = "0";
        }
        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(key))
            {
                table = st;
            }
        }
        if(table.getType().equals("INTEGER")) // sets integer variable value 
        {
            if(table.getName() != null)
            {
                for(SymbolTable st : symbolTable.keySet())
                {
                    if(table.getIndex() == st.getIndex())
                    {
                        symbolTable.replace(st, value); // replaces previous value with new value
                    }
                }
                int intValue = Integer.parseInt(value);
                mainV.visitLdcInsn((Integer)intValue);
                mainV.visitVarInsn(Opcodes.ISTORE, table.getIndex()); // stores value in register
            }
            else
            {
                int intValue = Integer.parseInt(value);
                symbolTable.put(new SymbolTable(totalVar, key, null), value); // stores integer value in hashmap
                mainV.visitLdcInsn((Integer)intValue);
                mainV.visitVarInsn(Opcodes.ISTORE, totalVar); // stores value in register
                totalVar = totalVar + 1;
            }
        }
        else if(table.getType().equals("STRING")) // sets string variable value
        {
            if(table.getName() != null)
            {
                for(SymbolTable st : symbolTable.keySet())
                {
                    if(table.getIndex() == st.getIndex())
                    {
                        symbolTable.replace(st, value); // replaces previous value with new value
                    }
                }
                mainV.visitLdcInsn((String)value);
                mainV.visitVarInsn(Opcodes.ASTORE, table.getIndex()); // stores value in register
            }
            else
            {
                symbolTable.put(new SymbolTable(totalVar, key, null), value); // stores string value in hashmap
                mainV.visitLdcInsn((String)value);
                mainV.visitVarInsn(Opcodes.ASTORE, totalVar); // stores value in register
                totalVar = totalVar + 1;
            }
        }
    }
    /** 
     * This overrides the exitSetvar method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitSetvar(KnightCodeParser.SetvarContext ctx)
    {
        System.out.println("Exiting Setvar");
    }
    /** 
     * This overrides the enterId method in BaseListener and prints
     * the context of the ID section of the program.
     */
    @Override public void enterId(KnightCodeParser.IdContext ctx)
    {
        System.out.println("Entering ID");

        System.out.println("ID: " + ctx.getText());
    }
    /** 
     * This overrides the exitId method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitId(KnightCodeParser.IdContext ctx)
    {
        System.out.println("Exiting ID");
    }
    /** 
     * This overrides the enterParenthesis method in BaseListener and prints
     * the context of the parenthesis section of the program. It gets rid of the 
     * parenthesis and splits what is inside and stores it so that it can
     * be executed and used in other methods.
     */
    @Override public void enterParenthesis(KnightCodeParser.ParenthesisContext ctx)
    {
        System.out.println("Entering Parenthesis");

        String a = ctx.getText();
        String splitA = " ";
        SymbolTable leftOperand = new SymbolTable(); // left operand
        SymbolTable rightOperand = new SymbolTable(); // right operand

        if(a.contains("/")) // splits division symbol
        {
            splitA = "/";
        }
        else if(a.contains("*")) // splits multiplication symbol
        {
            splitA = "*";
        }
        else if (a.contains("-")) // splits subtraction symbol
        {
            splitA = "-";
        }
        else if (a.contains("+")) // splits addition symbol
        {
            splitA = "+";
        }

        String[] expression = a.split(splitA);
        for(SymbolTable st : symbolTable.keySet()) // splits based on operands
        {
            if(st.getName().equals(expression[0]))
            {
                leftOperand = st;
            }
            if(st.getName().equals(expression[1]))
            {
                rightOperand = st;
            }
        }

        if(symbolTable.keySet().contains(leftOperand)) // stores based on left operand
        {
            mainV.visitVarInsn(Opcodes.ILOAD, leftOperand.getIndex());
        }
        else // pushes parsed integer to stack to be store
        {
            mainV.visitIntInsn(Opcodes.BIPUSH, Integer.parseInt(expression[0]));
        }
        if(symbolTable.keySet().contains(rightOperand)) // stores based on right operand
        {
            mainV.visitVarInsn(Opcodes.ILOAD, rightOperand.getIndex());
        }
        else // pushes parsed integer to stack to be store
        {
            mainV.visitIntInsn(Opcodes.BIPUSH, Integer.parseInt(expression[1]));
        }

        if(splitA.equals("/")) // division
        {
            mainV.visitInsn(Opcodes.IDIV);
        }
        else if(splitA.equals("*")) // multiplication
        {
            mainV.visitInsn(Opcodes.IMUL);
        }
        else if(splitA.equals("-")) // subtraction
        {
            mainV.visitInsn(Opcodes.ISUB);
        }
        else if(splitA.equals("+")) // addition
        {
            mainV.visitInsn(Opcodes.IADD);
        }
    }
    /** 
     * This overrides the exitParenthesis method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitParenthesis(KnightCodeParser.ParenthesisContext ctx)
    {
        System.out.println("Exiting Parenthesis");
    }
    /** 
     * This overrides the enterMultiplication method in BaseListener and prints
     * the context of the multiplication section of the program. This takes the two
     * childs and multiplies them and stores the result in an index in the hashmap.
     */
    @Override public void enterMultiplication(KnightCodeParser.MultiplicationContext ctx)
    {
        System.out.println("Entering Multiplication");

        String a = ctx.getText();
        String leftOperand = ctx.getText().substring(0, a.indexOf("*")); // gets left child before operand
        String rightOperand = ctx.getText().substring(a.indexOf("*") + 1); // gets right child after operand
        int leftIndex = 0;
        int rightIndex = 0;
        int root = 0;
        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(leftOperand)) // sets left index
            {
                leftIndex = st.getIndex();
            }
            if(st.getName().equals(rightOperand))
            {
                rightIndex = st.getIndex(); // set right index
            }
            if(symbolTable.get(st).equals("0"))
            {
                root = st.getIndex(); // sets root index
            }
        }
        mainV.visitVarInsn(Opcodes.ILOAD, leftIndex); // loads left variable
        mainV.visitVarInsn(Opcodes.ILOAD, rightIndex); // loads right variable
        mainV.visitInsn(Opcodes.IMUL); // multiplies the two variables
        mainV.visitVarInsn(Opcodes.ISTORE, root); // stores result in root index register
    }
    /** 
     * This overrides the exitMultiplication method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitMultiplication(KnightCodeParser.MultiplicationContext ctx)
    {
        System.out.println("Exiting Multiplication");
    }
    /** 
     * This overrides the enterAddition method in BaseListener and prints
     * the context of the addition section of the program. This takes the two
     * childs and adds them and stores the result in an index in the hashmap.
     */
    @Override public void enterAddition(KnightCodeParser.AdditionContext ctx)
    {
        System.out.println("Entering Addition");

        String a = ctx.getText();
        String leftOperand = a.substring(0, a.indexOf("+")); // gets child left of operator
        String rightOperand = a.substring(a.indexOf("+") + 1); // gets child right of operator
        int leftIndex = 0;
        int rightIndex = 0;
        int root = 0;

        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(leftOperand)) // sets left index
            {
                leftIndex = st.getIndex();
            }
            if(st.getName().equals(rightOperand)) // sets right index
            {
                rightIndex = st.getIndex();
            }
            if(symbolTable.get(st).equals("0")) // sets root index
            {
                root = st.getIndex();
            }
        }
        mainV.visitVarInsn(Opcodes.ILOAD, leftIndex); // loads left variable
        mainV.visitVarInsn(Opcodes.ILOAD, rightIndex); // loads right variable
        mainV.visitInsn(Opcodes.IADD); // adds the two variables
        mainV.visitVarInsn(Opcodes.ISTORE, root); // stores result in root index register
    }
    /** 
     * This overrides the exitAddition method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitAddition(KnightCodeParser.AdditionContext ctx)
    {
        System.out.println("Exiting Addition");
    }
    /** 
     * This overrides the enterSubtraction method in BaseListener and prints
     * the context of the subtraction section of the program. This takes the two
     * childs and subtracts them and stores the result in an index in the hashmap.
     */
    @Override public void enterSubtraction(KnightCodeParser.SubtractionContext ctx)
    {
        System.out.println("Entering Subtraction");

        String a = ctx.getText();
        String leftOperand = a.substring(0, a.indexOf("-")); // gets child left of operator
        String rightOperand = a.substring(a.indexOf("-") + 1); // gets child right of operator
        int leftIndex = 0;
        int rightIndex = 0;
        int root = 0;

        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(leftOperand)) // sets left index
            {
                leftIndex = st.getIndex();
            }
            if(st.getName().equals(rightOperand)) // sets right index
            {
                rightIndex = st.getIndex();
            }
            if(symbolTable.get(st).equals("0")) // sets root index
            {
                root = st.getIndex();
            }
        }
        mainV.visitVarInsn(Opcodes.ILOAD, leftIndex); // loads left variable
        mainV.visitVarInsn(Opcodes.ILOAD, rightIndex); // loads right variable
        mainV.visitInsn(Opcodes.ISUB); // subtracts the two variables
        mainV.visitVarInsn(Opcodes.ISTORE, root); // stores result in root index register
    }
    /** 
     * This overrides the exitSubtraction method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitSubtraction(KnightCodeParser.SubtractionContext ctx)
    {
        System.out.println("Exiting Subtraction");
    }
    /** 
     * This overrides the enterDivision method in BaseListener and prints
     * the context of the division section of the program. This takes the two
     * childs and divides them and stores the result in an index in the hashmap.
     */
    @Override public void enterDivision(KnightCodeParser.DivisionContext ctx)
    {
        System.out.println("Entering Division");

        String a = ctx.getText();
        String leftOperand = a.substring(0, a.indexOf("/")); // gets child left of operator
        String rightOperand = a.substring(a.indexOf("/") + 1); // gets child right of operator
        int leftIndex = 0;
        int rightIndex = 0;
        int root = 0;

        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(leftOperand)) // sets left index
            {
                leftIndex = st.getIndex();
            }
            if(st.getName().equals(rightOperand)) // sets right index
            {
                rightIndex = st.getIndex();
            }
            if(symbolTable.get(st).equals("0")) // sets root index
            {
                root = st.getIndex();
            }
        }
        mainV.visitVarInsn(Opcodes.ILOAD, leftIndex); // loads left variable
        mainV.visitVarInsn(Opcodes.ILOAD, rightIndex); // loads right variable
        mainV.visitInsn(Opcodes.IDIV); // divides the two variables
        mainV.visitVarInsn(Opcodes.ISTORE, root); // stores result in root index register
    }
    /** 
     * This overrides the exitDivision method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitDivision(KnightCodeParser.DivisionContext ctx)
    {
        System.out.println("Exiting Division");
    }
    /** 
     * This overrides the enterNumber method in BaseListener and prints
     * the context of the number section of the program.
     */
    @Override public void enterNumber(KnightCodeParser.NumberContext ctx)
    {
        System.out.println("Entering Number");

        System.out.println("Number: " + ctx.getText());
    }
    /** 
     * This overrides the exitNumber method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitNumber(KnightCodeParser.NumberContext ctx)
    {
        System.out.println("Exiting Number");
    }
    /** 
     * This overrides the enterComparison method in BaseListener and prints
     * the context of the comparison section of the program.
     */
    @Override public void enterComparison(KnightCodeParser.ComparisonContext ctx)
    {
        System.out.println("Entering Comparison");

        System.out.println("Comparison: " + ctx.getText());
    }
    /** 
     * This overrides the exitComparison method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitComparison(KnightCodeParser.ComparisonContext ctx)
    {
        System.out.println("Exiting Comparison");
    }
    /** 
     * This overrides the enterDecision method in BaseListener and prints
     * the context of the decision section of the program. This takes the
     * two children and decides what operator to use (greater than, less
     * than, equal to, or not equal to). Then it will execute the decision.
     *  
     * NOT FULLY IMPLEMENTED: For some reason it will not go to the 
     * comparison portion and executes the print section instead of
     * actually making the decision like it should. I tried putting labels
     * in the comparison to force it to jump but it kept failing.
     */
    @Override public void enterDecision(KnightCodeParser.DecisionContext ctx)
    {
        System.out.println("Entering Decision");
        System.out.println("Decision: " + ctx.getText());

        String a = ctx.getText();
        String leftOperand = ctx.getChild(1).getText(); // gets left child
        String rightOperand = ctx.getChild(3).getText(); // gets right child
        int leftIndex = 0;
        int rightIndex = 0;
        int root = 0;
        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(ctx.getChild(1).getText()) && st.getType().equals("INTEGER")) //determines if variable is left of operator and its type is INTEGER
            {
                leftOperand = st.getName();
                System.out.println(st.getName() + "--> " + st.getType() + "--> " + st.getIndex() + "--> " + symbolTable.get(st));
                leftIndex = st.getIndex(); // sets left index
                int op1 = Integer.parseInt(symbolTable.get(st)); // parses integer
                mainV.visitLdcInsn((Integer)op1);
                mainV.visitVarInsn(Opcodes.ISTORE, leftIndex); // stores variable in left index register
            }
            else if(st.getName().equals(ctx.getChild(3).getText()) && st.getType().equals("INTEGER")) //determines if variable is right of operator and its type is INTEGER
            {
                rightOperand = st.getName();
                System.out.println(st.getName() + "--> " + st.getType() + "--> " + st.getIndex() + "--> " + symbolTable.get(st));
                rightIndex = st.getIndex(); // sets right index
                int op2 = Integer.parseInt(symbolTable.get(st)); // parses integer
                mainV.visitLdcInsn((Integer)op2);
                mainV.visitVarInsn(Opcodes.ISTORE, rightIndex); // stores variable in right index register
            }
            else if(st.getName().equals(ctx.getChild(1).getText()) && st.getType().equals("STRING")) //determines if variable is left of operator and its type is STRING
            {
                leftOperand = st.getName();
                System.out.println(st.getName() + "--> " + st.getType() + "--> " + st.getIndex() + "--> " + symbolTable.get(st));
                leftIndex = st.getIndex(); // sets left index
                String op1 = symbolTable.get(st);
                mainV.visitLdcInsn((String)op1);
                mainV.visitVarInsn(Opcodes.ASTORE, leftIndex); // stores variable in left index register
            }
            else if(st.getName().equals(ctx.getChild(3).getText()) && st.getType().equals("STRING")) //determines if variable is right of operator and its type is STRING
            {
                rightOperand = st.getName();
                System.out.println(st.getName() + "--> " + st.getType() + "--> " + st.getIndex() + "--> " + symbolTable.get(st));
                rightIndex = st.getIndex(); // sets right index
                String op2 = symbolTable.get(st);
                mainV.visitLdcInsn((String)op2);
                mainV.visitVarInsn(Opcodes.ASTORE, rightIndex); // stores variable in right index register
            }
        }
        Label label = new Label();
        if(ctx.getChild(2).getText().equals(">")) // greater than section
        {
            
            mainV.visitVarInsn(Opcodes.ILOAD, leftIndex); // loads left variable
            mainV.visitVarInsn(Opcodes.ILOAD, rightIndex); // loads right variable
            mainV.visitJumpInsn(Opcodes.IF_ICMPGT, label); // jumps below if not greater than
        }
        else if(ctx.getChild(2).getText().equals("<")) // less than section
        {
            mainV.visitVarInsn(Opcodes.ILOAD, leftIndex); // loads left variable
            mainV.visitVarInsn(Opcodes.ILOAD, rightIndex); // loads right variable
            mainV.visitJumpInsn(Opcodes.IF_ICMPLT, label); // jumps below if not less than
        }
        else if(ctx.getChild(2).getText().equals("<>")) // not equal to section
        {
            mainV.visitVarInsn(Opcodes.ILOAD, leftIndex); // loads left variable
            mainV.visitVarInsn(Opcodes.ILOAD, rightIndex); // loads right variable
            mainV.visitJumpInsn(Opcodes.IF_ICMPNE, label); // jumps below if equal to
        }
        else if(ctx.getChild(2).getText().equals(":=")) // equal to section
        {
            mainV.visitVarInsn(Opcodes.ILOAD, leftIndex); // loads left variable 
            mainV.visitVarInsn(Opcodes.ILOAD, rightIndex); // loads right variable
            mainV.visitJumpInsn(Opcodes.IF_ICMPEQ, label); // jumps below if not equal to
        }
        mainV.visitLabel(label); // label jumped to
    }
    /** 
     * This overrides the exitDecision method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitDecision(KnightCodeParser.DecisionContext ctx)
    {
        System.out.println("Exiting Decision");
    }
    /** 
     * This overrides the enterLoop method in BaseListener and prints
     * the context of the loop section of the program. This takes the
     * two children and decides what operator to use (greater than, less
     * than, equal to, or not equal to). It will then loop back around
     * as long as the conditions are met and will continue until conditions
     * are no longer met.
     *  
     * NOT FULLY IMPLEMENTED
     */
    @Override public void enterLoop(KnightCodeParser.LoopContext ctx)
    {
        System.out.println("Entering Loop");
        String left = ctx.getChild(1).getText(); // gets left child
        String right = ctx.getChild(3).getText(); // gets right child
        SymbolTable leftOperand = new SymbolTable();
        SymbolTable rightOperand = new SymbolTable();

        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(left)) // sets left operand
            {
                leftOperand = st;
            }
            else if(st.getName().equals(right)) // sets right operand
            {
                rightOperand = st;
            }
        }
        if(symbolTable.keySet().contains(leftOperand)) // loads left variable
        {
            mainV.visitVarInsn(Opcodes.ILOAD, leftOperand.getIndex());
        }
        else // pushes variable to stack
        {
            mainV.visitIntInsn(Opcodes.BIPUSH, Integer.parseInt(left));
        }
        if(symbolTable.keySet().contains(rightOperand)) // loads right variable
        {
            mainV.visitVarInsn(Opcodes.ILOAD, rightOperand.getIndex());
        }
        else // pushes variable to stack
        {
            mainV.visitIntInsn(Opcodes.BIPUSH, Integer.parseInt(right));
        }
    }
    /** 
     * This overrides the exitDecision method in BaseListener and prints
     * that it is exiting this section of the program. This determines if
     * the conditions are met or not to determine if the loop should 
     * continue or not.
     * 
     * NOT FULLY IMPLEMENTED
     */
    @Override public void exitLoop(KnightCodeParser.LoopContext ctx)
    {
        System.out.println("Exiting Loop");

        Label start = new Label();
        if(ctx.getChild(2).getText().equals(">")) // jumps back to start if greater than
        {
            mainV.visitJumpInsn(Opcodes.IF_ICMPGT, start);
        }
        else if(ctx.getChild(2).getText().equals("<")) // jumps back to start if less than
        {
            mainV.visitJumpInsn(Opcodes.IF_ICMPLT, start);
        }
        else if(ctx.getChild(2).getText().equals("<>")) // jumps back to start if not equal to
        {
            mainV.visitJumpInsn(Opcodes.IF_ICMPNE, start);
        }
        else if(ctx.getChild(2).getText().equals(":=")) // jumps back to start if equal to
        {
            mainV.visitJumpInsn(Opcodes.IF_ICMPEQ, start);
        }
        mainV.visitLabel(start); // start label jumped to
    }
    /** 
     * This overrides the enterRead method in BaseListener and prints
     * the context of the read section of the program. This takes 
     * user input and reads it and stores it into the hashmap based
     * on its type.
     */
    @Override public void enterRead(KnightCodeParser.ReadContext ctx)
    {
        System.out.println("Entering Read");

        String var = ctx.getChild(1).getText(); // gets child
        SymbolTable table = new SymbolTable();
        int index = 0;
        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(var)) // sets name and index
            {
                index = st.getIndex();
                table = st;
            }
        }
        totalVar = totalVar + 1;

        // stores and loads the scanner object in totalVar register
        mainV.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
        mainV.visitInsn(Opcodes.DUP);
        mainV.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
        mainV.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);
        mainV.visitVarInsn(Opcodes.ASTORE, totalVar);
        mainV.visitVarInsn(Opcodes.ALOAD, totalVar);
        totalVar = totalVar + 1;
        mainV.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "next", "()Ljava/lang/String;", false);
            
        if(table.getType().equals("INTEGER")) // if type is INTEGER, reads and stores variable
        {
            mainV.visitVarInsn(Opcodes.ASTORE, index);
            mainV.visitVarInsn(Opcodes.ALOAD, index);
            mainV.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I", false);
            mainV.visitVarInsn(Opcodes.ISTORE, index);
        }
        else if(table.getType().equals("STRING")) // if type is STRING, reads and stores variable
        {
            mainV.visitVarInsn(Opcodes.ASTORE, index);
        }
    }
    /** 
     * This overrides the exitRead method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitRead(KnightCodeParser.ReadContext ctx)
    {
        System.out.println("Exiting Read");
    }
    /** 
     * This overrides the enterPrint method in BaseListener and prints
     * the context of the print section of the program. This prints
     * the child and its value from the hashmap / symbol table using
     * opcodes.
     */
    @Override public void enterPrint(KnightCodeParser.PrintContext ctx)
    {
        System.out.println("Entering Print");

        String output = ctx.getChild(1).getText(); // gets child
        int outputIndex = 0;
        SymbolTable table = new SymbolTable();
        for(SymbolTable st : symbolTable.keySet())
        {
            if(st.getName().equals(output) && st.getType().equals("INTEGER")) // if output is an INTEGER, prints variable
            {
                outputIndex = st.getIndex();
                table = st;
                mainV.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mainV.visitVarInsn(Opcodes.ILOAD, outputIndex);
                mainV.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
                break;
            }
            else if(st.getName().equals(output) && st.getType().equals("STRING")) // if output is a STRING, prints variable
            {
                outputIndex = st.getIndex();
                table = st;
                mainV.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mainV.visitVarInsn(Opcodes.ALOAD, outputIndex);
                mainV.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                break;
            }
            else // prints output
            {
                mainV.visitLdcInsn(output);
                mainV.visitVarInsn(Opcodes.ASTORE, totalVar);
                mainV.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mainV.visitVarInsn(Opcodes.ALOAD, totalVar);
                mainV.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                break;
            }
        }
    }
    /** 
     * This overrides the exitPrint method in BaseListener and prints
     * that it is exiting this section of the program.
     */
    @Override public void exitPrint(KnightCodeParser.PrintContext ctx)
    {
        System.out.println("Exiting Print");
    }
    /** 
     * This method prints the context of the program.
     */
    public void printContext(String ctx)
    {
        System.out.println(ctx);
    }
}