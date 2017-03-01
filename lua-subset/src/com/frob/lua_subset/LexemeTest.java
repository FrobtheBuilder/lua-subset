package com.frob.lua_subset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.*;

/**
 * A utility for matching character sequences to lexeme types.
 */
public class LexemeTest {
    HashMap<LexemeType, Pattern> tests; //Hashmap for lexemes defined by regex patterns
    HashMap<LexemeType, String> literals; //Hashmap for lexemes defined by fixed strings
    
    private void addTest(LexemeType type, String pattern) {
        tests.put(type, Pattern.compile(pattern));
    }
    private void addLit(LexemeType type, String literal) {
        literals.put(type, literal);
    }
    
    /**
     * Constructor sets up the associations between lexeme types and their acceptable patterns.
     */
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
    
    /**
     * Get a list of possible lexeme matches from a fragment. Matches with regex or as the beginning
     * of a literal pattern.
     * @param fragment character sequence for which to find possible lexeme matches
     * @return List of possibilities
     */
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
    
    /**
     * Tests more strictly to break the tie in the case where a fragment is finalized
     * with more than one possible candidate.
     * @param candidate The candidate string to test
     * @return The final decision on which lexeme type the candidate is
     */
    public LexemeType strictMatch(String candidate) {
        for (Map.Entry<LexemeType, String> e : literals.entrySet()) {
            if (e.getValue().equals(candidate)) {
                return e.getKey();
            }
        }
        for (Map.Entry<LexemeType, Pattern> e : tests.entrySet()) {
            Matcher m = e.getValue().matcher(candidate);
            if (m.find()) {
                return e.getKey();
            }
        }
        return LexemeType.EOS_TOK; //fallback, this should never happen.
    }
}
