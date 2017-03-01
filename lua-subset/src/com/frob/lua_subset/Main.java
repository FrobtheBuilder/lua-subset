package com.frob.lua_subset;

/*
 * Test harness, runs the scanner on the first argument provided to the executable
 * and prints the lexeme hash.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(args[0]);
        LexicalAnalyzer scanner = new LexicalAnalyzer(); //Creating the lexeme scanner
        System.out.println(scanner.analyze(args[0])); //Printing out all the lexemes found
    }
}
