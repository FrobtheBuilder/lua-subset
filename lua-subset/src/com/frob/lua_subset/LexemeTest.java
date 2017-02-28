package com.frob.lua_subset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.*;

/**
 * Created by Frob on 2/27/2017.
 */
public class LexemeTest {
    HashMap<LexemeType, Pattern> tests;
    HashMap<LexemeType, String> literals;
    private void addTest(LexemeType type, String pattern) {
        tests.put(type, Pattern.compile(pattern));
    }
    private void addLit(LexemeType type, String literal) {
        literals.put(type, literal);
    }
    public LexemeTest() {
        tests = new HashMap<>();
        literals = new HashMap<>();
        addLit(LexemeType.FUNCTION_TOK, "function");
        addLit(LexemeType.ADD_TOK, "+");
        addLit(LexemeType.LEFT_PAREN_TOK, "(");
        addLit(LexemeType.RIGHT_PAREN_TOK, ")");
        addLit(LexemeType.END_TOK, "end");
        addLit(LexemeType.ASSIGN_TOK, "=");
        addLit(LexemeType.IF_TOK, "if");
        addLit(LexemeType.ELSE_TOK, "else");
        addLit(LexemeType.THEN_TOK, "then");
        addLit(LexemeType.EQ_TOK, "==");
        addLit(LexemeType.GT_TOK, ">");
        addLit(LexemeType.LT_TOK, "<");
        addLit(LexemeType.GE_TOK, ">=");
        addLit(LexemeType.LE_TOK, "<=");
        addLit(LexemeType.NE_TOK, "<>");
        addLit(LexemeType.DO_TOK, "do");
        addLit(LexemeType.PRINT_TOK, "print");
        addLit(LexemeType.REPEAT_TOK, "repeat");
        addLit(LexemeType.UNTIL_TOK, "until");
        addLit(LexemeType.WHILE_TOK, "while");
        addLit(LexemeType.ADD_TOK, "+");
        addLit(LexemeType.SUB_TOK, "-");
        addLit(LexemeType.MUL_TOK, "*");
        addLit(LexemeType.DIV_TOK, "/");
        addLit(LexemeType.EOS_TOK, ";");

        addTest(LexemeType.LITERAL_INTEGER_TOK, "^[0-9]+$");
        addTest(LexemeType.ID_TOK, "^[a-z]$");
    }
    public ArrayList<LexemeType> getPossibilities(String fragment) {
        ArrayList<LexemeType> possibilities = new ArrayList<>();
        literals.forEach((type, literal) -> {
            if (literal.startsWith(fragment)) {
                possibilities.add(type);
            }
        });
        tests.forEach((type, pattern) -> {
            Matcher m = pattern.matcher(fragment);
            if (m.find()) {
                possibilities.add(type);
            }
        });
        return possibilities;
    }
    /*
    static HashMap<LexemeType, Function<String, Boolean>> getTests() {
        HashMap<LexemeType, Function<String, Boolean>> tests = new HashMap<>();
        tests.put(LexemeType.FUNCTION_TOK, str -> {
            return str.equals("function");
        });
        return tests;
    }
    */
}
