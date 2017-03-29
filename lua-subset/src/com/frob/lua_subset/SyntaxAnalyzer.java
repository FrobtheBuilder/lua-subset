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
            Token.EQ_TOK, Token.GE_TOK, Token.LE_TOK, Token.GT_TOK, Token.LT_TOK
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
        while (getCurrentToken() != Token.END_TOK && getCurrentToken() != Token.ELSE_TOK) {
            if (getCurrentToken() == Token.ID_TOK) {
                assign();
            } else if (getCurrentToken() == Token.IF_TOK) {
                conditional();
            }
        }
        System.out.println(getCurrentToken());
        System.out.println("Leaving block.");
    }
    public void comparison() {
        checkAndIncrement(comparisonTokens);
    }
    public void booleanExpression() {
        comparison();
        arithmeticExpression();
        checkAndIncrement(Token.SEPARATOR_TOK);
        arithmeticExpression();
    }
    public void conditional() {
        checkAndIncrement(Token.IF_TOK);
        checkAndIncrement(Token.LEFT_PAREN_TOK);
        booleanExpression();
        checkAndIncrement(Token.RIGHT_PAREN_TOK);
        checkAndIncrement(Token.THEN_TOK);
        block();
        if (getCurrentToken() == Token.ELSE_TOK) {
            System.out.println("Else...");
            increment();
            block();
        }
        increment();
    }
    public void assign() {
        System.out.println("Entering assignment statement.");
        checkAndIncrement(Token.ID_TOK);
        checkAndIncrement(Token.ASSIGN_TOK);
        arithmeticExpression();
        System.out.println("Leaving assignment statement.");
    }
    public void arithmeticExpression() {
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
    public void operation() {
        checkAndIncrement(operationTokens);
    }
    public void optionalId() {
        if (getCurrentToken() == Token.ID_TOK) {
            increment();
        }
    }
}
