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
        addTest(LexemeType.ID_TOK, "^[a-z]$");
    }
    public ArrayList<LexemeType> getPossibilities(String fragment) {
        ArrayList<LexemeType> possibilities = new ArrayList<>();
        literals.forEach((k, v) -> {
            if (v.startsWith(fragment)) {
                possibilities.add(k);
            }
        });
        tests.forEach((k, v) -> {
            Matcher m = v.matcher(fragment);
            if (m.find()) {
                possibilities.add(k);
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
