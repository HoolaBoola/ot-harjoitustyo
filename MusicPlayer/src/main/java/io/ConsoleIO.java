package io;

import java.util.Scanner;

/**
 * IO class to use in Console user interface
 */
public class ConsoleIO implements IO{
    
    private Scanner reader;

    /**
     * 
     * @param scanner the Scanner object to use for IO
     */
    public ConsoleIO(Scanner scanner) {
        reader = scanner;
    }

    /**
     * return the next line of the IO object
     * @return next line as String
     */
    @Override
    public String nextLine() {
        return reader.nextLine();
    }

    /**
     * prints the String given as argument
     * @param string the string wanted to be printed
     */
    @Override
    public void print(String string) {
        System.out.println(string);
    }
}
