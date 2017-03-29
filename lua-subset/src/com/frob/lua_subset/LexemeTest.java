package com.frob.lua_subset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

/**
 * A utility for matching character sequences to lexeme types.
 */
public class LexemeTest {
    HashMap<Token, Pattern> tests; //Hashmap for lexemes defined by regex patterns
    HashMap<Token, String> literals; //Hashmap for lexemes defined by fixed strings
    
    private void addTest(Token type, String pattern) {
        tests.put(type, Pattern.compile(pattern));
    }
    private void addLit(Token type, String literal) {
        literals.put(type, literal);
    }
    
    /**
     * Constructor sets up the associations between lexeme types and their acceptable patterns.
     */
    public LexemeTest() {
        tests = new HashMap<>();
        literals = new HashMap<>();
        addLit(Token.FUNCTION_TOK, "function");
        addLit(Token.ADD_TOK, "+");
        addLit(Token.LEFT_PAREN_TOK, "(");
        addLit(Token.RIGHT_PAREN_TOK, ")");
        addLit(Token.END_TOK, "end");
        addLit(Token.ASSIGN_TOK, "=");
        addLit(Token.IF_TOK, "if");
        addLit(Token.ELSE_TOK, "else");
        addLit(Token.THEN_TOK, "then");
        addLit(Token.EQ_TOK, "==");
        addLit(Token.GT_TOK, ">");
        addLit(Token.LT_TOK, "<");
        addLit(Token.GE_TOK, ">=");
        addLit(Token.LE_TOK, "<=");
        addLit(Token.NE_TOK, "<>");
        addLit(Token.DO_TOK, "do");
        addLit(Token.PRINT_TOK, "print");
        addLit(Token.REPEAT_TOK, "repeat");
        addLit(Token.UNTIL_TOK, "until");
        addLit(Token.WHILE_TOK, "while");
        addLit(Token.ADD_TOK, "+");
        addLit(Token.SUB_TOK, "-");
        addLit(Token.MUL_TOK, "*");
        addLit(Token.DIV_TOK, "/");
        addLit(Token.EOS_TOK, ";");
        addLit(Token.SEPARATOR_TOK, ",");

        addTest(Token.LITERAL_INTEGER_TOK, "^[0-9]+$");
        addTest(Token.ID_TOK, "^[a-z]$");
    }
    
    /**
     * Get a list of possible lexeme matches from a fragment. Matches with regex or as the beginning
     * of a literal pattern.
     * @param fragment character sequence for which to find possible lexeme matches
     * @return List of possibilities
     */
    public ArrayList<Token> getPossibilities(String fragment) {
        ArrayList<Token> possibilities = new ArrayList<>();
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
    public Token strictMatch(String candidate) {
        for (Map.Entry<Token, String> e : literals.entrySet()) {
            if (e.getValue().equals(candidate)) {
                return e.getKey();
            }
        }
        for (Map.Entry<Token, Pattern> e : tests.entrySet()) {
            Matcher m = e.getValue().matcher(candidate);
            if (m.find()) {
                return e.getKey();
            }
        }
        return Token.EOS_TOK; //fallback, this should never happen.
    }
}
