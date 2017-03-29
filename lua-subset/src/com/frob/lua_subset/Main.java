package com.frob.lua_subset;

import java.util.ArrayList;

/*
 * Test harness, runs the scanner on the first argument provided to the executable
 * and prints the lexeme hash.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(args[0]);
        LexicalAnalyzer scanner = new LexicalAnalyzer(); //Creating the lexeme scanner
        SyntaxAnalyzer parser = new SyntaxAnalyzer();
        ArrayList<Token> tokenList = scanner.analyze(args[0]);
        System.out.println(scanner.lexemes);
        System.out.println(tokenList);
        parser.analyze(tokenList);
        //System.out.println(scanner.analyze(args[0])); //Printing out all the lexemes found
    }
}
