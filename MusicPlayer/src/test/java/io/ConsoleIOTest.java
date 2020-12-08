package io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ConsoleIOTest {

    ConsoleIO io;
    
    @Before
    public void setUp() throws Exception {
        io = new ConsoleIO(new Scanner("eka\ntoka\nkolmas\n"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void nextLine() {
        assertEquals("eka", io.nextLine());
        
        assertEquals("toka", io.nextLine());
        
        assertEquals("kolmas", io.nextLine());
    }

    @Test
    public void print() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        PrintStream old = System.out;

        System.setOut(ps);

        io.print("Foofoofoo!");
        
        assertEquals("Foofoofoo!\n", baos.toString());

        System.out.flush();
        System.setOut(old);

    }
}