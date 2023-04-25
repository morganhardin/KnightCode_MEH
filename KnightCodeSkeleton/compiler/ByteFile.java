/** 
 * This class writes a bytefile based on inputted bytes.
 * 
 * @author morganhardin
 * @version 1.0
 * Compiler Project 4
 * CS322 - Compiler Construction
 * Spring 2023
 */
package compiler;

/** 
 * This imports necessary java.io libraries to read bytes
 * and write them to a file.
 */
import java.io.*;

public class ByteFile
{
    /** 
    * This takes byte and string parameters so that it
    * can read the inputted bytes and write them to a file.
    * If this fails, an error message will be printed.
    */
    public static void writeFile(byte[] b, String file)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b);
            fos.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR" + e.getMessage());
        }
        System.out.println("Byte File Created");
    }
}