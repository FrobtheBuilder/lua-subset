package com.frob.lua_subset;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Frob on 3/28/2017.
 */
public class SyntaxAnalyzer {
    private int currentTokenIndex;
    private ArrayList<Token> tokens;
    private ArrayList<Token> operationTokens = new ArrayList<Token>(Arrays.asList(
            Token.ADD_TOK, Token.DIV_TOK, Token.MUL_TOK, Token.SUB_TOK
    ));
    private ArrayList<Token> comparisonTokens = new ArrayList<Token>(Arrays.asList(
            Token.EQ_TOK, Token.GE_TOK, Token.LE_TOK, Token.GT_TOK, Token.LT_TOK, Token.NE_TOK
    ));
    public SyntaxAnalyzer() {

    }
    public Token getCurrentToken() {
        return tokens.get(currentTokenIndex);
    }
    public Token getAndIncrement() {
        Token current = getCurrentToken();
        currentTokenIndex++;
        return current;
    }
    public void increment() {
        currentTokenIndex++;
    }
    public void checkAndIncrement(Token expected) {
        if (getCurrentToken() != expected) {
            raiseError();
        }
        else {
            System.out.println(expected);
            increment();
        }
    }
    public void checkAndIncrement(ArrayList<Token> expected) {
        if (!expected.contains(getCurrentToken())) {
            raiseError();
        }
        else {
            //System.out.println(getCurrentToken());
            increment();
        }
    }
    private void raiseError() {
        System.out.println("Unexpected token at " + currentTokenIndex);
        System.exit(1); //die.
    }
    public void analyze(ArrayList<Token> tokens) {
        currentTokenIndex = 0;
        this.tokens = tokens;
        function();
    }
    public void function() {
        System.out.println("Entering program.");
        checkAndIncrement(Token.FUNCTION_TOK);
        checkAndIncrement(Token.ID_TOK);
        checkAndIncrement(Token.LEFT_PAREN_TOK);
        optionalId();
        checkAndIncrement(Token.RIGHT_PAREN_TOK);
        block();
        System.out.println("Leaving program.");
    }
    public void block() {
        System.out.println("Entering block.");
        while ( getCurrentToken() != Token.END_TOK &&
                getCurrentToken() != Token.ELSE_TOK &&
                getCurrentToken() != Token.UNTIL_TOK ) {
            switch (getCurrentToken()) {
                case ID_TOK:
                    assign();
                    break;
                case IF_TOK:
                    conditional();
                    break;
                case WHILE_TOK:
                    loop();
                    break;
                case REPEAT_TOK:
                    repeat();
                    break;
                default:
                    raiseError(); //unknown statement
                    break;
            }
            /*
            if (getCurrentToken() == Token.ID_TOK) {
                assign();
            } else if (getCurrentToken() == Token.IF_TOK) {
                conditional();
            } */
        }
        System.out.println(getCurrentToken());
        System.out.println("Leaving block.");
    }
    private void comparison() {
        checkAndIncrement(comparisonTokens);
    }
    private void print() {
        System.out.println("Entering print statement.");
        checkAndIncrement(Token.PRINT_TOK);
        checkAndIncrement(Token.LEFT_PAREN_TOK);
        arithmeticExpression();
        checkAndIncrement(Token.RIGHT_PAREN_TOK);
        System.out.println("Leaving print statement.");
    }
    private void booleanExpression() {
        System.out.println("Entering boolean expression.");
        comparison();
        arithmeticExpression();
        checkAndIncrement(Token.SEPARATOR_TOK);
        arithmeticExpression();
        System.out.println("Leaving boolean expression.");
    }
    private void conditional() {
        System.out.println("Entering conditional.");
        checkAndIncrement(Token.IF_TOK);
        booleanExpression();
        checkAndIncrement(Token.THEN_TOK);
        block();
        if (getCurrentToken() == Token.ELSE_TOK) {
            System.out.println("Else...");
            increment(); //consume the else
            block();
        }
        increment(); //don't forget to consume the final end token so the program doesn't immediately exit
        System.out.println("Leaving conditional.");
    }
    private void loop() {
        System.out.println("Entering loop.");
        checkAndIncrement(Token.WHILE_TOK);
        booleanExpression();
        checkAndIncrement(Token.DO_TOK);
        block();
        increment(); //do not forget
        System.out.println("Leaving loop.");
    }
    private void repeat() {
        System.out.println("Entering repeat loop.");
        checkAndIncrement(Token.REPEAT_TOK);
        block();
        checkAndIncrement(Token.UNTIL_TOK);
        booleanExpression();
        System.out.println("Leaving repeat loop.");
    }
    private void assign() {
        System.out.println("Entering assignment statement.");
        checkAndIncrement(Token.ID_TOK);
        checkAndIncrement(Token.ASSIGN_TOK);
        arithmeticExpression();
        System.out.println("Leaving assignment statement.");
    }
    private void arithmeticExpression() {
        System.out.println("Entering arithmetic expression.");
        if (getCurrentToken() == Token.ID_TOK) {
            checkAndIncrement(Token.ID_TOK);
        }
        else if (getCurrentToken() == Token.LITERAL_INTEGER_TOK) {
            checkAndIncrement(Token.LITERAL_INTEGER_TOK);
        }
        else if (operationTokens.contains(getCurrentToken())) {
            operation();
            arithmeticExpression();
            checkAndIncrement(Token.SEPARATOR_TOK);
            arithmeticExpression();
        }
        System.out.println("Leaving arithmetic expression.");
    }
    private void operation() {
        checkAndIncrement(operationTokens);
    }
    private void optionalId() {
        if (getCurrentToken() == Token.ID_TOK) {
            increment();
        }
    }
}
