package io;

import dao.FileRetriever;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleFileRetriever implements FileRetriever {
    
    private Scanner scanner;
    
    public ConsoleFileRetriever(Scanner scanner){
        this.scanner = scanner;
    }
    
    @Override
    public Optional<File> getFile() {
        
        return Optional.empty();
    }
}
