package com.frob.lua_subset;

/*
 * Main function of the Scanner for test.lua file in detecting tokens. 
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(args[0]);
        LexicalAnalyzer scanner = new LexicalAnalyzer(); //Creating the lexeme scanner
        System.out.println(scanner.analyze(args[0])); //Printing out all the lexemes found
    }
}
