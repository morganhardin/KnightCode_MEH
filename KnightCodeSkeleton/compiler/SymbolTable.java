/** 
 * This class creates a symbol table that holds an 
 * index, name, and type.
 * 
 * @author morganhardin
 * @version 1.0
 * Compiler Project 4
 * CS322 - Compiler Construction
 * Spring 2023
 */

package compiler;

/** 
 * This class can get and set indexes, names, and
 * types.
 */
public class SymbolTable
{
    String name;
    String type;
    int index;

    /** 
     * Sets basic values to these variables
     */
    public SymbolTable()
    {
        name = "";
        type = "";
        index = 0;
    }
    /** 
     * Sets inputted parameters equal to this index, 
     * name, and type.
     */
    public SymbolTable(int index, String name, String type)
    {
        this.index = index;
        this.name = name;
        this.type = type;
    }
    /**
     * Sets name equal to inputted name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /** 
     * Returns name
     */
    public String getName()
    {
        return name;
    }
    /**
     * Sets type equal to inputted type
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /** 
     * Returns type
     */
    public String getType()
    {
        return type;
    }
    /**
     * Sets index equal to inputted index
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
    /** 
     * Returns index
     */
    public int getIndex()
    {
        return index;
    }
}