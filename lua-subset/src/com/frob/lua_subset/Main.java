package com.frob.lua_subset;

public class Main {

    public static void main(String[] args) {
        System.out.println(args[0]);
        LexicalAnalyzer scanner = new LexicalAnalyzer();
        System.out.println(scanner.analyze(args[0]));
    }
}
