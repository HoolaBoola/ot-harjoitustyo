package io;

import java.util.Scanner;

public class ConsoleIO implements IO{
    
    private Scanner reader;
    
    public ConsoleIO(Scanner scanner) {
        reader = scanner;
    }
    
    @Override
    public String nextLine() {
        return reader.nextLine();
    }

    @Override
    public void print(String string) {
        System.out.println(string);
    }
}
